package com.huimi.common.entity.dtgrid;

import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryUtils {
    public static void parseDtGridSql(DtGrid dtGrid) throws Exception {
        //生成带占位符的hql
        String sql = getFastQuerySql(dtGrid.getFastQueryParameters(), dtGrid.getNcColumnsTypeList());
        dtGrid.setWhereSql(sql);
        //生成排序hql
        String sortHql = QueryUtils.getAdvanceQuerySortSql(dtGrid.getAdvanceQuerySorts());
        dtGrid.setSortSql(sortHql);
    }

    /**
     * 获取快速查询的条件SQL
     *
     * @param params 快速查询参数
     * @return 条件SQL
     * @throws Exception
     */
    public static String getFastQuerySql(Map<String, Object> params, HashMap<String, String> columnsTypeList) throws Exception {
        //如果传递的条件参数为空则返回空字符串
        if (params == null || params.isEmpty()) {
            return "";
        }
        //定义条件SQL
        StringBuffer conditionSql = new StringBuffer();
        //遍历参数，拼接SQL
        for (String key : params.keySet()) {
            if ("".equals(MapUtils.getString(params, key, "").trim())) {
                continue;
            }

            if (key.indexOf("_") != -1 && !key.endsWith("_format")) {

                // 适用于模糊查询中多参数组合
                if (key.contains("_or_")) {
                    String[] orConditions = key.split("_or_");
                    StringBuffer orConditionSql = new StringBuffer();
                    for (int i = 0; i < orConditions.length; i++) {
                        String subKey = orConditions[i];
                        String field = subKey.substring(subKey.indexOf("_") + 1, subKey.length());

                        String fieldUnderline;
                        // 处理关联查询
                        if (field.contains(".")) {
                            fieldUnderline = field.substring(0, field.indexOf(".") + 1) + camelToUnderline(field.substring(field.indexOf(".") + 1));
                        } else {
                            fieldUnderline = "t." + camelToUnderline(field);
                        }

                        if (i != 0) {
                            orConditionSql.append(" or ");
                        }
                        orConditionSql.append(fieldUnderline).append(" like ");
                        orConditionSql.append("'%" + MapUtils.getString(params, key) + "%'");
                    }
                    conditionSql.append(" and (" + orConditionSql.toString() + ") ");
                    continue;
                }

                String field = key.substring(key.indexOf("_") + 1, key.length());
                String fieldUnderline;
                // 处理关联查询
                if (field.contains(".")) {
                    fieldUnderline = field.substring(0, field.indexOf(".") + 1) + camelToUnderline(field.substring(field.indexOf(".") + 1));
                } else {
                    fieldUnderline = "t." + camelToUnderline(field);
                }

                // equal
                if (key.startsWith("eq_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" = ");
                    parseValue(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }

                // not equal
                if (key.startsWith("ne_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" <> ");
                    parseValue(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }

                // like
                if (key.startsWith("lk_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" like ");
                    conditionSql.append("'%" + MapUtils.getString(params, key) + "%'");
                    continue;
                }

                // left like
                if (key.startsWith("ll_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" like ");
                    conditionSql.append("'" + MapUtils.getString(params, key) + "%'");
                    continue;
                }

                // not like
                if (key.startsWith("nlk_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" not like ");
                    conditionSql.append("'%" + MapUtils.getString(params, key) + "%'");
                    continue;
                }

                // great then
                if (key.startsWith("gt_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" > ");
                    parseValue(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }

                // great then and equal
                if (key.startsWith("ge_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" >= ");
                    parseValue(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }

                //less then
                if (key.startsWith("lt_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" < ?");
                    parseValue(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }

                //less then and equal
                if (key.startsWith("le_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" <= ");
                    parseValue(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }

                //in
                if (key.startsWith("in_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" in  ");
                    parseValueArray(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }

                //not in
                if (key.startsWith("nin_")) {
                    conditionSql.append(" and ").append(fieldUnderline).append(" not in  ");
                    parseValueArray(conditionSql, columnsTypeList, field, MapUtils.getString(params, key));
                    continue;
                }
            }
        }
        //返回条件SQL
        return conditionSql.toString();
    }

    /**
     * 获取快速查询参数值数组表达式
     *
     * @param conditionSql
     * @param columnsTypeList
     * @param field
     * @param valueStr
     */
    private static void parseValueArray(StringBuffer conditionSql, HashMap<String, String> columnsTypeList, String field, String valueStr) {
        String str = "";
        StringBuffer sb = new StringBuffer();
        String[] vals = valueStr.split(",");
        for (String val : vals) {
            if (columnsTypeList != null && columnsTypeList.get(field) != null && !columnsTypeList.get(field).isEmpty()) {

                if (columnsTypeList.get(field).equals("int")) {
                    sb.append(Integer.parseInt(val) + ",");
                } else if (columnsTypeList.get(field).equals("BigDecimal")) {
                    sb.append(new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + ",");
                } else if (columnsTypeList.get(field).equals("long")) {
                    sb.append(Long.parseLong(valueStr) + ",");
                } else if (columnsTypeList.get(field).equals("Timestamp")) {
                    sb.append("'" + valueStr + "',");
                }

            } else {
                sb.append("'" + valueStr + "',");
            }
            str = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        }

        conditionSql.append(" (" + str + ") ");
    }

//    public static void main(String[] args) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("12,12,");
//        String str = sb.toString().substring(0, sb.toString().lastIndexOf(","));
//        System.out.println(str);
//    }

    /**
     * 获取快速查询参数值表达式
     *
     * @param conditionSql
     * @param columnsTypeList
     * @param field
     * @param valueStr
     */
    private static void parseValue(StringBuffer conditionSql, HashMap<String, String> columnsTypeList, String field, String valueStr) {
        if (columnsTypeList != null && columnsTypeList.get(field) != null && !columnsTypeList.get(field).isEmpty()) {
            if (columnsTypeList.get(field).equals("int")) {
                conditionSql.append(Integer.parseInt(valueStr));
            } else if (columnsTypeList.get(field).equals("BigDecimal")) {
                conditionSql.append(new BigDecimal(valueStr).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else if (columnsTypeList.get(field).equals("long")) {
                conditionSql.append(Long.parseLong(valueStr));
            } else if (columnsTypeList.get(field).equals("Timestamp")) {
                conditionSql.append("'" + valueStr + "'");
            }
        } else {
            conditionSql.append("'" + valueStr + "'");
        }
    }

    /**
     * 获取高级查询的排序SQL
     *
     * @param advanceQuerySorts 排序列表
     * @return 条件SQL
     * @throws Exception
     */
    public static String getAdvanceQuerySortSql(List<Sort> advanceQuerySorts) {
        //定义条件SQL
        StringBuffer sortSql = new StringBuffer();
        if (advanceQuerySorts != null && advanceQuerySorts.size() > 0) {
            //加入前置的and参数
            sortSql.append(" order by ");
            for (Sort advanceQuerySort : advanceQuerySorts) {
                //获取参数：field-字段名 logic-排序逻辑
                String field = advanceQuerySort.getField();
                String logic = advanceQuerySort.getLogic();
                //拼接SQL
                getSingleAdvanceQuerySortSql(sortSql, field, logic);
            }
            sortSql.delete(sortSql.lastIndexOf(","), sortSql.length());
        }
        //返回条件SQL
        return sortSql.toString();
    }

    /**
     * 拼接单条的高级排序SQL
     *
     * @param sortSql 排序SQL
     * @param field   字段信息
     * @param logic   逻辑符号 0-asc 1-desc
     */
    private static void getSingleAdvanceQuerySortSql(StringBuffer sortSql, String field, String logic) {
        //获取左括号内容、右括号内容、逻辑符号内容
        logic = getSortLogicContent(logic);
        //根据条件类型拼接SQL
        // 处理关联查询
        String fieldName = "";
        if (field.contains(".")) {
            fieldName = field.substring(0, field.indexOf(".") + 1) + camelToUnderline(field.substring(field.indexOf(".") + 1));
        } else {
            fieldName = "t." + camelToUnderline(field);
        }
        sortSql.append(" ").append(fieldName).append(" ").append(logic).append(",  ");
    }

    /**
     * 获取排序逻辑内容
     *
     * @param logic 逻辑码值
     * @return 逻辑内容
     */
    private static String getSortLogicContent(String logic) {
        String content = "";
        if ("1".equals(logic)) {
            content = "asc";
        } else if ("2".equals(logic)) {
            content = "desc";
        } else {
            content = "asc";
        }
        return content;
    }


    public static final char UNDERLINE = '_';

    /**
     * 字符串驼峰转下划线
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字符串下划线转驼峰
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字符串下划线转驼峰
     *
     * @param param
     * @return
     */
    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }
}



package com.huimi.common.baseMapper;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.ConditionProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 通用sql拼装方法
 * Created by Donfy on 2017/6/2.
 */
@Slf4j
public class GenericMapperProvider extends ConditionProvider {
    private static final Logger log = LoggerFactory.getLogger(GenericMapperProvider.class);
    public GenericMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String find(MappedStatement param) {
        return new SQL() {{
            SELECT("*");
            Map<String, Object> map = BeanUtil.beanToMap(param);
            FROM(BeanUtil.getProperty(param, "TABLE_NAME").toString());
            log.debug("参数:" + map.toString());
            for (String key : map.keySet()) {
                if (map.get(key) != null) WHERE(key + "=#{" + key + "}");
            }
        }}.toString();
    }


    /**
     * DtGrid分页使用
     */
    public String countBySql(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        EntityTable table = EntityHelper.getEntityTable(entityClass);
        StringBuilder sb = new StringBuilder(
                new SQL() {{
                    SELECT("count(1)");
                    FROM(table.getName() + " t");
                }}.toString()
        );
        sb.append(" WHERE 1=1 ${whereSql} ${sortSql} ");
        return sb.toString();
    }

    /**
     * DtGrid分页数据
     */
    public String findListBySql(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        EntityTable table = EntityHelper.getEntityTable(entityClass);
        StringBuilder sb = new StringBuilder(
                new SQL() {{
                    SELECT("*");
                    FROM(table.getName() + " t");
                }}.toString()
        );
        sb.append(" WHERE 1=1 ${whereSql}  ${sortSql}");
        sb.append(" limit ${nowPage}, ${pageSize} ");
        return sb.toString();
    }

    /**
     * DtGrid分页使用 本方法需要被重写
     */
    public String countBySqlJoin(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        EntityTable table = EntityHelper.getEntityTable(entityClass);
        StringBuilder sb = new StringBuilder(
                new SQL() {{
                    SELECT("count(1)");
                    FROM(table.getName() + " t");
                }}.toString()
        );
        sb.append(" WHERE 1=1 and del_flag = 0 and ${whereSql} ${sortSql} ");
        return sb.toString();
    }

    /**
     * DtGrid分页使用 本方法需要被重写
     */
    public String findListBySqlJoin(MappedStatement ms) {
        return "";
    }


    /**
     * 操作数据重复性校验
     */
    public String checkRepeatSql(MappedStatement ms) {
        return "";
    }

    public String insert(MappedStatement ms) throws Exception {
        final Class<?> entityClass = getEntityClass(ms);
        //获取表的各项属性
        EntityTable table = EntityHelper.getEntityTable(entityClass);

        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(table.getName());
        StringBuilder key = new StringBuilder("<trim suffixOverrides=\",\" >");
        StringBuilder val = new StringBuilder("<trim suffixOverrides=\",\" >");
        for (EntityColumn column : table.getEntityClassColumns()) {
            String c = column.getColumn();
            String p = column.getProperty();
            if (c.equals("id")) continue;
            if (c.equals("create_time")) continue;
            if (c.equals("del_flag")) continue;
            key.append("<if test=\"").append(p).append(" != null \">").append(c).append(",").append("</if>");
            val.append("<if test=\"").append(p).append(" != null \">").append("#{").append(p).append("},").append("</if>");
        }

        key.append("create_time").append(",");
        val.append("now(),");
        key.append("del_flag").append(",");
        val.append("0,");

        key.append("</trim>");
        val.append("</trim>");
        return sb.append("(").append(key).append(") value (").append(val).append(") ").toString();
    }

    public String dynamicSQL(Object record) {
        return "dynamicSQL";
    }

    /**
     * 分页查询使用
     */
    public String selectByRowBounds(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        return SqlHelper.selectAllColumns(entityClass) +
                SqlHelper.fromTable(entityClass, tableName(entityClass)) +
                SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()) +
                SqlHelper.orderByDefault(entityClass);
    }

    /**
     * 更新记录 用于版本控制
     */
    public String updateByIdAndVersionSelective(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append("<set>");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var6 = columnList.iterator();
        while (var6.hasNext()) {
            EntityColumn column = (EntityColumn) var6.next();
            if (!column.isId() && column.isUpdatable()) {
                sql.append(SqlHelper.getIfNotNull(null, column, column.getColumnEqualsHolder(null) + ",", this.isNotEmpty()));
            }
        }
        sql.append("</set>");
        sql.append("<where> AND id = #{id,javaType=java.lang.Integer}</where>");
        return sql.toString();
    }

    public String insertList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //获取表的各项属性
        EntityTable table = EntityHelper.getEntityTable(entityClass);

        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(table.getName());
        StringBuilder key = new StringBuilder("<trim suffixOverrides=\",\" >");
        StringBuilder val = new StringBuilder("<trim suffixOverrides=\",\" >");
        for (EntityColumn column : table.getEntityClassColumns()) {
            String c = column.getColumn();
            String p = column.getProperty();
            if (c.equals("id")) continue;
            if (c.equals("create_time")) continue;
            if (c.equals("del_flag")) continue;
            key.append(c).append(",");
            val.append("#{record.").append(p).append("},");
        }
        key.append("create_time").append(",");
        val.append("now(),");
        key.append("del_flag").append(",");
        val.append("0,");

        key.append("</trim>");
        val.append("</trim>");
        return sb.append("(").append(key).append(") values <foreach collection=\"list\" item=\"record\" separator=\",\" > (").append(val).append(") </foreach> ").toString();
    }


    public String updateByPrimaryKeySelective(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        Map<String, String> map = new HashMap<>();
        map.put("update_time", "update_time = now(),");
        sql.append(updateSetColumns(entityClass, null, true, this.isNotEmpty(), map));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }


    private static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty, Map<String, String> filterField) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var6 = columnList.iterator();
        while (var6.hasNext()) {
            EntityColumn column = (EntityColumn) var6.next();
            if (!column.isId() && column.isUpdatable() ) {
                if (filterField == null || !filterField.keySet().contains(column.getColumn())) {
                    if (notNull) {
                        sql.append(SqlHelper.getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                    } else {
                        sql.append(column.getColumnEqualsHolder(entityName)).append(",");
                    }
                } else if (filterField.get(column.getColumn()) != null) {
                    sql.append(filterField.get(column.getColumn()));
                }
            }
        }
        sql.append("</set>");
        return sql.toString();
    }

}

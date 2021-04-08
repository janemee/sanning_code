package com.huimi.common.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel导入工具类
 *
 * @author Vector
 * @create 2018-06-07 14:07
 */
public class ImportExcelUntil {

    /**
     * 拼装单个obj
     *
     * @param clazz
     * @param row
     * @return
     * @throws Exception
     */
    private static <T> T dataObj(Class<T> clazz, Row row, List<String> fields) throws Exception {
        //容器
        Map<String, Object> map = new HashMap();
        //创建实例
        T instance = null;
        //创建新的实例对象
        instance = clazz.newInstance();
        //实体字段
        String name ="";
        //实体值
        String val ="";
        //字段类型
        Class<?> type;
        String replace ="";
        //获得setter方法
        Method setMethod;
        //注意excel表格字段顺序要和obj字段顺序对齐 （如果有多余字段请另作特殊下标对应处理）
        for (int j = 0; j < fields.size(); j++) {
            name = fields.get(j);
            //获取字段的类型
            type = clazz.getDeclaredField(name).getType();
            // 首字母大写
            replace = name.substring(0, 1).toUpperCase()+ name.substring(1);
            //获得setter方法
            setMethod = clazz.getMethod("set" + replace, type);
            if (name.equals("phone") || name.equals("code")){
                row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
               val = row.getCell(j).getStringCellValue();
            }else {
                val = getVal(row.getCell(j));
            }
            if (val != null && !"".equals(val)) {
                // ---判断读取数据的类型
                if (type.isAssignableFrom(String.class)) {
                    setMethod.invoke(instance, val);
                } else if (type.isAssignableFrom(int.class)
                        || type.isAssignableFrom(Integer.class)) {
                    setMethod.invoke(instance, Integer.parseInt(val.substring(0,val.indexOf("."))));
                } else if (type.isAssignableFrom(BigDecimal.class)
                        || type.isAssignableFrom(double.class)) {
                    setMethod.invoke(instance, new BigDecimal(val));
                }
            }
        }
        return instance;
    }

    private static Object dataObj(Row row, List<String> fields) throws Exception {
        //容器
        Map<String, Object> map = new HashMap();

        //注意excel表格字段顺序要和obj字段顺序对齐 （如果有多余字段请另作特殊下标对应处理）
        String val = "";
        for (int j = 0; j < fields.size(); j++) {
            if (fields.get(j).equals("phone")){
                row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                val = row.getCell(j).getStringCellValue();
            }else {
                val = getVal(row.getCell(j));
            }
            map.put(fields.get(j), val);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 导入对象
     * @param file
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) throws Exception {
        //装载流
        //excel2007
//        OPCPackage pkg = OPCPackage.open(file.getInputStream());
//        XSSFWorkbook wb = new XSSFWorkbook(pkg);
//        XSSFSheet sheet = wb.getSheetAt(0);
        //excel2003
//        POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
//        HSSFWorkbook hw = new HSSFWorkbook(fs);
        //获取第一个sheet页
//        HSSFSheet sheet = wb.getSheetAt(0);

        Workbook wb = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        //容器
        List<T> ret = new ArrayList();

        // 字段行
        List<String> fields = new ArrayList<>();
        Row fieldsRow = sheet.getRow(1);
        int n = 0;
        Cell cell = fieldsRow.getCell(0);
        if (cell != null) {
            String val = getVal(fieldsRow.getCell(0));
            while (StrUtil.isNotBlank(val)) {
                fields.add(getVal(fieldsRow.getCell(n)));
                cell = fieldsRow.getCell(++n);
                if (cell == null) {
                    break;
                } else {
                    val = getVal(fieldsRow.getCell(n));
                }
            }
            //遍历行 从下标2（第3行）开始（去除标题行和字段行）
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    //装载obj
                    ret.add(dataObj(clazz, row, fields));
                }
            }
        }

        return ret;
    }


    /**
     * 处理val（暂时只处理string和number，可以自己添加自己需要的val类型）
     *
     * @param cell
     * @return
     */
    public static String getVal(Cell cell) {
        if (cell == null) {
            return null;
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return cell.getStringCellValue();
        }
    }
}

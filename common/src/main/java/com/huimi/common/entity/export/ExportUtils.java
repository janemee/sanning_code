package com.huimi.common.entity.export;

import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.entity.export.model.Column;
import com.huimi.common.utils.DateUtil;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import org.apache.commons.collections.MapUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.*;

/**
 * 导出工具类
 *
 * @since 2018-8-22 16:25:30
 */
public class ExportUtils {

    /**
     * 导出
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param pager    GridPager对象
     * @throws Exception
     */
    public static void export(HttpServletRequest request, HttpServletResponse response, DtGrid pager) throws Exception {
//		处理导出
        if (pager.getIsExport()) {
//			获取文件名
            String fileName = "datas";
            fileName = URLEncoder.encode(pager.getExportFileName(), "UTF-8");
//			导出excel
            if ("excel".equals(pager.getExportType())) {
                ExportUtils.exportExcel(request, response, pager, pager.getExportDatas(), fileName);
                return;
            }
//			导出cvs
            if ("csv".equals(pager.getExportType())) {
                ExportUtils.exportCsv(request, response, pager, pager.getExportDatas(), fileName);
                return;
            }
//			导出txt
            if ("txt".equals(pager.getExportType())) {
                ExportUtils.exportTxt(request, response, pager, pager.getExportDatas(), fileName);
                return;
            }
//			导出pdf
//            if ("pdf".equals(pager.getExportType())) {
//                ExportUtils.exportPdf(request, response, pager, pager.getExportDatas(), fileName);
//                return;
//            }
        }
    }

    public static void exportExcel(HttpServletRequest request, HttpServletResponse response, DtGrid pager) throws Exception {
        String colNames = request.getParameter("colNames");
        String colModel = request.getParameter("colModel");
        pager.setIsExport(true);
        String[] a = colNames.split(",");
        String[] b = colModel.split(",");
        List<String> lista = Arrays.asList(a);
        List<String> listb = Arrays.asList(b);
        List<String> listNames = new ArrayList<>(lista);
        List<String> listModel = new ArrayList<>(listb);
        listNames.remove("操作");
        listModel.remove("undefined");
        listNames.toArray(a);
        listModel.toArray(b);
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            Column column = new Column();
            column.setTitle(a[i]);
            column.setId(b[i]);
            columns.add(column);

        }
        pager.setExportColumns(columns);
        String fileName = URLEncoder.encode(pager.getExportFileName() + DateUtil.dateStr3(new Date()), "UTF-8");
        ExportUtils.exportExcel(request, response, pager, pager.getExportDatas(), fileName);
    }

    public static void exportExcel(HttpServletResponse response, String colNames, String colModel, List<Map<String, Object>> datas, String fileName) throws Exception {
        String[] a = colNames.split(",");
        String[] b = colModel.split(",");
        List<String> lista = Arrays.asList(a);
        List<String> listb = Arrays.asList(b);
        List<String> listNames = new ArrayList<>(lista);
        List<String> listModel = new ArrayList<>(listb);
        listNames.remove("操作");
        listModel.remove("undefined");
        listNames.toArray(a);
        listModel.toArray(b);
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            Column column = new Column();
            column.setTitle(a[i]);
            column.setId(b[i]);
            columns.add(column);

        }
        fileName = URLEncoder.encode(fileName + DateUtil.dateStr3(new Date()), "UTF-8");
        ExportUtils.exportExcel(response, columns, datas, fileName);
    }

    /**
     * 导出Excel
     *
     * @throws Exception
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse response,
                                   DtGrid pager, List<Map<String, Object>> exportDatas, String fileName) throws Exception {
//		设置响应头
        response.setContentType("application/vnd.ms-excel");
//		执行文件写入
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
//		获取输出流
        OutputStream outputStream = response.getOutputStream();
//		定义Excel对象
        WritableWorkbook book = Workbook.createWorkbook(outputStream);
//		创建Sheet页
        WritableSheet sheet = book.createSheet("数据", 0);
//		冻结表头
        SheetSettings settings = sheet.getSettings();
        settings.setVerticalFreeze(1);
//		定义表头字体样式、表格字体样式
        WritableFont headerFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
        WritableFont bodyFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.NO_BOLD);
        WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
        WritableCellFormat bodyCellFormat = new WritableCellFormat(bodyFont);
//		设置表头样式：加边框、背景颜色为淡灰、居中样式
        headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        headerCellFormat.setBackground(Colour.PALE_BLUE);
        headerCellFormat.setAlignment(Alignment.CENTRE);
        headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		设置表格体样式：加边框、居中
        bodyCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        bodyCellFormat.setAlignment(Alignment.CENTRE);
        bodyCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		判断一下表头数组是否有数据
        if (pager.getExportColumns() != null && pager.getExportColumns().size() > 0) {
//			循环写入表头
            int seq = 0;
            for (Column column : pager.getExportColumns()) {
                sheet.addCell(new Label(seq, 0, column.getTitle(), headerCellFormat));
                seq++;
            }
//			判断表中是否有数据
            if (exportDatas != null && exportDatas.size() > 0) {
//				循环写入表中数据
                for (int i = 0; i < exportDatas.size(); i++) {
                    Map<String, Object> record = (Map<String, Object>) exportDatas.get(i);
                    seq = 0;
                    for (int j = 0; j < pager.getExportColumns().size(); j++) {
                        Column column = pager.getExportColumns().get(j);

                        String content = MapUtils.getString(record, column.getId());
                        if (pager.getExportAllData() && column.getId().contains(".")) {
                            if (getMap(record, column.getId().split("\\.")[0]) != null) {
                                content = MapUtils.getString(getMap(record, column.getId().split("\\.")[0]), column.getId().split("\\.")[1]);
                            }
                        }
//						如果内容未被处理则进行格式化
                        if (!pager.getExportDataIsProcessed()) {
                            content = GridUtils.formatContent(column, content);
                        }
                        sheet.addCell(new Label(seq, i + 1, content, bodyCellFormat));
                        seq++;
                    }
                }
            }
//			写入Excel工作表
            book.write();
//			关闭Excel工作薄对象
            book.close();
//			关闭流
            outputStream.flush();
            outputStream.close();
            outputStream = null;
        }
    }


    /**
     * 导出Excel
     *
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, List<Column> columns, List<Map<String, Object>> datas, String fileName) throws Exception {
//		设置响应头
        response.setContentType("application/vnd.ms-excel");
//		执行文件写入
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
//		获取输出流
        OutputStream outputStream = response.getOutputStream();
//		定义Excel对象
        WritableWorkbook book = Workbook.createWorkbook(outputStream);
//		创建Sheet页
        WritableSheet sheet = book.createSheet("数据", 0);
//		冻结表头
        SheetSettings settings = sheet.getSettings();
        settings.setVerticalFreeze(1);
//		定义表头字体样式、表格字体样式
        WritableFont headerFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
        WritableFont bodyFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.NO_BOLD);
        WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
        WritableCellFormat bodyCellFormat = new WritableCellFormat(bodyFont);
//		设置表头样式：加边框、背景颜色为淡灰、居中样式
        headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        headerCellFormat.setBackground(Colour.PALE_BLUE);
        headerCellFormat.setAlignment(Alignment.CENTRE);
        headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		设置表格体样式：加边框、居中
        bodyCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        bodyCellFormat.setAlignment(Alignment.CENTRE);
        bodyCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

//		判断一下表头数组是否有数据
        if (columns != null && columns.size() > 0) {
//			循环写入表头
            int seq = 0;
            for (Column column : columns) {
                sheet.addCell(new Label(seq, 0, column.getTitle(), headerCellFormat));
                seq++;
            }
//			判断表中是否有数据
            if (datas != null && datas.size() > 0) {
//				循环写入表中数据
                for (int i = 0; i < datas.size(); i++) {
                    Map<String, Object> record = (Map<String, Object>) datas.get(i);
                    seq = 0;
                    for (int j = 0; j < columns.size(); j++) {
                        Column column = columns.get(j);

                        String content = MapUtils.getString(record, column.getId());
//						如果内容未被处理则进行格式化
                        content = GridUtils.formatContent(column, content);
                        sheet.addCell(new Label(seq, i + 1, content, bodyCellFormat));
                        seq++;
                    }
                }
            }
//			写入Excel工作表
            book.write();
//			关闭Excel工作薄对象
            book.close();
//			关闭流
            outputStream.flush();
            outputStream.close();
            outputStream = null;
        }
    }

    public static Map<String, Object> getMap(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return (Map<String, Object>) answer;
            }
        }
        return null;
    }

    /**
     * 导出Csv
     *
     * @param request     请求对象
     * @param response    响应对象
     * @param pager       GridPager对象
     * @param exportDatas 导出的数据
     * @param fileName    文件名
     * @throws Exception
     */
    public static void exportCsv(HttpServletRequest request, HttpServletResponse response,
                                 DtGrid pager, List<Map<String, Object>> exportDatas, String fileName) throws Exception {
//		设置响应头
        response.setContentType("application/CSV");
//		执行文件写入
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".csv");
//		定义文件内容
        StringBuffer buffer = new StringBuffer();
//		判断一下表头数组是否有数据
        if (pager.getExportColumns() != null && pager.getExportColumns().size() > 0) {
//			循环写入表头
            for (int i = 0; i < pager.getExportColumns().size(); i++) {
                Column column = pager.getExportColumns().get(i);
                buffer.append(column.getTitle()).append(i == (pager.getExportColumns().size() - 1) ? "" : ",");
            }
//			换行
            buffer.append("\n");
//			判断表中是否有数据
            if (exportDatas != null && exportDatas.size() > 0) {
//				循环写入表中数据
                for (int i = 0; i < exportDatas.size(); i++) {
                    Map<String, Object> record = (Map<String, Object>) exportDatas.get(i);
                    for (int j = 0; j < pager.getExportColumns().size(); j++) {
                        Column column = pager.getExportColumns().get(j);
                        String content = MapUtils.getString(record, column.getId());
//						如果内容未被处理则进行格式化
                        if (!pager.getExportDataIsProcessed()) {
                            content = GridUtils.formatContent(column, content);
                        }
                        buffer.append("\"").append(content).append("\"").append(j == (pager.getExportColumns().size() - 1) ? "" : ",");
                    }
//					换行
                    buffer.append("\n");
                }
            }
        }
//		以UTF-8字符集写入
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        writer.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
        writer.write(buffer.toString());
        writer.flush();
        writer.close();
    }

    /**
     * 导出Txt
     *
     * @param request     请求对象
     * @param response    响应对象
     * @param pager       GridPager对象
     * @param exportDatas 导出的数据
     * @param fileName    文件名
     * @throws Exception
     */
    public static void exportTxt(HttpServletRequest request, HttpServletResponse response,
                                 DtGrid pager, List<Map<String, Object>> exportDatas, String fileName) throws Exception {
//		设置响应头
        response.setContentType("application/txt");
//		执行文件写入
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".txt");
//		定义文件内容
        StringBuffer buffer = new StringBuffer();
//		判断一下表头数组是否有数据
        if (pager.getExportColumns() != null && pager.getExportColumns().size() > 0) {
//			循环写入表头
            for (int i = 0; i < pager.getExportColumns().size(); i++) {
                Column column = pager.getExportColumns().get(i);
                buffer.append(column.getTitle()).append(i == (pager.getExportColumns().size() - 1) ? "" : "\t");
            }
//			换行
            buffer.append("\r\n");
//			判断表中是否有数据
            if (exportDatas != null && exportDatas.size() > 0) {
//				循环写入表中数据
                for (int i = 0; i < exportDatas.size(); i++) {
                    Map<String, Object> record = (Map<String, Object>) exportDatas.get(i);
                    for (int j = 0; j < pager.getExportColumns().size(); j++) {
                        Column column = pager.getExportColumns().get(j);
                        String content = MapUtils.getString(record, column.getId());
//						如果内容未被处理则进行格式化
                        if (!pager.getExportDataIsProcessed()) {
                            content = GridUtils.formatContent(column, content);
                        }
                        buffer.append(content).append(j == (pager.getExportColumns().size() - 1) ? "" : "\t");
                    }
//					换行
                    buffer.append("\r\n");
                }
            }
        }
//		以UTF-8字符集写入
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        writer.write(buffer.toString());
        writer.flush();
        writer.close();
    }


}
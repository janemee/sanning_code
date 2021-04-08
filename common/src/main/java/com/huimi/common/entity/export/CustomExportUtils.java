package com.huimi.common.entity.export;


import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Border;
import jxl.write.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 自定义导出
 */
public class CustomExportUtils {


    public static void exportExcel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response,
                                   String fileName) throws Exception {
//		设置响应头
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/vnd.ms-excel");
//		执行文件写入
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
//		获取输出流
        OutputStream outputStream = response.getOutputStream();
//		定义Excel对象
        WritableWorkbook book = Workbook.createWorkbook(outputStream);

        //生成名为"第一页"的工作表，参数0表示这是第一页
        WritableSheet sheet =book.createSheet("第一页",0);
        //在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
        SheetSettings settings = sheet.getSettings();
        settings.setVerticalFreeze(1);

        WritableFont font1= new WritableFont(WritableFont.TIMES,14,WritableFont.BOLD);
        WritableCellFormat format1=new WritableCellFormat(font1);
        format1.setAlignment(jxl.format.Alignment.CENTRE);//设置为居中
        sheet.setColumnView(1,20);//设置第1列宽度，6cm左右
        sheet.mergeCells(0,0,14,0);
        Label label= new Label(0,0,"交易汇总",format1);
        sheet.addCell(label);

        //定义表头字体样式、表格字体样式
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

        //将定义好的单元格添加到工作表中
        WritableCellFormat format2= new WritableCellFormat(headerFont);
        format2.setAlignment(jxl.format.Alignment.CENTRE);//设置为居中
        //设置边框
        format2.setBorder(Border.ALL,jxl.format.BorderLineStyle.NONE);

        //交易汇总
        label=new Label(0,1,"用户数量",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,1,"美黄金交易人数",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,2,"美原油交易人数",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,3,"美白银交易人数",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,4,"恒指交易人数",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,5,"小恒指交易人数",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,6,"德指交易人数",headerCellFormat);
        sheet.addCell(label);
        label=new Label(2,1,map.get("userGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,2,map.get("userCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,3,map.get("userSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,4,map.get("userHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,5,map.get("userMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,6,map.get("userDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,7,map.get("totalUser").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(0,7,"合计",headerCellFormat);
        sheet.addCell(label);
        label=new Label(3,1,"点买数量",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,1,"美黄金(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,2,"美原油(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,3,"美白银(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,4,"恒指(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,5,"小恒指(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,6,"德指(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(5,1,map.get("numBuyGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,2,map.get("numBuyCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,3,map.get("numBuySI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,4,map.get("numBuyHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,5,map.get("numBuyMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,6,map.get("numBuyDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,7,map.get("totalNumBuy").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(6,1,"点买盈亏",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,1,"美黄金(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,2,"美原油(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,3,"美白银(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,4,"恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,5,"小恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,6,"德指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(8,1,map.get("proBuyGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,2,map.get("proBuyCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,3,map.get("proBuySI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,4,map.get("proBuyHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,5,map.get("proBuyMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,6,map.get("proBuyDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,7,map.get("totalProBuy").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(9,1,"跟单数量",headerCellFormat);
        sheet.addCell(label);
        label=new Label(10,1,"美黄金(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(10,2,"美原油(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(10,3,"美白银(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(10,4,"恒指(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(10,5,"小恒指(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(10,6,"德指(手)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(11,1,map.get("numFolGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(11,2,map.get("numFolCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(11,3,map.get("numFolSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(11,4,map.get("numFolHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(11,5,map.get("numFolMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(11,6,map.get("numFolDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(11,7,map.get("totalProBuy").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(12,1,"跟单盈亏",headerCellFormat);
        sheet.addCell(label);
        label=new Label(13,1,"美黄金(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(13,2,"美原油(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(13,3,"美白银(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(13,4,"恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(13,5,"小恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(13,6,"德指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(14,1,map.get("proFolGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(14,2,map.get("proFolCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(14,3,map.get("proFolSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(14,4,map.get("proFolHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(14,5,map.get("proFolMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(14,6,map.get("proFolDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(14,7,map.get("totalProFol").toString(),bodyCellFormat);
        sheet.addCell(label);

        //收益汇总
        sheet.mergeCells(0,9,8,0);
        label= new Label(0,9,"收益汇总",format1);
        sheet.addCell(label);

        label=new Label(0,10,"穿仓",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,10,"美黄金(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,11,"美原油(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,12,"美白银(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,13,"恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,14,"小恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,15,"德指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(0,16,"合计",headerCellFormat);
        sheet.addCell(label);
        label=new Label(2,10,map.get("penGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,11,map.get("penCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,12,map.get("penSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,13,map.get("penHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,14,map.get("penMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,15,map.get("penDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,16,map.get("totalPen").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(3,10,"手续费",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,10,"美黄金(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,11,"美原油(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,12,"美白银(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,13,"恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,14,"小恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,15,"德指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(5,10,map.get("feeGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,11,map.get("feeCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,12,map.get("feeSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,13,map.get("feeHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,14,map.get("feeMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,15,map.get("feeDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,16,map.get("totalFee").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(6,10,"投资人分成",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,10,"美黄金(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,11,"美原油(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,12,"美白银(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,13,"恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,14,"小恒指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(7,15,"德指(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(8,10,map.get("divGC").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,11,map.get("divCL").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,12,map.get("divSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,13,map.get("divHSI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,14,map.get("divMHI").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,15,map.get("divDAX").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(8,16,map.get("totalDiv").toString(),bodyCellFormat);
        sheet.addCell(label);

        //其他汇总
        sheet.mergeCells(0,18,5,0);
        label= new Label(0,18,"其他汇总",format1);
        sheet.addCell(label);

        label=new Label(0,19,"推广佣金支出",headerCellFormat);
        sheet.addCell(label);
        label=new Label(0,20,"点买人可用余额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(0,21,"投资人可用余额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(0,22,"快捷充值金额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(0,23,"其它充值金额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(0,24,"用户提现笔数",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,19,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,20,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,21,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,22,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,23,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(1,24,"单位(笔)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(2,19,map.get("defray").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,20,map.get("availableBuy").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,21,map.get("availableInv").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,22,map.get("quickAmo").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,23,map.get("otherAmo").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(2,24,map.get("cashNum").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(3,19,"积分抵扣",headerCellFormat);
        sheet.addCell(label);
        label=new Label(3,20,"点买人冻结余额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(3,21,"投资人冻结余额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(3,22,"支付宝充值金额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(3,23,"用户提现金额",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,19,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,20,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,21,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,22,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(4,23,"单位(元)",headerCellFormat);
        sheet.addCell(label);
        label=new Label(5,19,"0",bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,20,map.get("freezeBuy").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,21,map.get("freezeInv").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,22,map.get("baoAmo").toString(),bodyCellFormat);
        sheet.addCell(label);
        label=new Label(5,23,map.get("cashAmo").toString(),bodyCellFormat);
        sheet.addCell(label);

//			写入Excel工作表
            book.write();
//			关闭Excel工作薄对象
            book.close();
//			关闭流
            outputStream.flush();
            outputStream.close();
            outputStream = null;
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

}

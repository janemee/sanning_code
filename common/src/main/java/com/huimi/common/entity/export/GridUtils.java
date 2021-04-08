package com.huimi.common.entity.export;

import com.huimi.common.entity.export.model.Column;
import org.apache.commons.collections.MapUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 导出工具类
 * @since 2014-8-22 16:25:30
 */
public class GridUtils {


    /**
     * 格式化日期
     *
     * @param column
     * @param content
     * @return
     * @throws Exception
     */
    public static String formatContent(Column column, String content) {
        try {
            if (content == null || "".equals(content)) {
                return "";
            }
//			处理码表
            if (column.getCodeTable() != null) {
                if (column.getCodeTable().containsKey(content)) {
                    return MapUtils.getString(column.getCodeTable(), content);
                }
            }
//			处理日期、数字的默认情况
            if ("date".equalsIgnoreCase(column.getType()) && column.getFormat() != null && !"".equals(column.getFormat())) {
                if (column.getOtype() != null && !"".equals(column.getOtype())) {
                    if ("time_stamp_s".equals(column.getOtype())) {
                        SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
                        Date date = new Date(Integer.parseInt(content) * 1000);
                        return sdf.format(date);
                    } else if ("time_stamp_ms".equals(column.getOtype())) {
                        SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
                        Date date = new Date(Integer.parseInt(content));
                        return sdf.format(date);
                    } else if ("string".equals(column.getOtype())) {
                        if (column.getOformat() != null && !"".equals(column.getOformat())) {
                            SimpleDateFormat osdf = new SimpleDateFormat(column.getOformat());
                            SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
                            Date date = osdf.parse(content);
                            return sdf.format(date);
                        }
                    }
                } else {
//                    SimpleDateFormat SDF = new SimpleDateFormat(column.getFormat() == null ? "yyy-MM-dd HH:mm:ss" : column.getFormat());
//                    Date date = new Date(Long.parseLong(content));
//                    return SDF.format(date);
                    return content;
                }
            } else if ("number".equalsIgnoreCase(column.getType()) && !"".equals(column.getFormat())) {
                DecimalFormat df = new DecimalFormat(column.getFormat() == null ? "##0.00" : column.getFormat());
                content = df.format(Double.parseDouble(content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}
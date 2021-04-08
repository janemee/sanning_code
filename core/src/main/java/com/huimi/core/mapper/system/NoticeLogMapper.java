package com.huimi.core.mapper.system;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.model.system.NoticeLogModel1;
import com.huimi.core.model.system.ShopNoticeVO;
import com.huimi.core.po.system.NoticeLog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 交易手续费率模板相关Dao接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Repository
public interface NoticeLogMapper extends GenericMapper<NoticeLog, Integer> {


    /**
     * 获取用户站内信详情列表
     * 如果是订单类型2,则获取订单的第一个详情detail并且获取其中的goodsId,然后查询goods表获取goodsid,icon,title, content(描述)
     * <p>
     * todo sys_notice_log 相关条件加索引
     * todo shop_order_detail 相关条件加索引
     *
     * @param params
     * @return
     */
    @Select(" select " +
            " id, receiver_id user_id, tx_type, title, content, tx_id, create_time," +
            " case tx_type " +
            " when '2' then " +
            "       (select " +
            "           concat(ifnull(goods.title,'\"\"'), ';', ifnull(goods.content,'\"\"'), ';', ifnull(goods.icon,'\"\"'), ';') " +
            "        from " +
            "           shop_order_detail detail " +
            "       left join shop_goods goods on goods.id = detail.goods_id " +
            "       where " +
            "           detail.order_id = tx_id limit 1) " +
            " end tx_info " +
            " from " +
            " sys_notice_log t " +
            " where " +
            " receiver_id = #{userId} " +
            " and receiver_type = 1 " +
            " and type = 3 " +
            " and del_flag = 0 " +
            " order by state asc, id desc " +
            " limit #{startPos}, #{pageSize} ")
    List<NoticeLogModel1> getLetterList(Map<String, Object> params);

    /**
     * 获取用户站内信总数量
     *
     * @param params
     * @return
     */
    @Select(" select " +
            " count(1) " +
            " from " +
            " sys_notice_log t " +
            " where " +
            " receiver_id = #{userId} " +
            " and receiver_type = 1 " +
            " and type = 3 " +
            " and del_flag = 0 " +
            " limit 1 ")
    Long countList(Map<String, Object> params);

    @Select(" select " +
            " id, receiver_id shop_id, title, content, create_time " +
            " from sys_notice_log t " +
            " where " +
            " receiver_id = #{shopId} " +
            " and receiver_type = 3 " +
            " and type = 3 " +
            " and del_flag = 0 " +
            " limit #{startPos}, #{pageSize} ")
    List<ShopNoticeVO> getShopLetterListForAdmin(Map<String, Object> params);

    @Select(" select " +
            " count(*) " +
            " from sys_notice_log t " +
            " where " +
            " receiver_id = #{shopId} " +
            " and receiver_type = 3 " +
            " and del_flag = 0 " +
            " limit 1 ")
    Long countShopLetterListForAdmin(Map<String, Object> params);

    @Select(" select " +
            " * " +
            " from sys_notice_log  " +
            " where " +
            " receiver_id = #{shopId} " +
            " and receiver_type = 3 " +
            " and del_flag = 0 order by create_time desc" +
            " limit #{startPos}, #{pageSize}")
    ArrayList<NoticeLog> queryAll(Map<String, Object> params);

    @Select(" select " +
            " count(*) " +
            " from sys_notice_log  " +
            " where " +
            " receiver_id = #{shopId} " +
            " and receiver_type = 3 " +
            " and del_flag = 0 " +
            " limit 1")
    Long countQueryAll(Map<String, Object> params);

}

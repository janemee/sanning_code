package com.huimi.core.req;

import lombok.Data;

/**
 * 分页参数
 */
@Data
public class BasePageReq {

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 用户uuid
     */
    private String userUuid;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 当前页数
     */
    private Integer rows = 1;
    /**
     * 数据条数
     */
    private Integer page = 20;
    /**
     * 开始条数
     */
    private Integer nowPage = (rows - 1) * page;
    /**
     * 结束条数
     */
    private Integer pageSize = rows * page;

    /**
     * 默认条件
     */
    private Integer delFlag = 0;
    /**
     * 查询条件
     *
     * @return
     */
    private String search_val;

    public Integer getStartLimit() {
        return (rows - 1) * page;
    }

    public Integer getEndLimit() {

        return rows * page;
    }
}

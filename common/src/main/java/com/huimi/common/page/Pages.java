package com.huimi.common.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.function.Consumer;

/**
 * 分页返回对象
 *
 * @param <T>
 */
@Data
public class Pages<T> implements java.io.Serializable {

    @ApiModelProperty(value = "当前第几页")
    private int page; //当前第几页
    @ApiModelProperty(value = "系着页最大条件")
    private int size;//系着页最大条件
    @ApiModelProperty(value = "分页列表")
    private List<T> rows;//分页列表
    @ApiModelProperty(value = "记录总数")
    private long total;//记录总数
    @ApiModelProperty(value = "总页数")
    private long totalPage; //总页数


    /**
     * @param list    返回数据
     * @param total_  数据总量
     * @param offset_ 当前第几页
     * @param length_ 每页数量
     */
    public Pages(List<T> list, long total_, int offset_, int length_) {
        this.rows = list;
        this.total = total_;
        this.page = offset_;
        this.size = length_;

        this.totalPage = total_ / length_;
        if (total_ % length_ > 0) {
            this.totalPage++;
        }

    }

    public Pages() {

    }

    /**
     * 格式化返回对象
     *
     * @param consumer
     */
    public void formatRows(Consumer<List<T>> consumer) {
        if (rows != null) consumer.accept(rows);
    }

}

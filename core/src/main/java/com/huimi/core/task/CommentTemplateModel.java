package com.huimi.core.task;

import cn.hutool.http.HtmlUtil;
import com.huimi.common.tools.StringUtil;
import lombok.Data;

/**
 * create by lja on 2020/7/30 14:57
 */
@Data
public class CommentTemplateModel {
    /**
     * 话术名称
     */
    private String name;
    /**
     * 转发话术
     */
    private String[] turn;
    /**
     * 评论话术
     */
    private String[] comment;
    /**
     * 私信话术
     */
    private String[] letter;
    /**
     * 直播话术
     */
    private String[] live;


    public void setTurn(String turn) {
        if (StringUtil.isNotBlank(turn)) {
            turn = turn.replaceAll("\n", "");
            turn = turn.replaceAll("\r", "");
            this.turn = turn.split(",");
        }
    }

    public void setComment(String comment) {
        if (StringUtil.isNotBlank(comment)) {
            comment = comment.replaceAll("\n", "");
            comment = comment.replaceAll("\r", "");
            this.comment = comment.split(",");
        }
    }

    public void setLetter(String letter) {
        if (StringUtil.isNotBlank(letter)) {
            letter = letter.replaceAll("\r", "");
            letter = letter.replaceAll("\n", "");
            this.letter = letter.split(",");
        }
    }

    public void setLive(String live) {
        if (StringUtil.isNotBlank(live)) {
            live = live.replaceAll("\n", "");
            live = live.replaceAll("\r ", "");
            this.live = live.split(",");
        }
    }
}

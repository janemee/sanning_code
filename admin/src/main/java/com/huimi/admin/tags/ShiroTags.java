package com.huimi.admin.tags;

import freemarker.template.SimpleHash;

/**
 * ShiroTags
 *
 * @author liweidong
 * @date 2017年03月05日 12:35
 */
public class ShiroTags extends SimpleHash {

    public ShiroTags() {
        put("hasPermission", new HasPermissionTag());
    }
}

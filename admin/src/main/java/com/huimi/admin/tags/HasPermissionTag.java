package com.huimi.admin.tags;

/**
 * HasPermissionTag
 *
 * @author liweidong
 * @date 2017年03月05日 12:38
 */
public class HasPermissionTag extends PermissionTag {
    protected boolean showTagBody(String p) {
        return isPermitted(p);
    }
}

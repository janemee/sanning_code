package com.huimi.admin.controller.upload;

import java.util.Comparator;
import java.util.Hashtable;

/**
 * create by 张保林 on 2020/1/11 17:38
 */
public class NameComparator implements Comparator {
    public int compare(Object a, Object b) {
        Hashtable hashA = (Hashtable) a;
        Hashtable hashB = (Hashtable) b;
        if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
            return -1;
        } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
            return 1;
        } else {
            return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
        }
    }
}

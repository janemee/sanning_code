package com.huimi.common.mybatis;

/**
 * @author Donfy
 * 2018/10/23
 */
public class CodeEnumUtil {
    public static <E extends Enum<?> & BaseEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) return e;
        }
        return null;
    }

}

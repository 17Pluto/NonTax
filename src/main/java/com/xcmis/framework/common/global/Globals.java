package com.xcmis.framework.common.global;

/**
 * 
 * 
 */
public class Globals {

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";



    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_FAILURE = "登录失败";

    public static final String OP_SUCCESS = "操作成功";
    public static final String OP_FAILURE = "操作失败";


    public static final String DEL_TREE_FAILURE = "该节点下有子节点,删除失败!";


    public static final int SUCCESS_CODE = 1;
    public static final int FAILED_CODE = 0;

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Throwable getOriginException(Throwable e){
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }

}
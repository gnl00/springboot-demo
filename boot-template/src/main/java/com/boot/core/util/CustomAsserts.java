package com.boot.core.util;


import com.boot.core.exception.CustomAssertException;

/**
 * CustomAsserts
 *
 * @author lgn
 * @date 2021-11-15 17:25
 */

public class CustomAsserts {

    public static void assertTrue(boolean condition, String returnMsg) {
        assertTrue(condition, returnMsg, returnMsg);
    }

    public static void assertTrue(boolean condition, String errorMsg, String returnMsg) {
        if (!condition) {
            final StackTraceElement methodCaller = Thread.currentThread().getStackTrace()[2];
            StringBuffer logMessage = new StringBuffer();
            logMessage.append("\n Exception:{");
            logMessage.append("\n\tServices Name: " + methodCaller.getClassName());
            logMessage.append("\n\tMethod Name: " + methodCaller.getMethodName());
            logMessage.append("\n\tLine Num: " + methodCaller.getLineNumber());
            logMessage.append("\n\tService Message: " + errorMsg);
            logMessage.append("\n}\n");
            throw new CustomAssertException(returnMsg);
        }
    }

}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.support;

import com.global.framework.dbutils.support.DAOErrorCode;
import java.text.MessageFormat;
import org.apache.commons.lang.StringUtils;

public class DAOException extends RuntimeException {
    private static final long serialVersionUID = -3557798839576777848L;

    public DAOException(String errorCode) {
        super(getErrorMessage(errorCode));
    }

    public DAOException(Throwable throwable) {
        super(throwable);
    }

    public DAOException(String errorCode, Throwable ex) {
        super(getErrorMessage(errorCode), ex);
    }

    public DAOException(String errorCode, String[] args) {
        super(getErrorMessage(errorCode, args));
    }

    public DAOException(String errorCode, Throwable ex, String[] args) {
        super(getErrorMessage(errorCode, args), ex);
    }

    private static String getErrorMessage(String errorCode) {
        String message = DAOErrorCode.getErrorMsgByCode(errorCode);
        if(StringUtils.isBlank(message)) {
            return errorCode;
        } else {
            message = message.replaceAll("\\[", "").replaceAll("\\]", "");
            message = MessageFormat.format(message, new Object[]{""});
            return errorCode + ":" + message;
        }
    }

    private static String getErrorMessage(String errorCode, Object[] args) {
        String message = DAOErrorCode.getErrorMsgByCode(errorCode);
        if(StringUtils.isBlank(message)) {
            return errorCode;
        } else {
            if(args != null && args.length != 0) {
                message = MessageFormat.format(message, args);
            } else {
                message = message.replaceAll("\\[", "").replaceAll("\\]", "");
                message = MessageFormat.format(message, new Object[]{""});
            }

            return errorCode + ":" + message;
        }
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.global.framework.xmlbeans.bean;

public class DataCheckException extends Exception {
    private static final long serialVersionUID = 1758425347009967464L;
    private String code;
    private String reason;

    public DataCheckException(String code, String reason) {
        super(code + ":" + reason);
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return this.code;
    }

    public String getReason() {
        return this.reason;
    }
}

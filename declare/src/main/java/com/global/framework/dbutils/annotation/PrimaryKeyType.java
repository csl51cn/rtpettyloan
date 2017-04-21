//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.annotation;

public interface PrimaryKeyType {
    String SINGLE = "Single";
    String COMBINE = "Combine";
    String NONE = "None";

    public static enum PrimaryKeyTypeEnum {
        Single("Single", "单个主键"),
        Combine("Combine", "联合主键"),
        None("None", "无主键");

        private String code;
        private String name;

        private PrimaryKeyTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static boolean isCheckTypeExist(String code) {
            PrimaryKeyType.PrimaryKeyTypeEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                PrimaryKeyType.PrimaryKeyTypeEnum pkType = arr$[i$];
                if(pkType.getCode().equals(code)) {
                    return true;
                }
            }

            return false;
        }
    }
}

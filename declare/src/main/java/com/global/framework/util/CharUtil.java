//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.util;

import org.apache.commons.lang.StringUtils;

import java.lang.Character.UnicodeBlock;
import java.util.regex.Pattern;

public class CharUtil {
    public CharUtil() {
    }

    public static void main(String[] args) {
        String[] strArr = new String[]{"www.micmiu.com", "!@#$%^&*()_+{}[]|\"\'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊", "やめて", "韩佳人", "???"};
        String[] arr$ = strArr;
        int len$ = strArr.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String str = arr$[i$];
            System.out.println("===========> 测试字符串：" + str);
            System.out.println("正则判断结果：" + isChineseByREG(str) + " -- " + isChineseByName(str));
            System.out.println("Unicode判断结果 ：" + isChinese(str));
            System.out.println("详细判断列表：");
            char[] ch = str.toCharArray();

            for(int i = 0; i < ch.length; ++i) {
                char c = ch[i];
                System.out.println(c + " --> " + (isChinese(c)?"是":"否"));
            }
        }

        System.out.println("23中国".length());
        System.out.println(getChineseStrLen("23中国Ck陈"));
    }

    private static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();

        for(int i = 0; i < ch.length; ++i) {
            char c = ch[i];
            if(isChinese(c)) {
                return true;
            }
        }

        return false;
    }

    public static int getChineseStrLen(String str) {
        int len = 0;
        if(StringUtils.isNotBlank(str)) {
            char[] ch = str.toCharArray();

            for(int i = 0; i < ch.length; ++i) {
                char c = ch[i];
                if(isChinese(c)) {
                    len += 2;
                } else {
                    ++len;
                }
            }
        }

        return len;
    }

    public static boolean isChineseByREG(String str) {
        if(str == null) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
            return pattern.matcher(str.trim()).find();
        }
    }

    public static boolean isChineseByName(String str) {
        if(str == null) {
            return false;
        } else {
            String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
            Pattern pattern = Pattern.compile(reg);
            return pattern.matcher(str.trim()).find();
        }
    }
}

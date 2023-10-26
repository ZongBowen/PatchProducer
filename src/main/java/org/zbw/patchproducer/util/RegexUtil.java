package org.zbw.patchproducer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static final String DEFECT_PREFIX_PATTERN = "[Bb]ug\\s?\\d{6,7}";
    public static final String ILINK_PREFIX_PATTERN = "#\\d{6,7}|i[Ll]ink\\s?\\d{6,7}";
    public static final String BACKLOG_PREFIX_PATTERN = "产品积压工作项\\s?\\d{6,7}";
    public static final String DEFECT_CODE_PATTERN = "(?<=[Bb]ug\\s?)\\d{6,7}";
    public static final String ILINK_CODE_PATTERN = "(?<=#)\\d{6,7}|(?<=|i[Ll]ink\\s)\\d{6,7}";
    public static final String BACKLOG_CODE_PATTERN = "(?<=产品积压工作项\\s?)\\d{6,7}";
    public static final String DEFECT_MSG_PATTERN = "(?<=[Bb]ug\\s?\\d{6,7}[:：]\\s?).*";
    public static final String ILINK_MSG_PATTERN = "(?<=i[Ll]ink\\s?\\d{6,7}\\s?).*|(?<=#\\d{6,7}\\s?).*";
    public static final String BACKLOG_MSG_PATTERN = "(?<=产品积压工作项\\s?\\d{6,7}[:：]\\s?).*";
    public static final String DEFECT_PATTERN = "[Bb]ug\\s?\\d{6,7}[:：]\\s?.*?(?=[Bb]ug|[;；]|$|\\n)";
    public static final String ILINK_PATTERN = "(#|i[Ll]ink\\s?).*?(?=#|i[Ll]ink|[;；]|$|\\n)";
    public static final String BACKLOG_PATTERN = "产品积压工作项\\s?\\d{6,7}[:：]\\s?.*?(?=产品积压工作项|[;；]|$|\\n)";

    public static int getMatchCnt(String str, String pattern) {
        int cnt = 0;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            cnt++;
        }
        return cnt;
    }

    public static String getMatchStr(String str, String pattern) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if (m.find()) {
            return m.group();
        }
        return "";
    }
}

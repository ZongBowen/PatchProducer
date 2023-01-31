package org.zbw.patchproducer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static int getMatchCnt(String str, String pattern){
        int cnt = 0;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()){
            cnt++;
        }
        return cnt;
    }

    public static String getMatchStr(String str, String pattern){
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if (m.find()){
            return m.group();
        }
        return "";
    }
}

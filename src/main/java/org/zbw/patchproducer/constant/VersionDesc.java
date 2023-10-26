package org.zbw.patchproducer.constant;

import java.util.HashMap;

public class VersionDesc {
    public static final String TITLE = "补丁生成工具";
    public static final HashMap<String, String> INSTRUCTION = new HashMap<String, String>() {{
        put("v1.0.0-BETA", "1、支持根据特定时间区间内交付物自动生成补丁\n2、支持根据提交记录自动生成补丁描述");
        put("v1.0.1-BETA", "1、支持选择部分提交记录\n2、新增校验:无法解析提交内容的提交记录需人工确认");
        put("v1.0.2-BETA", "1、支持解析产品积压工作项");
        put("v1.0.3-BETA", "1、升级补丁工具版本\n2、修复数据库部分缺少文件夹问题");
        put("v1.0.4-BETA", "1、升级补丁工具版本3.5\n2、支持按照序号选择提交记录\n3、支持过滤su");
    }};
    private static final String HELP = "";
}

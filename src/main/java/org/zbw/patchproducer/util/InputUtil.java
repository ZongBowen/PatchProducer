package org.zbw.patchproducer.util;

import org.apache.commons.lang3.StringUtils;
import org.zbw.patchproducer.config.PatchProperties;
import org.zbw.patchproducer.enums.BuildPattern;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class InputUtil {
    private static String path;
    private static Date begin;
    private static Date end;
    private static final PatchProperties patchProperties = PropertyUtil.getProperties();

    public static BuildPattern getBuildPattern(Scanner scanner) {
        LogUtil.log("请选择补丁生成模式 0.基于交付物仓库生成 1.基于本地目录生成 :[0]");
        String str = scanner.nextLine();
        if (StringUtils.isBlank(str)) {
            str = "0";
        }
        return BuildPattern.forValue(str);
    }

    public static String getPatchTemp(Scanner scanner) {
        String path = "";
        while (StringUtils.isBlank(path)) {
            LogUtil.log("请输入补丁文件根目录:");
            path = scanner.nextLine();
            path = path.trim();
        }
        return path;
    }

    public static String getDeliveryPath(Scanner scanner) {
        LogUtil.log("请输入交付物Git仓库路径:[" + patchProperties.getDeliveryPath() + "]");
        path = scanner.nextLine();
        return checkPath(scanner);
    }

    public static Date getBegin(Scanner scanner) {
        LogUtil.log("请输入开始时间(yyyy-MM-dd HH:mm):[" + patchProperties.getDefaultBegin() + "]");
        String begin = scanner.nextLine();
        return checkBeginDate(scanner, begin);
    }

    public static Date getEnd(Scanner scanner) {
        LogUtil.log("请输入结束时间(yyyy-MM-dd HH:mm):[" + DateUtil.toString(new Date(), DateUtil.PATTERN) + "]");
        String end = scanner.nextLine();
        return checkEndDate(scanner, end);
    }

    public static ArrayList<String> getCommits(Scanner scanner) {
        ArrayList<String> commits = new ArrayList<>();
        while (commits.size() == 0) {
            LogUtil.log("请输入要包含的Commit,以英文逗号分割:");
            String str = scanner.nextLine();
            commits.addAll(Arrays.asList(StringUtils.split(str, ",")));
        }
        return commits;
    }

    public static String getPatchName(Scanner scanner, boolean auto) {
        String defaultName = "供应链日常补丁" + DateUtil.toString(new Date(), "yyyyMMdd");
        if (auto) {
            LogUtil.log("补丁名称: " + defaultName);
            return defaultName;
        }
        LogUtil.log("请输入补丁名称:[" + defaultName + "]");
        String patchName = scanner.nextLine();
        if (StringUtils.isBlank(patchName)) {
            patchName = defaultName;
        }
        return patchName.trim();
    }

    public static String getSolveDesc(Scanner scanner) {
        String solveDesc = "";
        while (StringUtils.isBlank(solveDesc)) {
            LogUtil.log("请输入解决问题说明:");
            solveDesc = scanner.nextLine();
            solveDesc = solveDesc.trim();
        }
        return solveDesc;
    }

    public static String getModifyDesc(Scanner scanner) {
        String modifyDesc = "";
        while (StringUtils.isBlank(modifyDesc)) {
            LogUtil.log("请输入修改功能说明:");
            modifyDesc = scanner.nextLine();
            modifyDesc = modifyDesc.trim();
        }
        return modifyDesc;
    }

    public static String getTestDesc(Scanner scanner) {
        String testDesc = "";
        while (StringUtils.isBlank(testDesc)) {
            LogUtil.log("请输入测试要点:");
            testDesc = scanner.nextLine();
            testDesc = testDesc.trim();
        }
        return testDesc;
    }

    public static boolean canBuild(Scanner scanner) {
        String canBuild = "";
        while (StringUtils.isBlank(canBuild)) {
            LogUtil.log("是否继续生成(y/n):");
            canBuild = scanner.nextLine();
            canBuild = canBuild.trim();
        }
        return canBuild.toLowerCase().equals("y");
    }

    private static String checkPath(Scanner scanner) {
        if (StringUtils.isBlank(path)) {
            path = patchProperties.getDeliveryPath();
        }
        path = path.trim();
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            LogUtil.log("交付物Git仓库路径不存在,请重新输入:");
            path = scanner.nextLine();
            return checkPath(scanner);
        }
        return path;
    }

    private static Date checkBeginDate(Scanner scanner, String date) {
        if (StringUtils.isBlank(date)) {
            date = patchProperties.getDefaultBegin();
        }
        begin = DateUtil.tryParse(date, DateUtil.PATTERN);
        if (begin == null) {
            LogUtil.log("日期格式不正确,请重新输入:");
            String newDate = scanner.nextLine();
            return checkBeginDate(scanner, newDate);
        }
        return begin;
    }

    private static Date checkEndDate(Scanner scanner, String date) {
        if (StringUtils.isBlank(date)) {
            end = new Date();
            return end;
        }
        end = DateUtil.tryParse(date, DateUtil.PATTERN);
        if (end == null) {
            LogUtil.log("日期格式不正确，请重新输入:");
            String newDate = scanner.nextLine();
            return checkEndDate(scanner, newDate);
        }
        return end;
    }
}

package org.zbw.patchproducer.util;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.archive.ArchiveFormats;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.zbw.patchproducer.config.PatchProperties;
import org.zbw.patchproducer.entity.BuildInfo;
import org.zbw.patchproducer.exception.PatchBuildRuntimeException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliveryUtil {
    private static String path;
    private static Date begin;
    private static Date end;
    private static String patchName;
    private static boolean auto;
    private static ArrayList<RevCommit> commits;
    private static HashMap<String, String> previousCommit;
    private static List<DiffEntry> diffs;
    private static ArrayList<String> diffPaths;
    private static HashMap<String, String> issues;
    private static String solveDesc;
    private static String modifyDesc;
    private static String testDesc;
    private static final PatchProperties patchProperties = PropertyUtil.getProperties();

    public static BuildInfo getDelivery(Scanner scanner) throws PatchBuildRuntimeException {
        // 交付物仓库路径
        path = InputUtil.getDeliveryPath(scanner);
        // 开始时间
        begin = InputUtil.getBegin(scanner);
        // 结束时间
        end = InputUtil.getEnd(scanner);
        // 获取日期区间内的交付物
        getDeliveryFile(scanner);
        // 输出问题列表
        getIssueLis(scanner);
        // 补丁参数
        getBuildParam(scanner);
        // 补丁名称
        patchName = InputUtil.getPatchName(scanner, auto);
        // 解决问题说明
        getSolveDesc(scanner);
        // 修改功能说明
        getModifyDesc(scanner);
        // 测试要点
        getTestDesc(scanner);
        // 构造返回值
        return getResult();
    }

    private static void getDeliveryFile(Scanner scanner) throws PatchBuildRuntimeException {
        try (Repository repository = JGitUtil.openRepository(path)) {
            try (Git git = new Git(repository)) {
                // 拉取到最新
                pull();
                // 获取日期区间内的提交记录
                getCommit(git, scanner);
                if (commits.size() == 0) {
                    throw new PatchBuildRuntimeException("未获取到任何提交记录,结束生成");
                }
                // 获取差异
                listDiff(git, scanner);
                if (diffs.size() == 0) {
                    throw new PatchBuildRuntimeException("未获取到任何差异,结束生成");
                }
                // 根据差异生成压缩包
                archive(git);
                // 解压缩到指定目录
                unZip();
            }
        } catch (IOException | GitAPIException e) {
            LogUtil.errorLog(e.getMessage());
        } finally {
            ArchiveFormats.unregisterAll();
        }
    }

    private static void getIssueLis(Scanner scanner) {
        issues = new HashMap<>(MapUtil.getCapacity(commits.size()));
        ArrayList<RevCommit> error = new ArrayList<>();
        for (RevCommit commit : commits) {
            analyseLog(commit, error);
        }
        if (error.size() > 0) {
            LogUtil.log("存在" + error.size() + "条提交记录无法解析提交内容: ");
            int serial = 1;
            for (RevCommit commit : error) {
                LogUtil.log(formatCommit(commit, serial++));
            }
            if (!InputUtil.canBuild(scanner)) {
                throw new PatchBuildRuntimeException("结束生成");
            }
        }
        LogUtil.log("缺陷编号: " + String.join(",", issues.keySet()));
        LogUtil.log("缺陷列表: ");
        for (Map.Entry<String, String> issue : issues.entrySet()) {
            LogUtil.log(issue.getKey() + ": " + issue.getValue());
        }
    }

    private static void getBuildParam(Scanner scanner) {
        LogUtil.log("是否自动生成补丁名称与补丁描述(y/n):[n]");
        String input = scanner.nextLine();
        auto = "y".equals(input) || "Y".equals(input);
    }

    private static void getSolveDesc(Scanner scanner) {
        String needModify = "n";
        if (auto) {
            int sequence = 1;
            StringBuilder sb = new StringBuilder();
            for (String issue : issues.values()) {
                sb.append(formatMsg(issue, "解决了", sequence++));
            }
            LogUtil.log("解决问题说明:\r\n" + sb);
            LogUtil.log("是否需要修改(y/n):[n]");
            needModify = scanner.nextLine();
            if (!"Y".equals(needModify) && !"y".equals(needModify)) {
                solveDesc = sb.toString();
                return;
            }
        }
        solveDesc = InputUtil.getSolveDesc(scanner);
    }

    private static void getModifyDesc(Scanner scanner) {
        String needModify = "n";
        if (auto) {
            int sequence = 1;
            StringBuilder sb = new StringBuilder();
            for (String issue : issues.values()) {
                sb.append(formatMsg(issue, "修改了", sequence++));
            }
            LogUtil.log("修改功能说明:\r\n" + sb);
            LogUtil.log("是否需要修改(y/n):[n]");
            needModify = scanner.nextLine();
            if (!"Y".equals(needModify) && !"y".equals(needModify)) {
                modifyDesc = sb.toString();
                return;
            }
        }
        modifyDesc = InputUtil.getModifyDesc(scanner);
    }

    private static void getTestDesc(Scanner scanner) {
        String needModify = "n";
        if (auto) {
            int sequence = 1;
            StringBuilder sb = new StringBuilder();
            for (String issue : issues.values()) {
                sb.append(formatMsg(issue, "测试是否解决了", sequence++));
            }
            LogUtil.log("测试要点:\r\n" + sb);
            LogUtil.log("是否需要修改(y/n):[n]");
            needModify = scanner.nextLine();
            if (!"Y".equals(needModify) && !"y".equals(needModify)) {
                testDesc = sb.toString();
                return;
            }
        }
        testDesc = InputUtil.getTestDesc(scanner);
    }

    private static BuildInfo getResult() {
        BuildInfo buildInfo = new BuildInfo();
        buildInfo.setPatchTemp(patchProperties.getDeliveryPath() + patchProperties.getTempPath());
        buildInfo.setPatchName(patchName);
        buildInfo.setIssues(issues);
        buildInfo.setSolveDesc(solveDesc);
        buildInfo.setModifyDesc(modifyDesc);
        buildInfo.setTestDesc(testDesc);
        return buildInfo;
    }

    private static void pull() {
        LogUtil.log("开始拉取...");
        String cmd = "git pull";
        try {
            Process process = Runtime.getRuntime().exec(cmd, null, new File(path));
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "GB2312");
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            while (content != null) {
                System.out.println(content);
                content = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getCommit(Git git, Scanner scanner) throws GitAPIException {
        LogUtil.log("开始获取" + DateUtil.toString(begin, DateUtil.PATTERN) + "-" + DateUtil.toString(end, DateUtil.PATTERN) + "的提交记录...");
        Iterable<RevCommit> logs = git.log().setRevFilter(CommitTimeRevFilter.between(begin, end)).call();
        commits = new ArrayList<>();
        LinkedHashMap<String, RevCommit> commitMap = new LinkedHashMap<>();
        for (RevCommit commit : logs) {
            if (commit.getFullMessage().startsWith("Merge")) {
                continue;
            }
            commitMap.put(commit.getName(), commit);
        }
        LogUtil.log("获取到" + commitMap.size() + "条提交记录: ");
        int serial = 1;
        // 序号与提交记录ID映射
        LinkedHashMap<Integer, String> commitSerial = new LinkedHashMap<>();
        for (RevCommit commit : commitMap.values()) {
            LogUtil.log(formatCommit(commit, serial));
            commitSerial.put(serial, commit.getName());
            serial += 1;
        }
        // 指定提交
        if (patchProperties.getCommitFilter()) {
            ArrayList<Integer> commitIds = InputUtil.getCommits(scanner);
            commits = new ArrayList<>(commitMap.size());
            for (Integer id : commitIds) {
                if (id == 0) {
                    commits = new ArrayList<>(commitMap.values());
                    break;
                }
                if (commitSerial.containsKey(id)) {
                    commits.add(commitMap.get(commitSerial.get(id)));
                }
            }
        } else {
            commits = new ArrayList<>(commitMap.values());
        }
    }

    private static void listDiff(Git git, Scanner scanner) throws IOException, GitAPIException {
        if (patchProperties.getCommitFilter()) {
            diffs = new ArrayList<>();
            for (RevCommit commit : commits) {
                LogUtil.log("开始获取" + commit.getParent(0).getName() + "-" + commit.getName() + "的差异...");
                List<DiffEntry> diff = git.diff()
                        .setOldTree(prepareTreeParser(git.getRepository(), commit.getParent(0).getId().getName()))
                        .setNewTree(prepareTreeParser(git.getRepository(), commit.getId().getName()))
                        .call();
                diffs.addAll(diff);
            }
        } else {
            RevCommit oldCommit = commits.get(commits.size() - 1).getParent(0);
            RevCommit newCommit = commits.get(0);
            LogUtil.log("开始获取" + oldCommit.getName() + "-" + newCommit.getName() + "的差异...");
            diffs = git.diff()
                    .setOldTree(prepareTreeParser(git.getRepository(), oldCommit.getId().getName()))
                    .setNewTree(prepareTreeParser(git.getRepository(), newCommit.getId().getName()))
                    .call();
        }
        LogUtil.log("获取到" + diffs.size() + "条差异: ");
        ArrayList<String> sus = new ArrayList<>();
        if (patchProperties.getSuFilter()) {
            sus = InputUtil.getSUs(scanner);
        }
        int serial = 1;
        diffPaths = new ArrayList<>();
        for (DiffEntry diff : diffs) {
            if (diff.getNewPath().contains("publish_df") || diff.getNewPath().contains("idp_df")) {
                continue;
            }
            boolean add = true;
            if (!sus.isEmpty()) {
                add = false;
                for (String su : sus) {
                    if (diff.getNewPath().startsWith(StringUtils.removeStart(patchProperties.getServerPath(), "/") + "/" + su)
                            || diff.getNewPath().startsWith(StringUtils.removeStart(patchProperties.getWebPath(), "/") + "/" + su)
                            || diff.getNewPath().startsWith(StringUtils.removeStart(patchProperties.getDboPath(), "/") + "/" + su)
                            || diff.getNewPath().startsWith(StringUtils.removeStart(patchProperties.getDataPath(), "/") + "/" + su)
                            || diff.getNewPath().startsWith(StringUtils.removeStart(patchProperties.getMetaDataPath(), "/") + "/" + su)
                            || diff.getNewPath().startsWith(StringUtils.removeStart(patchProperties.getIdpPath(), "/") + "/" + su)) {
                        add = true;
                        break;
                    }
                }
            }
            if (add) {
                LogUtil.log(formatDiff(diff, serial++));
                if (diff.getChangeType().equals(DiffEntry.ChangeType.ADD) || diff.getChangeType().equals(DiffEntry.ChangeType.MODIFY) || diff.getChangeType().equals(DiffEntry.ChangeType.RENAME)) {
                    diffPaths.add(diff.getNewPath());
                }
            }
        }
    }

    private static void archive(Git git) {
        File file = new File(path + patchProperties.getTempZipPath());
        FileUtil.createFolder(file.getParent());
        LogUtil.log("开始写入压缩包: " + file.getPath() + "...");
        LogUtil.log("文件列表: ");
        for (String diffPath : diffPaths) {
            LogUtil.log(diffPath);
        }
        ArchiveFormats.registerAll();
        try (OutputStream out = new FileOutputStream(file)) {
            git.archive()
                    .setTree(commits.get(0).getId())
                    .setPaths(diffPaths.toArray(new String[0]))
                    .setFormat("zip")
                    .setOutputStream(out)
                    .call();
        } catch (IOException | GitAPIException e) {
            LogUtil.errorLog(e.getMessage());
        }
    }

    private static void unZip() {
        String zipPath = path + patchProperties.getTempZipPath();
        String tempPath = path + patchProperties.getTempPath();
        // 清空文件夹
        LogUtil.log("清空文件夹:" + tempPath);
        FileUtil.clearFolder(tempPath);
        // 解压文件
        FileUtil.unZip(tempPath, zipPath);
        // 删除压缩包
        LogUtil.log("删除压缩包:" + zipPath);
        FileUtil.delete(zipPath);
    }

    private static String formatCommit(RevCommit commit, int serial) {
        return "Commit[" + serial + "]: " + commit.getName().substring(0, 8) + " " + DateUtil.toString(commit.getCommitterIdent().getWhen(), "yyyy-MM-dd HH:mm:ss") + " " + commit.getCommitterIdent().getName() + " " + commit.getShortMessage();
    }

    private static String formatDiff(DiffEntry diff, int serial) {
        return "Diff[" + serial + "]: " + diff.getChangeType() + ": " + (diff.getOldPath().equals(diff.getNewPath()) ? diff.getNewPath() : diff.getOldPath() + " -> " + diff.getNewPath());
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(repository.resolve(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());
            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }
            walk.dispose();
            return treeParser;
        }
    }

    private static void analyseLog(RevCommit commit, ArrayList<RevCommit> error) {
        // 解析Defect
        boolean hasDefect = listDefect(commit.getFullMessage());
        // 解析iLink
        boolean hasiLink = listiLink(commit.getFullMessage());
        // 解析BackLog
        boolean hasBackLog = listBackLog(commit.getFullMessage());
        if (!hasDefect && !hasiLink && !hasBackLog) {
            error.add(commit);
        }
    }

    private static boolean listDefect(String log) {
        if (RegexUtil.getMatchCnt(log, RegexUtil.DEFECT_PREFIX_PATTERN) == 1
                && RegexUtil.getMatchCnt(log, RegexUtil.ILINK_PREFIX_PATTERN) == 0
                && RegexUtil.getMatchCnt(log, RegexUtil.BACKLOG_PREFIX_PATTERN) == 0) {
            String code = getDefectCode(log);
            String msg = getDefectMsg(log);
            putIssue(code, msg);
            return true;
        }
        String pattern = RegexUtil.DEFECT_PATTERN;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(log);
        while (m.find()) {
            String defect = m.group();
            String code = getDefectCode(defect);
            String msg = getDefectMsg(defect);
            putIssue(code, msg);
        }
        return false;
    }

    private static boolean listiLink(String log) {
        if (RegexUtil.getMatchCnt(log, RegexUtil.DEFECT_PREFIX_PATTERN) == 1
                && RegexUtil.getMatchCnt(log, RegexUtil.DEFECT_PREFIX_PATTERN) == 0
                && RegexUtil.getMatchCnt(log, RegexUtil.BACKLOG_PREFIX_PATTERN) == 0) {
            String code = "iLink" + getiLinkCode(log);
            String msg = getiLinktMsg(log);
            putIssue(code, msg);
            return true;
        }
        String pattern = RegexUtil.ILINK_PATTERN;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(log);
        while (m.find()) {
            String defect = m.group();
            String code = "iLink" + getiLinkCode(defect);
            String msg = getiLinktMsg(defect);
            putIssue(code, msg);
        }
        return false;
    }

    private static boolean listBackLog(String log) {
        if (RegexUtil.getMatchCnt(log, RegexUtil.BACKLOG_PREFIX_PATTERN) == 1
                && RegexUtil.getMatchCnt(log, RegexUtil.DEFECT_PREFIX_PATTERN) == 0
                && RegexUtil.getMatchCnt(log, RegexUtil.DEFECT_PREFIX_PATTERN) == 0) {
            String code = getBackLogCode(log);
            String msg = getBackLogMsg(log);
            putIssue(code, msg);
            return true;
        }
        String pattern = RegexUtil.BACKLOG_PATTERN;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(log);
        while (m.find()) {
            String defect = m.group();
            String code = getBackLogCode(defect);
            String msg = getBackLogMsg(defect);
            putIssue(code, msg);
        }
        return false;
    }

    private static void putIssue(String code, String msg) {
        if (!issues.containsKey(code)) {
            issues.put(code, msg.trim());
        }
    }

    private static String getDefectCode(String str) {
        return RegexUtil.getMatchStr(str, RegexUtil.DEFECT_CODE_PATTERN);
    }

    private static String getDefectMsg(String str) {
        return RegexUtil.getMatchStr(str, RegexUtil.DEFECT_MSG_PATTERN).trim();
    }

    private static String getiLinkCode(String str) {
        return RegexUtil.getMatchStr(str, RegexUtil.ILINK_CODE_PATTERN);
    }

    private static String getiLinktMsg(String str) {
        String msg = RegexUtil.getMatchStr(str, RegexUtil.ILINK_MSG_PATTERN);
        return StringUtils.removeStart(StringUtils.removeEnd(msg.trim(), ")"), "(");
    }

    private static String getBackLogCode(String str) {
        return RegexUtil.getMatchStr(str, RegexUtil.BACKLOG_CODE_PATTERN);
    }

    private static String getBackLogMsg(String str) {
        return RegexUtil.getMatchStr(str, RegexUtil.BACKLOG_MSG_PATTERN).trim();
    }

    private static String formatMsg(String msg, String prefix, int sequence) {
        // 去除前后空格
        String newMsg = msg.trim();
        // 结尾句号,分号
        newMsg = StringUtils.removeEnd(newMsg, ".");
        newMsg = StringUtils.removeEnd(newMsg, "。");
        newMsg = StringUtils.removeEnd(newMsg, ";");
        newMsg = StringUtils.removeEnd(newMsg, "；");
        // 去除“请确认”
        newMsg = StringUtils.removeEnd(newMsg, "，请确认");
        newMsg = StringUtils.removeEnd(newMsg, ",请确认");
        // 增加前后缀
        newMsg = sequence + "、" + prefix + newMsg + "的问题;\n";
        return newMsg;
    }
}

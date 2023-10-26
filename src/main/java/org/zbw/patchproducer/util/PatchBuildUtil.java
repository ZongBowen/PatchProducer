package org.zbw.patchproducer.util;

import org.apache.commons.lang3.StringUtils;
import org.zbw.patchproducer.config.PatchProperties;
import org.zbw.patchproducer.entity.*;
import org.zbw.patchproducer.enums.BuildPattern;
import org.zbw.patchproducer.enums.PatchStep;

import java.io.File;
import java.util.*;

public class PatchBuildUtil {
    private static String patchId = "";
    private static String patchPath = "";
    private static BuildInfo buildInfo = null;
    private static ArrayList<FilePath> appFiles;
    private static ArrayList<FilePath> dboFiles;
    private static ArrayList<FilePath> dataFiles;
    private static ArrayList<FilePath> metaDataFiles;
    private static ArrayList<FilePath> idpFiles;
    private static final PatchProperties patchProperties = PropertyUtil.getProperties();

    public static void buildPatch() {
        Scanner scanner = new Scanner(System.in);
        // 补丁生成模式
        BuildPattern pattern = InputUtil.getBuildPattern(scanner);
        // 根据输入的交付物路径、开始期间、结束时间，获取交付物
        switch (pattern) {
            case DELIVERY:
                buildInfo = DeliveryUtil.getDelivery(scanner);
                break;
            case LOCALPATH:
                buildInfo = getBuildInfo(scanner);
                break;
            default:
                break;
        }
        // 生成补丁信息
        CloudPatchInfo patchInfo = buildPatchInfo();
        // 生成补丁文件
        writePatchFile(patchInfo);
    }

    private static CloudPatchInfo buildPatchInfo() {
        patchId = UUID.randomUUID().toString();
        CloudPatchInfo patch = new CloudPatchInfo();
        patch.setPatchBasic(buildBasic());
        patch.setPatchSteps(buildSteps());
        return patch;
    }

    private static void writePatchFile(CloudPatchInfo patchInfo) {
        LogUtil.log("开始生成补丁...");
        long start = System.currentTimeMillis();
        // 生成patch.json
        FileUtil.save(patchPath, "patch.json", JsonUtil.obj2String(patchInfo), "UTF-8");
        // 生成patch.xml
        FileUtil.save(patchPath, "patch.xml", getXML(buildPatchXML(patchInfo)), "x-UTF-16LE-BOM");
        // 生成补丁文件
        FileUtil.toZip(patchPath, patchInfo.getPatchBasic().getPatchCode() + patchInfo.getPatchBasic().getPatchName());
        long end = System.currentTimeMillis();
        LogUtil.log("生成完成，耗时: " + (end - start) + " ms");
    }

    private static CloudPatchBasic buildBasic() {
        CloudPatchBasic basic = new CloudPatchBasic();
        // ID
        basic.setPatchID(patchId);
        // 增量编号
        basic.setPatchCode(getPatchCode());
        patchPath = patchProperties.getDeliveryPath() + patchProperties.getPatchPath() + "/" + basic.getPatchCode() + "/PatchFile";
        // 增量名称
        basic.setPatchName(buildInfo.getPatchName());
        // 增量类型
        basic.setPatchType(getPatchType());
        // 增量类别
        basic.setPatchCategory(getPatchCategory());
        // su
        basic.setSu("");
        basic.setAppSus(new ArrayList<>());
        basic.setDBSus(new ArrayList<>());
        // 重要程度
        basic.setGSPPatchImportance(getPatchImportance());
        // 部署单元
        basic.setPatchDeployUnit(getPatchDeployUnit());
        // 产品系列
        basic.setPatchProduct(getPatchProduct());
        // 增量关系
        basic.setPatchDepInfo(new ArrayList<>());
        basic.setPatchReplaceInfo(null);
        basic.setSetContainPatches(null);
        // 补丁描述
        basic.setPatchNote(getPatchNote(StringUtils.removeEnd(buildInfo.getSolveDesc(), "\n"), StringUtils.removeEnd(buildInfo.getModifyDesc(), "\n")));
        // 其他信息
        basic.setPatchSize(0);
        basic.setToolVersion(patchProperties.getToolVersion());
        basic.setIsCommonChange(false);
        basic.setPublishDate(getPublishDate());
        basic.setPatchAppFiles(null);
        basic.setPatchDBFiles(null);
        return basic;
    }

    private static List<CloudPatchStep> buildSteps() {
        FileUtil.delete(patchProperties.getDeliveryPath() + patchProperties.getPatchPath());
        List<CloudPatchStep> steps = new ArrayList<>();
        // 文件操作
        CloudPatchStep appStep = buildAppStep();
        if (appStep != null) {
            steps.add(appStep);
        }
        // DBO部署
        CloudPatchStep dboStep = buildDBOStep();
        if (dboStep != null) {
            steps.add(dboStep);
        }
        // Data部署
        CloudPatchStep dataStep = buildDataStep();
        if (dataStep != null) {
            steps.add(dataStep);
        }
        // 元数据导入
        CloudPatchStep metaDataStep = buildMetaDataStep();
        if (metaDataStep != null) {
            steps.add(metaDataStep);
        }
        // IDP导入
        CloudPatchStep idpStep = buildIDPStep();
        if (idpStep != null) {
            steps.add(idpStep);
        }
        return steps;
    }

    private static CloudPatchStep buildAppStep() {
        CloudPatchStep step = new CloudPatchStep();
        step.setOrder(1);
        step.setStepType(0);
        step.setPatchAction(getPatchAction(PatchStep.APP.getValue(), "文件操作"));
        String folderPath = (buildInfo.getPatchTemp() + "/publish").replace("/", "\\");
        // server目录
        String serverPath = buildInfo.getPatchTemp() + patchProperties.getServerPath();
        ArrayList<FilePath> serverFiles = FileUtil.list(new File(serverPath), folderPath, patchProperties.getServerRoot(), ".dbo");
        // web目录
        String webPath = buildInfo.getPatchTemp() + patchProperties.getWebPath();
        ArrayList<FilePath> webFiles = FileUtil.list(new File(webPath), folderPath, patchProperties.getServerRoot());
        // 步骤参数
        ArrayList<CloudAppStepParam> params = new ArrayList<>(serverFiles.size() + webFiles.size());
        appFiles = new ArrayList<>(serverFiles.size() + webFiles.size());
        appFiles.addAll(serverFiles);
        appFiles.addAll(webFiles);
        if (appFiles.size() == 0) {
            return null;
        }
        ArrayList<String> files = new ArrayList<>(appFiles.size());
        for (FilePath appFile : appFiles) {
            params.add(new CloudAppStepParam(appFile.getLogicalPath() + appFile.getFileName()));
            files.add(appFile.getLogicalPath() + appFile.getFileName());
        }
        step.setStepParam(JsonUtil.obj2String(params));
        // 复制文件
        LogUtil.log("开始复制应用服务器文件...");
        FileUtil.copy(folderPath, patchPath + "/" + patchProperties.getServerRoot(), files.toArray(new String[0]));
        return step;
    }

    private static CloudPatchStep buildDBOStep() {
        CloudPatchStep step = new CloudPatchStep();
        step.setOrder(0);
        step.setStepType(1);
        step.setPatchAction(getPatchAction(PatchStep.DBO.getValue(), "DBO部署"));
        step.setStepParam(JsonUtil.obj2String(getDBStepParam(PatchStep.DBO)));
        String folderPath = (buildInfo.getPatchTemp() + "/publish/server/apps/scm").replace("/", "\\");
        String dboPath = buildInfo.getPatchTemp() + patchProperties.getDboPath();
        dboFiles = FileUtil.listBySuffix(new File(dboPath), folderPath, patchProperties.getDBRoot() + "\\Dbo", ".dbo");
        if (dboFiles.size() == 0) {
            return null;
        }
        ArrayList<String> files = new ArrayList<>(dboFiles.size());
        for (FilePath appFile : dboFiles) {
            files.add(appFile.getLogicalPath() + appFile.getFileName());
        }
        // 复制文件
        LogUtil.log("开始复制Dbo文件...");
        FileUtil.copy(dboPath, patchPath + "/DBUpdateFiles/Dbo", files.toArray(new String[0]));
        return step;
    }

    private static CloudPatchStep buildDataStep() {
        CloudPatchStep step = new CloudPatchStep();
        step.setOrder(1);
        step.setStepType(1);
        step.setPatchAction(getPatchAction(PatchStep.DATA.getValue(), "Data部署"));
        step.setStepParam(JsonUtil.obj2String(getDBStepParam(PatchStep.DATA)));
        String folderPath = (buildInfo.getPatchTemp() + patchProperties.getDataPath()).replace("/", "\\");
        String dataPath = buildInfo.getPatchTemp() + patchProperties.getDataPath();
        dataFiles = FileUtil.listBySuffix(new File(dataPath), folderPath, patchProperties.getDBRoot() + "\\Data", ".data");
        if (dataFiles.size() == 0) {
            return null;
        }
        // 复制文件
        LogUtil.log("开始复制Data文件...");
        FileUtil.copy(dataPath, patchPath + "/DBUpdateFiles/Data");
        return step;
    }

    private static CloudPatchStep buildMetaDataStep() {
        CloudPatchStep step = new CloudPatchStep();
        step.setOrder(2);
        step.setStepType(1);
        step.setPatchAction(getPatchAction(PatchStep.METADATA.getValue(), "元数据导入"));
        step.setStepParam(JsonUtil.obj2String(getDBStepParam(PatchStep.METADATA)));
        String folderPath = (buildInfo.getPatchTemp() + patchProperties.getMetaDataPath()).replace("/", "\\");
        String metaDataPath = buildInfo.getPatchTemp() + patchProperties.getMetaDataPath();
        metaDataFiles = FileUtil.list(new File(metaDataPath), folderPath, patchProperties.getDBRoot() + "\\MetaData");
        if (metaDataFiles.size() == 0) {
            return null;
        }
        // 复制文件
        LogUtil.log("开始复制元数据文件...");
        FileUtil.copy(metaDataPath, patchPath + "/DBUpdateFiles/MetaData");
        return step;
    }

    private static CloudPatchStep buildIDPStep() {
        CloudPatchStep step = new CloudPatchStep();
        step.setOrder(3);
        step.setStepType(1);
        step.setPatchAction(getPatchAction(PatchStep.IDP.getValue(), "IDP导入"));
        step.setStepParam(JsonUtil.obj2String(getDBStepParam(PatchStep.IDP)));
        String folderPath = (buildInfo.getPatchTemp() + patchProperties.getIdpPath()).replace("/", "\\");
        String idpPath = buildInfo.getPatchTemp() + patchProperties.getIdpPath();
        idpFiles = FileUtil.list(new File(idpPath), folderPath, patchProperties.getDBRoot() + "\\IDP");
        if (idpFiles.size() == 0) {
            return null;
        }
        // 复制文件
        LogUtil.log("开始复制IDP文件...");
        FileUtil.copy(idpPath, patchPath + "/DBUpdateFiles/IDP");
        return step;
    }

    private static String getXML(PatchInfo patchInfo) {
        return "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n<Patch xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" + StringUtils.remove(StringUtils.remove(StringUtils.removeStart(XMLUtil.obj2String(patchInfo).replace(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", ""), "<Patch>"), " standalone=\"yes\""), "xsi:nil=\"true\"");
    }

    public static PatchInfo buildPatchXML(CloudPatchInfo patchInfo) {
        PatchInfo patch = new PatchInfo();
        patch.setPatchBasicInfo(buildBasicXML(patchInfo));
        patch.setPatchAppInfo(buildAppXML());
        patch.setPatchDBInfo(buildDBXML());
        return patch;
    }

    private static PatchBasicInfo buildBasicXML(CloudPatchInfo patchInfo) {
        PatchBasicInfo basic = new PatchBasicInfo();
        basic.setPatchId(patchInfo.getPatchBasic().getPatchID());
        basic.setPatchName(patchInfo.getPatchBasic().getPatchName());
        basic.setPatchCode(patchInfo.getPatchBasic().getPatchCode());
        basic.setPatchType(new PatchType(patchInfo.getPatchBasic().getPatchType().getTypeCode(), patchInfo.getPatchBasic().getPatchType().getTypeName()));
        basic.setPatchCategory(new PatchType(patchInfo.getPatchBasic().getPatchCategory().getTypeCode(), patchInfo.getPatchBasic().getPatchCategory().getTypeName()));
        basic.setPatchImportance(new PatchImportance(patchInfo.getPatchBasic().getGSPPatchImportance().getImpCode(), patchInfo.getPatchBasic().getGSPPatchImportance().getImpName()));
        List<GSProduct> productList = new ArrayList<>();
        productList.add(new GSProduct(patchInfo.getPatchBasic().getPatchProduct().getProductID(), patchInfo.getPatchBasic().getPatchProduct().getProductCode(), patchInfo.getPatchBasic().getPatchProduct().getProductName(), patchInfo.getPatchBasic().getPatchProduct().getProductVersion()));
        basic.setPatchGSProductList(productList);
        basic.setPatchGSModule(new GSModule(patchInfo.getPatchBasic().getPatchDeployUnit().getDeployUnitName(), patchInfo.getPatchBasic().getPatchDeployUnit().getDeployUnitCode(), patchInfo.getPatchBasic().getPatchDeployUnit().getDeployUnitVersion()));
        basic.setDefectsCode(String.join(",", buildInfo.getIssues().keySet()));
        basic.setPatchNote(patchInfo.getPatchBasic().getPatchNote());
        basic.setPublishDate(new Date());
        basic.setVersion(patchInfo.getPatchBasic().getToolVersion());
        basic.setIsAppUpdate(appFiles.size() > 0);
        basic.setIsConfigUpdate(false);
        basic.setIsDBUpdate(false);
        basic.setIsDBStructUpdate(false);
        basic.setIsMetaDataUpdate(metaDataFiles.size() > 0);
        ArrayList<String> fileNames = new ArrayList<>(appFiles.size());
        appFiles.forEach(x -> fileNames.add(x.getFileName()));
        basic.setPatchAppFiles(String.join(";", fileNames));
        basic.setPatchGroupOrder(0);
        basic.setPatchSize(0);
        basic.setIsModuleInit(false);
        basic.setIsmatchStandard(false);
        basic.setIsCommonChange(false);
        basic.setProblemNote(StringUtils.removeEnd(buildInfo.getSolveDesc(), "\n"));
        basic.setFuncNote(StringUtils.removeEnd(buildInfo.getModifyDesc(), "\n"));
        basic.setBugNote(StringUtils.removeEnd(buildInfo.getTestDesc(), "\n"));
        return basic;
    }

    private static PatchAppInfo buildAppXML() {
        PatchAppInfo appInfo = new PatchAppInfo();
        if (appFiles.size() == 0) {
            ArrayList<GSPPatchFile> files = new ArrayList<>();
            files.add(new GSPPatchFile("root", "0"));
            appInfo.setPatchAppFiles(files);
            return appInfo;
        }
        HashSet<String> folderPaths = new HashSet<>();
        ArrayList<GSPPatchFile> files = new ArrayList<>();
        ArrayList<PatchAppFile> fileOps = new ArrayList<>();
        for (FilePath appFile : appFiles) {
            setParent(appFile.getLogicalRoot() + appFile.getLogicalPath(), appFile.getLevelPath(), files, folderPaths);
            files.add(getPatchFile(appFile));
            fileOps.add(new PatchAppFile(appFile.getFileName(), appFile.getLogicalRoot() + appFile.getLogicalPath()));
        }
        appInfo.setPatchAppFiles(files);
        ArrayList<GSPPatchStep> steps = new ArrayList<>();
        PatchAppFiles fs = new PatchAppFiles();
        fs.setFiles(fileOps);
        steps.add(new GSPPatchStep(1, new PatchAction("005", "文件操作"), XMLUtil.obj2String(fs).replace(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "")));
        appInfo.setPatchAppStepList(steps);
        return appInfo;
    }

    private static PatchDBInfo buildDBXML() {
        PatchDBInfo dbInfo = new PatchDBInfo();
        if (dboFiles.size() == 0 && dataFiles.size() == 0 && metaDataFiles.size() == 0 && idpFiles.size() == 0) {
            ArrayList<GSPPatchFile> files = new ArrayList<>();
            files.add(new GSPPatchFile("DBUpdateFiles", "0"));
            files.add(new GSPPatchFile("Dbo", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("Data", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("MetaData", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("BIModel", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("IDP", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("MSSSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("MSSSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("ORASQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("ORASQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("PGSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("PGSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("DMSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("DMSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("MYSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("MYSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("OSCARSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("OSCARSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("KINGBASE_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("KINGBASE_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("DB2_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("DB2_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            files.add(new GSPPatchFile("Assembly", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
            dbInfo.setPatchDBFiles(files);
            ArrayList<GSPPatchStep> steps = new ArrayList<>();
            steps.add(new GSPPatchStep(0, new PatchAction("101", "数据库升级"), null));
            dbInfo.setPatchDBStepList(steps);
            return dbInfo;
        }
        HashSet<String> folderPaths = new HashSet<>();
        ArrayList<GSPPatchFile> files = new ArrayList<>();
        ArrayList<GSPPatchDBGroup> dbGroups = new ArrayList<>();
        ArrayList<GSPPatchDBFile> dbFiles = new ArrayList<>();
        int order = 0;
        for (FilePath filePath : dboFiles) {
            setParent(filePath.getLogicalRoot() + filePath.getLogicalPath(), filePath.getLevelPath(), files, folderPaths);
            files.add(getPatchFile(filePath));
            dbFiles.add(new GSPPatchDBFile(filePath.getFileName(), order++, filePath.getLogicalRoot() + filePath.getLogicalPath()));
        }
        if (dboFiles.size() == 0) {
            files.add(new GSPPatchFile("Dbo", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        }
        dbGroups.add(new GSPPatchDBGroup("Dbo", 0, dbFiles.size() == 0 ? null : dbFiles));
        dbFiles.clear();
        order = 0;
        for (FilePath filePath : dataFiles) {
            setParent(filePath.getLogicalRoot() + filePath.getLogicalPath(), filePath.getLevelPath(), files, folderPaths);
            files.add(getPatchFile(filePath));
            dbFiles.add(new GSPPatchDBFile(filePath.getFileName(), order++, filePath.getLogicalRoot() + filePath.getLogicalPath()));
        }
        if (dataFiles.size() == 0) {
            files.add(new GSPPatchFile("Data", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        }
        dbGroups.add(new GSPPatchDBGroup("Data", 1, dbFiles.size() == 0 ? null : dbFiles));
        dbFiles.clear();
        order = 0;
        for (FilePath filePath : metaDataFiles) {
            setParent(filePath.getLogicalRoot() + filePath.getLogicalPath(), filePath.getLevelPath(), files, folderPaths);
            files.add(getPatchFile(filePath));
            dbFiles.add(new GSPPatchDBFile(filePath.getFileName(), order++, filePath.getLogicalRoot() + filePath.getLogicalPath()));
        }
        if (metaDataFiles.size() == 0) {
            files.add(new GSPPatchFile("MetaData", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        }
        dbGroups.add(new GSPPatchDBGroup("MetaData", 2, dbFiles.size() == 0 ? null : dbFiles));
        dbFiles.clear();
        files.add(new GSPPatchFile("BIModel", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        dbGroups.add(new GSPPatchDBGroup("BIModel", 3, null));
        order = 0;
        for (FilePath filePath : idpFiles) {
            setParent(filePath.getLogicalRoot() + filePath.getLogicalPath(), filePath.getLevelPath(), files, folderPaths);
            files.add(getPatchFile(filePath));
            dbFiles.add(new GSPPatchDBFile(filePath.getFileName(), order++, filePath.getLogicalRoot() + filePath.getLogicalPath()));
        }
        if (idpFiles.size() == 0) {
            files.add(new GSPPatchFile("IDP", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        }
        files.add(new GSPPatchFile("MSSSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("MSSSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("ORASQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("ORASQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("PGSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("PGSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("DMSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("DMSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("MYSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("MYSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("OSCARSQL_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("OSCARSQL_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("KINGBASE_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("KINGBASE_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("DB2_DDL", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("DB2_DML", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        files.add(new GSPPatchFile("Assembly", "0", "DBUpdateFiles\\", "DBUpdateFiles"));
        dbGroups.add(new GSPPatchDBGroup("MSSSQL_DDL", 5, null));
        dbGroups.add(new GSPPatchDBGroup("MSSSQL_DML", 6, null));
        dbGroups.add(new GSPPatchDBGroup("ORASQL_DDL", 7, null));
        dbGroups.add(new GSPPatchDBGroup("ORASQL_DML", 8, null));
        dbGroups.add(new GSPPatchDBGroup("PGSQL_DDL", 9, null));
        dbGroups.add(new GSPPatchDBGroup("PGSQL_DML", 10, null));
        dbGroups.add(new GSPPatchDBGroup("DMSQL_DDL", 11, null));
        dbGroups.add(new GSPPatchDBGroup("DMSQL_DML", 12, null));
        dbGroups.add(new GSPPatchDBGroup("MYSQL_DDL", 13, null));
        dbGroups.add(new GSPPatchDBGroup("MYSQL_DML", 14, null));
        dbGroups.add(new GSPPatchDBGroup("OSCARSQL_DDL", 15, null));
        dbGroups.add(new GSPPatchDBGroup("OSCARSQL_DML", 16, null));
        dbGroups.add(new GSPPatchDBGroup("KINGBASE_DDL", 17, null));
        dbGroups.add(new GSPPatchDBGroup("KINGBASE_DML", 18, null));
        dbGroups.add(new GSPPatchDBGroup("DB2_DDL", 19, null));
        dbGroups.add(new GSPPatchDBGroup("DB2_DML", 20, null));
        dbGroups.add(new GSPPatchDBGroup("Assembly", 21, null));
        dbInfo.setPatchDBFiles(files);
        ArrayList<GSPPatchStep> steps = new ArrayList<>();
        GSPPatchDBParam dbParam = new GSPPatchDBParam();
        dbParam.setName("DBUpdateFiles");
        dbParam.setDbGroups(dbGroups);
        steps.add(new GSPPatchStep(1, new PatchAction("101", "数据库升级"), "<?xml version=\"1.0\" encoding=\"utf-16\"?><GSPPatchDBParam xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" + StringUtils.removeStart(XMLUtil.obj2String(dbParam).replace(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", ""), "<GSPPatchDBParam>")));
        dbInfo.setPatchDBStepList(steps);
        return dbInfo;
    }

    private static BuildInfo getBuildInfo(Scanner scanner) {
        BuildInfo buildInfo = new BuildInfo();
        buildInfo.setPatchTemp(InputUtil.getPatchTemp(scanner));
        buildInfo.setPatchName(InputUtil.getPatchName(scanner, true));
        buildInfo.setIssues(new HashMap<>());
        buildInfo.setSolveDesc(InputUtil.getSolveDesc(scanner));
        buildInfo.setModifyDesc(InputUtil.getModifyDesc(scanner));
        buildInfo.setTestDesc(InputUtil.getTestDesc(scanner));
        return buildInfo;
    }

    private static String getPatchCode() {
        return patchProperties.getProduct() + getCloudVersion() + patchProperties.getDeployUnit() + DateUtil.toString(new Date(), "yyyyMMdd") + (char) (Math.random() * 26 + 'A') + (char) (Math.random() * 26 + 'A');
    }

    private static String getCloudVersion() {
        return patchProperties.getProductVersion().replace(".", "");
    }

    private static CloudPatchType getPatchType() {
        CloudPatchType patchType = new CloudPatchType();
        patchType.setTypeCode("official");
        patchType.setTypeName("正式");
        return patchType;
    }

    private static CloudPatchType getPatchCategory() {
        CloudPatchType patchCategory = new CloudPatchType();
        patchCategory.setTypeCode("require");
        patchCategory.setTypeName("需求");
        return patchCategory;
    }

    private static CloudPatchImportance getPatchImportance() {
        CloudPatchImportance patchImportance = new CloudPatchImportance();
        patchImportance.setImpCode("unimportant");
        patchImportance.setImpName("一般");
        return patchImportance;
    }

    private static CloudPatchDeployUnit getPatchDeployUnit() {
        CloudPatchDeployUnit patchDeployUnit = new CloudPatchDeployUnit();
        patchDeployUnit.setDeployUnitCode(patchProperties.getDeployUnit());
        patchDeployUnit.setDeployUnitName(patchProperties.getDeployUnitName());
        patchDeployUnit.setDeployUnitVersion(patchProperties.getDeployUnitVersion());
        return patchDeployUnit;
    }

    private static CloudPatchProduct getPatchProduct() {
        CloudPatchProduct patchProduct = new CloudPatchProduct();
        patchProduct.setProductID(patchProperties.getProduct() + patchProperties.getProductVersion());
        patchProduct.setProductCode(patchProperties.getProduct());
        patchProduct.setProductName(patchProperties.getProduct() + patchProperties.getProductVersion());
        patchProduct.setProductVersion(patchProperties.getProductVersion());
        return patchProduct;
    }

    private static String getPatchNote(String solveDesc, String modifyDesc) {
        return "【解决问题描述】" + solveDesc + "\r\n【修改功能说明】" + modifyDesc + "\r\n【影响功能说明】无\r\n【使用操作说明】无\r\n【其他注意事项】无";
    }

    private static String getPublishDate() {
        return "/Date(" + new Date().getTime() + "+0800)/";
    }

    private static CloudPatchAction getPatchAction(String code, String name) {
        CloudPatchAction patchAction = new CloudPatchAction();
        patchAction.setActionCode(code);
        patchAction.setActionName(name);
        patchAction.setDeployAssemblyName("");
        patchAction.setDeployClassName("");
        return patchAction;
    }

    private static CloudDBStepParam getDBStepParam(PatchStep patchStep) {
        CloudDBStepParam stepParam = new CloudDBStepParam();
        stepParam.setPatchId(patchId);
        switch (patchStep) {
            case DBO:
                stepParam.setParam("DBUpdateFiles\\Dbo");
                break;
            case DATA:
                stepParam.setParam("DBUpdateFiles\\Data");
                break;
            case METADATA:
                stepParam.setParam("DBUpdateFiles\\MetaData");
                break;
            case IDP:
                stepParam.setParam("DBUpdateFiles\\IDP");
                break;
            default:
                break;
        }
        stepParam.setSetPatchIDs(null);
        return stepParam;
    }

    private static GSPPatchFile getPatchFile(FilePath file) {
        GSPPatchFile patchFile = new GSPPatchFile();
        patchFile.setFileName(file.getFileName());
        patchFile.setIsDetail("1");
        patchFile.setDiskPath(file.getDiskPath());
        patchFile.setLogicalPath(file.getLogicalRoot() + file.getLogicalPath());
        patchFile.setLevelPath(file.getLevelPath());
        return patchFile;
    }

    private static void setParent(String path, String fileName, ArrayList<GSPPatchFile> files, HashSet<String> folderPaths) {
        if (StringUtils.isBlank(path)) {
            return;
        }
        if (folderPaths.contains(path)) {
            return;
        }
        String logicalPath = StringUtils.removeEnd(path, fileName + "\\");
        int index = logicalPath.lastIndexOf("\\", logicalPath.length() - 2) + 1;
        String levelPath = StringUtils.isBlank(logicalPath) ? null : logicalPath.substring(index, logicalPath.length() - 1);
        logicalPath = StringUtils.isBlank(logicalPath) ? null : logicalPath;
        setParent(logicalPath, levelPath, files, folderPaths);
        files.add(new GSPPatchFile(fileName, "0", logicalPath, levelPath));
        folderPaths.add(path);
    }
}

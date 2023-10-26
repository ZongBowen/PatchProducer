package org.zbw.patchproducer.util;

import org.apache.commons.lang3.StringUtils;
import org.zbw.patchproducer.entity.FilePath;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    public static final int BUFFER_SIZE = 1024;

    /**
     * 压缩文件
     *
     * @param dir      压缩文件地址
     * @param fileName 压缩包名
     */
    public static void toZip(String dir, String fileName) {
        try (FileOutputStream out = new FileOutputStream(new File(StringUtils.removeEnd(dir, "PatchFile") + "/" + fileName + ".gs"))) {
            try (ZipOutputStream zos = new ZipOutputStream(out)) {
                File file = new File(dir);
                compress(file, zos, "");
            }
        } catch (IOException e) {
            LogUtil.error(e);
        }
    }

    /**
     * 解压文件
     *
     * @param savePath 解压文件的路径，必须为目录
     * @param zipFile  输入的解压文件路径
     */
    public static void unZip(String savePath, String zipFile) {
        if (StringUtils.isBlank(savePath) || StringUtils.isBlank(zipFile)) {
            LogUtil.errorLog("解压文件路径:" + savePath + ",压缩包:" + zipFile + "输入不正确");
            return;
        }
        File folder = createFolder(savePath);
        if (!folder.isDirectory()) {
            LogUtil.errorLog("保存的路径必须为目录路径");
            return;
        }
        long start = System.currentTimeMillis();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        LogUtil.log("开始解压" + zipFile + "...");
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null) {
                File newFile = new File(savePath + File.separator + ze.getName());
                if (ze.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    initFolder(newFile);
                    FileOutputStream fos = new FileOutputStream(newFile);
                    write(zis, fos, false);
                    fos.close();
                }
            }
            zis.closeEntry();
        } catch (IOException e) {
            LogUtil.error(e);
        }
        LogUtil.log("解压缩完成,耗时：" + (System.currentTimeMillis() - start) + "ms,保存在" + savePath);
    }

    /**
     * 删除文件或目录
     *
     * @param filePath 文件路径
     */
    public static void delete(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delete(f);
            }
        }
        if (!file.delete()) {
            LogUtil.errorLog("文件" + file.toString() + "删除失败");
        }
    }

    /**
     * 删除文件或目录
     *
     * @param file 文件对象
     */
    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f);
                }
            }
        }
        if (!file.delete()) {
            LogUtil.errorLog("文件" + file.toString() + "删除失败");
        }
    }

    /**
     * 清空文件夹
     *
     * @param folder 文件夹
     */
    public static void clearFolder(String folder) {
        File file = new File(folder);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delete(f);
            }
        }
    }

    /**
     * 新建文件夹
     *
     * @param folder 如果路径不存在则自动创建
     * @return 新建文件的 File 对象
     */
    public static File createFolder(String folder) {
        LogUtil.log("正在新建文件夹: " + folder);
        mkDir(folder);
        return new File(folder);
    }

    /**
     * 递归获取文件夹下所有文件
     *
     * @param folder  文件夹
     * @param path    文件夹路径
     * @param root    根节点
     * @param excepts 排除文件类型
     * @return 文件列表
     */
    public static ArrayList<FilePath> list(File folder, String path, String root, String... excepts) {
        ArrayList<FilePath> filePaths = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return filePaths;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                filePaths.addAll(list(file, path, root, excepts));
            } else {
                boolean canList = true;
                if (excepts != null && excepts.length > 0) {
                    String suffix = getSuffix(file.getName());
                    for (String except : excepts) {
                        if (except.equals(suffix)) {
                            canList = false;
                            break;
                        }
                    }
                }
                if (canList) {
                    filePaths.add(new FilePath(file.getName(), file.getPath(), root, StringUtils.removeStart(file.getParentFile().getPath(), path) + "\\", file.getParentFile().getName()));
                }
            }
        }
        return filePaths;
    }

    /**
     * 递归获取文件夹下固定后缀的文件
     *
     * @param folder 文件夹
     * @param path   文件夹路径
     * @param root   根节点
     * @param suffix 后缀
     */
    public static ArrayList<FilePath> listBySuffix(File folder, String path, String root, String suffix) {
        ArrayList<FilePath> filePaths = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return filePaths;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                filePaths.addAll(listBySuffix(file, path, root, suffix));
            } else {
                if (suffix.equals(getSuffix(file.getName()))) {
                    filePaths.add(new FilePath(file.getName(), file.getPath(), root, StringUtils.removeStart(file.getParentFile().getPath(), path) + "\\", file.getParentFile().getName()));
                }
            }
        }
        return filePaths;
    }

    /**
     * 复制文件
     *
     * @param srcPath  来源目录
     * @param targPath 目标目录
     * @param files    复制文件列表
     */
    public static void copy(String srcPath, String targPath, String... files) {
        if (files != null && files.length > 0) {
            try {
                for (String file : files) {
                    Path src = new File(srcPath + file).toPath();
                    Path targ = new File(targPath + file).toPath();
                    mkDir(targ.getParent().toString());
                    LogUtil.log(src.toString() + " -> " + targ.toString());
                    Files.copy(src, targ);
                }
            } catch (IOException e) {
                LogUtil.error(e);
            }
            return;
        }
        copyFile(new File(srcPath), new File(targPath));
    }

    public static void save(String path, String fileName, String content, String charsetName) {
        try {
            mkDir(path);
            File file = new File(path + "/" + fileName);
            if (file.createNewFile()) {
                Writer write = new OutputStreamWriter(new FileOutputStream(file), charsetName);
                write.write(content);
                write.flush();
                write.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归压缩方法
     *
     * @param file 文件
     * @param zos  zip输出流
     * @param name 压缩后的名称
     * @throws IOException 异常
     */
    private static void compress(File file, ZipOutputStream zos, String name) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        if (file.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len = 0;
            FileInputStream in = new FileInputStream(file);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File f : listFiles) {
                    compress(f, zos, StringUtils.isBlank(name) ? f.getName() : (name + "/" + f.getName()));
                }
            }
        }
    }

    private static void copyFile(File src, File targ) {
        // 若为目录,递归复制目录下文件
        if (src.isDirectory()) {
            File[] files = src.listFiles();
            if (files != null) {
                for (File file : files) {
                    copyFile(file, new File(targ.toPath().resolve(file.toPath().getFileName()).toString()));
                }
            }
        } else {
            try {
                Path targPath = targ.toPath();
                mkDir(targPath.getParent().toString());
                LogUtil.log(src.getAbsolutePath() + " -> " + targ.toString());
                Files.copy(src.toPath(), targPath);
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return 后缀
     */
    private static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 检测文件所在的目录是否存在，如果没有则建立。可以跨多个未建的目录
     *
     * @param file 必须是文件，不是目录
     */
    private static boolean initFolder(File file) {
        if (file.isDirectory()) {
            LogUtil.errorLog("参数必须是文件，不是目录");
            return false;
        }
        return mkDir(file.getParent());
    }

    /**
     * 写入文件
     *
     * @param in       输入流，无须手动关闭
     * @param out      输出流
     * @param isBuffer 是否加入缓冲功能
     */
    private static void write(InputStream in, OutputStream out, boolean isBuffer) {
        // 读取到的数据长度
        int readSize;
        // 通过 byte 作为数据中转，用于存放循环读取的临时数据
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            if (isBuffer) {
                try (OutputStream _out = new BufferedOutputStream(out)) {// 加入缓冲功能
                    while ((readSize = in.read(buffer)) != -1) {
                        _out.write(buffer, 0, readSize);
                    }
                }
            } else {
                // 每次读 1KB 数据，将输入流数据写入到输出流中
                // readSize = in.read(buffer, 0, bufferSize);
                while ((readSize = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                    out.write(buffer, 0, readSize);
                    // readSize = in.read(buffer, 0, bufferSize);
                }
                out.flush();
            }
        } catch (IOException e) {
            LogUtil.error(e);
        }
    }

    /**
     * 创建目录
     *
     * @param folder 目录字符串
     */
    private static boolean mkDir(String folder) {
        File _folder = new File(folder);
        // 先检查目录是否存在，若不存在建立
        if (!_folder.exists()) {
            _folder.mkdirs();
        }
        return _folder.mkdir();
    }
}

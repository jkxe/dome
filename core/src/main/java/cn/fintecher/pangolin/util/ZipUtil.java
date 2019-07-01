package cn.fintecher.pangolin.util;

import com.ice.tar.TarEntry;
import com.ice.tar.TarInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class ZipUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    public static String dozip(String zipfile) throws IOException {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        Charset charset = Charset.forName("GBK");
        try(ZipFile zip = new ZipFile(zipfile, charset)) {
            checkFileName(zipfile);
            long startTime = System.currentTimeMillis();
            // 获取待解压文件的父路径
            File zfile = new File(zipfile);
            String Parent = zfile.getParent() + "/" + zfile.getName().substring(0, zfile.getName().lastIndexOf(".")) + "/";
            File parentFile = new File(Parent);
            parentFile.mkdir();
            // 设置,默认是UTF-8
            ZipEntry entry = null;
            Enumeration<ZipEntry> enums = (Enumeration<ZipEntry>) zip.entries();
            while (enums.hasMoreElements()) {
                entry = enums.nextElement();
                if (entry.isDirectory()) {
                    File filePath = new File(Parent + entry.getName());
                    // 如果目录不存在，则创建
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                }
            }
            enums = (Enumeration<ZipEntry>) zip.entries();
            while (enums.hasMoreElements()) {
                entry = enums.nextElement();
                if (!entry.isDirectory()) {
                    bos = new BufferedOutputStream(new FileOutputStream(Parent + entry.getName()));
                    //获取条目流
                    bis = new BufferedInputStream(zip.getInputStream(entry));
                    byte buf[] = new byte[1024];
                    int len;
                    while ((len = bis.read(buf)) != -1) {
                        bos.write(buf, 0, len);
                    }

                    bos.close();
                }
            }
            System.out.println("解压后的路径是：" + Parent);
            long endTime = System.currentTimeMillis();
            System.out.println("解压成功,所需时间为:" + (endTime - startTime) + "ms");
            return Parent;
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
        }
    }

    private static void checkFileName(String name) {
        // 文件是否存在
        if (!new File(name).exists()) {
            System.err.println("要解压的文件不存在！");
            //System.exit(1);
        }
        // 判断是否是zip文件
        int index = name.lastIndexOf(".");
        String str = name.substring(index + 1);
        if (!"zip".equalsIgnoreCase(str)) {
            System.err.println("不是zip文件,无法解压！");
            //System.exit(1);
        }
    }

    private static void checkTargzFileName(String name) {
        // 文件是否存在
        if (!new File(name).exists()) {
            System.err.println("要解压的文件不存在！");
            //System.exit(1);
        }
        // 判断是否是tar.gz文件
        int index = name.lastIndexOf(".");
        String str = name.substring(index + 1);
        if (!"gz".equalsIgnoreCase(str)) {
            System.err.println("不是tar.gz文件,无法解压！");
            //System.exit(1);
        }
    }

    public static void main(String[] args) {

        try {
            ZipUtil.dozip("D:\\压缩\\Desktop.zip");
            File file = new File("D:\\压缩\\1.xlsx");
            //获取excel内容
//            List<List<Object>> lists = ExcelUtil.readExcel(file);
//            List<Object> labels = lists.get(0);
//            System.out.println(labels.toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    //------------------------------------------------------------------------------------------------------

    /**
     * 解压tar.gz 文件
     *
     * @param fileName      要解压的tar.gz文件对象
     * @param outputDir 要解压到某个指定的目录下
     * @throws IOException
     */
    public static void unTarGz(String fileName, String outputDir) throws IOException {
        checkTargzFileName(fileName);
        long startTime = System.currentTimeMillis();
        TarInputStream tarIn = null;
        try {
            File file=new File(fileName);
            tarIn = new TarInputStream(new GZIPInputStream(
                    new BufferedInputStream(new FileInputStream(file))),
                    1024 * 2);
            createDirectory(outputDir, null);//创建输出目录
            TarEntry entry = null;
            while ((entry = tarIn.getNextEntry()) != null) {
                if (entry.isDirectory()) {//是目录
                    entry.getName();
                    createDirectory(outputDir, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(outputDir + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/", null);//创建输出目录
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(tmpFile);
                        int length = 0;
                        byte[] b = new byte[2048];
                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }
                    } catch (IOException ex) {
                        throw ex;
                    } finally {
                        if (out != null)
                            out.close();
                    }
                }
            }
        } catch (IOException ex) {
            logger.error("解压归档文件出现异常", ex);
            throw new IOException("解压归档文件出现异常", ex);
        } finally {
            try {
                if (tarIn != null) {
                    tarIn.close();
                }
            } catch (IOException ex) {
                logger.error("关闭tarFile出现异常", ex);
            }
            long endTime = System.currentTimeMillis();
            logger.info("解压成功,所需时间为:" + (endTime - startTime) + "ms");
        }
    }

    /**
     * 构建目录
     *
     * @param outputDir
     * @param subDir
     */
    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || subDir.trim().equals(""))) {//子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.mkdirs();
        }
    }

}
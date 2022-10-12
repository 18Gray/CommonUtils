package com.eighteengray.commonutillibrary;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.channels.FileChannel;

/**
 * 文件操作工具类
 */
public class FileUtils {
    //*************************************************************************************************************************
    // 创建删除文件
    //*************************************************************************************************************************

    /**
     * 创建目录
     */
    public File createDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 创建文件
     */
    public static File createFile(String folderPath, String fileName) {
        String filePath = SDCardUtils.getSDCardPath() + File.separator + folderPath + File.separator + fileName;
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 判断文件夹是否存在
     */
    public boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 复制文件
     */
    public static void copyFile(File from, File to) {
        if (null == from || !from.exists()) {
            return;
        }
        if (null == to) {
            return;
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(from);
            if (!to.exists())
            {
                to.createNewFile();
            }
            os = new FileOutputStream(to);
            copyFileFast(is, os);
        } catch (Exception e) {
            throw new RuntimeException(FileUtils.class.getClass().getName(), e);
        } finally {
            closeIO(is, os);
        }
    }

    /**
     * 快速复制文件（采用nio操作）
     * @param is
     * @param os
     * @throws IOException
     */
    public static void copyFileFast(FileInputStream is, FileOutputStream os) throws IOException {
        FileChannel in = is.getChannel();
        FileChannel out = os.getChannel();
        in.transferTo(0, in.size(), out);
    }

    /**
     * 关闭文件
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                throw new RuntimeException(FileUtils.class.getClass().getName(), e);
            }
        }
    }


    //*************************************************************************************************************************
    // 读写文件
    //*************************************************************************************************************************

    /**
     * 字符串写入指定路径File
     */
    public static boolean writeString2File(String res, String filePath, String fileName) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = createFile(filePath, fileName);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * InputStream写入指定路径File
     * @return
     */
    public File writeInputStream2File(InputStream input, String filePath, String fileName) {
        int FILESIZE = 4 * 1024;
        File file = null;
        OutputStream output = null;
        try {
            createDir(filePath);
            file = createFile(filePath, fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[FILESIZE];
            int length;
            while ((length = (input.read(buffer))) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * byte[]写入指定路径File
     */
    public static void writeByte2File(byte[] input, String filePath, String fileName) {
        File folder = new File(filePath);
        folder.mkdirs();
        File file = new File(filePath, fileName);
        ByteArrayInputStream is = new ByteArrayInputStream(input);
        OutputStream os = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
                os = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while (-1 != (len = is.read(buffer))) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            } catch (Exception e) {
                throw new RuntimeException(FileUtils.class.getClass().getName(), e);
            } finally {
                closeIO(is, os);
            }
        }
    }


    /**
     * 指定文件路径中读取String
     */
    public static String readStringFromFile(String filePath, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(filePath), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(filePath));
            }
            // 将输入流写入输出流
            char[] buffer = new char[1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 返回转换结果
        if (writer != null)
            return writer.toString();
        else
            return null;
    }

    /**
     * 从指定文件路径中读取byte[]
     */
    public static byte[] readBytesFromFile(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * Delete the all in directory.
     */
    public static boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null || filter == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Delete the directory.
     */
    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }
}

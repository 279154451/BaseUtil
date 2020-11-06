package com.coder.baseutil.download;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：
 **/
public class FileUtil {
    public static void closeIO(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取根目录地址
     * @param context
     * @param folderName
     * @return
     */
    public String getFolderPath(Context context,String folderName){
        String folder = "";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            folder = context.getExternalCacheDir() +File.separator+folderName+File.separator;
        } else {
            folder = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator;
        }

        File dir = new File(folder);
        FileUtil.createFolder(dir);
        return dir.getAbsolutePath();
    }

    /**
     * 删除之前的apk
     *
     * @param apkName apk名字
     * @return
     */
    public static File clearFile(Context context, String apkName) {
        File apkFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), apkName);
        if (apkFile.exists()) {
            apkFile.delete();
        }
        return apkFile;
    }
    /**
     * Create a folder, If the folder exists is not created.
     *
     * @param targetFolder folder path.
     * @return True: success, or false: failure.
     */
    public static boolean createFolder(File targetFolder) {
        if (targetFolder.exists()) {
            if (targetFolder.isDirectory()) return true;
            //noinspection ResultOfMethodCallIgnored
            targetFolder.delete();
        }
        return targetFolder.mkdirs();
    }
    /**
     * Delete file or folder.
     *
     * @param file file.
     * @return is succeed.
     * @see #delFileOrFolder(String)
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean delFileOrFolder(File file) {
        if (file == null || !file.exists()) {
            // do nothing
        } else if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File sonFile : files) {
                    delFileOrFolder(sonFile);
                }
            }
            file.delete();
        }
        return true;
    }
}

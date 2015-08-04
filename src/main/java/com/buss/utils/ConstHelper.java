package com.buss.utils;

import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by shilin on 2015/7/10.
 */
public class ConstHelper {
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static String makeGenDir(String dirName, HttpServletRequest request) {
        String dir = request.getSession().getServletContext().getRealPath("/")
                + dirName;
        File f = new File(dir);
        f.mkdirs();
        return dir;
    }

    public static String getStandardPropertyName(String name) {
        if (name.length() > 1) {
            if ((Character.isLowerCase(name.charAt(0)) && Character.isUpperCase(name.charAt(1)))
                    || (Character.isLowerCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0)))
                    ) {
                if (name.length() == 2) {
                    name = name.toUpperCase();
                } else {
                    name = name.substring(0, 2).toLowerCase() + name.substring(2);
                }
            }
        }
        return name;
    }
}

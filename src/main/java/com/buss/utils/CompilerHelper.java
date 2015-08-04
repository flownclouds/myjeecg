package com.buss.utils;

import java.io.ByteArrayInputStream;
import java.util.StringTokenizer;
import org.codehaus.janino.SimpleCompiler;
/**
 * Created by shilin on 2015/8/4.
 * 移除注释类
 */
public class CompilerHelper {
    private static final char MARK = '"';
    private static final char SLASH = '/';
    private static final char STAR = '*';
    private static final char BACKSLASH = '\\';

//    public static void main(String[] args) {
//        String Name = "//*/\"";
//
//
//        String javaCode = "package slr; import java.lang.String; " + "class T1 {\n" +
//                "    private String Name = \"//*/\\\"\";\n" +
//                "    private int Age;\n" +
//                "\n" +
//                "    //*/\\n\n" +
//                "\n" +
//                "    /*   //  /**\n" +
//                "    public String getName() {\n" +
//                "        return Name;\n" +
//                "    }\n" +
//                "    */\n" +
//                "    public String getName() {\n" +
//                "        return Name;\n" +
//                "    }\n" +
//                "\n" +
//                "    public void setName(String name) {\n" +
//                "        Name = name;\n" +
//                "    }\n" +
//                "\n" +
//                "    public int getAge() {\n" +
//                "        return Age;\n" +
//                "    }\n" +
//                "\n" +
//                "    public void setAge(int age) {\n" +
//                "        Age = age;\n" +
//                "    }\n" +
//                "}";
//
//
//        String str = removeComment(javaCode);
//        String ss = str;
//    }


    public static Class getCompilerClass(String code) throws Throwable {
        String strCode= removeComment(code);
        String clsName=extractFullClassName(strCode);
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.cook(new ByteArrayInputStream(strCode.getBytes("UTF-8")) );
        Class cls= compiler.getClassLoader().loadClass(clsName);
        return cls;
    }


    public static String removeComment(String javaCode) {
        int curIndex = 0;
        int markIndex = -1;
        StringBuffer sb = new StringBuffer();
        char[] arrInput = javaCode.toCharArray();
        for (curIndex = 0; curIndex < arrInput.length; curIndex++) {
            char curChar = arrInput[curIndex];
            if (curChar == MARK) {
                if (0 <= curIndex - 1 && arrInput[curIndex - 1] == BACKSLASH) {

                } else {
                    if (0 <= markIndex) {
                        markIndex = -1;
                    } else {
                        markIndex = curIndex;
                    }
                }

            } else if (curChar == SLASH) {
                if (markIndex == -1) {
                    int endIndex = getComment(curIndex, arrInput);
                    if (curIndex != endIndex) {
                        curIndex = endIndex;
                        continue;
                    }
                }
            }
            sb.append(curChar);
        }
        return sb.toString();
    }

    private static int getComment(int curIndex, char[] arrInput) {
        int beginIndex = curIndex;
        int endIndex = curIndex;

        boolean isSingle = false;
        char preChar = ' ';
        if (curIndex + 1 < arrInput.length) {
            char curChar = arrInput[curIndex + 1];
            if (curChar == SLASH) {
                isSingle = true;
            } else if (curChar == STAR) {
                isSingle = false;
            } else {
                return endIndex;
            }
        }
        curIndex = curIndex + 2;
        for (; curIndex < arrInput.length; curIndex++) {
            char curChar = arrInput[curIndex];
            if (isSingle) {
                if (curChar == '\n') {
                    endIndex = curIndex;
                    break;
                }
            } else {
                if (curChar == SLASH) {
                    if (0 <= curIndex - 1 && arrInput[curIndex - 1] == STAR) {
                        endIndex = curIndex;
                        break;
                    }
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        for (; beginIndex <= endIndex; beginIndex++) {
            sb.append(arrInput[beginIndex]);
        }
        String strCmt = sb.toString();

        return endIndex;
    }

    private static String extractFullClassName(String javaCode) {
        if (javaCode == null || "".equals(javaCode)) {
            throw new IllegalArgumentException(
                    "javaCode must be non-null and non-empty!");
        }
        StringTokenizer strTokenizer = new StringTokenizer(javaCode, " \t\n\r;{");
        String fullClassName = "";
        String packageName = "";
        String token = null;
        while (strTokenizer.hasMoreTokens()) {
            token = strTokenizer.nextToken();
            if ("package".equals(token)) {
                packageName = strTokenizer.nextToken();
            } else if ("class".equals(token)) {
                fullClassName = strTokenizer.nextToken();
                if (!"".equals(packageName)) {
                    fullClassName = packageName + "." + fullClassName;
                }
                break;
            }
        }
        return fullClassName;
    }
}

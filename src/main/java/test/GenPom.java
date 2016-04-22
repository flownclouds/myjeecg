package test;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenPom {
    private static  final String  GROUPID="ccsplib.infosky";
    private  static final String libraryDir="D:\\Programe\\java\\";
	private static Configuration getConfiguration() throws IOException {
		Configuration cfg = new Configuration();
		String path = GenPom.class.getResource("").getPath();
		File templateDirFile = new File(URLDecoder.decode(path, "UTF-8"));
		cfg.setDirectoryForTemplateLoading(templateDirFile);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}

	private static String getTemplateInfo(Map data) {
		String result = "";
		try {
			Template template = getConfiguration().getTemplate("poms.ftl");
			ByteOutputStream bStream = new ByteOutputStream();
			Writer out = new OutputStreamWriter(bStream, "UTF-8");
			template.process(data, out);
			result = bStream.toString();
			bStream.close();
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static Map<File, String> readJar() {
		String dirString = libraryDir+"CCSPJavaLibrary";
		List<File> list = new ArrayList<File>();
		getFile(dirString, list,".jar");

		Map<File, String> map = new HashMap<File, String>();

		for (File f : list) {
			String name = f.getName();
			String name2 = name;
			ResultInfo ri = isContainsFileName(map, name);
			if (ri.value) {
				File tf = ri.file;
				name2 = name.replace(".jar", "_") + f.getParentFile().getName()
						+ ".jar";
				String sn = name.replace(".jar", "_")
						+ tf.getParentFile().getName() + ".jar";
				map.put(tf, sn);
			}
			map.put(f, name2);
		}

		return map;
	}

	private static void genDep() throws IOException,DocumentException {
		String dirString = libraryDir+GROUPID.replaceAll("\\.","\\\\"); // "infosky\\sl";
		List<File> list = new ArrayList<File>();
		getFile(dirString, list,".pom");

		List<String> libList=getCcspLibdep();
		StringBuffer libSb=new StringBuffer();
		StringBuffer sb=new StringBuffer();
		for(File item :list){
			String s= FileUtils.readFileToString(item);
			s="<?xml version=\"1.0\"?>"+s;
			
			String groupId=getDocText(s,"groupId");
			String artifactId=getDocText(s,"artifactId");
			String version=getDocText(s,"version");
			String description=getDocText(s,"description");
			
			Map<String, String> data = new HashMap<String, String>();
			data.put("groupId", groupId);
			data.put("artifactId", artifactId);
			data.put("version", version);
			data.put("description", description);
			data.put("dep", "Y");
			String strDep = getTemplateInfo(data);
			sb.append(strDep+"\n");

			if(isCcsplib(libList,description)){
				libSb.append(strDep+"\n");
			}
		}
		
	 String path=	dirString+"\\infosky.dep";
		File tf=new File(path);
		if(tf.exists())
			tf.delete();
		FileUtils.writeStringToFile(tf,sb.toString());

		String path2=	dirString+"\\ccsplib.dep";
		File tf2=new File(path2);
		if(tf2.exists())
			tf2.delete();
		FileUtils.writeStringToFile(tf2,libSb.toString());
		
	}

	private static boolean isCcsplib(List<String> list,String  path){
		for (String s : list) {
			if(path.contains(s)) return true;
			if(s.contains("*")){
				String tmp= s.substring(0,s.indexOf("*"));
				if(path.contains(tmp)) return true;
			}
		}
		return false;
	}
private static List<String> getCcspLibdep(){
	List<String> list = new ArrayList<String>();
	try {
		String path=GenPom.class.getResource("copylibrary.xml").getPath();

		String text = FileUtils.readFileToString(new File(path));

		Pattern p = Pattern.compile("name=\"[\\s\\S]*?\"");
		Matcher m = p.matcher(text);


		while (m.find()) {
			String str = m.group().replaceAll("name=\"", "").replaceAll("\"", "").trim();
			if (str.endsWith("jar")) {
				list.add(str.replaceAll("/","\\\\"));
			}
		}


	} catch (Throwable e) {
		e.printStackTrace();
	}

	return list;
}
    
 	private static String getDocText(String s,String node) throws DocumentException {
		String groupId="";
		Document doc= DocumentHelper.parseText(s);
		Element arg = (Element) doc.selectSingleNode("//"+node);
		if(arg!=null)
		{
			groupId=arg.getText();
		}
		return groupId;
	}
	private static void genPom() throws IOException {
		Map<File, String> map = readJar();
		List<String> listSame = new ArrayList<String>();
		String r = "";
		System.out.println("repeat begin:");
		for (String s : map.values()) {
			if (listSame.contains(s)) {
				System.out.println(s);
			} else {
				listSame.add(s);
			}
			r += s + "\n";
		}
		System.out.println("repeat end!");

		String dirInfosky = libraryDir+GROUPID.replaceAll("\\.","\\\\"); 
		File f=new File(dirInfosky);
		if(f.exists()){
			deleteDir(f);
		}
		f.mkdirs();
		
		
		String pom = "";
		for (File item : map.keySet()) {
//			String path = item.getParent() + "\\"
//					+ item.getName().replace(".jar", ".pom");
//			File tf = new File(path);
//			if (tf.exists()) {
//				tf.delete();
//			}

			String description=item.getPath().replace(libraryDir+"CCSPJavaLibrary","CCSPJavaLibrary");
			String version="1.1.1";
			String mapVal=map.get(item);
			String artifactId=mapVal.replace(".jar", "");
			String jarDir= dirInfosky+"\\"+artifactId+"\\"+version;
			new File(jarDir).mkdirs();
			
			FileUtils.copyFile(item, new File(jarDir+"\\"+artifactId+"-"+version+".jar"));
			String pomPath = jarDir + "\\"
					+artifactId+"-"+version+".pom";
			
			Map<String, String> data = new HashMap<String, String>();
			data.put("artifactId", artifactId);
			data.put("version", version);
			data.put("description",description);
			data.put("groupId", GROUPID);
			String s = getTemplateInfo(data);
			FileUtils.writeStringToFile(new File(pomPath), s);
		}
	}
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// �ݹ�ɾ��Ŀ¼�е���Ŀ¼��
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// Ŀ¼��ʱΪ�գ�����ɾ��
		return dir.delete();
	}
	private static ResultInfo isContainsFileName(Map<File, String> map,
			String fileName) {
		for (File item : map.keySet()) {
			String n = item.getName();
			if (n.equals(fileName)) {
				return new ResultInfo(true, item);
			}
		}
		return new ResultInfo(false, null);
	}

	private static ResultInfo isContainsFileName(List<File> list,
			String fileName) {
		for (File f : list) {
			String name = f.getName();
			if (name.equals(fileName))
				return new ResultInfo(true, f);
		}
		return new ResultInfo(false, null);
	}

	private static void getFile(String path, List<File> list,String ends) {
		File f = new File(path);
		if (f.isDirectory()) {
			File[] dirFiles = f.listFiles();
			for (File _f : dirFiles) {
				if (_f.isDirectory()) {
					getFile(_f.getAbsolutePath(), list,ends);
				} else {
					if (_f.getName().endsWith(ends)) {
						list.add(_f);
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private static void throwtest() {
		try {
			Integer u = Integer.parseInt("rr");
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date birthDay = format.parse("25456");
		} catch (Exception ex) {
			String q = ex.getMessage();
		}
	}
private static void testdir(){
	String src="D:\\Programe\\java\\maven\\ICustoms_EDI\\SourceCode\\target\\infoskylibrary\\WEB-INF\\lib";
	String tar="D:\\Programe\\java\\maven\\ICustoms_EDI\\SourceCode\\mu_check-1.0\\WEB-INF\\lib";
	
	File f1 = new File(src);
	File[] dirFiles = f1.listFiles();
	
	File f2 = new File(tar);
	File[] dirFiles2 = f2.listFiles();
	
 List<String> lStr=new ArrayList<String>();
	for (int i=0;i< dirFiles2.length;i++) {
		File file =dirFiles2[i];
		String n2= file.getName().replace(".jar", "");
		for(int j=0;j< dirFiles.length;j++){
			 File item=dirFiles[j];
			 if(item==null) continue;
			String n1= item.getName().replace(".jar", "");
			if(n2.contains(n1))
			{
				lStr.add(n2+"=>"+n1+"\n");
				dirFiles2[i]=null;
				dirFiles[j]=null;
			}
		}
	}
	
	lStr.add("$$$\n");
	for (File item : dirFiles) {
		if(item!=null){
			lStr.add( item.getName()+"\n");
		}
	}
	lStr.add("$$$\n");
	for (File item : dirFiles2) {
		if(item!=null){
			lStr.add( item.getName()+"\n");
		}
	}
	
	System.out.print(lStr.toString());
}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			//testdir();
			//genPom();
			
			genDep();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class ResultInfo {
		public ResultInfo(Boolean bValue, File fFile) {
			value = bValue;
			file = fFile;
		}

		public Boolean value;
		public File file;
	}
}

package com.renqiang.str.extract;

import java.io.*;
import java.util.Properties;

/**
 * Author: renqiang6
 * Email: renqiang6@jd.com
 * Date: 2017/9/21
 * Description:
 */
public class StartMainStringExtract {

    private String packageName = null;

    public void extractString(){
        Properties properties = new Properties();
        try {
            //加载配置属性
            FileInputStream fis = new FileInputStream("src/main/resources/extract.properties");
            properties.load(fis);
            packageName = (String)properties.get("package.name");
            String targetPath = (String)properties.get("target.path");
            String targetFileName = (String) properties.get("target.file.name");
            String sourcePath = (String)properties.get("source.path");


            String rootPath = System.getProperty("user.dir");
            //需要提取的代码路径
            File file = new File(rootPath+sourcePath);
            String packagePath = packageName.replace(".", "/");
            //提取出的message文件所在路径
            String javaFileDirPath = rootPath+targetPath+ packagePath;
            File dirFile = new File(javaFileDirPath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            //提取后生产的常量类
            File javaFile = new File(javaFileDirPath +"/"+targetFileName);

            if(!javaFile.exists()){
                boolean isCreate = javaFile.createNewFile();
                if(isCreate){
                    writeFile(javaFile);
                }
            }

            //提取字符串
            JavaMessageFile javaMessageFile = new JavaMessageFile(javaFile,"UTF-8");
            getMessage(file, javaMessageFile,javaFile.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(File javaFile) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(javaFile));
            bw.write("package " + packageName + ";");
            bw.newLine();
            bw.write("public class Message {");
            bw.newLine();
            bw.write("}");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMessage(File file, JavaMessageFile jmf, String jmfName) {
        try {
            if(file.isFile()){
                String fileName = file.getName();
                if(!fileName.contains("Message") && !fileName.contains("Constant") && !fileName.equals(jmfName)){
                    MessageVerifyUtil.fetchStringMessages(file.getAbsolutePath(), "UTF-8",jmf);
                    int count = jmf.getNewCount();
                    if(count > 0){
                        jmf.save();
                    }
                }
            }else{
                //过滤不要解析的文件
                if(!file.getAbsolutePath().endsWith(".svn") && !file.getAbsolutePath().endsWith(".git")){
                    File[] files = file.listFiles();
                    for(File f: files){
                        getMessage(f,jmf, jmfName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

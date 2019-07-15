package com.threey.guard.base.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class FileUtil {

    /**
     * 递归删除文件夹及子文件等
     * @param  sPath        文件夹路径
     * @return boolean      返回对象
     * @author 邹建松 2013-03-11
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                deleteFile(files[i].getAbsolutePath());
            }else {//删除子目录
                deleteDirectory(files[i].getAbsolutePath());
            }
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件夹
     * @param  sPath        文件路径
     * @return boolean      返回对象
     * @author 邹建松 2013-03-11
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 取得文件大小
     * @param  f         文件对象
     * @return long      返回对象
     * @author 邹建松 2013-03-11
     */
    public static long getFileSizes(File f) throws Exception{
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s= fis.available();
        }
        return s;
    }

    /**
     * 递归取得文件夹大小
     * @param  f         文件对象
     * @return long      返回对象
     * @author 邹建松 2013-03-11
     */
    public static long getFileSize(File f)throws Exception{
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++){
            if(flist[i].isDirectory()){
                size = size + getFileSize(flist[i]);
            }else{
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     * @param  fileS      文件大小
     * @return String     返回对象
     * @author 邹建松 2013-03-11
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 递归求取目录文件个数
     * @param  f        文件对象
     * @return long     返回对象
     * @author 邹建松 2013-03-11
     */
    public static long getlist(File f){
        long size = 0;
        File flist[] = f.listFiles();
        size=flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }

    /**
     * 复制单个文件
     * @param  oldPath  String  原文件路径  如：c:/fqf.txt
     * @param  newPath  String  复制后路径  如：f:/fqf.txt
     * @return void
     * @throws IOException
     * @author 邹建松 2013-03-12
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {
//		int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPath);
        if (oldfile.exists()) { //文件存在时
            InputStream inStream = new FileInputStream(oldPath); //读入原文件
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[]	buffer = new byte[1024 * 64];
            while ((byteread = inStream.read(buffer)) != -1) {
//				bytesum += byteread; //字节数  文件大小
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
        }
    }

    /**
     * 复制文件流
     * @param  in      InputStream   输入流
     * @param  out     OutputStream  输出流
     * @param  length  long          缓冲区大小
     * @return void
     * @throws Exception
     * @author 邹建松 2013-03-12
     */
    public static long copyStream(InputStream in, OutputStream out, long length) throws Exception {
        byte buffer[] = new byte[1024 * 64];
        long copiedLength = 0L;
        int readSize;
        int sizeToRead;
        while (true) {
            if (length < 0) {
                sizeToRead = buffer.length;
            } else {
                sizeToRead = (int) (length - copiedLength);
                if (sizeToRead > buffer.length) {
                    sizeToRead = buffer.length;
                }
            }
            if (sizeToRead <= 0) {
                break;
            }
            readSize = in.read(buffer, 0, sizeToRead);
            if (readSize < 0) {
                break;
            }else if (readSize == 0) {
                int oneByte = in.read();
                if (oneByte < 0) {
                    break;
                }
                if (out != null) {
                    out.write(oneByte);
                    out.flush();
                }
                copiedLength++;
            }else {
                if (out != null) {
                    out.write(buffer, 0, readSize);
                    out.flush();
                }
                copiedLength += readSize;
            }
        }
        return copiedLength;
    }

    /**
     * [简要描述]:复制文件流,并设置进度<br/>
     * [详细描述]:<br/>
     *
     * @param in
     * @param out
     * @param length
     * @param session
     * @param setKey
     * @param splitSize
     * @return
     * @throws Exception
     * @exception
     * @author 邹建松 2013-03-12
     */
    public static long copyStream(InputStream in, OutputStream out, long length, HttpSession session, String setKey, long splitSize, long totalSize) throws Exception {
        byte buffer[] = new byte[1024 * 64];
        long copiedLength = 0L;
        int readSize;
        int sizeToRead;
        long sNum = 1;
        session.setAttribute(setKey,"0.00");
        DecimalFormat df = new DecimalFormat("0.00");
        while (true) {
            if (length < 0) {
                sizeToRead = buffer.length;
            } else {
                sizeToRead = (int) (length - copiedLength);
                if (sizeToRead > buffer.length) {
                    sizeToRead = buffer.length;
                }
            }
            if (sizeToRead <= 0) {
                break;
            }
            readSize = in.read(buffer, 0, sizeToRead);
            if (readSize < 0) {
                break;
            }else if (readSize == 0) {
                int oneByte = in.read();
                if (oneByte < 0) {
                    break;
                }
                if (out != null) {
                    out.write(oneByte);
                    out.flush();
                }
                copiedLength++;
            }else {
                if (out != null) {
                    out.write(buffer, 0, readSize);
                    out.flush();
                }
                copiedLength += readSize;
            }
            if((Math.round(Math.floor(copiedLength/splitSize)))>=sNum){
                session.setAttribute(setKey, df.format((double)copiedLength/totalSize));
                sNum++;
            }
        }
        session.setAttribute(setKey,"1.00");
        return copiedLength;
    }

    /**
     * 复制整个文件夹内容  注意：直接使用会造成死循环，需要中转存储临时文件再复制可以避免系循环问题
     * @param  oldPath  String  原文件路径  如：c:/fqf
     * @param  newPath  String  复制后路径  如：f:/fqf/ff
     * @return void
     * @throws IOException
     * @author 邹建松 2013-03-12
     */
    public static void copyFolder(String oldPath, String newPath) throws IOException {
        (new File(newPath)).mkdirs(); //如果文件夹不存在  则建立新文件夹
        File a = new File(oldPath);
        String[] file = a.list();
        File temp = null;
        for (int i = 0; i < file.length; i++) {
            if (oldPath.endsWith(File.separator)) {
                temp = new File(oldPath + file[i]);
            } else {
                temp = new File(oldPath + File.separator + file[i]);
            }

            if (temp.isFile()) {
                FileInputStream input = new FileInputStream(temp);
                FileOutputStream output = new FileOutputStream(newPath + File.separator + (temp.getName()).toString());
                byte[]	b = new byte[1024 * 64];
                int len;
                while ((len = input.read(b)) != -1) {
                    output.write(b, 0, len);
                }
                output.flush();
                output.close();
                input.close();
            }
            if (temp.isDirectory()) {//如果是子文件夹
                copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i]);
            }
        }
    }

    /**
     * 移动文件到指定目录
     * @param  oldPath  String  如：c:/fqf.txt
     * @param  newPath  String  如：d:/fqf.txt
     * @return void
     * @throws IOException
     * @author 邹建松 2013-03-12
     */
    public static void moveFile(String oldPath, String newPath) throws IOException {
        copyFile(oldPath, newPath);
        deleteFile(oldPath);
    }

    /**
     * 移动文件夹到指定目录
     * @param  oldPath  String  如：c:/fqf
     * @param  newPath  String  如：d:/fqf/fgf
     * @return void
     * @throws IOException
     * @author 邹建松 2013-03-12
     */
    public static void moveFolder(String oldPath, String newPath) throws IOException {
        copyFolder(oldPath, newPath);
        deleteDirectory(oldPath);
    }

    /**
     * 复制文件夹到指定目录
     * @param  oldPath  String  如：c:/fqf
     * @param  newPath  String  如：d:/fqf/fgf
     * @return void
     * @throws IOException
     * @author 邹建松 2013-03-12
     */
    public static void moveFolderCopy(String oldPath, String newPath,boolean isDel) throws IOException {
        copyFolder(oldPath, newPath);
        if(isDel){
            deleteDirectory(oldPath);
        }
    }
    /**
     * 压缩文件夹到指定目录的指定文件
     * @param  inputFolderPath  String  被压缩的文件夹路径
     * @param  outZipFilePath   String  输出压缩文件夹的路径
     * @param  outZipFileName   String  输出压缩文件名称
     * @param  charSet          String  压缩使用的字符集
     * @return void
     * @throws Exception
     * @author 邹建松 2013-03-13
     */
    public static void zipFolder(String inputFolderPath,String outZipFilePath,String outZipFileName, String charSet) throws Exception {
        String zipPath = "";
        if(outZipFilePath.endsWith(File.separator)){
            zipPath = outZipFilePath+outZipFileName+".zip";
        }else{
            zipPath = outZipFilePath+File.separator+outZipFileName+".zip";
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
        if(System.getProperty("os.name").equalsIgnoreCase("window")){
            out.setEncoding(charSet);
        }

        File folderFile = new File(inputFolderPath);
        zipCycle(out, folderFile, folderFile.getName());
        out.close();
    }

    /**
     * 解压缩文件夹并输出流
     * @param  inputZipPath  String  压缩的文件夹路径
     * @param  outputZipPath String  输出文件的路径
     * @param  charSet       String  解压缩使用的字符集
     * @return void
     * @throws Exception
     * @author 邹建松 2013-03-18
     */
    public static void unZipFile(String inputZipPath,String outputZipPath,String charSet) throws Exception{
        File file = new File(inputZipPath);
        if(!file.exists()) {
            throw new Exception("解压文件不存在!");
        }
        InputStream is = null;
        FileOutputStream fos = null;
        ZipFile zipFile = null;
        try{
            zipFile = new ZipFile(file,charSet);
            Enumeration<ZipEntry> e = zipFile.getEntries();
            while(e.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry)e.nextElement();
                String name = zipEntry.getName();
                if(zipEntry.isDirectory()) {
                    File f = new File(outputZipPath +File.separator+ name);
                    f.mkdirs();
                } else {
                    File f = new File(outputZipPath +File.separator+ name);
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                    is = zipFile.getInputStream(zipEntry);
                    fos = new FileOutputStream(f);
                    FileUtil.copyStream(is, fos, -1l);
                    if (is != null) {//必须的
                        is.close();
                    }
                    if (fos != null) {//必须的
                        fos.close();
                    }
                }
            }
        }catch(Exception ex){
            throw ex;
        }finally{
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (zipFile != null) {
                zipFile.close();
            }
        }

    }

    /**
     * 压缩文件夹并输出流
     * @param  inputFolderPath  String  被压缩的文件夹路径
     * @param  os               OutputStream  输出流
     * @param  charSet          String  压缩使用的字符集
     * @return void
     * @throws Exception
     * @author 邹建松 2013-03-13
     */
    public static void zipFolder(String inputFolderPath,OutputStream os, String charSet) throws Exception {
        ZipOutputStream out = new ZipOutputStream(os);
        out.setEncoding(charSet);
        File folderFile = new File(inputFolderPath);
        zipCycle(out, folderFile, folderFile.getName());
        out.close();
    }

    /**
     * 递归压缩文件夹和文件
     * @param  out    ZipOutputStream  压缩文件输出流
     * @param  f      File             被压缩的文件或文件夹
     * @param  base   String           递归的文件或文件夹路径
     * @return void
     * @throws Exception
     * @author 邹建松 2013-03-13
     */
    public static void zipCycle(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zipCycle(out, fl[i], base + fl[i].getName());
            }
        }else {
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            byte b[] = new byte[1024*64];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();
        }
    }

    /**
     * 字符文件输出方法
     * @param  path    文件存放地址
     * @param  content 文件内容
     * @param  charSet 字符集
     * @return int
     * @author 邹建松 2013-04-01
     */
    public static int writeStrToFile(String path,String content,String charSet){
        File outFile = new File(path);
        FileOutputStream out = null;
        BufferedWriter writer = null;
        OutputStreamWriter outWriter = null;
        int reInt = -1;
        try {
            out = new FileOutputStream(outFile);
            outWriter = new OutputStreamWriter(out,charSet);
            writer = new BufferedWriter(outWriter);
            if (content != null) {
                writer.write(content);
            }
            reInt = 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                writer.flush();
                outWriter.close();
                writer.close();
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reInt;
    }
    /**
     * 将list对象输出单个csv文件
     * @param  path    文件存放地址
     * @param  li      内容列表
     * @param  columnNameArr 标题数组
     * @param  charSet 字符集
     * @return int
     * @author 邹建松 2013-12-07
     */
    public static int writeListToFile(String path, List<Map<String,Object>> li, String[] columnNameArr, String charSet){
        File outFile = new File(path);
        FileOutputStream out = null;
        BufferedWriter writer = null;
        OutputStreamWriter outWriter = null;
        String columnName = "";
        Object temp = null;
        int reInt = -1;
        try {
            out = new FileOutputStream(outFile);
            outWriter = new OutputStreamWriter(out,charSet);
            writer = new BufferedWriter(outWriter);
            for(int i=0;i<li.size();i++){
                for (int j = 0; j < columnNameArr.length; j++) {
                    columnName = columnNameArr[j];
                    temp = li.get(i).get(columnName);
                    if (temp == null) {
                        temp = "";
                    }
                    writer.write(temp.toString().replaceAll("\n", "").replaceAll(",", "，").replaceAll("\"", ""));
                    if (j < columnNameArr.length - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
                writer.flush();
            }
            reInt = 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                writer.flush();
                writer.close();
                outWriter.close();
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reInt;
    }
    /**
     * 生成zip文件头和csv文件头
     * @param  out           ZIP输出流管道
     * @param  bw            字符流写入器
     * @param  fileName      文件名称
     * @param  columnNameArr 标题行数组
     * @return int
     * @author 邹建松 2013-12-07
     * @throws IOException
     */
    private static int createZIPHead(ZipOutputStream out,BufferedWriter bw,String fileName,String[] columnNameArr) throws IOException{
        int reInt = -1;
        out.putNextEntry(new org.apache.tools.zip.ZipEntry(fileName));
        for (int i = 0; i < columnNameArr.length; i++) {
            bw.write(columnNameArr[i]);
            if (i < columnNameArr.length - 1) {
                bw.write(",");
            }
        }
        bw.write("\n");
        bw.flush();
        reInt = 1;
        return reInt;
    }
    /**
     * 生成csv文件行记录
     * @param  bw            字符流写入器
     * @param  rs            游标
     * @param  columnNameArr 标题行数组
     * @return int
     * @author 邹建松 2013-12-07
     * @throws SQLException
     * @throws IOException
     */
    private static int createRowRecord(BufferedWriter bw, ResultSet rs, String[] columnNameArr, String fileType) throws SQLException, IOException{
        int reInt = -1;
        String columnName = "";
        Object temp = null;
        for (int j = 0; j < columnNameArr.length; j++) {
            columnName = columnNameArr[j];
            temp = rs.getObject(columnName);
            if (temp == null) {
                temp = "";
            }
            if(fileType!=null && fileType.toLowerCase().equals(".csv")){
                bw.write(temp.toString().replaceAll("\n", "").replaceAll(",", "，").replaceAll("\"", ""));
            }else{
                bw.write(temp.toString());
            }
            if (j < columnNameArr.length - 1) {
                bw.write(",");
            }
        }
        bw.write("\n");
        bw.flush();
        reInt = 1;
        return reInt;
    }
    /**
     * 将游标结果集打包成zip文件包，带拆分文件
     * @param  fileName    文件名称
     * @param  rs          游标
     * @param  os          输出流管道
     * @param  splitSize   拆分文件行记录
     * @param  charSet     字符集
     * @return Map<String,Object> 返回键值对对象
     * @author 邹建松 2013-12-07
     * @throws SQLException
     * @throws IOException
     */
    public static Map<String,Object> writeRsToFileZip(String fileName, String fileType, ResultSet rs, OutputStream os,Integer splitSize, String charSet) throws SQLException, IOException{
        int countAll = 0,fileNum = 0,count = 1,numOfColumns;
        String[] columnNameArr = {};
        ResultSetMetaData metaData;
        BufferedWriter bw = null;
        ZipOutputStream out = null;
        BufferedOutputStream bfOutStream = null;
        OutputStreamWriter outStWriter = null;
        Map<String,Object> reMap = new HashMap<String,Object>();
        List<String> fileNameLi = new ArrayList<String>();//产生压缩文件名称列表
        String zipFileName = "";
        try {
            //start 产生数据列
            metaData = rs.getMetaData();
            numOfColumns =  metaData.getColumnCount();
            columnNameArr = new String[numOfColumns];
            for(int column = 0; column < numOfColumns; column++) {
                columnNameArr[column] = metaData.getColumnLabel(column+1);
            }
            //end   产生数据列
            bfOutStream = new BufferedOutputStream(os, 1024);
            out = new ZipOutputStream(bfOutStream);
            out.setEncoding(charSet);
            outStWriter = new OutputStreamWriter(out, charSet);
            bw = new BufferedWriter(outStWriter);
            fileType = StringUtil.isEmpty(fileType)?".csv":fileType;
            zipFileName = fileName+fileType;//第一个文件名称不用加fileNum
            createZIPHead(out,bw,zipFileName,columnNameArr);
            fileNameLi.add(zipFileName);
            while(rs.next()){
                countAll++;
                //超过拆分行记录需要重新产生文件
                if (splitSize!=null && count>=(splitSize.intValue())) {//splitSize如果为null则不拆分文件,只产生一个文件
                    zipFileName = fileName+"_"+(++fileNum)+fileType;
                    createZIPHead(out,bw,zipFileName,columnNameArr);
                    fileNameLi.add(zipFileName);
                    count = 1;
                }
                //创建每行
                createRowRecord(bw, rs, columnNameArr, fileType);
                count++;
            }
        } finally{
            if(bw!=null){
                bw.flush();
                bw.close();
            }
            if(bfOutStream!=null){
                bfOutStream.close();
            }
            if(outStWriter!=null){
                outStWriter.close();
            }
            if(out!=null){
                out.close();
            }
        }
        reMap.put("countAll", countAll);//一共多少行
        reMap.put("fileNum", fileNum);//一共拆分多少个文件
        reMap.put("columnNameArr", columnNameArr);//对象列数组
        reMap.put("fileNameLi", fileNameLi);//压缩文件名称列表
        return reMap;
    }

    /**
     * 将文本文件按行读入到集合中
     * @param  file        文件
     * @param  charSet     字符集
     * @return List<Map<String,String>> 返回列表键值对对象
     * @author 邹建松 2014-07-09
     * @throws IOException
     */
    public static List<Map<String,String>> readTextFileToList(File textFile,String charSet) throws IOException{
        List<Map<String,String>> reList = new ArrayList<Map<String,String>>();
        BufferedReader importReader;
        String tempStr;
        importReader = new BufferedReader(new InputStreamReader(new FileInputStream(textFile),charSet));
        tempStr = importReader.readLine();
        Map<String,String> map = new HashMap<String,String>();
        while (tempStr != null) {
            if(tempStr!=null && tempStr.length()>0){
                map = new HashMap<String,String>();
                map.put("content", tempStr);
                reList.add(map);
//	        	System.out.println(tempStr);
            }else{
                tempStr = importReader.readLine();
                continue;
            }
            tempStr = importReader.readLine();
        }
        return reList;
    }
}

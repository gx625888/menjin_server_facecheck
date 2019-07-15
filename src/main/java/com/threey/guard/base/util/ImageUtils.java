package com.threey.guard.base.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.*;
import java.util.Date;

/**
 * 图片处理工具类
 * 
 * @author wusq
 * @since 2011-05-05
 */
public class ImageUtils {
	private static double EARTH_RADIUS = 6378.137;

	/**
	 * 图片上传到服务器的方法
	 * 
	 * @param upload
	 *            图片文件
	 * @param serverPath
	 *            保存在服务器的路径
	 * @param imgName
	 *            图片名字
	 * @since 2011-05-05
	 */
	public static void uploadToServer(File upload, String serverPath,
                                      String imgName) throws FileNotFoundException, IOException {
		File dirPath = new File(serverPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		String path = dirPath + "//" + imgName;
		FileOutputStream fos = new FileOutputStream(path);
		FileInputStream fis = new FileInputStream(upload);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
	}

	/**
	 * 相关说明：验证文件是否为图片 开发者：汤云涛 时间：2014年12月17日 上午11:19:10
	 */
	public static boolean isImage(InputStream is) {

		Image img = null;
		try {
			img = ImageIO.read(is);
			if (img == null || img.getWidth(null) <= 0
					|| img.getHeight(null) <= 0) {
				return false;
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 相关说明：获取图片文件 开发者：汤云涛 时间：2014年12月17日 下午4:15:58
	 * 
	 * @throws IOException
	 */
	public static File getPicFile(String picName, HttpServletRequest req)
			throws IOException {

		String imagePath =  GETProperties.readValue("DISH_IMAGE_PATH");
		imagePath = imagePath.replace("/", File.separator);
		String root = RequestContextUtils.getWebApplicationContext(req)
				.getServletContext().getRealPath("/")
				+ File.separator + imagePath;
		File file = new File(root + File.separator + picName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		} else if (!file.exists()) {
			file.createNewFile();
		}

		return file;
	}

	public static String savePicture(String fileName, String path, MultipartFile imgFiles){
	      //获得图片后缀名 
        String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
         
        //重新生成图片名字 
        String imgN = new Date().getTime()+StringUtil.getSequence() + fileExt;
        
        File targetFile = new File(path, imgN);
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        //保存  
        try {  
        	imgFiles.transferTo(targetFile);  
        } catch (Exception e) {
            e.printStackTrace();  
        }
        return imgN;
	}
	
	/**
	 * 相关说明：删除文件
	 * 开发者：tangchang
	 * 时间：2015-3-2 上午11:39:39
	 */
	public static void deleteFile(String path) {
	    File file = new File(path);
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	    }  
	} 
	
	/**
	 * 相关说明：保存一张图片，图片的名称和对应的记录ID保持一致
	 * 开发者：tangchang
	 * 时间：2015-2-27 下午5:37:45
	 */
	public static String saveOnePicture(String fileName, String path, MultipartFile imgFiles, String pictureId){
	    //获得图片后缀名 
        String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
         
        //重新生成图片名字 
        String imgN = pictureId + fileExt;
        
        File targetFile = new File(path, imgN);
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        //保存  
        try {  
        	imgFiles.transferTo(targetFile);  
        } catch (Exception e) {
            e.printStackTrace();  
        }
        return imgN;
	}
	
	public static void main(String[] args) {
		
	}
}
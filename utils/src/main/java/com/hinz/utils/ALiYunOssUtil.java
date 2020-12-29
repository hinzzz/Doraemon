package com.hinz.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.codec.binary.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class ALiYunOssUtil {


	private static final String ACCESS_KEY_ID="";
	private static final String ACCESS_KEY_SECRET="";
	private static final String ENDPOINT = "";
	private static final String BUCKET_NAME = "";

	public static void main(String[] args) {
		try {
			ALiYunOssUtil.saveFile("bbb.jpg",new File("C:\\Users\\quanhz\\Desktop\\2222.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存文件到阿里云
	 * @param fileName 保存在阿里云的文件名
	 * @param file 要保存的文件对象
	 * @throws IOException
	 */
	public static void  saveFile(String fileName,File file) throws IOException{
		FileInputStream fis=new FileInputStream(file);
		saveFile(fileName, fis);
	}
	/**
	 * 保存文件到阿里云
	 * @param fileName 保存在阿里云的文件名
	 * @param is 数据输入流 输入流会在方法执行完毕 关闭
	 * @throws IOException
	 */
	public static void saveFile(String fileName, InputStream is) throws IOException{

		ObjectMetadata metadata=new ObjectMetadata();
		metadata.setContentLength(is.available());
		OSSClient client=new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		client.putObject(BUCKET_NAME, fileName, is, metadata);
		is.close();
	}
	/**
	 * 复制阿里云上的文件
	 * @param srcBUCKET_NAME 源 bucket名称
	 * @param srcFileName 源文件名
	 * @param fileName 目的文件名
	 * @throws IOException
	 */
	public static void cp(String srcBUCKET_NAME,String srcFileName,String fileName) throws IOException{

		OSSClient client=new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		client.copyObject(srcBUCKET_NAME, srcFileName, BUCKET_NAME, fileName);

	}


	/**
	 * 删除文件
	 * @param fileName 文件名称
	 */
	public static void rm(String fileName){

		OSSClient client=new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		client.deleteObject(BUCKET_NAME, fileName);
	}
	/**
	 * 生成url地址
	 * @param fileName
	 * @param expiresDate 过期时间
	 * @return
	 */
	public static String genUrl(String fileName, Date expiresDate){

		OSSClient client = new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		GeneratePresignedUrlRequest generatePresignedUrlRequest =new GeneratePresignedUrlRequest(BUCKET_NAME, fileName);
		generatePresignedUrlRequest.setExpiration(expiresDate);
		return client.generatePresignedUrl(generatePresignedUrlRequest).toString();
	}
	public static String genUrl(String fileName) {
        OSSClient client = new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =new GeneratePresignedUrlRequest(BUCKET_NAME, fileName);
        generatePresignedUrlRequest.setExpiration(new Date(32500886400000L));//3000-00-00
        return client.generatePresignedUrl(generatePresignedUrlRequest).toString();
	}

	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean exists(String fileName){

		OSSClient client=new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		try{
			client.getObject(BUCKET_NAME, fileName);
		}catch(OSSException e){
			if(e.getErrorCode().equals("NoSuchKey")){
				return false;
			}else{
				throw e;
			}
		}
		return true;
	}

	/**
	 * by ivan
	 * 转换透明背景图片
	 * @param signImg 阿里云图片路径
	 * @return
	 */
	public static BufferedImage fmtTransparentImg(String signImg){
		try {
            BufferedImage temp = ImageIO.read(new URL(signImg));
            int imgHeight = temp.getHeight();//取得图片的长和宽
            int imgWidth = temp.getWidth();
            int c = temp.getRGB(3, 3);
          //新建一个类型支持透明的BufferedImage
            BufferedImage bi = new BufferedImage(imgWidth, imgHeight,
                    BufferedImage.TYPE_4BYTE_ABGR);
          //把原图片的内容复制到新的图片，同时把背景设为透明
            for(int i = 2; i < imgWidth-2; ++i)
            {
                for(int j = 2; j < imgHeight-2; ++j)
                {
                    if(temp.getRGB(i, j) == c)
                        bi.setRGB(i, j, c & 0x00ffffff);//这里把背景设为透明
                    else
                        bi.setRGB(i, j, temp.getRGB(i, j));
                }
            }
            return bi;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}


	public static void saveFile(File file)throws IOException {
		saveFile(file.getName(),file);
	}
}

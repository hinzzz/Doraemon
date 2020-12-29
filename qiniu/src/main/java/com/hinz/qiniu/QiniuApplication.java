package com.hinz.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class QiniuApplication {
	public static void main(String[] args) {
		Scanner input =new Scanner(System.in);
		System.out.println( "七牛云图片启动成功>>>>>>>>>>");
		System.out.print("请将图片拖入到此窗口：");
		String path=input.nextLine();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(new Date())+path.substring(path.lastIndexOf("."));
		Configuration cfg = new Configuration(Region.huanan());
		cfg.useHttpsDomains = false;
		UploadManager uploadManager = new UploadManager(cfg);
		String accessKey = "wFBGWK3bJZrniIHZwXr6pfqlXa_HTM9wHOVCAFPC";
		String secretKey = "cw4KpX0BpVOTUVCEjme7JKSS4BepfsplenRjXwHg";
		String bucket = "hinz-blog";

		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		try {
			Response response = uploadManager.put(path, fileName, upToken);
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			String domainOfBucket = "www.hinzzz.cn";
			String finalUrl = String.format("%s/%s", domainOfBucket, fileName);
			System.out.println("上传成功，地址图片为"+finalUrl);
		} catch (QiniuException e) {
			e.printStackTrace();
			Response r = e.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				//ignore
			}
		}
		input.close();
	}

}

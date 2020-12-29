package com.hinz.qiniu.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.IOException;


public class UploadDemo {


    public static void main(String args[]) throws IOException {

        String ACCESS_KEY = "wFBGWK3bJZrniIHZwXr6pfqlXa_HTM9wHOVCAFPC";
        String SECRET_KEY = "cw4KpX0BpVOTUVCEjme7JKSS4BepfsplenRjXwHg";
        String bucketname = "hinz-blog";
        String key = null;
        String FilePath = "C:\\Users\\quanhz\\Desktop\\1111.png";

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        Region region = Region.region2();
        Configuration c = new Configuration(region);
        c.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(c);
        try {
            //调用put方法上传
            String upToken = auth.uploadToken(bucketname);
            Response res = uploadManager.put(FilePath, key, upToken);
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            e.printStackTrace();
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
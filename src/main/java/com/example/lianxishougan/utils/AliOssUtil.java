package com.example.lianxishougan.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AliOssUtil {



    /*

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    //EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    private static final String ACCESS_KEY_ID="";
    private static final String ACCESS_KEY_SECRET="";
    // 填写Bucket名称，例如examplebucket。
    private static final String BUCKET_NAME = "lixiaojin";
    // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
    private static final String REGION = "cn-beijing";

    */

    // 配置文件路径（相对于resources目录）  
    private static final String CONFIG_PATH = "config/aliyun-oss.properties";  
    private static String ACCESS_KEY_ID;  
    private static String ACCESS_KEY_SECRET;  
    private static String ENDPOINT;  
    private static String BUCKET_NAME;  


// 静态代码块：初始化时读取配置文件  
    static {  
        try {  
            // 加载配置文件  
            Properties properties = new Properties();
            // 通过类加载器读取resources目录下的文件  
            InputStream inputStream = AliOssUtil.class.getClassLoader().getResourceAsStream(CONFIG_PATH);  
            if (inputStream == null) {  
                throw new RuntimeException("配置文件不存在：" + CONFIG_PATH);  
            }  
            properties.load(inputStream);  

            // 读取配置项（注意与properties文件中的key保持一致）  
            ACCESS_KEY_ID = properties.getProperty("aliyun.oss.access-key-id");
            ACCESS_KEY_SECRET = properties.getProperty("aliyun.oss.access-key-secret");
            ENDPOINT = properties.getProperty("aliyun.oss.endpoint");
            BUCKET_NAME = properties.getProperty("aliyun.oss.bucket-name");

            // 校验配置是否完整  
            if (ACCESS_KEY_ID == null || ACCESS_KEY_SECRET == null || ENDPOINT == null || BUCKET_NAME == null) {
                throw new RuntimeException("配置文件缺少必要参数，请检查：" + CONFIG_PATH);  
            }  

        } catch (Exception e) {  
            throw new RuntimeException("读取阿里云OSS配置失败：" + e.getMessage(), e);  
        }  
    }  












    public static String uploadFile(String objectName, InputStream in) throws Exception {

        // 创建OSSClient实例。

        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        String url= "";
        try {
            // 填写字符串。
            String content = "Hello OSS，你好世界";

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, in);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            url = "https://"+BUCKET_NAME+"."+ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)+"/"+objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}

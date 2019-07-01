package cn.fintecher.pangolin.common.oss;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

/**
 * 阿里云存储
 *
 * @author hanwannan
 * @date 2017-03-26 16:22
 */
@Service
public class AliyunCloudStorageService {

    //阿里云绑定的域名
    @Value("${pangolin.oss.domain}")
    private String aliyunDomain;
    //阿里云路径前缀
    @Value("${pangolin.oss.prefix}")
    private String aliyunPrefix;
    //阿里云EndPoint
    @Value("${pangolin.oss.endpoint}")
    private String aliyunEndPoint;
    //阿里云AccessKeyId
    @Value("${pangolin.oss.accesskeyId}")
    private String aliyunAccessKeyId;
    //阿里云AccessKeySecret
    @Value("${pangolin.oss.accessKeySecret}")
    private String aliyunAccessKeySecret;
    //阿里云BucketName
    @Value("${pangolin.oss.bucketName}")
    private String aliyunBucketName;

    private OSSClient client;

    public String get(String objectName) {
        if(client==null){
            client = new OSSClient(aliyunEndPoint, aliyunAccessKeyId,
                    aliyunAccessKeySecret);
        }
        try {
            if(objectName!=null&&objectName.startsWith("/")){
                objectName=objectName.substring(1, objectName.length());
            }
            Date expiration = new Date(new Date().getTime() + 3600 * 1000);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = client.generatePresignedUrl(aliyunBucketName, objectName, expiration);
            return url.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

package top.nanli.weixin.util;

import java.util.Arrays;


/**
 * @Description: 请求校验
 *
 * @Auther LiNan
 * @Date: 2018/12/30 21:11
 */

public class CheckUtil {

    private static final String token = "xxxn20181216";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        if(timestamp==null){
            timestamp = "debugnull";
        }
        if(nonce==null){
            nonce = "debugnull";
        }
        String[] str = new String[]{token,timestamp,nonce};
        //排序
        System.out.println(timestamp+"#data#"+nonce);
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for(int i =0 ;i<str.length;i++){
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        if(signature==null){
            signature = "errorsignature";
        }
        System.out.println(temp+"#data#"+signature);
        return signature.equals(temp);
    }
}


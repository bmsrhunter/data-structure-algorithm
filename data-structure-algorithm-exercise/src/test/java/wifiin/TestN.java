package wifiin;
/*
 * 创建人：baimiao
 * 创建时间：2023/9/14 14:21
 *
 */

import com.google.common.collect.Maps;
import com.wifiin.common.CommonConstant;
import com.wifiin.util.digest.MessageDigestUtil;
import com.wifiin.util.net.http.HttpClient;
import com.wifiin.util.security.EnhancedSymmetricEncryptor;

import java.util.Map;

public class TestN {
    private static String key = "18hlcHCh15070t43";
    private static String salt = "k670jv5Ab0HdPffs";
    private static String iv = "yx10x0j605N9zjgm";
//e1837ddaae5cdd251fb196c0fea58077  299901
    public static void main(String[] args) throws Exception {
//        String url = "http://127.0.0.1:32090/plug/mail/login.do";
        String url = "http://172.16.1.8:32090/plug/mail/login.do";
        String body = "{\"username\":\"app@kuai.com\", \"password\":\"123456\"}";
//        String url = "http://172.16.1.8:32090/user/login";
//        String url = "http://127.0.0.1:32090/plug/appInfo.do";
//                String body = "{\"userId\":299901, \"password\":\"e1837ddaae5cdd251fb196c0fea58077\"}";
        post(url, body, true, false);
    }

    public static String post(String url, String body, boolean encrypt, boolean sign) throws Exception {
        HttpClient httpClient = new HttpClient(url);
        httpClient.unpooled();
        Map<String,String> header = Maps.newHashMap();
        header.put("Accept","application/json");
        header.put("Content-Type","application/json");
        httpClient.setHeaders(header);
        System.out.println("decrypt post:" + body);
        if (encrypt) {
            EnhancedSymmetricEncryptor aes = EnhancedSymmetricEncryptor.getAES(key, salt, iv, "UTF-8");
            body = aes.encryptToBase64(body);
            System.out.println("encrypt post:" + body);
            System.out.println("decrypt post:" + aes.decryptFromBase64(body));
        }
        if (sign) {
            httpClient.addHeader("Sign", MessageDigestUtil.sha1Hex(body));
        }
        httpClient.stringEntity(body);
        System.out.println("url:"+url);
        String result = httpClient.post();

        if (encrypt) {
            result = EnhancedSymmetricEncryptor.getAES(key, salt, iv, CommonConstant.DEFAULT_CHARSET_NAME).decryptFromBase64(result);
        }
        System.out.println("decrypt result:" + result);
        return result;
    }
}

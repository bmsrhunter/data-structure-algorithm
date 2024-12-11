package wifiin;
/*
 * 创建人：baimiao
 * 创建时间：2023/9/14 21:40
 *
 */

import com.google.common.collect.Maps;
import com.wifiin.common.CommonConstant;
import com.wifiin.util.digest.MessageDigestUtil;
import com.wifiin.util.net.http.HttpClient;
import com.wifiin.util.security.EnhancedSymmetricEncryptor;

import java.util.Map;

public class TestNe {
    private static String key = "18hlcHCh15070t43";
    private static String salt = "k670jv5Ab0HdPffs";
    private static String iv = "yx10x0j605N9zjgm";
    //e1837ddaae5cdd251fb196c0fea58077  299901
    public static void main(String[] args) throws Exception {
        String body = "{\"username\":\"app@kuai.com\",\"password\":\"123456\"}";
        EnhancedSymmetricEncryptor aes = EnhancedSymmetricEncryptor.getAES(key, salt, iv, "UTF-8");
        body = aes.encryptToBase64(body);
        System.out.println("encrypt post:" + body);
        System.out.println("decrypt post:" + aes.decryptFromBase64(body));
// result = EnhancedSymmetricEncryptor.getAES(key, salt, iv, CommonConstant.DEFAULT_CHARSET_NAME).decryptFromBase64(result);
    }

}

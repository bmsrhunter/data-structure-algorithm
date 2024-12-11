
/*
 * 创建人：baimiao
 * 创建时间：2023/7/14 14:23
 *
 */

import com.google.api.client.util.Lists;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wifiin.common.CommonConstant;
import net.glxn.qrgen.core.AbstractQRCode;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.testng.collections.Maps;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class PatternTest {
    public static void main1(String[] args) {
//        String url ="http://localhost:8090/static/pages/deeplink.html?nonce=1689316016291&td_callback_url=http://devtrack.kuaifan.co/api/data/quality/debug/callback&project=procuction2&projectId=18&token=3c22da9ec2bba9e6c0050a919df4ac06&userId=156";
        String url = "http://localhost:8090/static/pages/deeplink.html";
        AbstractQRCode qrCode = QRCode.from(url);
        qrCode.withErrorCorrection(ErrorCorrectionLevel.H);
        // 设置字符集，支持中文
        qrCode.withCharset(CommonConstant.DEFAULT_CHARSET_NAME);
        // 设置生成的二维码图片大小
        qrCode.withSize(300, 300);
        qrCode.withHint(EncodeHintType.MARGIN, 0);
        ByteArrayOutputStream out = qrCode.to(ImageType.PNG).stream();
        String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
        System.out.println(base64);
    }

    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        List<String> list = Lists.newArrayList(map.keySet());
        System.out.println(list.size());
    }
}

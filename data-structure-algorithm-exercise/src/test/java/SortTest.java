import com.google.api.client.util.Lists;
import com.wifiin.common.JSON;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: BaiDing
 * @Date: 2018/4/12 19:23
 * @Email: liujiabaiding@foxmail.com
 */
public class SortTest {
    public static void main1(String[] args) {
        List<String> metaIdSet = Lists.newArrayList();
        metaIdSet.add("1235");
        metaIdSet.add("7896");
        metaIdSet.add("4568");
        metaIdSet.add("451234");
//        String val = JSON.common().toJSON(metaIdSet.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        Set<Long> list = metaIdSet.stream().filter(f -> {
            return Integer.valueOf(f) > 100;
        }).map(Long::valueOf).collect(Collectors.toSet());

        System.out.println(list.contains(Long.valueOf(451234)));
    }


    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        String ap = PinyinHelper.toHanYuPinyinString("{vpn}", new HanyuPinyinOutputFormat(), "", false);
        System.out.println(ap);
        List<String> list = Lists.newArrayList();
        list.add("$app");
        list.add("$app中国");
        list.add("中国");
        list.add("{属性}");
        list.add("{中国}");
        list.add("【vpn】属性");
        list.add("tg");
        list.add("ad");
        list.add("app 中国");
        list.add("埃及");
        list.add("45");
        list.add("1123");
        String val = JSON.common().toJSON(list.stream().sorted(SortTest::compare).collect(Collectors.toList()));
        System.out.println(val);
    }

    private static Pattern pcn = Pattern.compile("^[\u4e00-\u9fa5]");
    private static Pattern pnum = Pattern.compile("^[a-zA-Z0-9]");
    private static Pattern psc = Pattern.compile("^[^a-zA-Z\u4e00-\u9fa5]");

    private static int compare(String a, String b) {
        try {
            int sa = psc.matcher(a).find() ? 2 : (pcn.matcher(a).find() ? 1 : 0);
            int sb = psc.matcher(b).find() ? 2 : (pcn.matcher(b).find() ? 1 : 0);
            int diff = sa - sb;
            if (diff > 0) {
                return -1;
            } else if (diff < 0) {
                return 1;
            } else {
                switch (sa) {
                    case 2:
                    case 0:
                        return a.compareTo(b);
                    case 1:
                        String ap = PinyinHelper.toHanYuPinyinString(a, new HanyuPinyinOutputFormat(), "", false);
                        String bp = PinyinHelper.toHanYuPinyinString(b, new HanyuPinyinOutputFormat(), "", false);
                        return ap.compareTo(bp);
                }
            }
        } catch (Exception e) {

        }
        return 0;
    }
}

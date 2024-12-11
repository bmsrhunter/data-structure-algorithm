package wifiin;
/*
 * 创建人：baimiao
 * 创建时间：2024/4/12 11:34
 *
 */

import com.google.common.collect.Lists;
import com.wifiin.common.JSON;

import java.util.List;
import java.util.stream.Collectors;

public class Chaos {
    public static void main(String[] args) {
        List<Integer> userIds = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 1, 22, 3, 4, 5, 6, 7);
        List<Integer> sub = Lists.newArrayList(1, 5, 8);
        int size = userIds.size();
   /*     for (int i = 0; i < size; i = i + 10) {
            int end = (i + 10) < size ? (i + 10) : size;
            List<Integer> subUsers = userIds.subList(i, end);
            subUsers.removeAll(subUsers);
            System.out.println(JSON.common().toJSON(subUsers));
            System.out.println(JSON.common().toJSON(userIds));
        }*/

        for (int i = 0; i < size; i = i + 10) {
            List<Integer> subUsers = userIds.stream().skip(i).limit(10).collect(Collectors.toList());
            subUsers.removeAll(sub);
            System.out.println(JSON.common().toJSON(subUsers));
        }
    }
}

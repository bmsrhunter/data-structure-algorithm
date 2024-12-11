
/*
 * 创建人：baimiao
 * 创建时间：2023/9/28 14:47
 *
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wifiin.common.JSON;

public class JSONP {

    public static void main(String[] args) {
        String json = "{\"A\":\"yes\",\"B\":\"love\"}";
        Option opt = JSON.common().parse(json, Option.class);
        System.out.println(opt.getA());
        System.out.println(opt.getB());
    }

    public static class Option {

        @JsonProperty("A")
        private String a;
        @JsonProperty("B")
        private String b;
        @JsonProperty("C")
        private String c;
        private String d;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }
    }
}

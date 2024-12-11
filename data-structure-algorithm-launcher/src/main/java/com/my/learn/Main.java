package com.my.learn;

//import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.wifiin.springboot.SpringBootLauncher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by baimiao on 2019/8/5.
 */
@SpringBootApplication(scanBasePackages={"com.my.learn.**"})
@EnableAspectJAutoProxy
//@EnableDubboConfiguration
public class Main {
    public synchronized static void main(String[] args) {
        try{
            SpringBootLauncher.launch("learn-model",Main.class,args);
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}

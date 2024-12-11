package com.my.learn.common.beans;

import com.google.common.collect.Lists;
import com.wifiin.log.LoggerFactory;
import com.wifiin.multilanguage.rpc.MultiLangRPC;
import com.wifiin.multilanguage.rpc.model.vo.MultiLangData;
import com.wifiin.multilanguage.rpc.model.vo.MultiLangResponse;
import com.wifiin.spring.ApplicationContextHolder;
import com.wifiin.springmvc.*;
import com.wifiin.springmvc.aop.ControllerLogAspect;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.ConnectorStatistics;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.List;

@Configuration
@EnableWebMvc
//@AutoConfigureAfter(MybatisAutoConfiguration.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
//@EnableDubboConfiguration
public class CommonBeans implements WebMvcConfigurer, ApplicationContextAware {
    private static final LoggerFactory.Logger log = LoggerFactory.getLogger(CommonBeans.class);
    private static CommonBeans instance;

    public static CommonBeans getInstance() {
        return instance;
    }

    private ApplicationContext appctx;

    public CommonBeans() {
        instance = this;
    }

    public Environment env() {
        return appctx.getEnvironment();
    }

    @Bean
    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "mysqlDataSource")
    @Qualifier("mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }


//    @Bean(name = "mysqlSessionFactory")
//    @Qualifier("mysqlSessionFactory")
//    @Primary
//    public SqlSessionFactoryBean mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setVfs(SpringBootVFS.class);
//        bean.setTypeAliasesPackage("com.wifiin.speedin.**.model");
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/wifiin/speedin/**/dao/*DAO.xml"));
//        return bean;
//    }

//    @Bean(name = "mysqlMapperScannerConfigurer")
    /*@Qualifier("mysqlMapperScannerConfigurer")
    @Primary
    public static MapperScannerConfigurer mysqlMybatisScanner() {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setBasePackage("com.my.learn.**.dao");
        scanner.setSqlSessionFactoryBeanName("mysqlSessionFactory");
        return scanner;
    }*/



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowedHeaders("*").allowCredentials(true).maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**.*").addResourceLocations("classpath:/META-INF/resources/").addResourceLocations("classpath:/resources/").addResourceLocations("classpath:/static/").addResourceLocations("classpath:/public/");
    }

    @Component
    public static class GracefulShutdownUndertow implements ApplicationListener<ContextClosedEvent> {
        @Autowired
        private GracefulShutdownUndertowWrapper gracefulShutdownUndertowWrapper;
        @Autowired
        private ServletWebServerApplicationContext context;

        @Override
        public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
            gracefulShutdownUndertowWrapper.getGracefulShutdownHandler().shutdown();
            try {
                UndertowServletWebServer webServer = (UndertowServletWebServer) context.getWebServer();
                Field field = webServer.getClass().getDeclaredField("undertow");
                field.setAccessible(true);
                Undertow undertow = (Undertow) field.get(webServer);
                List<Undertow.ListenerInfo> listenerInfo = undertow.getListenerInfo();
                Undertow.ListenerInfo listener = listenerInfo.get(0);
                ConnectorStatistics connectorStatistics = listener.getConnectorStatistics();
                while (connectorStatistics.getActiveConnections() > 0) {
                }
            } catch (Exception e) {
                // Application Shutdown
            }
        }
    }

    @Component
    public static class GracefulShutdownUndertowWrapper implements HandlerWrapper {
        private GracefulShutdownHandler gracefulShutdownHandler;

        @Override
        public HttpHandler wrap(HttpHandler handler) {
            if (gracefulShutdownHandler == null) {
                this.gracefulShutdownHandler = new GracefulShutdownHandler(handler);
            }
            return gracefulShutdownHandler;
        }

        public GracefulShutdownHandler getGracefulShutdownHandler() {
            return gracefulShutdownHandler;
        }
    }

    @Bean
    public UndertowServletWebServerFactory servletWebServerFactory(GracefulShutdownUndertowWrapper wrapper) {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addDeploymentInfoCustomizers(deploymentInfo -> deploymentInfo.addOuterHandlerChainWrapper(wrapper));
        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_STATISTICS, true));
        return factory;
    }

    @Bean
    public ControllerLogAspect controllerLogAspect() {
        return new ControllerLogAspect();
    }

    @Bean
    public Base64Converter base64Converter() {
        return new Base64Converter();
    }

    @Bean
    public NoneConverter noneConverter() {
        return new NoneConverter();
    }

    @Bean
    public NoneCryptor noneCryptor() {
        return new NoneCryptor();
    }

//    @Bean(name = "sentinelPool")
  /*  @Qualifier("sentinelPool")
    @Primary
    public RedisSentinelPool redisSentinelPool() {
        Environment env = env();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(env.getProperty("spring.redis.max-total", int.class, 1000));
        config.setMaxIdle(env.getProperty("spring.redis.max-idle", int.class, 500));
        config.setMaxWaitMillis(env.getProperty("spring.redis.max-wait-millis", long.class, 10000L));
        config.setMinIdle(env.getProperty("spring.redis.min-idle", int.class, 10));
        config.setTestOnBorrow(env.getProperty("spring.redis.test-on-borrow", boolean.class, true));
        config.setTestOnReturn(env.getProperty("spring.redis.test-on-return", boolean.class, false));
        config.setTestWhileIdle(env.getProperty("spring.redis.test-while-idle", boolean.class, true));
        String hosts = env.getProperty("spring.redis.host");
        String[] hostArray = Help.split2Array(hosts, ",");
        Set<String> hostSet = Sets.newHashSet(hostArray);
        String password = env.getProperty("spring.redis.password");
        int timeout = env.getProperty("spring.redis.timeout", int.class, 10000);
        RedisSentinelPool sentinel = new RedisSentinelPool(hostSet, password, timeout, config);
        return sentinel;
    }
*/
    /*@Bean
    public RedisConnection jedisConnection(@Qualifier("sentinelPool") RedisSentinelPool sentinelPool) {
        Environment env = env();
        JedisConnection jedis = new JedisConnection();
        jedis.setToThrowOnError(env.getProperty("spring.redis.to-throw-on-error", boolean.class, true));
        jedis.setJedisPool(sentinelPool);
        return jedis;
    }*/
//    @Bean
  /*  public RedisConnection jedisConnection() {
        Environment env = env();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(env.getProperty("spring.redis.max-total", int.class, 1000));
        config.setMaxIdle(env.getProperty("spring.redis.max-idle", int.class, 500));
        config.setMaxWaitMillis(env.getProperty("spring.redis.max-wait-millis", long.class, 10000L));
        config.setMinIdle(env.getProperty("spring.redis.min-idle", int.class, 10));
        config.setTestOnBorrow(env.getProperty("spring.redis.test-on-borrow", boolean.class, true));
        config.setTestOnReturn(env.getProperty("spring.redis.test-on-return", boolean.class, false));
        config.setTestWhileIdle(env.getProperty("spring.redis.test-while-idle", boolean.class, true));
        String hosts = env.getProperty("spring.redis.host");
        String[] hostArray = Help.split2Array(hosts, ",");
        Set<String> hostSet = Sets.newHashSet(hostArray);
        String password = env.getProperty("spring.redis.password");
        int timeout = env.getProperty("spring.redis.timeout", int.class, 10000);
        RedisSentinelPool sentinel = new RedisSentinelPool(hostSet, password, timeout, config);
        JedisConnection jedis = new JedisConnection();
        jedis.setToThrowOnError(env.getProperty("spring.redis.to-throw-on-error", boolean.class, true));
        jedis.setJedisPool(sentinel);
        return jedis;
    }*/
//    @Bean(name="multiLangRPC")
    @Qualifier("multiLangRPC")
    public MultiLangRPC multiLangRPC(){
        MultiLangRPC multiLangRPC = new MultiLangRPC() {
            @Override
            public MultiLangResponse queryLang(MultiLangData data) {
                MultiLangResponse response = new MultiLangResponse(1,"zh-cn");
                return response;
            }
        };
        return multiLangRPC;
    }
//    @Bean
    @AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
    public Result.MultiLangRPCGetter multiLangRPCGetter(@Qualifier("multiLangRPC")MultiLangRPC multiLangRPC){
        Result.MultiLangRPCGetter getter=new Result.MultiLangRPCGetter();
        getter.setRpc(multiLangRPC);
        return getter;
    }
    @Override
    public void configureMessageConverters(List<org.springframework.http.converter.HttpMessageConverter<?>> converters) {
        HttpMessageConverter converter=new HttpMessageConverter();
        converter.setDefaultMessageConverter(NoneConverter.class);
        converter.setDefaultCryptoType(CryptoType.NONE);
        converter.setDefaultDeSer(JSONDeSer.class);
        converter.setRequestMediaTypes(Lists.newArrayList("application/json"));
        converter.setResponseMediaType("application/json");
        converter.init();
        converters.add(0,converter);
    }

    @Override
    public void setApplicationContext(ApplicationContext appctx) throws BeansException {
        this.appctx = appctx;
        ApplicationContextHolder holder = new ApplicationContextHolder();
        holder.setApplicationContext(appctx);
    }
}

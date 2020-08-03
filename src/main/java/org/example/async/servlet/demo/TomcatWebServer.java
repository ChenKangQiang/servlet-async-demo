package org.example.async.servlet.demo;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Loader;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.util.stream.Stream;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2020/7/28
 */
public class TomcatWebServer {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir("."); // tomcat 信息保存在项目下

        StandardContext context = (StandardContext) tomcat.addContext("/access", null);
        /*
           tomcat.addWebapp的区别：
         */
        // StandardContext context = (StandardContext) tomcat.addWebapp("", "");

        context.setReloadable(false);
        // 上下文监听器
        context.addLifecycleListener(new AprLifecycleListener());

        // 注册servlet，编写servlet mapping
        tomcat.addServlet("/access", "demoServlet", new DemoServlet());
        context.addServletMappingDecoded("/demo.do", "demoServlet");

        tomcat.addServlet("/access", "asyncDemoServlet", new AsyncDemoServlet());
        context.addServletMappingDecoded("/asyncdemo.do", "asyncDemoServlet");

        tomcat.addServlet("/access", "nonBlockingAsyncDemoServlet", new NonBlockingAsyncDemoServlet());
        context.addServletMappingDecoded("/nonblockingasyncdemo.do", "nonBlockingAsyncDemoServlet");

//        tomcat.init();
        tomcat.start();
        // System.out.println(tomcat.getConnector());

        FilterMap[] filterMaps = context.findFilterMaps();
        System.out.println(filterMaps.length);

        FilterDef[] filterDefs = context.findFilterDefs();
        Stream.of(filterDefs).forEach(item -> item.setAsyncSupported("true"));
        System.out.println(filterMaps.length);

        Loader loader = context.getLoader();
        System.out.println(loader);

        ClassLoader classLoader = loader.getClassLoader();
        System.out.println(classLoader);

        // 主线程启动
//        tomcat.getServer().await();

        // 使用守护线程启动Tomcat
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tomcat.getServer().await();
            }
        });
        thread.start();
    }

}

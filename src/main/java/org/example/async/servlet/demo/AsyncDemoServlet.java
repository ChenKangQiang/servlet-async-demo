package org.example.async.servlet.demo;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2020/7/29
 */
@WebServlet(value = "/nonBlockingThreadPoolAsync", asyncSupported = true)
public class AsyncDemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1217614583382821373L;

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200,
            50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();

        // 使用业务自定义线程池
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    int result = new LongRunningProcess().doSomething();
//                    asyncContext.getResponse().getWriter().write("Hello World Async Demo, Process Time is " + result + " ms");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        // 使用Tomcat容器内部的线程池，默认还是会使用Tomcat的请求处理线程，格式http-nio-8080-exec
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName());
                    int result = new LongRunningProcess().doSomething();
                    asyncContext.getResponse().getWriter().write("Hello World Async Demo, Process Time is " + result + " ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

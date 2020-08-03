package org.example.async.servlet.demo;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
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
@WebServlet(value = "/nonBlockingAsyncDemoServlet", asyncSupported = true)
public class NonBlockingAsyncDemoServlet extends HttpServlet {
    private static final long serialVersionUID = -5971111426847476112L;

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200,
            50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();
        ServletInputStream inputStream = req.getInputStream();

        // 添加IO监听器，只有IO完成时调用，实现非阻塞IO
        inputStream.setReadListener(new ReadListener() {
            @Override
            public void onDataAvailable() throws IOException {

            }

            @Override
            public void onAllDataRead() throws IOException {
                executor.execute(() -> {
                    try {
                        int result = new LongRunningProcess().doSomething();
                        asyncContext.getResponse().getWriter().write("Hello World Async Demo, Process Time is " + result + " ms");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    asyncContext.complete();
                });
            }

            @Override
            public void onError(Throwable t) {
                asyncContext.complete();
            }
        });
    }
}

package org.example.async.servlet.demo;

import javax.servlet.WriteListener;
import java.io.IOException;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2020/7/29
 */
public class DemoWriterListener implements WriteListener {

    @Override
    public void onWritePossible() throws IOException {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}

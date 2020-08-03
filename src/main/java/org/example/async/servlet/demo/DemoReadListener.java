package org.example.async.servlet.demo;

import javax.servlet.ReadListener;
import java.io.IOException;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2020/7/29
 */
public class DemoReadListener implements ReadListener {

    @Override
    public void onDataAvailable() throws IOException {

    }

    @Override
    public void onAllDataRead() throws IOException {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}

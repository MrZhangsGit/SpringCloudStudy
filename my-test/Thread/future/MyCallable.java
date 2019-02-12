package Thread.future;

import java.util.concurrent.Callable;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/2/12
 */
public class MyCallable implements Callable<String> {
    private long waitTime;

    public MyCallable(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        return Thread.currentThread().getName();
    }
}

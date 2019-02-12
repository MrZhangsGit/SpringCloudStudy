package Thread.future;

import java.util.concurrent.Callable;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/2/12
 */
public class CallableDemo1 implements Callable<String> {
    @Override
    public String call() throws Exception {
        String th_name = Thread.currentThread().getName();
        System.out.println(th_name + "遭遇大规模敌军突袭..." + System.currentTimeMillis());
        System.out.println(th_name + "迅速变换阵型...");
        System.out.println(th_name + "极速攻杀敌军...");
        return "敌军损失惨重，我军大获全胜";
    }
}

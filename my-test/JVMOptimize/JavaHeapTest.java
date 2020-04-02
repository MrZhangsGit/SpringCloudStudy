package JVMOptimize;

/**
 * @author zhangs
 * @Description
 *  查看程序的JVM内存
 * @createDate 2020/3/27
 */
public class JavaHeapTest {
    public final static int OUTOFMEMORY = 200000000;

    private String oom;

    private int length;

    StringBuffer tempOOM = new StringBuffer();

    public JavaHeapTest(int leng) {
        this.length = leng;

        int i = 0;
        while (i < leng) {
            i++;
            try {
                /**
                 * 在JVisualVM中heap dump会看到char[]的实例数最多，其中的value是tempOOM，是因为tempOOM一直被this.oom所引用，而StringBuffer底层又是char[]实现的。
                 * 在tempOOM = null;之后可以看到实例数最多的char[]中value是oom
                 */
                tempOOM.append("a");
                /**
                 * 打印对象的内存地址
                 * System.out.println("---" + System.identityHashCode(tempOOM));
                 */
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                break;
            }
        }
        this.oom = tempOOM.toString();
        tempOOM = null;
    }

    public String getOom() {
        return oom;
    }

    public int getLength() {
        return length;
    }

    public static void main(String[] args) {
        JavaHeapTest javaHeapTest = new JavaHeapTest(OUTOFMEMORY);
        System.out.println(javaHeapTest.getOom().length());
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package ScriptEngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/4/15
 */
public class ScriptEngineDemo {
    public static void main(String[] args) {
        String s1 = "true";
        String o = "=";
        String s2 = "true";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(s1).append(o).append(s2);
        System.out.println(stringBuffer.toString());

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("js");
        /*try {
            System.out.println(scriptEngine.eval(stringBuffer.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        for (int i=0;i<10;i++) {
            switch (i) {
                case 3:
                    int a = i;
                    System.out.println("break switch3:" + a);
                    break;
                case 5:
                    a = i;
                    System.out.println("break switch5:" + a);
                    break;
            }
//            System.out.println("i:" + i);
        }
    }
}

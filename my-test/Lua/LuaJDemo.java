package Lua;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.net.URL;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/2/26
 */
public class LuaJDemo {
    public static void main(String[] args) {
        luaTest6();
    }

    public static void luaTest1() {
        String luaStr = "print 'hello, world!'";
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.load(luaStr);
        chunk.call();
    }

    public static String getLuaFile(String fileName) {
        LuaJLoadFile luaJLoadFile = new LuaJLoadFile();
        return luaJLoadFile.getFileName(fileName);
    }

    public static LuaValue getLvByMethodName(String luaFileName, String methodName) {
        Globals globals = JsePlatform.standardGlobals();
        globals.loadfile(luaFileName).call();
        return globals.get(LuaValue.valueOf(methodName));
    }

    /**
     * 无返回对象的调用情况
     */
    public static void luaTest2() {
        String luaFileName = getLuaFile("Lua/hello.lua");
        if (StringUtils.isEmpty(luaFileName)) {
            return;
        }
        Globals globals = JsePlatform.standardGlobals();
        globals.loadfile(luaFileName).call();
        LuaValue func = globals.get(LuaValue.valueOf("helloWithoutTranscoder"));
        String result = func.call().toString();
        System.out.println("result---:" + result);
    }

    /**
     * 有返回的无参函数
     */
    public static void luaTest3() {
        String luaFileName = getLuaFile("Lua/hello.lua");
        if (StringUtils.isEmpty(luaFileName)) {
            return;
        }
        Globals globals = JsePlatform.standardGlobals();
        LuaValue transcoderObj = globals.loadfile(luaFileName).call();
        LuaValue func = transcoderObj.get(LuaValue.valueOf("hello"));
        String result = func.call().toString();
        System.out.println("result---:" + result);
    }

    /**
     * 有返回(String)有参函数
     */
    public static void luaTest4() {
        String luaFileName = getLuaFile("Lua/hello.lua");
        if (StringUtils.isEmpty(luaFileName)) {
            return;
        }
        Globals globals = JsePlatform.standardGlobals();
        LuaValue transcoderObj = globals.loadfile(luaFileName).call();
        LuaValue func = transcoderObj.get(LuaValue.valueOf("test"));
        String result = func.call(LuaValue.valueOf("zhangs")).toString();
        System.out.println("result---:" + result);
    }

    /**
     * 返回对象
     */
    public static void luaTest5() {
        String luaFileName = getLuaFile("Lua/hello.lua");
        if (StringUtils.isEmpty(luaFileName)) {
            return;
        }
        Globals globals = JsePlatform.standardGlobals();
        LuaValue transcoderObj = globals.loadfile(luaFileName).call();
        LuaValue func = transcoderObj.get(LuaValue.valueOf("getInfo"));
        LuaValue hTable = func.call();
        System.out.println("hTable--:" + JSON.toJSONString(hTable));

        System.out.println("userId:" + JSON.toJSONString(hTable.get("userId").toString()));
        /*String servicesData = hTable.get("services").toString();
        System.out.println("services:" + JSON.toJSONString(servicesData));*/
    }

    public static void luaTest6() {
        String luaFileName = getLuaFile("Lua/hello.lua");
        if (StringUtils.isEmpty(luaFileName)) {
            return;
        }
        Globals globals = JsePlatform.standardGlobals();
        LuaValue transcoderObj = globals.loadfile(luaFileName).call();
        LuaValue func = transcoderObj.get(LuaValue.valueOf("readInfo"));

        /**
         * CoerceJavaToLua.coerce(javaObject) 经测试，可以直接调用该方法将一个java对象转化为luaValue，
         * 但是嵌套model情况下的java对象转换有问题，因此这里直接使用LuaValue手动去包装
         */
        LuaValue luaValue = new LuaTable();
        luaValue.set("userId", "123456");
        String userId = func.invoke(luaValue).toString();
        System.out.println("userId:" + userId);
    }
}

class LuaJLoadFile {
    public String getFileName(String fileName) {
        try {
            URL url = getClass().getClassLoader().getResource(fileName);
            String luaFile = url.getPath();
            //String luaFile = getClass().getClassLoader().getResource(fileName).toURI().getPath();
            return luaFile;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
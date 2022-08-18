package org.example.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 首先要维护一个map存储className和class的对应关系，这样在收到请求时可以通过className找到对应的类，再通过反射获取对应的方法进行调用
 * 在我们的dubbo框架中封装这么一个map结构供provider使用
 */
public class LocalRegister {

    private static Map<String, Object> map = new HashMap<>();

    public static void register(String className, Object impl) {
        map.put(className, impl);
    }

    public static Object get(String className) {
        return map.get(className);
    }
}

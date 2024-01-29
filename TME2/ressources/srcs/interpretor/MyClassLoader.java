package srcs.interpretor;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends ObjectInputStream {
    public MyClassLoader(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(final ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        String className = objectStreamClass.getName();
        try {
            return ClassLoader.getSystemClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            // 处理类找不到的情况，可以尝试加载默认类或其他处理
            // return findClass(className);
        }
        return null;
    }
}


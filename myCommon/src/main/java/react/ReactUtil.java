package react;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 反射工具
 * @author: ricci
 * @date: 2021-11-18 14:49:55
 */
public class ReactUtil {
    /**
     * 获得某路径下的所有继承某接口或抽象类的实体类
     *
     * @param _interfaceType 继承某接口或抽象类
     * @param _pack          路径
     * @return List<Class < ?>>
     */
    public static List<Class<?>> getPathAllClassWithInterface(Class<?> _interfaceType, String _pack) {
        List<Class<?>> classList = new ArrayList<>();
        //不是接口或者抽象类
        if (!Modifier.isInterface(_interfaceType.getModifiers())
                && !Modifier.isAbstract(_interfaceType.getModifiers())) {
            return classList;
        }

        Set<Class<?>> classes = getAllClassByPath(_pack);
        for (Class<?> cls : classes) {
            if (!_interfaceType.isAssignableFrom(cls)) {
                continue;
            }
            //跳过接口和抽象类
            if (Modifier.isInterface(_interfaceType.getModifiers())
                    || Modifier.isAbstract(_interfaceType.getModifiers())) {
                continue;
            }
            //跳过自己
            if (_interfaceType.equals(cls)) {
                continue;
            }
            classList.add(cls);
        }
        return classList;
    }

    /**
     * 获取某个包下的所有 class 文件
     *
     * @param _packageName 包名
     * @return Set<Class < ?>>
     */
    public static Set<Class<?>> getAllClassByPath(String _packageName) {
        //替换路径分隔符
        String packPath = _packageName.replace('.', '/');
        Set<Class<?>> classes = new HashSet<>();
        //根据当前线程上下文类加载器得到路径下的所有类
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packPath);
            if (resources == null) {
                return classes;
            }
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                switch (protocol) {
                    case "file":
                        String urlFile = URLDecoder.decode(url.getFile(), "UTF-8");
                        scanFileGetAllClass(packPath, urlFile, classes);
                        break;
                    case "jar":
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 扫描某个文件，查找所有class
     *
     * @param _packageName 包名
     * @param _urlFile     文件路径
     * @param _classes     class集合
     */
    public static void scanFileGetAllClass(String _packageName, String _urlFile, Set<Class<?>> _classes) {
        File file = new File(_urlFile);
        if (!file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        //遍历文件树
        for (File subFile : files) {
            if (subFile == null) {
                continue;
            }
            //是目录不是文件
            if (file.isDirectory()) {
                scanFileGetAllClass(_packageName + "." + file.getName(), file.getAbsolutePath(), _classes);
            } else {

                String javaFileName = file.getName();
                //是一个 java 文件
                if (javaFileName.endsWith(".class")) {
                    javaFileName = javaFileName.substring(0, javaFileName.length() - 6);
                    //通过文件名调用类加载器，加载一个class
                    try {
                        _classes.add(ClassLoader.getSystemClassLoader().loadClass(javaFileName));
                    } catch (ClassNotFoundException e) {
                        System.out.println("class load fail name: " + javaFileName);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

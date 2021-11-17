package configReader;

import java.util.Properties;
import java.util.function.Function;

/**
 * @description: 配置文件读取
 * @author: ricci
 * @date: 2021-11-17 20:30:21
 */
public class RiConfReader {

    /**
     * 读取一个 int 值
     *
     * @param _property   配置文件
     * @param _name       配置字段名
     * @param _defaultVal 默认值
     * @return 返回值
     */
    public static int readInt(Properties _property, String _name, int _defaultVal) {
        return commRead(_property, _name, _defaultVal, Integer::parseInt);
    }

    /**
     * 读取一个 long 值
     *
     * @param _property   配置文件
     * @param _name       配置字段名
     * @param _defaultVal 默认值
     * @return 返回值
     */
    public static long readLong(Properties _property, String _name, long _defaultVal) {
        return commRead(_property, _name, _defaultVal, Long::parseLong);
    }

    /**
     * 读取一个 string 值
     *
     * @param _property   配置文件
     * @param _name       配置字段名
     * @param _defaultVal 默认值
     * @return 返回值
     */
    public static String readStr(Properties _property, String _name, String _defaultVal) {
        return commRead(_property, _name, _defaultVal, s -> s);
    }

    /**
     * 通用的读取
     *
     * @param _property   配置文件
     * @param _name       配置字段名
     * @param _defaultVal 默认值
     * @param _function   读取方法
     * @param <R>         返回值类型
     * @return R
     */
    public static <R> R commRead(Properties _property, String _name, R _defaultVal, Function<String, R> _function) {
        String property = _property.getProperty(_name);
        if (property == null || property.isEmpty()) {
            return _defaultVal;
        }
        try {
            R read = _function.apply(property);
            System.out.println("[config] " + _name + " = " + read);
            return read;
        } catch (Exception e) {
            System.out.println("[config Exception] " + _name + " = " + _defaultVal);
            e.printStackTrace();
        }
        return _defaultVal;
    }
}

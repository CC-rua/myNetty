package ref;

import configReader.RiConfReader;
import log.CommLog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description:
 * @author: ricci
 * @date: 2021-11-19 15:21:13
 */
public class GameResConf {
    private static GameResConf g_instance = new GameResConf();

    public static GameResConf getInstance() {
        return g_instance;
    }

    private String _m_sRefPath;


    public String getRefPath() {
        return _m_sRefPath;
    }

    /*******************
     * 初始化函数
     *
     */
    public boolean init() {
        if (!_init("./conf/GameResConf.properties", false))
            return false;

        _init("./customConf/GameResConf.properties", true);

        return true;
    }

    /*******************
     * 初始化单个配置文件的处理函数
     * @param _filePath 文件位置
     * @param _bOptional 是否忽略错误数据
     */
    protected boolean _init(String _filePath, boolean _bOptional) {
        Properties properties = new Properties();

        InputStream propertiesInputStream = null;

        try {
            propertiesInputStream = new FileInputStream(_filePath);
        } catch (Exception e) {
            if (_bOptional) {
                CommLog.error("ignore bad config file:{}", _filePath);
            } else {
                e.printStackTrace();
            }
        }

        if (null == propertiesInputStream) {
            return false;
        }

        // 载入配置文件
        try {
            properties.load(propertiesInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("[Conf init] Start load GameRes Properties ... ...");

            //配置文件地址
            _m_sRefPath = RiConfReader.readStr(properties, "RiConfReader.refPath", "");

            System.out.println("[Conf init] Finish load GameRes Properties ... ...");
        } catch (Exception e) {
            System.out.println("[Conf Init Error] Load GameRes Properties Error!!");
            e.printStackTrace();
        } finally {
            try {
                propertiesInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}

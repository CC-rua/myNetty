package refData;

import java.io.File;
import java.util.Map;

/**
 * @description: 配置资源数据总管理器
 * @author: ricci
 * @date: 2021-11-17 19:24:58
 */
public abstract class _ARefDataMgr {
    /**
     * 资源数据管理器集合
     */
    private Map<_ARefIndex, _ARefContainerMgr> _m_RefMgrMap;

    /**
     * 初始化配置文件
     */
    public abstract void initConfig();

    /**
     * 获得配置资源文件所在目录
     *
     * @return String
     */
    public abstract String getRefPath();

    /**
     * 初始化
     *
     * @return 初始化方法
     */
    public boolean init() {
        try {
            initConfig();
            if (!configAssert()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置检测
     *
     * @return boolean
     */
    public boolean configAssert() {
        File file = new File(getRefPath());
        return file.exists() && file.isDirectory();
    }

    /**
     * 加载配置文件到管理器集合
     */
    public void loadMain() {

    }

}

package ref;

import refData._ARef;
import refData._ARefContainerMgr;
import refData._ARefDataMgr;

import java.io.File;

/**
 * @description: 配置文件管理器
 * @author: ricci
 * @date: 2021-11-19 15:12:22
 */
public class RefDataMgr extends _ARefDataMgr<_ARefContainerMgr> {
    private String _m_refDataPath = "../data" + File.pathSeparator + "refData";

    private static RefDataMgr _m_instance = new RefDataMgr();

    public static RefDataMgr getInstance() {
        return _m_instance;
    }

    @Override
    public void initConfig() {
        GameResConf.getInstance().init();
        String refPath = GameResConf.getInstance().getRefPath();
        if (refPath == null || refPath.isEmpty()) {
            _m_refDataPath = refPath;
        }
    }

    @Override
    public String getRefPath() {
        return _m_refDataPath;
    }

    @Override
    public Class<? extends _ARef> getRefDataClassName() {
        return null;
    }
}

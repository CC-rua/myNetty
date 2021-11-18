package refData;

import react.ReactUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @description: 配置资源数据总管理器
 * @author: ricci
 * @date: 2021-11-17 19:24:58
 */
public abstract class _ARefDataMgr extends _ARef {
    /**
     * 资源数据管理器集合
     */
    private Map<RefIndex, _ARefContainerMgr> _m_RefMgrMap;

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
     * Ref数据继承的超类
     *
     * @return Class<? extends _ARefData>
     */
    public abstract Class<? extends _ARef> getRefDataClassName();

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
            if (loadMain()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
    public boolean loadMain() {
        List<Class<?>> classes = ReactUtil.getPathAllClassWithInterface(getRefDataClassName(), getRefPath());
        for (Class<?> cls : classes) {
            _ARefData c = null;
            //初始化一个实例，强转一个 _ARefData 主要是为了知道它的类型
            try {
                Object o = cls.newInstance();
                if (o instanceof _ARefData) {
                    c = (_ARefData) o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (c == null) {
                continue;
            }
            //该 RefData 的类型
            ERefType refType = c.getRefType();
            //检查是否已经注册过了
            if (lookupContainerByRefType(refType) != null) {
                System.out.println("RefDataMgr Had load Ref type: " + refType);
                return false;
            }
            //通过配置文件初始化
            _ARefContainerMgr mgr = loadRefFromFileByClass(c);
            RefIndex mgrIndex = makeMgrIndex(mgr);
            _m_RefMgrMap.put(mgrIndex, mgr);
        }
        return true;
    }

    /**
     * 构造 ContainerMgr 的索引信息
     *
     * @param _mgr _ARefContainerMgr
     * @return RefIndex
     */
    public RefIndex makeMgrIndex(_ARefContainerMgr _mgr) {
        if (_mgr == null) {
            return null;
        }
        return RefIndex.builder()
                ._m_RefType(_mgr.getRefType())
                ._m_RefTableName(_mgr.getTableName())
                .build();
    }

    /**
     * 通过加载对应的配置文件 初始化一个 _ARefContainerMgr
     *
     * @param _cls _ARefData 配置文件单元
     * @return _ARefContainerMgr
     */
    public _ARefContainerMgr loadRefFromFileByClass(_ARefData _cls) {
        //检查是否带有配表注解
        RefTable tableAnnotation = _cls.getClass().getAnnotation(RefTable.class);
        if (tableAnnotation == null) {
            return null;
        }
        return null;
    }

    /**
     * 通过类型查找 RefContainerMgr
     *
     * @param _refType 配置类型
     * @return _ARefContainerMgr
     */
    public _ARefContainerMgr lookupContainerByRefType(ERefType _refType) {
        for (Entry<RefIndex, _ARefContainerMgr> entry : _m_RefMgrMap.entrySet()) {
            if (entry == null) {
                continue;
            }
            if (entry.getKey().is(_refType)) {
                return entry.getValue();
            }
        }
        return null;
    }
}

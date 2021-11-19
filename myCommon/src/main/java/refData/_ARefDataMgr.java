package refData;

import file.CommFile;
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
public abstract class _ARefDataMgr<T extends _ARefData> extends _ARef {
    /**
     * 资源数据管理器集合
     */
    private Map<RefDataIndex, T> _m_RefMgrMap;

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
            //通过配置内容解析类初始化
            T mgr = loadRefFromFileByClass(c);
            RefDataIndex mgrIndex = makeMgrIndex(mgr);
            _m_RefMgrMap.put(mgrIndex, mgr);
        }
        return true;
    }

    /**
     * 构造 ContainerMgr 的索引信息
     *
     * @param _mgr T
     * @return RefDataIndex
     */
    public RefDataIndex makeMgrIndex(T _mgr) {
        if (_mgr == null) {
            return null;
        }
        return RefDataIndex.builder()
                ._m_RefType(_mgr.getRefType())
                ._m_RefTableName(_mgr.getTableName())
                ._m_refData(_mgr)
                .build();
    }

    /**
     * 通过加载对应的配置文件 初始化一个 T
     *
     * @param _cls _ARefData 配置文件单元
     * @return T
     */
    public T loadRefFromFileByClass(_ARefData _cls) {
        //检查是否带有配表注解
        RefTable tableInfo = _cls.getClass().getAnnotation(RefTable.class);
        boolean isEmptyTable = false;
        if (tableInfo != null) {
            isEmptyTable = tableInfo.emptyTable();
        }
        T mgr = _cls.createContainerMgr();
        mgr.setOwner(this);
        if (!isEmptyTable) {
            //拼装出文件所在路径
            String path = String.format("%s%c%s.txt", getRefPath(), File.separatorChar, mgr.getTableName().toLowerCase());
            //读取文件内容加载配置
            loadTableFromFile(_cls, mgr, path);
        }
        return mgr;
    }

    /**
     * 读取文件内存加载配表
     *
     * @param _cls  配置文件对应的数据解析类
     * @param _mgr  数据解析类的管理器
     * @param _path 文件所在位置
     */
    public void loadTableFromFile(_ARefData _cls, T _mgr, String _path) {
        String fileStr = CommFile.getStringFromFile(_path);
        if (fileStr == null || fileStr.isEmpty()) {
            System.out.println("txt is empty for file: " + _path);
            return;
        }
        //通过字符数据加载
        loadTableFromStr(_cls, _mgr, fileStr);
    }

    /**
     * 通过字符数据加载配表
     *
     * @param _cls     配置文件对应的数据解析类
     * @param _mgr     数据解析类的管理器
     * @param _fileStr 文件字符
     */
    private void loadTableFromStr(_ARefData _cls, T _mgr, String _fileStr) {

    }

    /**
     * 通过类查找 T
     *
     * @param _refData 配置文件类
     * @return T
     */
    public T lookupContainerByClass(_ARefData _refData) {
        for (Entry<RefDataIndex, T> entry : _m_RefMgrMap.entrySet()) {
            if (entry == null) {
                continue;
            }
            if (!entry.getKey().is(_refData)) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }

    /**
     * 通过类型查找 RefContainerMgr
     *
     * @param _refType 配置类型
     * @return T
     */
    public T lookupContainerByRefType(ERefType _refType) {
        for (Entry<RefDataIndex, T> entry : _m_RefMgrMap.entrySet()) {
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

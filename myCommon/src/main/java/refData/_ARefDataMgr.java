package refData;

import file.CommFile;
import react.ReactUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 配置资源数据总管理器
 * @author: ricci
 * @date: 2021-11-17 19:24:58
 */
public abstract class _ARefDataMgr extends _ARef {
    /**
     * 资源数据管理器集合
     */
    private final Map<RefDataIndex, _ARefContainerMgr<? extends _ARef>> _m_RefMgrMap = new ConcurrentHashMap<>();

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
     * @return Class<? extends _ARef>
     */
    public abstract Class<? extends _ARef> getRefDataClass();

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
            if (!loadMain()) {
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
        List<Class<?>> classes = ReactUtil.getPathAllClassWithInterface(getRefDataClass(), getRefDataClass().getPackage().getName());
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
            _ARefContainerMgr<? extends _ARef> mgr = loadRefFromFileByClass(cls);
            RefDataIndex mgrIndex = makeMgrIndex(mgr);
            _m_RefMgrMap.put(mgrIndex, mgr);
        }
        return true;
    }

    /**
     * 构造 ContainerMgr 的索引信息
     *
     * @param _mgr _ARefContainerMgr<? extends _ARef>
     * @return RefDataIndex
     */
    public RefDataIndex makeMgrIndex(_ARefContainerMgr<? extends _ARef> _mgr) {
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
     * 通过加载对应的配置文件 初始化一个 _ARefData
     *
     * @param _cls _ARefContainerMgr<? extends _ARef> 配置文件单元
     * @return _ARefData
     */
    public _ARefContainerMgr<? extends _ARef> loadRefFromFileByClass(Class<?> _cls) {
        //检查是否带有配表注解
        _ARefData cls = null;
        try {
            cls = (_ARefData) _cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cls == null) {
            return null;
        }
        RefTable tableInfo = cls.getClass().getAnnotation(RefTable.class);
        boolean isEmptyTable = false;
        if (tableInfo != null) {
            isEmptyTable = tableInfo.emptyTable();
        }
        _ARefContainerMgr<? extends _ARef> mgr = cls.createContainerMgr();
        mgr.setOwner(this);
        if (!isEmptyTable) {
            //拼装出文件所在路径
            String path = String.format("%s%c%s.txt", getRefPath(), File.separatorChar, mgr.getTableName().toLowerCase());
            //读取文件内容加载配置
            loadTableFromFile(cls, mgr, path);
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
    public void loadTableFromFile(_ARefData _cls, _ARefContainerMgr<? extends _ARef> _mgr, String _path) {
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
    private  void loadTableFromStr(_ARefData _cls, _ARefContainerMgr<? extends _ARef> _mgr, String _fileStr) {
        String[] lines = _fileStr.split("\n");
        if (lines.length <= 0) {
            return;
        }
        //表头数据
        String[] tableHead = lines[0].split("\t");
        for (int i = 1; i < lines.length; i++) {
            //清掉内容，重新生成
            Map<String, String> tempLineParamMap = new HashMap<>();//每行数据
            String[] lineParam = lines[i].split("\t");
            //遍历行数据
            for (int j = 0; j < tableHead.length; j++) {
                if (lineParam[j] == null) {
                    continue;
                }
                String contain = lineParam[j].trim();
                String head = tableHead[j].trim();
                tempLineParamMap.put(head, contain);
            }
            //设置字段值
            try {
                for (Entry<String, String> entry : tempLineParamMap.entrySet()) {
                    Field field = _cls.getClass().getField(entry.getKey());
                    field.set(_cls, entry.getValue());
                }
                _mgr.castElement(_cls);
//                _mgr.addElement(_cls);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 通过类查找 _ARefData
     *
     * @param _refData 配置文件类
     * @return _ARefContainerMgr<? extends _ARef>
     */
    public _ARefContainerMgr<? extends _ARef> lookupContainerByClass(_ARefContainerMgr<? extends _ARef> _refData) {
        for (Entry<RefDataIndex, _ARefContainerMgr<? extends _ARef>> entry : _m_RefMgrMap.entrySet()) {
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
     * @return _ARefContainerMgr<? extends _ARef>
     */
    public _ARefContainerMgr<? extends _ARef> lookupContainerByRefType(ERefType _refType) {
        for (Entry<RefDataIndex, _ARefContainerMgr<? extends _ARef>> entry : _m_RefMgrMap.entrySet()) {
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

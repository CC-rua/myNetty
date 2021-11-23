package refData;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 配置文件中的内容管理器 是整张表的抽象
 * @author: ricci
 * @date: 2021-11-17 19:58:59
 */
public abstract class _ARefContainerMgr<T extends _ARef> {
    //对象列表
    protected ArrayList<T> _m_list;
    //列表锁
    protected ReentrantLock _m_mutex;

    public _ARefContainerMgr() {
        _m_list = new ArrayList<>();
        _m_mutex = new ReentrantLock();
    }

    protected void _lock() {
        _m_mutex.lock();
    }

    protected void _unlock() {
        _m_mutex.unlock();
    }

    /**
     * 增加一行数据
     *
     * @param _element T
     */
    public abstract void addElement(T _element);

    /**
     * 设置本对象的管理器
     *
     * @param _mgr _ARefDataMgr
     */
    public abstract void setOwner(_ARefDataMgr _mgr);

    /**
     * 表名称
     *
     * @return String
     */
    public abstract String getTableName();

    /**
     * 表枚举
     *
     * @return ERefType
     */
    public abstract ERefType getRefType();

    /**
     * 嵌套类型
     *
     * @param _cls _ARefData
     * @return T
     */
    public abstract T castElement(_ARefData _cls);
}

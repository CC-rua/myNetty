package ref;

import refData._ARefContainerMgr;
import refData._ARefData;
import refData._ARefDataMgr;

/**
 * @description: 游戏内容管理器
 * @author: ricci
 * @date: 2021-11-20 11:47:26
 */
public abstract class _ARefGameContainerMgr<K, T extends _ARefGameData> extends _ARefContainerMgr<T> {
    /**
     * 拥有者
     */
    private _ARefDataMgr _m_owner;

    @Override
    public void setOwner(_ARefDataMgr _mgr) {
        _m_owner = _mgr;
    }

    @Override
    public void addElement(T _element) {
        _m_list.add(_element);
    }

    @Override
    public T castElement(_ARefData _cls) {
        return null;
    }
}

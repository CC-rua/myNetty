package refData;

/**
 * @description: 配置文件中的内容管理器 是整张表的抽象
 * @author: ricci
 * @date: 2021-11-17 19:58:59
 */
public abstract class _ARefContainerMgr<T extends _ARef> {
    public abstract void setOwner(_ARefDataMgr _mgr);

    public abstract String getTableName();

    public abstract ERefType getRefType();
}

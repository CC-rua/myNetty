package refData;

/**
 * @description: 配置文件的内容解析类 是表结构的抽象
 * @author: ricci
 * @date: 2021-11-17 19:43:48
 */
public abstract class _ARefData extends _ARef {
    /**
     * 类型
     *
     * @return ERefType
     */
    public abstract ERefType getRefType();

    /**
     * 表名
     *
     * @return String
     */
    public abstract String getTableName();

    /**
     * 创建管理该对象的管理器
     *
     * @param <T> <T extends _ARefData>
     */
    public abstract <T extends _ARefData> T createContainerMgr();

    /**
     * 设置管理该对象管理器的管理者
     * 该对象管理器 一般指 createContainerMgr() 创建出来的对象
     *
     * @param _mgr _ARefDataMgr
     */
    public abstract void setOwner(_ARef _mgr);
}

package refData;

/**
 * @description: 配置文件
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
}

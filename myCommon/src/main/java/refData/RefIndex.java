package refData;

import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @description: 配置资源索引信息
 * @author: ricci
 * @date: 2021-11-17 20:02:24
 */
@Builder
@NoArgsConstructor
public class RefIndex extends _ARef {
    /**
     * 表名
     */
    private String _m_RefTableName;

    /**
     * 类型
     */
    private ERefType _m_RefType;

    public boolean is(ERefType _refType) {
        return _m_RefType == _refType;
    }

    @Override
    public int hashCode() {
        return _m_RefType.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefIndex) {
            return ((RefIndex) obj).is(this._m_RefType);
        }
        return super.equals(obj);
    }
}

package refData;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 配置资源索引信息
 * @author: ricci
 * @date: 2021-11-17 20:02:24
 */
@Data
@Builder
public class RefDataIndex extends _ARef {
    /**
     * 表名
     */
    private String _m_RefTableName;
    /**
     * 类型
     */
    private ERefType _m_RefType;
    /**
     * 数据
     */
    private _ARefData _m_refData;

    public boolean is(ERefType _refType) {
        return _m_RefType == _refType;
    }

    public boolean is(_ARefData _refData) {
        return _m_refData.equals(_refData);
    }

    @Override
    public int hashCode() {
        return _m_RefType.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefDataIndex) {
            return ((RefDataIndex) obj).is(this._m_RefType);
        }
        return super.equals(obj);
    }

}

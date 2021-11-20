package ref;

import refData.RefTable;
import refData._ARefData;

/**
 * @description: game ref
 * @author: ricci
 * @date: 2021-11-19 17:02:37
 */
public abstract class _ARefGameData extends _ARefData {

    @Override
    public String getTableName() {
        RefTable annotation = getClass().getAnnotation(RefTable.class);
        if (annotation == null) {
            return "";
        } else {
            return annotation.tableName();
        }
    }
}

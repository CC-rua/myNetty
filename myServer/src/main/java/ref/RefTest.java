package ref;

import refData.ERefType;
import refData.RefTable;
import refData._ARef;
import refData._ARefData;

/**
 * @description: test
 * @author: ricci
 * @date: 2021-11-19 17:01:21
 */
@RefTable(tableName = "test")
public class RefTest extends _ARefGameData {
    @Override
    public ERefType getRefType() {
        return ERefType.test;
    }

    @Override
    public <T extends _ARefData> T createContainerMgr() {
        return null;
    }

    @Override
    public void setOwner(_ARef _mgr) {

    }
}

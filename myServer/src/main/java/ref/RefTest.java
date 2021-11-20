package ref;

import refData.ERefType;
import refData.RefTable;
import refData._ARef;
import refData._ARefContainerMgr;

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
    public _ARefContainerMgr<? extends _ARef> createContainerMgr() {
        return new RefTestMgr();
    }

    @Override
    public void setOwner(_ARef _mgr) {

    }

    public static class RefTestMgr extends _ARefGameContainerMgr<Long, RefTest> {

        @Override
        public String getTableName() {
            return "test";
        }

        @Override
        public ERefType getRefType() {
            return ERefType.test;
        }
    }

}

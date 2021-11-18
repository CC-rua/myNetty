package refData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RefTable {
    /**
     * 是否是没有表头的表
     *
     * @return boolean
     */
    boolean emptyTable() default false;

    /**
     * 表名
     *
     * @return String
     */
    String tableName() default "";
}

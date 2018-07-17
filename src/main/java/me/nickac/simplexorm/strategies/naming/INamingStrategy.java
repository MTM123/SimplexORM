package me.nickac.simplexorm.strategies.naming;

import me.nickac.simplexorm.DatabaseObject;

import java.lang.reflect.Field;

public interface INamingStrategy {

    boolean isFieldExternal(Field f);

    String getFieldSqlType(Field f);

    String getFieldName(Field f);

    String getTableName(Class<? extends DatabaseObject> clazz);

}

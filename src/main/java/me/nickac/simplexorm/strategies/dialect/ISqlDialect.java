package me.nickac.simplexorm.strategies.dialect;

import me.nickac.simplexorm.DatabaseObject;

public interface ISqlDialect {
    String createTable(Class<? extends DatabaseObject> clazz);
}

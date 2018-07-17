package me.nickac.simplexorm.strategies.dialect;

import me.nickac.simplexorm.DatabaseManager;
import me.nickac.simplexorm.DatabaseObject;
import me.nickac.simplexorm.strategies.naming.DefaultNamingStrategy;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DefaultSqlDialect implements ISqlDialect {
    private static final DefaultNamingStrategy namingStategy = new DefaultNamingStrategy();

    public String createTable(Class<? extends DatabaseObject> clazz) {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "CREATE TABLE " + namingStategy.getTableName(clazz) + "(");

        Field[] fields = DatabaseManager.getPersistedFields(clazz);
        appendLine(sb, "  Id BIGINT AUTO_INCREMENT" + (fields.length > 0 ? "," : ""));

        for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
            Field field = fields[i];
            String type = namingStategy.getFieldSqlType(field);
            String name = namingStategy.getFieldName(field);
            //reference type (one-to-one)
            if (type.isEmpty()) type = "INTEGER";

            appendLine(sb, String.format("  %s %s,", name, type));
        }
        appendLine(sb, "  PRIMARY KEY (id)");
        sb.append(");");

        return sb.toString();
    }

    private void appendLine(StringBuilder sb, String line) {
        sb.append(line).append(System.lineSeparator());
    }
}

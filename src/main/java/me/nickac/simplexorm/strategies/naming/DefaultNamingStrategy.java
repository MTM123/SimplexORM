package me.nickac.simplexorm.strategies.naming;

import com.google.common.base.CaseFormat;
import me.nickac.simplexorm.DatabaseObject;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;

public class DefaultNamingStrategy implements INamingStrategy {
    public boolean isFieldExternal(Field f) {
        return getFieldSqlType(f).equals("");
    }

    public String getFieldSqlType(Field f) {
        Class<?> clazz = f.getType();

        int length = 255;

        Column columnAnnotation = getColumnAnnotation(f);
        if (columnAnnotation != null)
            length = columnAnnotation.length();

        if (clazz.equals(String.class))
            return String.format("VARCHAR(%d)", length);
        else if (clazz.equals(java.math.BigDecimal.class))
            return "NUMERIC";
        else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
            return "BIT";
        else if (clazz.equals(byte.class) || clazz.equals(Byte.class))
            return "TINYINT";
        else if (clazz.equals(Short.class) || clazz.equals(short.class))
            return "SMALLINT";
        else if (clazz.equals(Integer.class) || clazz.equals(int.class))
            return "INTEGER";
        else if (clazz.equals(Long.class) || clazz.equals(long.class))
            return "BIGINT";
        else if (clazz.equals(Float.class) || clazz.equals(float.class))
            return "FLOAT";
        else if (clazz.equals(double.class) || clazz.equals(Double.class))
            return "DOUBLE";
        return "";
    }

    private Column getColumnAnnotation(Field f) {
        if (f.isAnnotationPresent(Column.class))
            return f.getAnnotation(Column.class);
        return null;
    }


    private Table getTableAnnotation(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class))
            return clazz.getAnnotation(Table.class);
        return null;
    }

    public String getFieldName(Field f) {
        String fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, f.getName());

        Column columnAnnotation = getColumnAnnotation(f);
        if (columnAnnotation != null && !columnAnnotation.name().equals(""))
            fieldName = columnAnnotation.name();

        return (isFieldExternal(f) ? "ref" : "") + fieldName;
    }

    public String getTableName(Class<? extends DatabaseObject> clazz) {
        String tableName = clazz.getSimpleName();

        Table table = getTableAnnotation(clazz);
        if (table != null && !table.name().equals(""))
            tableName = table.name();

        return tableName;
    }
}

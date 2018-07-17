package me.nickac.simplexorm;

import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Arrays;

public class DatabaseManager {
    public static void connect(String ip, int port, String username, String password, String database) {
        DatabaseOptions options = DatabaseOptions.builder().mysql(username, password, "db", ip + ":" + port).build();
        Database db = PooledDatabaseOptions.builder().options(options).createHikariDatabase();
        DB.setGlobalDatabase(db);
    }

    public static Field[] getPersistedFields(Class<? extends DatabaseObject> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(f->f.isAnnotationPresent(Column.class)).toArray(Field[]::new);
    }
}

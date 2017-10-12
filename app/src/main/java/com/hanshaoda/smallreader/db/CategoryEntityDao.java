package com.hanshaoda.smallreader.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.hanshaoda.smallreader.model.CategoryEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午1:54
 * description:
 */

public class CategoryEntityDao extends AbstractDao<CategoryEntity, Long> {

    private static final String TABLE_NAME = "CATEGORY_ENTITY";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property Name = new Property(1, String.class, "name", false, "NAME");
        public static final Property Key = new Property(2, String.class, "key", false, "KEY");
        public static final Property Order = new Property(3, int.class, "order", false, "ORDER");
    }

    public CategoryEntityDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    public CategoryEntityDao(DaoConfig config) {
        super(config);
    }

    /**
     * 创建表
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS" : "";
        db.execSQL("CREATE TABLE " + constraint + "\"CATEGORY_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"KEY\" TEXT," + // 2: key
                "\"ORDER\" INTEGER NOT NULL );"); // 3: order
    }

    /**
     * 删除表
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS" : "") + "\"CATEGORY_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected CategoryEntity readEntity(Cursor cursor, int offset) {
        CategoryEntity entity = new CategoryEntity(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1),
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2),
                cursor.getInt(offset + 3)
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, CategoryEntity entity, int offset) {

        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setKey(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setOrder(cursor.getInt(offset + 3));
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, CategoryEntity entity) {

        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }

        String key = entity.getKey();
        if (key != null) {
            stmt.bindString(3, key);
        }

        stmt.bindLong(4, entity.getOrder());
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, CategoryEntity entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }

        String key = entity.getKey();
        if (key != null) {
            stmt.bindString(3, key);
        }

        stmt.bindLong(4, entity.getOrder());
    }

    @Override
    protected Long updateKeyAfterInsert(CategoryEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(CategoryEntity entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected boolean hasKey(CategoryEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}

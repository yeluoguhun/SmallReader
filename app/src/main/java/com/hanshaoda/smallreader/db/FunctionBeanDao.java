package com.hanshaoda.smallreader.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.hanshaoda.smallreader.model.FunctionBean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * author: hanshaoda
 * created on: 2017/9/9 上午11:36
 * description:
 */
public class FunctionBeanDao extends AbstractDao<FunctionBean, Long> {

    private static final String TABLE_NAME = "FUNCTION_BEAN";

    public static class Properties {
        public final static Property FunctionId = new Property(0, Long.class, "functionId", true, "_id");
        public final static Property Id = new Property(1, int.class, "id", false, "ID");
        public final static Property Mark = new Property(2, int.class, "mark", false, "MARK");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Code = new Property(4, String.class, "code", false, "CODE");
        public final static Property NotOpen = new Property(5, boolean.class, "notOpen", false, "NOT_OPEN");
    }

    public FunctionBeanDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    public FunctionBeanDao(DaoConfig config) {
        super(config);
    }

    /**
     * 创建表
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS" : "";
        db.execSQL("CREATE TABLE " + constraint + "\"FUNCTION_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: functionId
                "\"ID\" INTEGER NOT NULL ," + // 1: id
                "\"MARK\" INTEGER NOT NULL ," + // 2: mark
                "\"NAME\" TEXT," + // 3: name
                "\"CODE\" TEXT," + // 4: code
                "\"NOT_OPEN\" INTEGER NOT NULL );"); // 5: notOpen
    }

    /**
     * 删除表
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FUNCTION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected FunctionBean readEntity(Cursor cursor, int offset) {
        FunctionBean entity = new FunctionBean(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.getInt(offset + 1),
                cursor.getInt(offset + 2),
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3),
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4),
                cursor.getShort(offset + 5) != 0
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, FunctionBean entity, int offset) {

        entity.setFunctionId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.getInt(offset + 1));
        entity.setMark(cursor.getInt(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNotOpen(cursor.getLong(offset + 5) != 0);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, FunctionBean entity) {

        stmt.clearBindings();
        Long functionId = entity.getFunctionId();
        if (functionId != null) {
            stmt.bindLong(1, functionId);
        }
        stmt.bindLong(2, entity.getId());
        stmt.bindLong(3, entity.getMark());

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(5, code);
        }
        stmt.bindLong(6, entity.isNotOpen() ? 1L : 0L);

    }

    @Override
    protected void bindValues(SQLiteStatement stmt, FunctionBean entity) {
        stmt.clearBindings();
        Long functionId = entity.getFunctionId();
        if (functionId != null) {
            stmt.bindLong(1, functionId);
        }
        stmt.bindLong(2, entity.getId());
        stmt.bindLong(3, entity.getMark());

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(5, code);
        }
        stmt.bindLong(6, entity.isNotOpen() ? 1L : 0L);
    }

    @Override
    protected Long updateKeyAfterInsert(FunctionBean entity, long rowId) {
        entity.setFunctionId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(FunctionBean entity) {

        if (entity != null) {
            return entity.getFunctionId();
        } else {
            return null;
        }
    }

    @Override
    protected boolean hasKey(FunctionBean entity) {
        return entity.getFunctionId() != null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}

package com.hanshaoda.smallreader.database;

import android.content.Context;

import com.hanshaoda.smallreader.db.CategoryEntityDao;
import com.hanshaoda.smallreader.db.DaoMaster;
import com.hanshaoda.smallreader.db.DaoSession;
import com.hanshaoda.smallreader.model.CategoryEntity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/11 下午4:54
 * description:
 */

public class CategoryDao {

    public DbManager mDbManager;

    public CategoryDao(Context context) {
        mDbManager = DbManager.getInstance(context);
    }

    /**
     * 插入一条数据
     */
    public void insertCategory(CategoryEntity user) {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.insert(user);
    }


    /**
     * 插入一组数据
     */
    public void insertCategoryList(List<CategoryEntity> users) {
        if (users == null || users.size() == 0) {
            return;
        }
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        CategoryEntityDao categoryEntityDao = daoSession.getCategoryEntityDao();
        categoryEntityDao.insertOrReplaceInTx(users);
    }

    /**
     * 删除一条数据
     */
    public void deleteCategory(CategoryEntity user) {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.delete(user);
    }

    /**
     * 删除所有数据
     */
    public void deleteCategoryList() {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.deleteAll();
    }

    /**
     * 更新一条数据
     */
    public void updateCategory(CategoryEntity user) {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.update(user);
    }


    /**
     * 查询所有数据
     */
    public List<CategoryEntity> getCategoryAll() {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        QueryBuilder<CategoryEntity> qb = userDao.queryBuilder();
        List<CategoryEntity> list = qb.orderAsc(CategoryEntityDao.Properties.Order).list();
        return list;
    }
}

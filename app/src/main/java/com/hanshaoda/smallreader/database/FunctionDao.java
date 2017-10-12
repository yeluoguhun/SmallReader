package com.hanshaoda.smallreader.database;

import android.content.Context;

import com.hanshaoda.smallreader.db.DaoMaster;
import com.hanshaoda.smallreader.db.DaoSession;
import com.hanshaoda.smallreader.db.FunctionBeanDao;
import com.hanshaoda.smallreader.model.FunctionBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/11 下午5:26
 * description:
 */

public class FunctionDao {
    private DbManager mDbManager;

    public FunctionDao(Context context) {
        mDbManager = DbManager.getInstance(context);
    }

    /**
     * 插入一条数据
     */
    public void insertFunction(FunctionBean bean) {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        beanDao.insert(bean);
    }

    /**
     * 插入一组数据
     */
    public void insertFunctionList(List<FunctionBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        beanDao.insertInTx(list);
    }

    /**
     * 删除一条数据
     */
    public void deleteFunction(FunctionBean bean) {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        beanDao.delete(bean);
    }

    /**
     * 更新一条数据
     */
    public void updateFunction(FunctionBean bean) {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        beanDao.update(bean);
    }

    /**
     * 更新一组数据
     */
    public void updateAllBean(List<FunctionBean> list) {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        beanDao.updateInTx(list);
    }

    /**
     * 查询所所有数据
     */
    public List<FunctionBean> getAllFun() {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = beanDao.queryBuilder();
        List<FunctionBean> list = qb.orderAsc(FunctionBeanDao.Properties.Id).list();
        return list;
    }

    public List<FunctionBean> getFunctionListSmall() {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = beanDao.queryBuilder();
        List<FunctionBean> list = qb.where(FunctionBeanDao.Properties.Mark.eq(1))
                .orderAsc(FunctionBeanDao.Properties.Id).list();
        return list;
    }

    public List<FunctionBean> getFunctionListSmallNoMore() {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = beanDao.queryBuilder();
        List<FunctionBean> list = qb.where(FunctionBeanDao.Properties.Mark.eq(1))
                .where(FunctionBeanDao.Properties.Name.notEq("更多"))
                .orderAsc(FunctionBeanDao.Properties.Id).list();
        return list;
    }

    public List<FunctionBean> getFunctionListSmallNoNeed() {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = beanDao.queryBuilder();
        List<FunctionBean> list = qb.where(FunctionBeanDao.Properties.Mark.eq(1))
                .orderAsc(FunctionBeanDao.Properties.Id)
                .limit(6)
                .list();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public boolean queryFunctionIsOpen(String string) {
        DaoMaster daoMaster = new DaoMaster(mDbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        FunctionBean functionBean = qb.where(FunctionBeanDao.Properties.Code.eq(string)).uniqueOrThrow();
        return !functionBean.isNotOpen();
    }

    public int queryMoreFunctionBean() {
        DaoMaster master = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = master.newSession();
        FunctionBeanDao beanDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = beanDao.queryBuilder();
        FunctionBean bean = qb.where(FunctionBeanDao.Properties.Name.eq("更多"))
                .uniqueOrThrow();
        return bean.getId();
    }


    public void uodateMoreFunctionBean() {
        DaoMaster daoMaster = new DaoMaster(mDbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        FunctionBean functionBean = qb.where(FunctionBeanDao.Properties.Name.eq("更多")).uniqueOrThrow();
        functionBean.setId(6);
        userDao.update(functionBean);
    }
}

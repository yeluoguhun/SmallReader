package com.hanshaoda.smallreader.db;

import com.hanshaoda.smallreader.model.CategoryEntity;
import com.hanshaoda.smallreader.model.FunctionBean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午2:35
 * description:
 */

public class DaoSession extends AbstractDaoSession {


    private final DaoConfig functionBeanDaoConfig;
    private final DaoConfig categoryEntityDaoConfig;
    private final FunctionBeanDao functionBeanDao;
    private final CategoryEntityDao categoryEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        functionBeanDaoConfig = daoConfigMap.get(FunctionBeanDao.class).clone();
        functionBeanDaoConfig.initIdentityScope(type);

        categoryEntityDaoConfig = daoConfigMap.get(CategoryEntityDao.class).clone();
        categoryEntityDaoConfig.initIdentityScope(type);

        functionBeanDao = new FunctionBeanDao(functionBeanDaoConfig, this);
        categoryEntityDao = new CategoryEntityDao(categoryEntityDaoConfig, this);

        registerDao(FunctionBean.class, functionBeanDao);
        registerDao(CategoryEntity.class, categoryEntityDao);
    }

    public void clear() {
        functionBeanDaoConfig.clearIdentityScope();
        categoryEntityDaoConfig.clearIdentityScope();
    }

    public FunctionBeanDao getFunctionBeanDao() {
        return functionBeanDao;
    }

    public CategoryEntityDao getCategoryEntityDao() {
        return categoryEntityDao;
    }
}

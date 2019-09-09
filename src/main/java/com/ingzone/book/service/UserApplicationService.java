package com.ingzone.book.service;

import com.ingzone.book.base.Result;
import com.ingzone.book.dao.ApplicationDao;
import com.ingzone.book.dao.UserDao;
import com.ingzone.book.model.JqGridVO;
import com.ingzone.book.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ingzone.book.cache.ResultCache.getCache;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Service
public class UserApplicationService {
    @Autowired
    ApplicationDao applicationDao;
    @Autowired
    UserDao userDao;
    public JqGridVO query(Page page) {
        return new JqGridVO(page,applicationDao.count(),applicationDao.query(page.conversion()));
    }

    @Transactional
    public Result handle(String id, String userId) {
        int result = applicationDao.delete(id);
        if(result==1&&userId!=null&&!"".equals(userId))
            result = userDao.updateRole(1,userId);
        return getCache(result);
    }
}

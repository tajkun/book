package com.ingzone.book.dao;

import com.ingzone.book.model.vo.LoginVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Repository
public interface UserDao {
    int updateRole(@Param("n") int i,@Param("userId") String userId);

    LoginVO queryLogin(@Param("account") String account);
}

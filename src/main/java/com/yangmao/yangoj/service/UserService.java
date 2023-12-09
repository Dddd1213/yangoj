package com.yangmao.yangoj.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yangmao.yangoj.model.VO.UserVO;
import com.yangmao.yangoj.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author 31067
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-11-08 16:08:43
*/
public interface UserService extends IService<User> {

    UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    Long register(String userAccount, String userPassword, String checkPassword);

    Boolean userLogout(HttpServletRequest request);

    UserVO getLoginUser(HttpServletRequest request);

    Boolean isAdmin(HttpServletRequest request);
}

package com.yangmao.yangoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.constant.PasswordConstant;
import com.yangmao.yangoj.constant.UserConstant;
import com.yangmao.yangoj.mapper.UserMapper;
import com.yangmao.yangoj.pojo.VO.user.UserVO;
import com.yangmao.yangoj.pojo.entity.User;
import com.yangmao.yangoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

import static com.yangmao.yangoj.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 31067
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-11-08 16:08:43
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     * 注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    @Override
    public Long register(String userAccount, String userPassword, String checkPassword) {

        /**
         * 简单校验
         */
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 4 || checkPassword.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致");
        }

        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            userPassword = DigestUtils.md5DigestAsHex((userPassword+ PasswordConstant.PASSWORD_SALT).getBytes(StandardCharsets.UTF_8));
            // 3. 插入数据
            User user = User.builder()
                    .userAccount(userAccount)
                    .userPassword(userPassword)
                    .build();
            boolean save = this.save(user);
            if(!save){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库异常操作失败");
            }

            return user.getId();
        }
    }

    /**
     * 登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        // 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

//        userPassword = DigestUtils.md5DigestAsHex((userPassword+ PasswordConstant.PASSWORD_SALT).getBytes());

        /**
         * 查询数据库
         */
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, userPassword);
        User user = baseMapper.selectOne(queryWrapper);

        if(user==null)
        {
            log.info("user login failed, userAccount cannot match userPassword",userAccount,userPassword);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }

        /**
         * 如果用户存在且密码正确
         */
        UserVO userLoginVO = UserVO.builder().build();
        BeanUtils.copyProperties(user,userLoginVO);

        //记录登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,user);

        return userLoginVO;
    }
    /**
     * 登出
     * @param request
     * @return
     */
    @Override
    public Boolean userLogout(HttpServletRequest request) {
        if(request.getSession().getAttribute(USER_LOGIN_STATE)==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"没有用户的登录信息");
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    /**
     * 获取当前登录的用户信息
     * @param request
     * @return
     */
    @Override
    public UserVO getLoginUser(HttpServletRequest request) {

        User currentUser = (User)request.getSession().getAttribute(USER_LOGIN_STATE);

        System.out.println(currentUser);
        UserVO userVO = UserVO.builder().build();
        BeanUtils.copyProperties(currentUser,userVO);
        System.out.println(userVO);
        return userVO;
    }

    /**
     * 判断是否为管理员
     * @param request
     * @return
     */
    @Override
    public Boolean isAdmin(HttpServletRequest request) {

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && UserConstant.ADMIN_ROLE.equals(user.getUserRole());
    }

}





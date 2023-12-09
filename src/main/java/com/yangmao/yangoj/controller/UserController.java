package com.yangmao.yangoj.controller;

import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.common.result.Result;
import com.yangmao.yangoj.common.utils.ResultUtils;
import com.yangmao.yangoj.model.DTO.user.UserLoginDTO;
import com.yangmao.yangoj.model.DTO.user.UserRegisterDTO;
import com.yangmao.yangoj.model.VO.UserVO;
import com.yangmao.yangoj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Api
public class UserController {

    private final UserService userService;

    /**
     *用户注册
     * @param userRegisterDTO
     * @return registerId
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Long> userRegister(@RequestBody UserRegisterDTO userRegisterDTO){

        if (userRegisterDTO == null ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userRegisterDTO.getUserAccount();
        String userPassword = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long registerId = userService.register(userAccount, userPassword, checkPassword);

        return ResultUtils.success(registerId);
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserVO> userLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request){

        String userAccount = userLoginDTO.getUserAccount();
        String userPassword = userLoginDTO.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = userService.userLogin(userAccount, userPassword, request);

        return ResultUtils.success(userVO);
    }

    /**
     * 用户登出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public Result<Boolean> userLogout(HttpServletRequest request){

        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result = userService.userLogout(request);

        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    @GetMapping("/getLogin")
    @ApiOperation("获取当前登录用户")
    public Result<UserVO> getLoginUser(HttpServletRequest request){
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = userService.getLoginUser(request);
        return ResultUtils.success(userVO);
    }

}

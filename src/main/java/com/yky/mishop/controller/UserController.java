package com.yky.mishop.controller;

import com.yky.mishop.common.BaseResponse;
import com.yky.mishop.common.ErrorCode;
import com.yky.mishop.common.ResultUtils;
import com.yky.mishop.exception.BusinessException;
import com.yky.mishop.model.dto.user.UserRegisterRequest;
import com.yky.mishop.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public BaseResponse usernameIsExist(@RequestParam String username) {
        // 1. 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不能为空");
        }

        // 2. 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名已存在");
        }

        // 3. 请求成功，用户名没有重复
        return ResultUtils.success(null);
    }

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            return null;
        }
        // 2. 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "用户名已经存在");
        }
        // 3. 密码长度不能少于 6 位
        if (password.length() < 6) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "密码长度不能少于 6 位");
        }
        // 4. 判断两次密码是否一致
        if (!password.equals(checkPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "两次密码输入不一致");
        }
        // 5. 实现注册
        long result = userService.userRegister(username, password, checkPassword);

        return ResultUtils.success(result);
    }
}

package com.share.shamir.controller;

import com.share.shamir.controller.request.LoginRequest;
import com.share.shamir.controller.request.RegisterRequest;
import com.share.shamir.controller.response.CommonResponse;
import com.share.shamir.controller.response.ResponseBuilder;
import com.share.shamir.model.UserManageModel;
import com.share.shamir.service.UserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shamirManagement")
public class ShamirController {
    private static Logger LOGGER = LoggerFactory.getLogger(ShamirController.class);

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody CommonResponse login(@RequestBody LoginRequest loginRequest) {
        LOGGER.info("记录接口请求参数:{},{}",loginRequest.getUserName(),loginRequest.getPassword());

        UserManageModel userManageModel = new UserManageModel();
        userManageModel.setUsername(loginRequest.getUserName());
        userManageModel.setPassword(loginRequest.getPassword());

        Boolean loginAccess = userManageService.userQuery(userManageModel);
        if(loginAccess) {
            LOGGER.info("用户登录成功");
            return ResponseBuilder.success("登录成功");
        }else {
            LOGGER.info("用户登录失败");
            return ResponseBuilder.error("账号或者密码错误");
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody CommonResponse register(@RequestBody RegisterRequest registerRequest) {
        LOGGER.info("记录接口请求参数:{},{}",registerRequest.getUserName(),registerRequest.getPassword(),registerRequest.getPhone(),registerRequest.getEmail(),registerRequest.getRole());

        UserManageModel userManageModel = new UserManageModel();
        userManageModel.setUsername(registerRequest.getUserName());
        userManageModel.setPassword(registerRequest.getPassword());
        userManageModel.setPhone(registerRequest.getPhone());
        userManageModel.setEmail(registerRequest.getEmail());
        userManageModel.setRole(registerRequest.getRole());

        userManageService.userInsert(userManageModel);
        LOGGER.info("用户注册成功");
        return ResponseBuilder.success("注册成功");
    }
}

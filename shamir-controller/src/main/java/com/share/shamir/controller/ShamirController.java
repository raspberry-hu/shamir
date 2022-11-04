package com.share.shamir.controller;

import com.share.shamir.controller.request.LoginRequest;
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
            return ResponseBuilder.success("登录成功");
        }else {
            return ResponseBuilder.error("账号或者密码错误");
        }
    }
}

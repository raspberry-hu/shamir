package com.share.shamir.controller;

import com.share.shamir.controller.request.*;
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

import java.util.List;

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
        LOGGER.info("记录接口请求参数:{},{},{},{},{}",registerRequest.getUserName(),registerRequest.getPassword(),registerRequest.getPhone(),registerRequest.getEmail(),registerRequest.getRole());

        UserManageModel userManageModel = new UserManageModel();
        userManageModel.setUsername(registerRequest.getUserName());
        userManageModel.setPassword(registerRequest.getPassword());
        userManageModel.setPhone(registerRequest.getPhone());
        userManageModel.setEmail(registerRequest.getEmail());
        userManageModel.setRole(registerRequest.getRole());
        try {
            userManageService.userInsert(userManageModel);
        }catch (Exception e) {
            LOGGER.error("用户注册失败");
            return ResponseBuilder.error("信息重复用户注册失败");
        }
        LOGGER.info("用户注册成功");
        return ResponseBuilder.success("注册成功");
    }

    @RequestMapping(value = "/getallrole", method = RequestMethod.POST)
    public @ResponseBody CommonResponse getAllRole(@RequestBody RoleRequest roleRequest) {
        LOGGER.info("记录接口请求参数:{}", roleRequest.getRole());
        List<UserManageModel> userManageModelList = userManageService.getSameOrgUser(roleRequest.getRole());
        return ResponseBuilder.success(userManageModelList);
    }

    @RequestMapping(value = "/distributekey", method = RequestMethod.POST)
    public @ResponseBody CommonResponse distributeKey(@RequestBody DistributeKeyRequest distributeKeyRequest) {
        LOGGER.info("记录接口请求参数:{},{},{},{}", distributeKeyRequest.getMin(), distributeKeyRequest.getKeyName(),distributeKeyRequest.getKey(), distributeKeyRequest.getUsers());
        try {
            Boolean result = userManageService.keyDistribution(distributeKeyRequest.getUsers(), distributeKeyRequest.getKey(), distributeKeyRequest.getMin(), distributeKeyRequest.getKeyName());
            if(result){
                return ResponseBuilder.success("分发密钥成功");
            }
        }catch (Exception e) {
            LOGGER.error("分发密钥失败", e);
            return ResponseBuilder.error("分发密钥失败");
        }
        return ResponseBuilder.error("分发密钥失败");
    }

    @RequestMapping(value = "/keyrestore", method = RequestMethod.POST)
    public @ResponseBody CommonResponse keyRestore(@RequestBody KeyRestoreRequest KeyRestoreRequest) {
        LOGGER.info("记录接口请求参数:{}", KeyRestoreRequest.getKeyName());
        try{
            String key = userManageService.keyRestore(KeyRestoreRequest.getKeyName());
            return ResponseBuilder.success(key);
        } catch (Exception e) {
            return ResponseBuilder.error("恢复密钥失败");
        }
    }

    @RequestMapping(value = "/getkeyname", method = RequestMethod.POST)
    public @ResponseBody CommonResponse keyRestore(@RequestBody GetKeyNameRequest getKeyNameRequest) {
        LOGGER.info("记录接口请求参数:{}", getKeyNameRequest.getUserId());
        try{
            List<String> key = userManageService.getKeyNames(getKeyNameRequest.getUserId());
            return ResponseBuilder.success(key);
        } catch (Exception e) {
            return ResponseBuilder.error("获取密钥名称失败");
        }
    }

    @RequestMapping(value = "/getshamiruserkey", method = RequestMethod.POST)
    public @ResponseBody CommonResponse getUserShamirKey(@RequestBody GetUserShamirKey getUserShamirKey) {
        LOGGER.info("记录接口请求参数:{}", getUserShamirKey.getUserId());
        try{
            List<String> keys = userManageService.getKeys(getUserShamirKey.getUserId());
            return ResponseBuilder.success(keys);
        }catch (Exception e) {
            return ResponseBuilder.error("获取用户密钥失败");
        }
    }

    @RequestMapping(value = "/approveshamirkey", method = RequestMethod.POST)
    public @ResponseBody CommonResponse approveShamirKey(@RequestBody ApproveShamirKeyRequest approveShamirKeyRequest) {
        LOGGER.info("记录接口请求参数:{},{}", approveShamirKeyRequest.getShamirKeyName(), approveShamirKeyRequest.getUserId());
        try{
            userManageService.approveKey(approveShamirKeyRequest.getUserId(), approveShamirKeyRequest.getShamirKeyName());
            return ResponseBuilder.success("审批成功");
        }catch (Exception e) {
            return ResponseBuilder.error("审批失败");
        }
    }
}

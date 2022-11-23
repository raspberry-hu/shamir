package com.share.shamir.service;

import com.share.shamir.model.UserManageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserManageService {

    /**
     * 插入用户信息
     * @param userManageModel
     */
    void userInsert(UserManageModel userManageModel);

    /**
     * 查询用户信息
     * @param userManageModel
     */
    Boolean userQuery(UserManageModel userManageModel);

    /**
     * 批量插入
     * @param userManageModelList
     */
    void batchInsert(List<UserManageModel> userManageModelList);

    /**
     * 批量更新插入
     * @param userManageModelList
     */
    void batchUpdate(List<UserManageModel> userManageModelList);

    /**
     * 获取相同组织成员信息
     * @param role
     */
    List<UserManageModel> getSameOrgUser(String role);

    /**
     * 获取所有组织
     * @param
     */
    List<String> getAllOrg();

    /**
     * 分配密钥
     * @param userId
     * @param key
     * @param min
     * @param keyName
     */
    Boolean keyDistribution(List<Integer> userId, String key, int min, String keyName) throws Exception;

    /**
     * 密钥恢复
     * @param
     */
    String keyRestore(String keyName) throws UnsupportedEncodingException;
}

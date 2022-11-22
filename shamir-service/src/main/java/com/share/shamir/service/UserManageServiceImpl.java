package com.share.shamir.service;

import com.share.shamir.dal.entity.Shamir;
import com.share.shamir.dal.entity.User;
import com.share.shamir.dal.mapper.OrganizationMapper;
import com.share.shamir.dal.mapper.ShamirMapper;
import com.share.shamir.dal.mapper.UserMapper;
import com.share.shamir.model.UserManageModel;
import com.share.shamir.util.shamir.ShamirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserManageServiceImpl implements UserManageService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserManageServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShamirMapper shamirMapper;
    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 用户注册接口
     * @param userManageModel
     */
    @Override
    public void userInsert(UserManageModel userManageModel) {
        User user = new User();
        user.setRole(userManageModel.getRole());
        user.setEmail(userManageModel.getEmail());
        user.setUsername(userManageModel.getUsername());
        user.setPassword(userManageModel.getPassword());
        user.setPhone(userManageModel.getPhone());
        user.setId(null);
        user.setShamirkey("zero");
        user.setShamirid("zero");
        userMapper.insert(user);
    }

    /**
     * 用户登录接口
     * @param userManageModel
     * @return
     */
    @Override
    public Boolean userQuery(UserManageModel userManageModel) {
        User user = userMapper.selectByUserName(userManageModel.getUsername());
        if(user != null && user.getPassword().equals(userManageModel.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void batchInsert(List<UserManageModel> userManageModelList) {

    }

    @Override
    public void batchUpdate(List<UserManageModel> userManageModelList) {

    }

    /**
     * 获取相同组织成员信息
     * @param role
     */
    @Override
    public List<UserManageModel> getSameOrgUser(String role) {
        List<User> userList = userMapper.selectUserByRole(role);
        List<UserManageModel> userManageModelList = new ArrayList<>();
        for (User user : userList) {
            UserManageModel userManageModel = new UserManageModel();
            userManageModel.setId(user.getId());
            userManageModel.setUsername(user.getUsername());
            userManageModel.setRole(user.getRole());
            userManageModelList.add(userManageModel);
        }
        return userManageModelList;
    }

    /**
     * 获取所有组织
     * @param
     */
    @Override
    public List<String> getAllOrg() {
        return organizationMapper.selectAllOrg();
    }

    /**
     * 分配密钥
     * @param userId
     * @param key
     * @param min
     * @param keyName
     */
    @Override
    public Boolean keyDistribution(List<Integer> userId, String key, int min, String keyName) {
        //创建密钥分配
        HashMap<Integer, byte[]> keyDistribution = new HashMap<>();
        //创建密钥分配人员数据库模型
        List<User> users = null;
        //创建密钥分配人员服务层模型
        List<UserManageModel> userModels = null;
        //将密钥分配给用户
        keyDistribution = ShamirUtils.shamirGenerate(key, userId.size(), min);
        Iterator<Map.Entry<Integer, byte[]>> iterator = keyDistribution.entrySet().iterator();
        for (int i = 0; i < userId.size(); i++) {
            Map.Entry<Integer, byte[]> entry = iterator.next();
            //获取用户信息
            User user = userMapper.selectByPrimaryKey(userId.get(i));
            if(user == null) {
                return false;
            }
            //获取用户密钥分配信息
            Shamir shamir = new Shamir();
            shamir.setShamirkey(String.valueOf(entry.getValue()));
            shamir.setShamirid(keyName);
            shamir.setShamiruserkey(userId.get(i));
            shamir.setId(null);
            //分配密钥
            shamirMapper.insertSelective(shamir);
        }
        return true;
    }

    /**
     * 恢复密钥
     * @param keyName
     */
    @Override
    public String keyRestore(String keyName) throws UnsupportedEncodingException {
        //获取密钥恢复人员信息
        List<Shamir> shamirs = shamirMapper.selectByShamirId(keyName);
        System.out.println("打印0" + shamirs);
        HashMap<Integer, byte[]> temp = new HashMap<>();
        for(int i = 0; i < shamirs.size(); i++) {
            System.out.println("打印1" + shamirs.get(i).getShamirkey());
            temp.put(i, shamirs.get(i).getShamirkey().getBytes("UTF-8"));
        }
        System.out.println("打印2" + temp);
        System.out.println("打印3" + ShamirUtils.shamirRecover(temp));
        return ShamirUtils.shamirRecover(temp);
    }
}

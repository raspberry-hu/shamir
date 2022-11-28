package com.share.shamir.service;

import com.share.shamir.dal.entity.Shamir;
import com.share.shamir.dal.entity.User;
import com.share.shamir.dal.mapper.OrganizationMapper;
import com.share.shamir.dal.mapper.ShamirMapper;
import com.share.shamir.dal.mapper.UserMapper;
import com.share.shamir.model.UserManageModel;
import com.share.shamir.util.eosio.java.abieos.serialization.EosUtils;
import com.share.shamir.util.shamir.ShamirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import static com.share.shamir.util.shamir.ShamirUtils.getSHA256StrJava;

@Service
public class UserManageServiceImpl implements UserManageService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserManageServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShamirMapper shamirMapper;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Value("${eosio.chainurl}")
    private String chainUrl;

    @Value("${eosio.privatekey}")
    private String privatekey;

    @Value("${eosio.username}")
    private String username;

    /**
     * 用户注册接口
     * @param userManageModel
     */
    @Override
    public void userInsert(UserManageModel userManageModel) {
        User user = new User();
        user.setRole(userManageModel.getRole());
        user.setUser_id(String.valueOf(System.nanoTime()));
        user.setEmail(userManageModel.getEmail());
        user.setUsername(userManageModel.getUsername());
        user.setPassword(userManageModel.getPassword());
        user.setPhone(userManageModel.getPhone());
        user.setId(null);
        user.setShamirkey("zero");
        user.setShamirid("zero");
        user.setFhe_pk(null);
        user.setFhe_sk(null);
        user.setEcc_pk(null);
        user.setEcc_sk(null);
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
    public Boolean keyDistribution(List<Integer> userId, String key, int min, String keyName) throws Exception {
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
            byte[] a = entry.getValue();
            System.out.println(Arrays.toString(a));
            String aStr = new String(a, Charset.forName("ISO-8859-1"));
            shamir.setShamirkey(aStr);
            shamir.setShamirid(keyName);
            shamir.setShamiruserkey(userId.get(i));
            shamir.setId(null);
            shamir.setApprove("disapprove");
            //分配密钥
            shamirMapper.insertSelective(shamir);
            //密钥分配数据上链
//            EosUtils eos = new EosUtils(chainUrl,privatekey,username);
//            eos.callCreateContract(user.getUsername(), keyName, getSHA256StrJava(aStr));
        }
        return true;
    }

    /**
     * 恢复密钥
     * @param keyName
     */
    @Override
    public String keyRestore(String keyName, Integer id) throws UnsupportedEncodingException {
        //获取密钥恢复人员信息
        String judge = "false";
        List<Shamir> shamirs = shamirMapper.selectByShamirId(keyName);
        for(Shamir shamir : shamirs) {
            if(shamir.getShamiruserkey().equals(id)) {
                judge = "true";
            }
        }
        if(judge.equals("false")) {
            return "false";
        }
        HashMap<Integer, byte[]> temp = new HashMap<>();
        for(int i = 0; i < shamirs.size(); i++) {
            if(shamirs.get(i).getApprove().equals("approve")) {
                temp.put(i+1, shamirs.get(i).getShamirkey().getBytes(Charset.forName("ISO-8859-1")));
            }
        }
        return ShamirUtils.shamirRecover(temp);
    }

    @Override
    public List<String> getKeyNames(int userId) {
        List<Shamir> sharp = shamirMapper.selectByShamirUserKey(userId);
        List<String> keyNames = new ArrayList<>();
        for (Shamir shamir : sharp) {
            keyNames.add(shamir.getShamirid());
        }
        return keyNames;
    }

    @Override
    public List<List<String>> getKeys(int userId) {
        List<Shamir> sharp = shamirMapper.selectByShamirUserKey(userId);
        List<List<String>> result = new LinkedList<>();
        for (Shamir shamir : sharp) {
            List<String> temp = new ArrayList<>();
            temp.add(shamir.getShamirkey());
            temp.add(shamir.getShamirid());
            result.add(temp);
        }
        return result;
    }

    @Override
    public void approveKey(int userId, String keyName) {
        List<Shamir> sharp = shamirMapper.selectByShamirUserKey(userId);
        Shamir temp = null;
        for (Shamir shamir : sharp) {
            System.out.println(shamir.getShamirid());
            if(shamir.getShamirid().equals(keyName)) {
                temp = shamir;
            }
        }
        temp.setApprove("approve");
        shamirMapper.updateByPrimaryKey(temp);
    }
}

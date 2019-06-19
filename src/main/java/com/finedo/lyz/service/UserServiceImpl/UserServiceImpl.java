package com.finedo.lyz.service.UserServiceImpl;

import com.finedo.lyz.Utils.RedisUtils;
import com.finedo.lyz.mapper.UserMapper;
import com.finedo.lyz.model.User;
import com.finedo.lyz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public User selectByPrimaryKey(Integer id) {
        String key = "user" + id;
        User user = (User)redisUtils.get(key);
        if(null==user){
            if(redisUtils.exists(key)){
                return null;
            }
            //从DB中获取信息
            logger.info("从数据库中获取数据");
            user = userMapper.selectByPrimaryKey(id);
            //放入到缓存中
            redisUtils.set(key,user,10L, TimeUnit.MINUTES);
            return user;
        }else {
            user = (User)redisUtils.get(key);
            logger.info("从缓存获取的数据"+ user);
            return user;
        }

    }


}

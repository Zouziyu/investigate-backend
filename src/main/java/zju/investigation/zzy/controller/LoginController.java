package zju.investigation.zzy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zju.investigation.zzy.dto.LoginMessage;
import zju.investigation.zzy.mapper.UserMapper;
import zju.investigation.zzy.provider.EncryptProvider;
import zju.investigation.zzy.provider.RedisProvider;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
public class LoginController {
    @Resource
    private UserMapper userMapper;

    @Resource
    private EncryptProvider encryptProvider;

    @Resource
    private RedisProvider redisProvider;

    @PostMapping(value = "/login")
    public LoginMessage login(@RequestParam("email") String email,
                              @RequestParam("password") String password) throws NoSuchAlgorithmException {
        // 将登录信息存储进Redis数据库
        LoginMessage loginMessage = new LoginMessage();
        String authorizeToken = encryptProvider.getMD5Code(email);
        redisProvider.setAuthorizeToken(authorizeToken, email);
        int isValid = userMapper.getUserByEmailAndPassword(email, password);
        loginMessage.setState(isValid == 1);
        loginMessage.setToken(authorizeToken);
        return loginMessage;
    }
}

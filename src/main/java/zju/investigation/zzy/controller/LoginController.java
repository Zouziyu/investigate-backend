package zju.investigation.zzy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zju.investigation.zzy.mapper.UserMapper;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Api(tags = "登录")
@RestController
public class LoginController {
    @Resource
    private UserMapper userMapper;

    @ApiOperation("普通登录")
    @PostMapping(value = "/login")
    public Boolean login(@RequestParam("email") String email,
                         @RequestParam("password") String password) throws NoSuchAlgorithmException {

        int isValid = userMapper.getUserByEmailAndPassword(email, password);
        return isValid == 1;
    }
}

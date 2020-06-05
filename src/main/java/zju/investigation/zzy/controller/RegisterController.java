package zju.investigation.zzy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zju.investigation.zzy.mapper.UserMapper;
import zju.investigation.zzy.service.MailService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Api(tags = "注册")
@RestController
public class RegisterController {
    @Resource
    private UserMapper userMapper;

    @Resource
    private MailService mailService;

    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public Boolean register(@RequestParam("name") String name,
                            @RequestParam("password") String password,
                            @RequestParam("email") String email,
                            @RequestParam("token") String token,
                            BindingResult bindingResult,
                            HttpServletRequest request) throws NoSuchAlgorithmException {
        HttpSession session = request.getSession();

        if (session.getAttribute(email) == null || !session.getAttribute(email).toString().equals(token)) {
            return false;
        }

        if (userMapper.isEmailExist(email) > 0 || userMapper.isNameExist(name) > 0) {
            return false;
        }

        userMapper.createUser(email, name, password);
        return true;
    }

    @ApiOperation("发送邮件认证")
    @PostMapping(value = "/applyEmail")
    public boolean register(@RequestParam("email") String email,
                            HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String token = UUID.randomUUID().toString().substring(0, 6);

        session.setAttribute(email, token);
        mailService.sendToken(email, token);
        return true;
    }
}

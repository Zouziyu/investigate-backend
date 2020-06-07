package zju.investigation.zzy.controller;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zju.investigation.zzy.dto.AnswerNaire;
import zju.investigation.zzy.dto.Message;
import zju.investigation.zzy.interceptor.AuthToken;
import zju.investigation.zzy.mapper.AnswerNaireMapper;
import zju.investigation.zzy.mapper.QuestionMapper;
import zju.investigation.zzy.service.AnswernaireService;
import zju.investigation.zzy.service.IpAddressService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class AnswernaireController {
    @Resource
    private AnswernaireService answernaireService;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private AnswerNaireMapper answerNaireMapper;

    @Resource
    private IpAddressService ipAddressService;

    @Value("${spring.questionnaire.onlyRegister}")
    private int onlyRegister;
    @Value("${spring.questionnaire.TotalN}")
    private int totalN;
    @Value("${spring.questionnaire.DailyN}")
    private int dailyN;

    @PostMapping(value = "/isValidToAnswer")
    public Message isValidAnswer(@RequestParam("id") long id,
                                 @Param("email") String email,
                                 HttpServletRequest request) throws NoSuchAlgorithmException, ParseException {
        Message message = new Message();
        int type = questionMapper.getType(id);
        // 仅限注册用户
        if (type == onlyRegister && email == null) {
            message.setMessage("该问题仅限注册用户回答");
            message.setState(false);
            return message;
        }
        // 判断填写是否超过次数
        int answerCount = answerNaireMapper.getAnswerCount(id);
        int questionCount = questionMapper.getQuestionCount(id);
        if (type == totalN && answerCount > questionCount) {
            message.setState(false);
            message.setMessage("该问卷的填写次数超过限制");
            return message;
        }
        // 判断填写是否超过每日限制

        int dailyAnswerCount = answerNaireMapper.getDailyAnswerCount(id);
        if (type == dailyN && dailyAnswerCount > answerCount) {
            message.setState(false);
            message.setMessage("该问卷的填写次数超过每日限制");
            return message;
        }
        // 判断用户是否已经填写过
        String address = ipAddressService.getIpAddress(request);
        int answerCountByIp = answerNaireMapper.getAnswerCountByIp(address, id);
        if (answerCountByIp > 0) {
            message.setState(false);
            message.setMessage("该用户已经填写过问卷");
            return message;
        }
        message.setState(true);
        return message;
    }

    @AuthToken
    @PostMapping(value = "/setAnswer")
    public boolean setAnswer(@RequestParam("id") long id,
                             @Param("email") String email,
                             @RequestParam("answers") ArrayList<String> answers,
                             HttpServletRequest request) throws NoSuchAlgorithmException, ParseException, IOException {
        AnswerNaire answerNaire = new AnswerNaire();
        answerNaire.setCreateTime(LocalDate.now().toString());
        String address = ipAddressService.getIpAddress(request);
        answerNaire.setEmail(email);
        answerNaire.setId(id);
        // 查询IP地址所属地域
        OkHttpClient client = new OkHttpClient();
        Request requestBuilder = new Request.Builder()
                .url("https://api.map.baidu.com/location/ip?ak=VtgknYbEvwpTQVeIVMUu6XpxWs40wxbn&ip=" + address + "&coor=bd09ll")
                .build();
        Response response = client.newCall(requestBuilder).execute();
        String string = response.body().string();
        string = JSON.parseObject(string, HashMap.class).get("content").toString();
        string = JSON.parseObject(string, HashMap.class).get("point").toString();
        HashMap geometry = JSON.parseObject(string, HashMap.class);

        answerNaire.setGeometry(geometry.get("x").toString() + "-" + geometry.get("y").toString());
        answerNaire.setAddress(address);
        return answernaireService.setAnswernaireInfo(answerNaire, answers);
    }

    @PostMapping(value = "/getAnswer")
    public ArrayList<String> getAnswer(long id, String email) throws NoSuchAlgorithmException {
        return answernaireService.getAnswerNaireInfo(email, id);
    }
}

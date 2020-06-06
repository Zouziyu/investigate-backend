package zju.investigation.zzy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zju.investigation.zzy.dto.Question;
import zju.investigation.zzy.dto.QuestionNaire;
import zju.investigation.zzy.service.QuestionnaireService;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@RestController
public class AnswernaireController {
    @Resource
    private QuestionnaireService questionnaireService;

    @PostMapping(value = "/setQuestion")
    public Boolean register(@RequestParam("title") String title,
                            @RequestParam("email") String email,
                            @RequestParam("title") String content,
                            @RequestParam("deadTime") String deadTime,
                            @RequestParam("questions") ArrayList<Question> quesions) throws NoSuchAlgorithmException, ParseException {
        QuestionNaire questionNaire = new QuestionNaire();
        questionNaire.setCreateTime(System.currentTimeMillis());
        questionNaire.setQuestions(quesions);
        questionNaire.setContent(content);
        questionNaire.setEmail(email);
        questionNaire.setTitle(title);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        long lDeadTime = dateformat.parse(deadTime).getTime();
        questionNaire.setDeadTime(lDeadTime);
        return questionnaireService.setQuestionnaireInfo(questionNaire);
    }

    @PostMapping(value = "/getQuestion")
    public QuestionNaire register(long id) throws NoSuchAlgorithmException {
        QuestionNaire questionnaireInfo = questionnaireService.getQuestionnaireInfo(id);
        return questionnaireInfo;
    }
}

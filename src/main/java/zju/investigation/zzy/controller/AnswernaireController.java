package zju.investigation.zzy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zju.investigation.zzy.dto.AnswerNaire;
import zju.investigation.zzy.dto.Question;
import zju.investigation.zzy.dto.QuestionNaire;
import zju.investigation.zzy.service.AnswernaireService;
import zju.investigation.zzy.service.QuestionnaireService;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@RestController
public class AnswernaireController {
    @Resource
    private AnswernaireService answernaireService;

    @PostMapping(value = "/setAnswer")
    public Boolean setAnswer(@RequestParam("id") long id,
                             @RequestParam("email") String email,
                             @RequestParam("answers") ArrayList<String> answers) throws NoSuchAlgorithmException, ParseException {
        AnswerNaire answerNaire = new AnswerNaire();
        answerNaire.setCreateTime(System.currentTimeMillis());
        answerNaire.setEmail(email);
        return answernaireService.setAnswernaireInfo(answerNaire, answers);
    }

    @PostMapping(value = "/getAnswer")
    public ArrayList<String> getAnswer(long id, String email) throws NoSuchAlgorithmException {
        return answernaireService.getAnswerNaireInfo(email,id);
    }
}

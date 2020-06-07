package zju.investigation.zzy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zju.investigation.zzy.dto.*;
import zju.investigation.zzy.interceptor.AuthToken;
import zju.investigation.zzy.mapper.AnswerNaireMapper;
import zju.investigation.zzy.mapper.QuestionnaireMapper;
import zju.investigation.zzy.service.AnswernaireService;
import zju.investigation.zzy.service.QuestionnaireService;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@RestController
public class StatisticsController {
    @Resource
    private QuestionnaireMapper questionnaireMapper;

    @Resource
    private QuestionnaireService questionnaireService;

    @Resource
    private AnswernaireService answernaireService;

    @Resource
    private AnswerNaireMapper answerNaireMapper;

    @AuthToken
    @PostMapping(value = "/statisticQuestionInfo")
    public Statistics statisticsQuestionInfo(@RequestParam("id") long id) throws NoSuchAlgorithmException {
        Statistics statistic = questionnaireMapper.getStatisticById(id);
        QuestionNaire questionnaireInfo = questionnaireService.getQuestionnaireInfo(id);
        // 获得所有问题内容和类型
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<Question> questions = questionnaireInfo.getQuestions();
        for (Question question : questions) {
            titles.add(question.getTitle());
            types.add(question.getType());
        }
        statistic.setQuestions(titles);
        // 获得每道题的所有答案
        ArrayList<ArrayList<String>> answers = answernaireService.listAllAnswerInfo(id);
        statistic.setAnswers(answers);
        statistic.setQuestionTypes(types);
        int answerCount = answerNaireMapper.getAnswerCount(id);
        statistic.setFillNumber(answerCount);
        return statistic;
    }

    @AuthToken
    @PostMapping(value = "/statisticReign")
    public GeometryAddress statisticsReign(@RequestParam("id") long id) throws NoSuchAlgorithmException {
        ArrayList<String> reigns = answerNaireMapper.getAllreigns(id);
        GeometryAddress geometryAddress = new GeometryAddress();
        for (String reign : reigns) {
            String[] strings = reign.split("-");
            Geometry geometry = new Geometry();
            Features features = new Features();
            for (String s : strings) {
                geometry.getCoordinates().add(s);
            }
            geometry.getCoordinates().add("0.0");
            features.setGeometry(geometry);
            geometryAddress.getFeatures().add(features);
        }
        return geometryAddress;
    }
}

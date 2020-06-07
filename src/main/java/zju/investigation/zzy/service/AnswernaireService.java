package zju.investigation.zzy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zju.investigation.zzy.dto.*;
import zju.investigation.zzy.mapper.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class AnswernaireService {
    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private AnswerNaireMapper answerNaireMapper;

    @Value("${spring.End}")
    private long End;

    @Value("${spring.questionTypes}")
    private ArrayList<String> questionTypes;

    public ArrayList<String> getAnswerNaireInfo(String email, long id) {
        AnswerNaire answerNaire = answerNaireMapper.getAnswerNaireById(email, id);

        ArrayList<String> answers = new ArrayList<>();

        long nextid = answerNaire.getNextid();
        while (nextid != End) {
            Answer answer = answerMapper.getAnswer(nextid);
            nextid = answer.getNextid();
            String content = answer.getContent();
            answers.add(content);
        }

        return answers;
    }

    public boolean setAnswernaireInfo(AnswerNaire answerNaire, ArrayList<String> answers) {
        Collections.reverse(answers);
        long nextAnswerId = -1;
        for (String answer : answers) {
            long currentChoiceId = answerNaireMapper.getLastId() + 1;
            answerMapper.insertAnswer(currentChoiceId, answer, nextAnswerId);
            nextAnswerId = currentChoiceId;
        }
        answerNaire.setNextid(nextAnswerId);
        answerNaireMapper.insertAnswernaire(answerNaire);
        return true;
    }

    public ArrayList<ArrayList<String>> listAllAnswerInfo(long id) {
        ArrayList<Long> answerIds = answerNaireMapper.getAllanswers(id);
        ArrayList<ArrayList<String>> allAnswers = new ArrayList<>();
        for (Long answerId : answerIds) {
            int index = 0;
            long nextAnswerId = answerId;
            while (nextAnswerId != End) {
                Answer answer = answerMapper.getAnswer(nextAnswerId);
                nextAnswerId = answer.getNextid();
                String content = answer.getContent();
                if (allAnswers.get(index) == null) {
                    allAnswers.add(index, new ArrayList<>());
                }
                allAnswers.get(index).add(content);
                index = index + 1;
            }
        }
        return allAnswers;
    }

}

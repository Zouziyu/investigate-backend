package zju.investigation.zzy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zju.investigation.zzy.dto.Choice;
import zju.investigation.zzy.dto.Question;
import zju.investigation.zzy.dto.QuestionNaire;
import zju.investigation.zzy.mapper.ChoiceMapper;
import zju.investigation.zzy.mapper.QuestionMapper;
import zju.investigation.zzy.mapper.QuestionnaireMapper;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

@Service
public class QuestionnaireService {
    @Resource
    private ChoiceMapper choiceMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionnaireMapper questionnaireMapper;

    @Value("${spring.End}")
    private long End;

    @Value("${spring.questionTypes}")
    private ArrayList<String> questionTypes;

    public QuestionNaire getQuestionnaireInfo(long id) {
        // 根据Id获取问卷内容
        QuestionNaire questionnaire = questionnaireMapper.getQuestionnaireByID(id);
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<String> choices = new ArrayList<>();

        // 获取下一个问卷的内容
        long nextid = questionnaire.getNextid();
        while (nextid != End) {
            Question question = questionMapper.getCurrentQuestion(nextid);
            long nextChoiceId = question.getChoiceId();
            // 获取下一个选项的内容
            while (nextChoiceId != End) {
                Choice choice = choiceMapper.getChoice(nextChoiceId);
                choices.add(choice.getContent());
                nextChoiceId = choice.getNextChoice();
            }
            question.setChoices(choices);
            choices.clear();
            questions.add(question);
        }
        //设置问卷的问题内容
        questionnaire.setQuestions(questions);
        return questionnaire;
    }

    public boolean setQuestionnaireInfo(QuestionNaire questionNaire) {
        // 逆转Questions 方便nextId的获取
        ArrayList<Question> questions = questionNaire.getQuestions();
        Collections.reverse(questions);
        long nextQuestionId = -1;
        // 遍历每一个Question
        for (Question question : questions) {
            long currentQuestionId = questionMapper.getLastId() + 1;
            question.setId(currentQuestionId);
            ArrayList<String> choices = question.getChoices();
            // 逆转Choices 方便nextId的获取
            Collections.reverse(choices);
            long nextChoiceId = -1;
            for (String choice : choices) {
                long currentChoiceId = choiceMapper.getLastId() + 1;
                choiceMapper.insertChoice(currentChoiceId, choice, nextChoiceId);
                nextChoiceId = currentChoiceId;
            }

            question.setChoiceId(nextChoiceId);
            question.setNextId(nextQuestionId);
            nextQuestionId = currentQuestionId;
            // 存储相应的Question
            questionMapper.insertQuestionByType(question);
            questionMapper.insertQuestion(question);
        }

        questionNaire.setCreateTime(LocalDate.now().toString());
        questionnaireMapper.insertQuestionnaire(questionNaire);
        return true;
    }
}

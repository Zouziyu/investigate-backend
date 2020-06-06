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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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

    QuestionNaire getQuestionnaireInfo(long id) {
        QuestionNaire questionnaire = questionnaireMapper.getQuestionnaireByID(id);
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Choice> choices = new ArrayList<>();
        long nextid = questionnaire.getNextid();
        while (nextid != End) {
            // 解析问题的类型
            String s = String.valueOf(nextid);
            int t = Integer.parseInt(s.substring(0, 1));
            long nextQuestionId = Long.parseLong(s.substring(1));
            String type = questionTypes.get(t);

            Question question = questionMapper.getCurrentQuestion(type, nextQuestionId);
            // 解析当前的选项
            long nextChoiceId = question.getNextChoice();
            while (nextChoiceId != End) {
                Choice choice = choiceMapper.getChoice(nextChoiceId);
                choices.add(choice);
                nextChoiceId = choice.getNextChoice();
            }
            // 保存当前选项
            question.setChoices(choices);
            choices.clear();
            questions.add(question);
        }
        questionnaire.setQuestions(questions);
        return questionnaire;
    }

    boolean setQuestionnaireInfo(QuestionNaire questionNaire) {
        ArrayList<Question> questions = questionNaire.getQuestions();
        Iterator questionIterator = questions.iterator();
        while (questionIterator.hasNext()) {
            Question currentQuestion = (Question) questionIterator.next();

        }
//        for (int i = 0, j = 1; j < questions.size(); i++, j++) {
//            Question current_question = questions.get(i);
//            Question next_question = questions.get(j);
//            long current_newId = questionMapper.getLastId(current_question.getType());
//            long next_newId = questionMapper.getLastId(current_question.getType());
//            if (next_newId == current_newId) {
//                next_newId += 1;
//            }
//            ArrayList<Choice> choices = current_question.getChoices();
//            for(int ci = 0)
//            current_question.setId(current_newId);
//            current_question.setNextid(next_newId);
//            questionMapper.insertCurrentQuestion(current_question);
//        }
    }
}

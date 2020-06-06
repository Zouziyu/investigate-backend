package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class QuestionNaire {
    long id;
    String title;
    String content;
    long createTime;
    long deadTime;
    String email;
    long nextid;
    ArrayList<Question> questions;
    ArrayList<Choice> choices;
}

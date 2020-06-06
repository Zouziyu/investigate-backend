package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AnswerNaire {
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

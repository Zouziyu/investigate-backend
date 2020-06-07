package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class QuestionNaire {
    long id;
    String title;
    String content;
    String createTime;
    String deadTime;
    String email;
    long nextid;
    ArrayList<Question> questions;
}

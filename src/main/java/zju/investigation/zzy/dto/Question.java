package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Question {
    long id;
    long nextId;
    String title;
    String type;
    long choiceId;
    ArrayList<String> choices;
}

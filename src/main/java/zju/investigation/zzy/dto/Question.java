package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Question {
    long id;
    String title;
    String type;
    long nextid;
    long nextChoice;
    ArrayList<Choice> choices;
}

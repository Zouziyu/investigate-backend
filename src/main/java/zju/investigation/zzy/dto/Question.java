package zju.investigation.zzy.dto;

import lombok.Data;

@Data
public class Question {
    long id;
    String title;
    long nextid;
    long nextChoice;
}

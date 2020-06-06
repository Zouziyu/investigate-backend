package zju.investigation.zzy.dto;

import lombok.Data;

@Data
public class Choice {
    long id;
    String content;
    long nextChoice;
}

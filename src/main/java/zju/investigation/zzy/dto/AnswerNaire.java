package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AnswerNaire {
    long id;
    String email;
    String address;
    long nextid;
    String createTime;
    String geometry;
}

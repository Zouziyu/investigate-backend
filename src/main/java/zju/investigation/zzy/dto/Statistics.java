package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Statistics {
    String title;
    String createTime;
    String deadTime;
    String number;
    int fillNumber;
    String type;
    ArrayList<String> questions;
    ArrayList<ArrayList<String>> answers;
    ArrayList<String> questionTypes;
}

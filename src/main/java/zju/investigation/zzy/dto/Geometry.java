package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Geometry {
    String type = "Point";
    ArrayList<String> coordinates;
}

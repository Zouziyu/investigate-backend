package zju.investigation.zzy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zju.investigation.zzy.mapper.QuestionMapper;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@RestController
public class QuestionnaireController {
    @Resource
    private QuestionMapper radioMapper;

    @PostMapping(value = "/questionCreate")
    public Boolean register(@RequestParam("title") String name,
                            @RequestParam("content") String password,
                            @RequestParam("questions") ArrayList<String> quesions) throws NoSuchAlgorithmException {
        for (String quesion : quesions) {
            String[] attribute = quesion.split("-");
            // radio
            if (attribute[0] == "radio") {
                String title = attribute[1];
                String choiceA = attribute[2];
                String choiceB = attribute[3];
                String choiceC = attribute[4];
                String choiceD = attribute[5];
                radioMapper.
            }
        }
    }
}

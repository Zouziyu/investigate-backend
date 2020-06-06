package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zju.investigation.zzy.dto.Answer;
import zju.investigation.zzy.dto.Question;

@Mapper
public interface AnswerMapper {

    @Select("select * from answer where id = #{id}")
    Answer getAnswer(long id);

    @Insert("insert into answer (id, answerContent, nextid) values (#{id},#{answerContent},#{nextid})")
    void insertAnswer(long id, String answerContent, long nextid);


}

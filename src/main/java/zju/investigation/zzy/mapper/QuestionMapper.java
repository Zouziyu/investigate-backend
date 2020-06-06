package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zju.investigation.zzy.dto.Question;

@Mapper
public interface QuestionMapper {

    @Select("select * from question where id = #{id}")
    Question getCurrentQuestion(long id);

    @Insert("insert into question (id, nextId, type, choiceId) values (#{id},#{nextId},#{type}),#{choiceId}")
    void insertQuestion(Question question);

    @Insert("insert into #{type} (id, title) values (#{id},#{title}")
    void insertQuestionByType(Question question);

    @Select("select max(id) from question")
    long getLastId();

    @Update("update questionnaire set number = #{number} where id=#{id}")
    void setQuestionNumber(long id, int number);

    @Select("Select type from questionnaire where id = #{id}")
    int getType(long id);

    @Select("Select number from questionnaire")
    int getQuestionCount(long id);
}

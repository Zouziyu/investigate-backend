package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import zju.investigation.zzy.dto.Question;
import zju.investigation.zzy.dto.QuestionNaire;
import zju.investigation.zzy.dto.Statistics;

@Mapper
public interface QuestionnaireMapper {
    @Select("select * from questionnaire where id = #{id}")
    QuestionNaire getQuestionnaireByID(long id);

    @Insert("insert into questionnaire (id, title, content, createTime, deadTime, email, nextid) values (#{id},#{title},#{content},#{createTime},#{deadTime},#{email},#{nextid})")
    void insertQuestionnaire(QuestionNaire questionNaire);

    @Select("select last_insert_id() from questionnaire")
    long getLastId();

    @Select("select * from questionnaire where id = #{id}")
    Statistics getStatisticById(long id);
}

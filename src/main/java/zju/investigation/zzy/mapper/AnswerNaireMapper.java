package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import zju.investigation.zzy.dto.AnswerNaire;
import zju.investigation.zzy.dto.QuestionNaire;

@Mapper
public interface AnswerNaireMapper {
    @Insert("insert into answernaire (id, email, nextid, createTime) values (#{id},#{email},#{nextid},#{createTime})")
    void insertAnswernaire(AnswerNaire answerNaire);

    @Select("select last_insert_id() from questionnaire")
    long getLastId();

    @Select("select * from questionnaire where id = #{id} and email = #{email}")
    AnswerNaire getAnswerNaireById(String email, long id);
}

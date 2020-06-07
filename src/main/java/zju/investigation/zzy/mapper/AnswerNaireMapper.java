package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import zju.investigation.zzy.dto.AnswerNaire;
import zju.investigation.zzy.dto.QuestionNaire;

import java.util.ArrayList;

@Mapper
public interface AnswerNaireMapper {
    @Insert("insert into answernaire (id, email, nextid, createTime) values (#{id},#{email},#{nextid},#{createTime})")
    void insertAnswernaire(AnswerNaire answerNaire);

    @Select("select max(id) from questionnaire")
    long getLastId();

    @Select("select * from answernaire where id = #{id} and email = #{email}")
    AnswerNaire getAnswerNaireById(String email, long id);

    @Select("select count(*) from answernaire where id = #{id}")
    int getAnswerCount(long id);

    @Select("select count(*) from answernaire where id = #{id} and address = #{address}")
    int getAnswerCountByIp(String address, long id);

    @Select("select * from answernaire where to_days(createTime) = to_days(now()) and id = #{id};")
    int getDailyAnswerCount(long id);

    @Select("select * from answernaire where id = #{id}")
    ArrayList<Long> getAllanswers(long id);

    @Select("select geometry from answernaire where id = #{id}")
    ArrayList<String> getAllreigns(long id);
}

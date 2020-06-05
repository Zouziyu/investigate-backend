package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zju.investigation.zzy.dto.Question;

@Mapper
public interface QuestionMapper {

    @Select("select * from #{type} where id = #{id}")
    Question getCurrentQuestion(String type, long id);

    @Insert("insert into Question (id, nextid, nextChoice) values (#{id},#{nextid},#{nextChoice})")
    void insertCurrentQuestion(long id, long nextid, String nextChoice);


}

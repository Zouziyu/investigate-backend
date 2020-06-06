package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zju.investigation.zzy.dto.Choice;
import zju.investigation.zzy.dto.Question;

@Mapper
public interface ChoiceMapper {

    @Select("select * from choice where id = #{id}")
    Choice getChoice(long id);

    @Insert("insert into choice (id, content, nextChoice) values (#{id},#{content},#{nextChoice})")
    void insertChoice(long id, String content, long nextChoice);


}

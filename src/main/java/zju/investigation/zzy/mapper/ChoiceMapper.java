package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zju.investigation.zzy.dto.Question;

@Mapper
public interface ChoiceMapper {

    @Select("select * from #{type} where id = #{id}")
    Question getChoice(String type, long id);

    @Insert("insert into Choice (id, content, nextid) values (#{id},#{content},#{nextid})")
    void insertChoice(long id, String content, long nextid);


}

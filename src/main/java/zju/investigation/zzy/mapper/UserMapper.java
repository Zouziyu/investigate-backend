package zju.investigation.zzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zju.group1.forum.dto.User;

@Mapper
public interface UserMapper {

    @Insert("insert into user (email,name,password) values (#{email},#{name},#{password})")
    void createUser(String email, String name, String password);

    @Select("select count(*) from user where email = #{email} and password = #{password}")
    int getUserByEmailAndPassword(String email, String password);

    //
//    @Update("update user set password = #{password} where email = #{email} ")
//    void updatePassword(String email, String password);
//
    @Select("select count(*) from user where email=#{email}")
    int isEmailExist(String email);

    @Select("select count(*) from user where name=#{name}")
    int isNameExist(String email);

//
//    @Select("select count(*) from user where name=#{name}")
//    int isNameExist(User user);
//
//    @Select("select name from user where email = #{email}")
//    String searchName(String email);
//
//    @Select("select isadmin from user where email = #{email}")
//    String isAdmin(String email);
//
//    @Update("update user set isadmin = #{isAdmin} where email = #{email} ")
//    void setAdmin(String email, String isAdmin);
//
//    @Update("update user set avatarUrl = #{filepath} where email = #{email}")
//    void updateAvatarUrl(String email, String filepath);
//
//    @Select("select avatarUrl from user where name=#{name}")
//    String getAvatarUrlByName(String name);
//
//    @Select("select email from user where name=#{name}")
//    String getEmailByName(String name);
}

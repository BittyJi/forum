package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xiaoji.forum.forum.model.User;

/**
 * @Author 纪钦涛
 * @Date 2020/2/28 17:20
 * @Version 1.0
 */

@Repository
@Mapper
public interface UserMapper {
    @Insert("Insert into User (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified}) ")
    void insert(User user);

    @Select("select * from user where token =#{token}")
    User findByToken(@Param("token") String token);
}

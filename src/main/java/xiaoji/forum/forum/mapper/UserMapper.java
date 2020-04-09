package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xiaoji.forum.forum.model.User;

import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/2/28 17:20
 * @Version 1.0
 */

@Repository
@Mapper
public interface UserMapper {
    @Insert("Insert into User (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl}) ")
    void insert(User user);

    @Select("select * from user where token =#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
     User findByid(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);


    @Select("<script> select * from user where id in" +
            "  <foreach collection='list' item='userIds' open='(' separator=',' close=')'>" +
            "    #{userIds}" +
            "  </foreach>" +
            "</script>")
    List<User> selectByComList(List<Integer> userIds);
}

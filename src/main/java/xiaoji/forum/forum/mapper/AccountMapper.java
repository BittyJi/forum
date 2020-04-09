package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xiaoji.forum.forum.model.Account;

/**
 * @Author 纪钦涛
 * @Date 2020/4/1 0:42
 * @Version 1.0
 */

@Repository
@Mapper
public interface AccountMapper {

    @Insert("insert into account (user_name,email,user_pwd,gmt_create)  values (#{name},#{email},#{password},#{gmtCreate}) ")
    void insertaccount(
            @Param(value = "name") String name,
            @Param(value = "email") String email,
            @Param(value = "password") String password,
            @Param(value = "gmtCreate") Long gmtCreate);

    @Select("select * from account where email=#{email}")
    Account selectByEmail(@Param(value = "email") String email);

    @Update("update account set user_name=#{userName},user_pwd=#{userPwd},iphone=#{iphone},sex=#{sex} where email=#{email}")
    void updateAccount(Account account);
}

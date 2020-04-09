package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import sun.awt.SunHints;
import xiaoji.forum.forum.model.Notification;

import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/3/30 23:21
 * @Version 1.0
 */
@Repository
@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier,notifier_name,receiver,outer_id,outer_title,type,gmt_create,status) values(#{notifier},#{notifierName},#{receiver},#{outerId},#{outerTitle},#{type},#{gmtCreate},#{status})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver=#{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId
                          );

    @Select("select count(1) from notification where receiver=#{userId} and status=#{status}")
    Integer countByUserIdAndStatus(@Param(value = "userId") Integer userId,
                          @Param(value = "status") Integer status);

    @Select("select * from notification where receiver=#{userId} Order By gmt_create Desc limit #{offset},#{size} ")
    List<Notification> listByUserId(
            @Param(value = "userId") Integer userId,
            @Param(value = "offset") Integer offset,
            @Param(value = "size") Integer size);

    @Select("select * from notification where id=#{id}")
    Notification selectById(Integer id);

    @Update("update notification set status=#{status} where id=#{id}")
    void updateStatusById(Notification notification);
}

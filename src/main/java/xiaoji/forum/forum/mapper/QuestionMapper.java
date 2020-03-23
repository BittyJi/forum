package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import xiaoji.forum.forum.model.Question;


import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/3/5 1:09
 * @Version 1.0
 */
@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)  values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    public void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    public List<Question> list(@Param(value = "offset") Integer offset,
                               @Param(value = "size") Integer size);

    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    public List<Question> listByUserId(@Param("userId") Integer userId,
                                       @Param(value = "offset") Integer offset,
                                       @Param(value = "size") Integer size);


    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where Creator =#{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
}

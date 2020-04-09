package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import xiaoji.forum.forum.dto.QusetionQueryDto;
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

    @Select("<script>select * from question <where><if test=\"search != null and search != '' \"> and title regexp #{search} </if> </where> Order By gmt_create Desc limit #{page},#{size}</script>")
    public List<Question> list(QusetionQueryDto qusetionQueryDto);

    @Select("select * from question where creator=#{userId} Order By gmt_create Desc limit #{offset},#{size} ")
    public List<Question> listByUserId(@Param(value = "userId") Integer userId,
                                       @Param(value = "offset") Integer offset,
                                       @Param(value = "size") Integer size);


    @Select("<script>select count(1) from question <where>  <if test=\"search != null and search != '' \"> and title regexp #{search} </if> </where> </script> ")
    Integer count(QusetionQueryDto qusetionQueryDto);

    @Select("select count(1) from question where Creator =#{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);

    @Update("update question set view_count=view_count+1 where id=#{id}")
    void incView(Integer id);

    @Select("select * from question where id=#{id}")
    Question selectByParentId(@Param("id") Integer id);

    @Update("update question set comment_count=comment_count+1 where id=#{id}")
    void  incCommentCount(Question question);

    @Select("select * from question where id !=#{id} and tag REGEXP #{tag}")
    List<Question> selectRelated(Question question);
}

package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
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

    @Select("select count(1) from question")
    Integer count();

}

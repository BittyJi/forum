package xiaoji.forum.forum.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xiaoji.forum.forum.model.Comment;

import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/3/24 16:07
 * @Version 1.0
 */
@Repository
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id,type,content,commentor,gmt_create,gmt_modified,like_count) values(#{parentId},#{type},#{content},#{commentor},#{gmtCreate},#{gmtModified},#{likeCount})")
    public void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment selectId(@Param("id") Integer id);

    @Select("select * from comment where parent_id=#{parentId} and type=#{type}")
    List<Comment> selectByParentIdAndType(@Param(value = "parentId") Integer parentId,
                                          @Param(value = "type")Integer type);

    @Update("update comment set comment_count=comment_count+1 where id=#{id}")
    void inCommentCount(Comment comment);
}

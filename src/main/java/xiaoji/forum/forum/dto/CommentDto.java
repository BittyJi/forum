package xiaoji.forum.forum.dto;

import lombok.Data;
import xiaoji.forum.forum.model.User;

/**
 * @Author 纪钦涛
 * @Date 2020/3/27 15:36
 * @Version 1.0
 */
@Data
public class CommentDto {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentor;
    private Integer commentCount;
    private Long gmtCreate;
    private Long gmtModified;
    private String content;
    private User user;
}

package xiaoji.forum.forum.model;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/3/24 16:01
 * @Version 1.0
 */
@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private String content;
    private Integer commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;

}

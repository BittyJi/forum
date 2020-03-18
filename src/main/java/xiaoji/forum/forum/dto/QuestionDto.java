package xiaoji.forum.forum.dto;

import lombok.Data;
import xiaoji.forum.forum.model.User;

/**
 * @Author 纪钦涛
 * @Date 2020/3/6 23:13
 * @Version 1.0
 */
@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer creator;
    private String tag;
    private User user;

}

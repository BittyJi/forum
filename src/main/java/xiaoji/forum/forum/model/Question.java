package xiaoji.forum.forum.model;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/3/5 1:10
 * @Version 1.0
 */

@Data
public class Question {
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
}

package xiaoji.forum.forum.dto;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/3/24 16:22
 * @Version 1.0
 */
@Data
public class CommentCreateDto {
    private Integer parentId;
    private String content;
    private Integer type;
}

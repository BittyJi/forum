package xiaoji.forum.forum.dto;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/3/31 17:33
 * @Version 1.0
 */
@Data
public class QusetionQueryDto {
    private String search;
    private Integer page;
    private Integer size;
}

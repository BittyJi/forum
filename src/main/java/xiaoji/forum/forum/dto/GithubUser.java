package xiaoji.forum.forum.dto;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/2/20 0:17
 * @Version 1.0
 */

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}

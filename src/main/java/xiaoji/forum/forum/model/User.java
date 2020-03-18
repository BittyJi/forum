package xiaoji.forum.forum.model;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/2/28 17:25
 * @Version 1.0
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}

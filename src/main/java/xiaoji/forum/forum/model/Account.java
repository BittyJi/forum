package xiaoji.forum.forum.model;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/4/1 14:34
 * @Version 1.0
 */

@Data
public class Account {
    private Integer id;
    private String userName;
    private String userPwd;
    private String email;
    private Integer sex;
    private String iphone;

    private Long gmtCreate;
    private Long gmtModified;
}

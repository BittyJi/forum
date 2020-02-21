package xiaoji.forum.forum.dto;

import lombok.Data;

/**
 * @Author 纪钦涛
 * @Date 2020/2/19 23:40
 * @Version 1.0
 */

@Data
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}

package xiaoji.forum.forum.model;

import lombok.Data;

import java.math.BigInteger;

/**
 * @Author 纪钦涛
 * @Date 2020/3/30 23:15
 * @Version 1.0
 */
@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private String notifierName;
    private Integer receiver;
    private Integer outerId;
    private String outerTitle;
    private Integer type;
    private Long gmtCreate;
    private Integer status;


}

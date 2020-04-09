package xiaoji.forum.forum.enums;

/**
 * @Author 纪钦涛
 * @Date 2020/3/30 23:33
 * @Version 1.0
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    public int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}

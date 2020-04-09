package xiaoji.forum.forum.utils;

import org.springframework.util.DigestUtils;

/**
 * @Author 纪钦涛
 * @Date 2020/3/31 21:58
 * @Version 1.0
 */
public class MD5Util {
    private static String salt = "Xiaoji";

    public static String getMD5(String pwd) {
        String base = pwd + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


}

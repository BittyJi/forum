package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiaoji.forum.forum.mapper.AccountMapper;
import xiaoji.forum.forum.model.Account;
import xiaoji.forum.forum.model.LoginUser;
import xiaoji.forum.forum.utils.MD5Util;

import java.util.HashMap;

/**
 * @Author 纪钦涛
 * @Date 2020/3/31 22:25
 * @Version 1.0
 */

@Controller
public class RegisteredController {

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/registered")
    public String registered(
    ){
        return "registered";
    }
    @PostMapping("/registered")
    public String registered(
            @RequestParam(name = "name")String name,
            @RequestParam(name = "email")String email,
            @RequestParam(name = "password")String password
    ){
        MD5Util md5Util = new MD5Util();
        String mdpassword = md5Util.getMD5(password);
        Long gmtCreate = System.currentTimeMillis();
        accountMapper.insertaccount(name,email,mdpassword,gmtCreate);
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/registered/existEmail", method = RequestMethod.POST)
    public HashMap existEmail(
            @RequestBody LoginUser email
    ){
        HashMap hashMap = new HashMap();
        Account account =accountMapper.selectByEmail(email.getEmail());
        if(account==null){
            hashMap.put("code",200);
            hashMap.put("message","邮箱可用");
        }
        else {
            hashMap.put("code",500);
            hashMap.put("message","邮箱已存在");
        }

        return hashMap;
    }

}

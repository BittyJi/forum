package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xiaoji.forum.forum.mapper.AccountMapper;
import xiaoji.forum.forum.model.Account;
import xiaoji.forum.forum.model.LoginUser;
import xiaoji.forum.forum.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @Author 纪钦涛
 * @Date 2020/4/1 17:02
 * @Version 1.0
 */
@Controller
public class LoginController {
    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap Login(
            @RequestBody LoginUser loginUser,
            Model model, HttpServletRequest request
    ){
        HashMap hashMap = new HashMap();
        Account account =accountMapper.selectByEmail(loginUser.getEmail());
        if (account ==null){
            hashMap.put("code",300);
            hashMap.put("message","用户不存在");
        }
        else {
            MD5Util md5Util = new MD5Util();

            String userPwd= account.getUserPwd();

            if(userPwd.equals(md5Util.getMD5(loginUser.getUserPwd()))){
                hashMap.put("code",200);
                hashMap.put("message","登录成功");
                request.getSession().setAttribute("account",account);

            }
            else {
                hashMap.put("code",500);
                hashMap.put("message","邮箱或密码错误");

            }
        }
        model.addAttribute("hash",hashMap);
        return  hashMap;

    }

}

package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xiaoji.forum.forum.mapper.AccountMapper;
import xiaoji.forum.forum.model.Account;
import xiaoji.forum.forum.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author 纪钦涛
 * @Date 2020/4/1 23:00
 * @Version 1.0
 */

@Controller
public class InformationController {

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/information")
    public String information() {
        return "information";
    }

    @PostMapping("/information")
    public String information(@RequestParam(name = "userName", required = false) String userName,
                              @RequestParam(name = "userPwd", required = false) String userPwd,
                              @RequestParam(name = "iphone", required = false) String iphone,
                              @RequestParam(name = "sex", required = false) Integer sex,
                              HttpServletRequest request) {
        Account account = new Account();
        account.setUserName(userName);
        account.setUserPwd(MD5Util.getMD5(userPwd));
        account.setIphone(iphone);
        account.setSex(sex);
        Account session = (Account) request.getSession().getAttribute("account");
        account.setEmail(session.getEmail());
        accountMapper.updateAccount(account);
        Account accountData = accountMapper.selectByEmail(session.getEmail());
        request.getSession().invalidate();
        request.getSession().setAttribute("account",accountData);
        return "redirect:information";
    }

}

package xiaoji.forum.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 纪钦涛
 * @Date 2020/2/13 17:12
 * @Version 1.0
 */

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexController(){
        return "index";
    }


}

package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xiaoji.forum.forum.dto.PaginationDto;
import xiaoji.forum.forum.service.QuestionService;

/**
 * @Author 纪钦涛
 * @Date 2020/2/13 17:12
 * @Version 1.0
 */

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {

        PaginationDto pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }


}

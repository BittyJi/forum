package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xiaoji.forum.forum.dto.PaginationDto;
import xiaoji.forum.forum.model.User;
import xiaoji.forum.forum.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 纪钦涛
 * @Date 2020/3/18 13:56
 * @Version 1.0
 */

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我发起的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新的回复");
        }
        PaginationDto paginationDto = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination", paginationDto);
        return "profile";
    }
}

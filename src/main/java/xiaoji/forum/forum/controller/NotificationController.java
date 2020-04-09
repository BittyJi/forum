package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xiaoji.forum.forum.dto.NotificationDto;
import xiaoji.forum.forum.dto.PaginationDto;
import xiaoji.forum.forum.model.User;
import xiaoji.forum.forum.service.NotificationService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 纪钦涛
 * @Date 2020/3/31 2:43
 * @Version 1.0
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request,
                          Model model
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "redirect:/";
        }
        
        NotificationDto notificationDto = notificationService.read(id, user);
        return "redirect:/question/" + notificationDto.getNotifier();
    }
}

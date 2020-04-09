package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xiaoji.forum.forum.dto.CommentDto;
import xiaoji.forum.forum.dto.QuestionDto;
import xiaoji.forum.forum.enums.CommentTypeEnum;
import xiaoji.forum.forum.service.CommentService;
import xiaoji.forum.forum.service.QuestionService;

import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/3/19 14:14
 * @Version 1.0
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        QuestionDto questionDto = questionService.getById(id);
        List<CommentDto> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        List<QuestionDto> relateQuestions = questionService.selectRelated(questionDto);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relateQuestions",relateQuestions);
        return "question";
    }
}

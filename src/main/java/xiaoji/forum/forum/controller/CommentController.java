package xiaoji.forum.forum.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xiaoji.forum.forum.dto.CommentCreateDto;
import xiaoji.forum.forum.dto.CommentDto;
import xiaoji.forum.forum.enums.CommentTypeEnum;
import xiaoji.forum.forum.model.Comment;
import xiaoji.forum.forum.model.User;
import xiaoji.forum.forum.service.CommentService;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/3/24 16:07
 * @Version 1.0
 */

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        if(commentCreateDto ==null || StringUtils.isBlank(commentCreateDto.getContent())){
            hashMap.put("message", "评论内容不能为空");
            hashMap.put("code", "9000");
        }
        if (user == null) {
            hashMap.put("message", "未登录,请登录后在操作");
            hashMap.put("code", "8001");
        } else {

            Comment comment = new Comment();
            comment.setParentId(commentCreateDto.getParentId());
            comment.setType(commentCreateDto.getType());
            comment.setContent(commentCreateDto.getContent());
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(System.currentTimeMillis());
            comment.setLikeCount(0L);
            comment.setCommentor(user.getId());
            hashMap = (HashMap<Object, Object>) commentService.insert(comment,user);
        }

        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public  HashMap<Object,List<CommentDto>> comments(@PathVariable(name = "id") Integer id,
                                                      Model model){
        List<CommentDto> commentDtos =commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        HashMap<Object,List<CommentDto>> hashMap = new HashMap<>();
        hashMap.put("data", commentDtos);
        model.addAttribute("data",commentDtos);

        return hashMap;
    }

}

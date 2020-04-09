package xiaoji.forum.forum.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaoji.forum.forum.dto.CommentDto;
import xiaoji.forum.forum.enums.CommentTypeEnum;
import xiaoji.forum.forum.enums.NotificationStatusEnum;
import xiaoji.forum.forum.enums.NotificationTypeEnum;
import xiaoji.forum.forum.mapper.CommentMapper;
import xiaoji.forum.forum.mapper.NotificationMapper;
import xiaoji.forum.forum.mapper.QuestionMapper;
import xiaoji.forum.forum.mapper.UserMapper;
import xiaoji.forum.forum.model.Comment;
import xiaoji.forum.forum.model.Notification;
import xiaoji.forum.forum.model.Question;
import xiaoji.forum.forum.model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author 纪钦涛
 * @Date 2020/3/24 16:07
 * @Version 1.0
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    @ResponseBody
    public Object insert(Comment comment,User commentator ){
        HashMap<Object, Object> hashMap = new HashMap<>();
        if(comment.getParentId() == null || comment.getParentId()==0){
            hashMap.put("message", "未选中问题进行回复");
            hashMap.put("code", "8002");
        }
        if(comment.getType() ==null || !CommentTypeEnum.isExist(comment.getType())){
            hashMap.put("message","评论内容不存在");
            hashMap.put("code", "8003");
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectId(comment.getParentId());
            if(dbComment ==null){
                hashMap.put("message","评论不存在");
                hashMap.put("code", "8004");

            }
            //回复问题
            Question question=questionMapper.selectByParentId(dbComment.getParentId());
            if(question ==null){
                hashMap.put("message","问题不存在");
                hashMap.put("code", "8005");
            }
            else {
                hashMap.put("message","成功");
                hashMap.put("code", "200");
                commentMapper.insert(comment);
                //增加评论数
                commentMapper.inCommentCount(dbComment);
                //创建通知
                createNotify(comment, dbComment.getCommentor(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
            }
        }else {
            //回复问题

            Question question=questionMapper.selectByParentId(comment.getParentId());
            if(question ==null){
                hashMap.put("message","问题不存在");
                hashMap.put("code", "8005");
            }
            else {
                hashMap.put("message","成功");
                hashMap.put("code", "200");
                commentMapper.insert(comment);
                questionMapper.incCommentCount(question);
                //创建通知
                createNotify(comment, question.getCreator(), commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION, question.getId());
            }

        }


        return hashMap;
    }

    private void createNotify(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Integer outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setNotifier(outerId);
        notification.setOuterId(comment.getParentId());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    @Transactional
    public List<CommentDto> listByTargetId(Integer id, CommentTypeEnum typeEnum) {

        List<Comment> comments = commentMapper.selectByParentIdAndType(id, typeEnum.getType());
        if(comments.size() ==0){
            return new ArrayList<>();
        }
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        List<User> users = userMapper.selectByComList(userIds);
        Map<Integer,User> userMap =users.stream().collect(Collectors.toMap(user -> user.getId(),user-> user));

        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDTO = new CommentDto();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}

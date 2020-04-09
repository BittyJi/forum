package xiaoji.forum.forum.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaoji.forum.forum.dto.NotificationDto;
import xiaoji.forum.forum.dto.PaginationDto;
import xiaoji.forum.forum.enums.NotificationStatusEnum;
import xiaoji.forum.forum.enums.NotificationTypeEnum;
import xiaoji.forum.forum.mapper.NotificationMapper;
import xiaoji.forum.forum.model.Notification;
import xiaoji.forum.forum.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/3/31 0:27
 * @Version 1.0
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;


    public PaginationDto list(Integer userId, Integer page, Integer size) {

        PaginationDto<NotificationDto> paginationDto = new PaginationDto();
        Integer totalPage;

        Integer totalCount = notificationMapper.countByUserId(userId);
        //判断最大的页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDto.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        if(offset<0){
            offset=0;
        }
        List<Notification> notifications  = notificationMapper.listByUserId(userId, offset, size);

        if(notifications.size() ==0){
            return paginationDto;
        }

        List<NotificationDto> notificationDtos = new ArrayList<>();
        for (Notification notification:notifications){
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDtos.add(notificationDto);
        }
        paginationDto.setData(notificationDtos);

        return paginationDto;

    }

    public Integer unreadCount(Integer userId) {
        return notificationMapper.countByUserIdAndStatus(userId,NotificationStatusEnum.UNREAD.getStatus());
    }

    public NotificationDto read(Integer id, User user) {
        Notification notification = notificationMapper.selectById(id);
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateStatusById(notification);
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);

        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;
    }
}

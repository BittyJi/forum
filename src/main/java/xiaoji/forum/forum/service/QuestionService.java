package xiaoji.forum.forum.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xiaoji.forum.forum.dto.PaginationDto;
import xiaoji.forum.forum.dto.QuestionDto;
import xiaoji.forum.forum.mapper.QuestionMapper;
import xiaoji.forum.forum.mapper.UserMapper;
import xiaoji.forum.forum.model.Question;
import xiaoji.forum.forum.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 纪钦涛
 * @Date 2020/3/6 23:11
 * @Version 1.0
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDto list(Integer page, Integer size){

        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.count();
        paginationDto.setPagination(totalCount,page,size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDto.getTotalPage()) {
            page = paginationDto.getTotalPage();
        }
        Integer offset = size*(page -1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList =new ArrayList<>();

        for (Question question:questions){
            User user = userMapper.findByid(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);

        return paginationDto;
    }
}

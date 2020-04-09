package xiaoji.forum.forum.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaoji.forum.forum.dto.PaginationDto;
import xiaoji.forum.forum.dto.QuestionDto;
import xiaoji.forum.forum.dto.QusetionQueryDto;
import xiaoji.forum.forum.mapper.QuestionMapper;
import xiaoji.forum.forum.mapper.UserMapper;
import xiaoji.forum.forum.model.Question;
import xiaoji.forum.forum.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public PaginationDto list(Integer page, Integer size,String search) {

        PaginationDto<QuestionDto> paginationDto = new PaginationDto<>();
        if(StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
             search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        //分割

        QusetionQueryDto qusetionQueryDto = new QusetionQueryDto();
        qusetionQueryDto.setSearch(search);
        Integer totalCount = questionMapper.count(qusetionQueryDto);
        Integer totalPage;
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
            offset =0;
        }
        qusetionQueryDto.setSize(size);
        qusetionQueryDto.setPage(offset);
        List<Question> questions = questionMapper.list(qusetionQueryDto);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findByid(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setData(questionDtoList);

        return paginationDto;
    }

    public PaginationDto list(Integer userId,
                              Integer page,
                              Integer size) {

        PaginationDto<QuestionDto> paginationDto = new PaginationDto<>();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
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

        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findByid(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setData(questionDtoList);

        return paginationDto;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.findByid(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }

    }

    public void incView(Integer id) {
         questionMapper.incView(id);
    }
    public void incCommentCount(Question question){
        questionMapper.incCommentCount(question);
    }


    public List<QuestionDto> selectRelated(QuestionDto queryDto) {
        if(StringUtils.isBlank(queryDto.getTag())){
            return new ArrayList<>();
        }
        //分割
        String[] tags = StringUtils.split(queryDto.getTag(), ",，");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionMapper.selectRelated(question);
        //
        List<QuestionDto> questionDtos= questions.stream().map(q ->{
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q,questionDto);
            return questionDto;
        } ).collect(Collectors.toList());
        return questionDtos;
    }
}

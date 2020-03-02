package xiaoji.forum.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xiaoji.forum.forum.dto.AccessTokenDto;
import xiaoji.forum.forum.dto.GithubUser;
import xiaoji.forum.forum.mapper.UserMapper;
import xiaoji.forum.forum.model.User;
import xiaoji.forum.forum.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


/**
 * @Author 纪钦涛
 * @Date 2020/2/19 0:04
 * @Version 1.0r
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state,
                            HttpServletRequest requeset){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser !=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.Insert(user);
            requeset.getSession().setAttribute("githubUser",githubUser);
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }

    }
}

package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;
import com.watchapedia.watchpedia_user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @GetMapping("login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/signup")
    public String loginOk(@ModelAttribute UserRequestDto userRequestDto, HttpSession session){
        userRequestDto = userService.login(userRequestDto);
        if(userRequestDto != null){
            User user = userService.findEmail(userRequestDto.userEmail());
            UserSessionDto userSessionDto = UserSessionDto.from(user);

            // 세션 유지시간 설정(초단위로)
            // 60*60 = 1시간
            int sTime = 60*60;
            session.setMaxInactiveInterval(sTime);
            session.setAttribute("userSession", userSessionDto);
            return "redirect:/";
        }else{

            return "user/login";
        }
    }

    @GetMapping("/mypage")
    public String mypage(){
        return "mypage/myPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("regist")
    public String regist(){
        return "user/userRegist";
    }


}

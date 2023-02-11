package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;
import com.watchapedia.watchpedia_user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

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
    public String loginOk(@ModelAttribute UserRequestDto userRequestDto, HttpSession session, ModelMap map){
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
            map.addAttribute("loginerrorMessage", "이메일 또는 비밀번호를 확인하세요");
            return "user/login";
        }
    }

    @PostMapping("/signup/ajax")
    @ResponseBody
    public boolean loginOk(@RequestBody UserRequestDto userRequestDto, HttpSession session) throws IOException {
        userRequestDto = userService.login(userRequestDto);
        if(userRequestDto != null){
            User user = userService.findEmail(userRequestDto.userEmail());
            UserSessionDto userSessionDto = UserSessionDto.from(user);

            // 세션 유지시간 설정(초단위로)
            // 60*60 = 1시간
            int sTime = 60*60;
            session.setMaxInactiveInterval(sTime);
            session.setAttribute("userSession", userSessionDto);
            return true;
        }else{
            return false;
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
    @PostMapping("/registOk")
    public String save(@ModelAttribute UserRequestDto userRequestDto, ModelMap map) {
        String result = "";
        result = this.userService.save(userRequestDto);
        if (result.equals("USEREMAILALREADYEXIST")) {
            map.addAttribute("errorMessage", "이미 존재하는 이메일주소입니다.");
            return "user/userRegist";
        } else if (result.equals("USEREMSSNALREADYEXIST")) {
            map.addAttribute("errorMessage", "이미 존재하는 사용자입니다.");
            return "user/userRegist";
        } else {
            return "redirect:login";
        }
    }

    @GetMapping("/delete")
    public String delete(
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        userService.deleteUser(dto.userIdx());
        session.invalidate();
        return "redirect:/";
    }

}

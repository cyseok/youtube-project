package com.project.youtube.controller;

import com.project.youtube.dto.VisitorLog;
import com.project.youtube.service.VisitorLogService;
import com.project.youtube.service.VisitorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final VisitorService visitorService;
    private final VisitorLogService visitorLogService;

    /*
    @RequiredArgsConstructor 어노테이션은 final 필드나 @NonNull로 표시된 필드에 대해
    아래와 같은 생성자를 자동으로 생성해준다..
    public HomeController(YoutubeApiService youTubeCommentService) {
        this.youTubeCommentService = youTubeCommentService;
    }
     */

    @GetMapping("/")
    public String home(VisitorLog visitorLog
                        , HttpServletRequest request
                        , HttpServletResponse response) {

        String ipAddress = request.getRemoteAddr();
        System.out.println("ipAddress :" + ipAddress);
        // 홈페이지를 들어오자마자 조회수를 업데이트 해야할거같다.
        // 쿠키 사용후 24(1시간) 시간 지난 사용자라면 무조건 조회수 insert 해주기
        visitorService.addCount();
        visitorLog.setVisitorIp(ipAddress);

        visitorCount(visitorLog, request, response);
        // visitorLogService.addVisitor(visitorLog);

        return "home";
    }

    private void visitorCount(VisitorLog visitorLog
                            , HttpServletRequest request
                            , HttpServletResponse response) {
        Cookie oldCookie = null;
        String visitorId = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("visitorId")) {
                    oldCookie = cookie;
                    visitorId = cookie.getValue();
                    break;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains(visitorId)) {
                visitorLogService.addVisitor(visitorLog);

                visitorId = UUID.randomUUID().toString();
                Cookie newCookie = new Cookie("visitorId", visitorId);
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            visitorId = UUID.randomUUID().toString();
            Cookie newCookie = new Cookie("visitorId", visitorId);
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);

            visitorLogService.addVisitor(visitorLog);
        }

    }

}
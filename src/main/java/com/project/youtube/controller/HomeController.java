package com.project.youtube.controller;

import com.project.youtube.dto.Comment;
import com.project.youtube.dto.VideoInfo;
import com.project.youtube.dto.VisitorLog;
import com.project.youtube.service.CommentApiService;
import com.project.youtube.service.VideoApiService;
import com.project.youtube.service.VisitorLogService;
import com.project.youtube.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CommentApiService commentApiService;
    private final VideoApiService videoApiService;
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
    public String home(VisitorLog visitorLog, jakarta.servlet.http.HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        System.out.println("ipAddress :" + ipAddress);
        // 홈페이지를 들어오자마자 조회수를 업데이트 해야할거같다.
        // 쿠키 사용후 24(1시간) 시간 지난 사용자라면 무조건 조회수 insert 해주기
        visitorService.addCount();
        visitorLog.setVisitorIp(ipAddress);

        visitorLogService.addVisitor(visitorLog);

        return "home";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Comment> getComments(@RequestParam String videoUrl
                                        , @RequestParam String keyword
                                        , @RequestParam int count) {
        return commentApiService.getCommentsWithKeyword(videoUrl, keyword, count);
    }

    @GetMapping("/video")
    @ResponseBody
    public ResponseEntity<VideoInfo> getVideoInfo(@RequestParam String videoUrl) {
        VideoInfo info = videoApiService.getVideoInfo(videoUrl);
        System.out.println("info @@@@@@@@@@@@" + info);
        return  ResponseEntity.ok(videoApiService. getVideoInfo(videoUrl));
    }
}
package com.project.youtube.controller;

import com.project.youtube.dto.Comment;
import com.project.youtube.dto.SearchLog;
import com.project.youtube.dto.VideoInfo;
import com.project.youtube.dto.VisitorLog;
import com.project.youtube.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.ConfigurationKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeRestController {

    private final SearchLogService searchLogService;
    private final VisitorLogService visitorLogService;
    private final CommentApiService commentApiService;
    private final VideoApiService videoApiService;

//    @PatchMapping("/visitor-count")
//    public ResponseEntity<String> modifyCount() {
//        visitorService.addCount();
//        return ResponseEntity.ok("Count incremented successfully");
//    }

    @PostMapping("/visitor-log")
    public ResponseEntity<String> addVisitor(VisitorLog visitorLog
                                        , HttpServletRequest request
                                        , HttpServletResponse response) {

        String ipAddress = request.getRemoteAddr();
        visitorLog.setVisitorIp(ipAddress);

        visitorLogService.addVisitor(visitorLog);

        return ResponseEntity.ok("Search added successfully");
    }


    @PostMapping("/search-log")
    public ResponseEntity<String> addSearch(SearchLog searchLog
                                        , @RequestParam("searchUrl") String searchUrl
                                        , jakarta.servlet.http.HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        searchLog.setIp(ipAddress);
        searchLog.setSearchUrl(searchUrl);

        searchLogService.addSearch(searchLog);

        return ResponseEntity.ok("Search added successfully");
    }

    // youtube API
    @GetMapping("/search")
    public List<Comment> getComments(@RequestParam String videoUrl
            , @RequestParam String keyword
            , @RequestParam int count) {
        return commentApiService.getCommentsWithKeyword(videoUrl, keyword, count);
    }

    @GetMapping("/video")
    public ResponseEntity<VideoInfo> getVideoInfo(@RequestParam String videoUrl) {
        VideoInfo info = videoApiService.getVideoInfo(videoUrl);
        System.out.println("info @@@@@@@@@@@@" + info);
        return  ResponseEntity.ok(videoApiService. getVideoInfo(videoUrl));
    }

}

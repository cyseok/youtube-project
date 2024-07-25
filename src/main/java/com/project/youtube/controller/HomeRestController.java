package com.project.youtube.controller;

import com.project.youtube.dto.Comment;
import com.project.youtube.dto.SearchLog;
import com.project.youtube.dto.VideoInfo;
import com.project.youtube.dto.VisitorLog;
import com.project.youtube.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/visitor-log")
    public ResponseEntity<String> addVisitor(VisitorLog visitorLog
                                        , HttpServletRequest request
                                        , HttpServletResponse response) {

        String ipAddress = request.getRemoteAddr();
        visitorLog.setVisitorIp(ipAddress);

        visitorLogService.addVisitor(visitorLog);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/search-log")
    public ResponseEntity<String> addSearch(SearchLog searchLog
                                        , @RequestParam("searchUrl") String searchUrl
                                        , jakarta.servlet.http.HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        searchLog.setIp(ipAddress);
        searchLog.setSearchUrl(searchUrl);

        searchLogService.addSearch(searchLog);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // youtube API
    @GetMapping("/search")
    public ResponseEntity<List<Comment>> getComments(@RequestParam String videoUrl
                                        , @RequestParam String keyword
                                        , @RequestParam int count) {

        List<Comment> commentList = commentApiService.getCommentsWithKeyword(videoUrl, keyword, count);

        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }

    @GetMapping("/video")
    public ResponseEntity<VideoInfo> getVideoInfo(@RequestParam String videoUrl) {
        VideoInfo videoInfoinfo = videoApiService.getVideoInfo(videoUrl);
        System.out.println("videoInfoinfo @@@@@@@@@@@@" + videoInfoinfo);

        // return  ResponseEntity.ok(videoApiService. getVideoInfo(videoUrl));
        // return new ResponseEntity<>(videoInfoinfo, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(videoInfoinfo);
    }

}

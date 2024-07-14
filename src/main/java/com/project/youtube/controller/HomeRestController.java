package com.project.youtube.controller;

import com.project.youtube.dto.SearchLog;
import com.project.youtube.dto.VisitorLog;
import com.project.youtube.service.SearchLogService;
import com.project.youtube.service.VisitorLogService;
import com.project.youtube.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeRestController {

    private final VisitorService visitorService;
    private final SearchLogService searchLogService;
    private final VisitorLogService visitorLogService;

//    @PatchMapping("/visitor-count")
//    public ResponseEntity<String> modifyCount() {
//        visitorService.addCount();
//        return ResponseEntity.ok("Count incremented successfully");
//    }

    @PostMapping("/visitor-log")
    public ResponseEntity<String> addVisitor(VisitorLog visitorLog
                                        , jakarta.servlet.http.HttpServletRequest request) {

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

}

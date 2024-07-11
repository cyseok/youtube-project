package com.project.youtube.service;

import com.project.youtube.Repository.VisitorLogRepository;
import com.project.youtube.dto.SearchLog;
import com.project.youtube.dto.VisitorLog;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorLogService {

    private final VisitorLogRepository visitorLogRepository;

    @Transactional
    public void addVisitor(VisitorLog visitorLog) {
        visitorLogRepository.save(visitorLog);
    }
}

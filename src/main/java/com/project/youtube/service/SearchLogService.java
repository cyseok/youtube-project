package com.project.youtube.service;

import com.project.youtube.Repository.SearchRepository;
import com.project.youtube.dto.SearchLog;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final SearchRepository searchRepository;

    @Transactional
    public void addSearch(SearchLog searchLog) {
        searchRepository.save(searchLog);
    }

}

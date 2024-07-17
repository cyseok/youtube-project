package com.project.youtube.service;

import com.project.youtube.Repository.SearchRepository;
import com.project.youtube.dto.SearchLog;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final SearchRepository searchRepository;

    @Transactional
    public void addSearch(SearchLog searchLog) {
        searchRepository.save(searchLog);
    }

    /*
    @Transactional
    public List<SearchLog> getPostList() {
        List<SearchLog> searchLog = searchRepository.findAll();

        return searchLog.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

     */

}

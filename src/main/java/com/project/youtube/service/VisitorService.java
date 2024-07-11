package com.project.youtube.service;

import com.project.youtube.Repository.VisitorRepository;
import com.project.youtube.dto.Visitor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;

    @Transactional
    public void addCount() {
        visitorRepository.findById(1).ifPresent(visitor -> {
            visitor.setCount(visitor.getCount() + 1);
            visitorRepository.save(visitor);
        });
    }

}

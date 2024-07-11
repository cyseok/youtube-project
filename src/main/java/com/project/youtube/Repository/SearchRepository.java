package com.project.youtube.Repository;

import com.project.youtube.dto.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<SearchLog, Integer> {
}

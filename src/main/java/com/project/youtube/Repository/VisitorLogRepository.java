package com.project.youtube.Repository;

import com.project.youtube.dto.Visitor;
import com.project.youtube.dto.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorLogRepository extends JpaRepository<VisitorLog, Integer> {
}

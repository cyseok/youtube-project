package com.project.youtube.Repository;

import com.project.youtube.dto.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
    // Visitor updateVisitor(Visitor visitor);
}

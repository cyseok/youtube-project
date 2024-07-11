package com.project.youtube.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
//@NoArgsConstructor( access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "visitorlog")
public class VisitorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "visitor_ip")
    private String visitorIp;

    @CreationTimestamp
    @Column(name = "visitor_date")
    private LocalDateTime visitorDate;
}

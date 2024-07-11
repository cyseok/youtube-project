package com.project.youtube.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "searchlog")
public class SearchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "ip")
    private String ip;

    @Column(name = "search_url")
    private String searchUrl;

    @CreationTimestamp
    @Column(name = "search_date")
    private LocalDateTime searchDate;

}

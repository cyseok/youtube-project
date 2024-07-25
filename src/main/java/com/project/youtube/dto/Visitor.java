package com.project.youtube.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Entity
@Table(name = "visitor")
public class Visitor {
    @Id
    private int id;

    @Column(name = "count")
    private long count;

}

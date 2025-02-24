package org.dirimo.biblioteca.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@MappedSuperclass
public abstract class BaseEntity {


    @Column(name="CREATED_AT",nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    @Column(name = "UPDATED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    @Column(name="CREATED_BY",updatable = false)
    private String createdBy;


    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Version
    @Column
    private long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}

package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "report")
public class Report {

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    private String title;

    @Column(nullable = false)
    @Type(type="text")
    private String content;

    /** 更新日時*/
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /** 登録日時*/
    @Column(name = "created_at",updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    /** 従業員テーブルのID */
    @ManyToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

}

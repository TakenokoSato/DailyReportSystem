package com.techacademy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import javax.persistence.OneToOne;
import javax.persistence.PreRemove;


import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employee")
@Where(clause = "delete_flag = 0")
public class Employee {

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 氏名 */
    @Column(length = 20)
    @NotEmpty
    private String name;

    /** 削除フラグ */
    private int deleteFlag;

    /** 更新日時*/
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /** 登録日時*/
    @Column(name = "created_at",updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;


    @OneToOne(mappedBy="employee", cascade = CascadeType.ALL)
    private Authentication authentication;

    /** レコードが削除される前に行なう処理 */
    @PreRemove
    @Transactional
    private void preRemove() {
        // 認証エンティティからuserを切り離す
        if (authentication!=null) {
            authentication.setEmployee(null);
        }
    }
}

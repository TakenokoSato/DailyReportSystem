package com.techacademy.entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "authentication")
public class Authentication {

    /** 権限用の列挙型 */
    public static enum Role{
        一般,管理者
    }

    /** 社員番号 */
    @Id
    @Column(length = 20)
    private String code;

    /** パスワード */
    @Column(length = 255)
    private String password;

    /** 権限。2桁。列挙型（文字列） */
    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private Role role;

    /** Id */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

}

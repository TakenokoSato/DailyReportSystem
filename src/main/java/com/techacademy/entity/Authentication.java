package com.techacademy.entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

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
    @NotBlank
    @Column(length = 20)
    @Id
    private String code;

    /** パスワード */
    @NotBlank
    @Column(length = 255)
    private String password;

    /** 権限。2桁。列挙型（文字列） */
    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private Role role;

    /** 従業員テーブルのID */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

}

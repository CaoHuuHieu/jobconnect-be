package com.job_connect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@Builder
public class Role {

    public static String SUPER_ADMIN = "SUPER_ADMIN";
    public static String ORG_ADMIN = "ORG_ADMIN";
    public static String HR_ADMIN = "HR_ADMIN";

    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private String code;

}

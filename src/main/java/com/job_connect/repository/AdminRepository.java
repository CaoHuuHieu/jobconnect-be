package com.job_connect.repository;

import com.job_connect.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRepository extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
}

package com.job_connect.repository;

import com.job_connect.entity.Organization;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationRepository extends JpaRepository<Organization, String>, JpaSpecificationExecutor<Organization> {
    Organization findByCode(String orgCode);
}

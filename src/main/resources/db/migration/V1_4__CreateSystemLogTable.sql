CREATE TABLE system_logs (
    id VARCHAR(50) PRIMARY KEY,
    admin_id VARCHAR(50),
    org_id VARCHAR(50),
    created_by VARCHAR(50),
    created_at TIMESTAMP,
    msg VARCHAR(500),

    CONSTRAINT fk_systemlog_organization
        FOREIGN KEY (org_id) REFERENCES organizations(id),

    CONSTRAINT fk_systemlog_admin
        FOREIGN KEY (admin_id) REFERENCES admins(id)
);
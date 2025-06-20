CREATE TABLE admins (
    id VARCHAR(50) PRIMARY KEY,
    full_name VARCHAR(100),
    email VARCHAR(50),
    phone_number VARCHAR(20),
    avt VARCHAR(255),
    organization_id VARCHAR(50),
    role_id INT,
    created_by VARCHAR(50),
    created_at TIMESTAMP,
    updated_by VARCHAR(50),
    updated_at TIMESTAMP,
    status int,

    CONSTRAINT fk_admins_organization
        FOREIGN KEY (organization_id) REFERENCES organizations(id),

    CONSTRAINT fk_admins_role
        FOREIGN KEY (role_id) REFERENCES roles(id)
);
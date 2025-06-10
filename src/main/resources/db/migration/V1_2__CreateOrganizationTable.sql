CREATE TABLE organizations (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    email VARCHAR(50),
    phone VARCHAR(20),
    website VARCHAR(255),
    policy_url VARCHAR(255),
    term_url VARCHAR(255),
    avt VARCHAR(255),
    created_by VARCHAR(50),
    created_at TIMESTAMP,
    updated_by VARCHAR(50),
    updated_at TIMESTAMP
);
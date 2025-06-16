CREATE TABLE organizations (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    org_logo VARCHAR(255),
    website VARCHAR(255),
    org_code VARCHAR(10),
    email VARCHAR(50),
    address VARCHAR(255),
    privacy_url VARCHAR(255),
    term_url VARCHAR(255),
    facebook_url VARCHAR(255).
    linked_in VARCHAR(255)
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    status int

);
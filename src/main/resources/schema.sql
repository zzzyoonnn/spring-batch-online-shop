CREATE TABLE IF NOT EXISTS product_import_error (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `row_number` INT,
    product_name VARCHAR(255),
    error_message VARCHAR(500),
    raw_data TEXT,
    created_at DATETIME
);

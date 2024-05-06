CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19, 4) NOT NULL,
    quantity INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE product_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    score INT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    visible BOOLEAN NOT NULL,
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE feedback_commentary (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    message TEXT NOT NULL,
    is_product_owner BOOLEAN NOT NULL,
    is_visible BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    product_feedback_id BIGINT,
    FOREIGN KEY (product_feedback_id) REFERENCES product_feedback(id)
);


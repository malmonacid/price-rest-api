CREATE TABLE IF NOT EXISTS prices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand_id INT,
    product_id INT,
    start_date TIMESTAMP,
    priority INT,
    price DECIMAL(10, 2),
    curr VARCHAR(3),
    end_date TIMESTAMP,
    price_list VARCHAR(10)
);
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

INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list) VALUES ('1', '35455', '2020-06-14T00:00:00', 0, 35.50, 'EUR', '2020-12-31T23:59:59', '1');
INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list) VALUES ('1', '35455', '2020-06-14T15:00:00', 1, 35.50, 'EUR', '2020-06-14T18:30:00', '2');
INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list) VALUES ('1', '35455', '2020-06-15T00:00:00', 1, 35.50, 'EUR', '2020-06-15T11:00:00', '3');
INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list) VALUES ('1', '35455', '2020-06-15T16:00:00', 1, 35.50, 'EUR', '2020-12-31T23:59:59', '4');
INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list)
SELECT '1', '35455', '2020-06-14T00:00:00', 0, 35.50, 'EUR', '2020-12-31T23:59:59', '1'
WHERE NOT EXISTS (
    SELECT 1 FROM prices
    WHERE brand_id = '1' AND product_id = '35455' AND start_date = '2020-06-14T00:00:00'
);

INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list)
SELECT '1', '35455', '2020-06-14T15:00:00', 1, 25.45, 'EUR', '2020-06-14T18:30:00', '2'
WHERE NOT EXISTS (
    SELECT 1 FROM prices
    WHERE brand_id = '1' AND product_id = '35455' AND start_date = '2020-06-14T15:00:00'
);

INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list)
SELECT '1', '35455', '2020-06-15T00:00:00', 1, 30.50, 'EUR', '2020-06-15T11:00:00', '3'
WHERE NOT EXISTS (
    SELECT 1 FROM prices
    WHERE brand_id = '1' AND product_id = '35455' AND start_date = '2020-06-15T00:00:00'
);

INSERT INTO prices (brand_id, product_id, start_date, priority, price, curr, end_date, price_list)
SELECT '1', '35455', '2020-06-15T16:00:00', 1, 38.95, 'EUR', '2020-12-31T23:59:59', '4'
WHERE NOT EXISTS (
    SELECT 1 FROM prices
    WHERE brand_id = '1' AND product_id = '35455' AND start_date = '2020-06-15T16:00:00'
);
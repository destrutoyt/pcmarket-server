INSERT INTO roles (role_name) VALUES
('ROLE_BUYER'), -- Using "ROLE_" to meet Spring Security standards
('ROLE_SELLER'),
('ROLE_ADMIN');

INSERT into categories (category_name) VALUES
('GAMING'),
('WORK'),
('DAILY'),
('SERVER');

INSERT INTO users (first_name, last_name, username, password_hash, dob, address_1, address_2, state_code, zip_code, country_code) VALUES 
('First Test', 'Last Test', 'TestUser', 'TestPassword', '2003-01-02', '123 Main St', NULL, 'FL', '32801', 'US');
    
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2);

INSERT INTO sellers (user_id, shop_name, shop_description)
SELECT user_id, 'JaneTechShop', 'Specializes in high-performance gaming laptops and accessories.'
FROM users
WHERE username = 'TestUser';

INSERT INTO products (seller_id, category_id, product_name, product_description, product_image_url, product_price) VALUES
(1, 1, 'Gaming Mouse', 'High precision gaming mouse', 'http://localhost:8080/images/testImg1.jpg', 49.99),
(1, 1, 'Mechanical Keyboard', 'RGB mechanical keyboard', 'http://localhost:8080/images/testImg2.jpg', 89.99),
(1, 2, 'Work Laptop', 'Lightweight laptop for office tasks', 'http://localhost:8080/images/testImg3.jpg', 1099.99),
(1, 2, 'Monitor 24"', '24 inch Full HD monitor', 'http://localhost:8080/images/testImg4.jpg', 149.99),
(1, 3, 'Office Chair', 'Ergonomic office chair', 'http://localhost:8080/images/testImg5.jpg', 199.99),
(1, 1, 'Gaming Headset', 'Surround sound gaming headset', 'http://localhost:8080/images/testImg1.jpg', 79.99),
(1, 1, 'Graphics Card', 'High-end GPU for gaming', 'http://localhost:8080/images/testImg2.jpg', 599.99),
(1, 3, 'Desk Lamp', 'LED desk lamp', 'http://localhost:8080/images/testImg3.jpg', 29.99),
(1, 2, 'External Hard Drive', '2TB USB 3.0 HDD', 'http://localhost:8080/images/testImg4.jpg', 89.99),
(1, 3, 'Keyboard Wrist Rest', 'Comfortable wrist rest', 'http://localhost:8080/images/testImg5.jpg', 14.99);

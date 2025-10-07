INSERT INTO roles (role_name) VALUES
('ROLE_BUYER'), -- Using "ROLE_" to meet Spring Security standards
('ROLE_SELLER'),
('ROLE_ADMIN');

INSERT into categories (category_name) VALUES
('GAMING'),
('WORK'),
('DAILY'),
('SERVER');

INSERT INTO users (first_name, last_name, username, password_hash, dob, address_1, address_2, state_code, zipcode, country_code) VALUES 
('First Test', 'Last Test', 'TestUser', 'TestPassword', '2003-01-02', '123 Main St', NULL, 'FL', '32801', 'US');
    
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2);

INSERT INTO sellers (user_id, shop_name, shop_description)
SELECT user_id, 'JaneTechShop', 'Specializes in high-performance gaming laptops and accessories.'
FROM users
WHERE username = 'TestUser';

INSERT INTO products (seller_id, category_id, product_name, product_description, product_image_url, product_price) VALUES 
(1, 1, 'Pro Gamer Headset', 'High-quality surround sound headset designed for professional gamers.', 'https://example.com/images/headset.jpg', 149.99);


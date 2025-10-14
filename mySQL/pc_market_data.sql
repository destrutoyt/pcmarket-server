-- 
-- Data for the `users` table
-- 
INSERT INTO users (first_name, last_name, username, password_hash, dob, address_1, address_2, state_code, zip_code, country_code) VALUES
('John', 'Doe', 'johndoe', '$2a$12$RSpGw7Wv4OiYAUXva.pxgO.4CywcjTHII8bdwYCZkwGCvEnaD4T3S', '1990-05-15', '123 Main St', 'Apt 4B', 'NY', '10001', 'US'),
('Jane', 'Smith', 'janesmith', '$2a$12$XoRGd2JUGHbfKtTge5tZauMJH8waBfKbGAxriYyH2NCKGX5Sz/8ym', '1988-11-20', '456 Oak Ave', NULL, 'CA', '90210', 'US'),
('Peter', 'Jones', 'pjones', '$2a$12$hNYHdIEpnMTCDu9erUQRYeQnMrF2gLTQV1Xe00poGvxM9DCb7xvQm', '1995-03-01', '789 Pine Ln', 'Suite 101', 'TX', '75001', 'US'),
('Emily', 'Davis', 'emilyd', '$2a$12$1X786x6.xSe4b0gkRFWf7OqY1MrohEb6fNrdgHcyIwXfxBH1opxf6', '1992-07-25', '101 Maple Rd', NULL, 'FL', '33101', 'US'),
('Michael', 'Brown', 'mikeb', '$2a$12$IWMCE7jbq/NslM2nEpAss.PE5u71dvLtPv1vlmHw1kx1dQML5oZDy', '1985-01-10', '202 Birch Blvd', 'Unit 5', 'WA', '98101', 'US'),
('Sarah', 'Wilson', 'sarahw', '$2a$12$zZ87ot79yrtQSkZXeUbrL.JuzKw2lgnFesEyi/7Tztw3od/vIkCZ.', '1998-09-03', '303 Cedar Dr', NULL, 'IL', '60601', 'US');

-- 
-- Data for the `roles` table
-- 
INSERT INTO roles (role_name) VALUES
('ROLE_BUYER'), -- Using "ROLE_" to meet Spring Security standards
('ROLE_SELLER'),
('ROLE_ADMIN');

-- 
-- Data for the `users_roles` table
-- 
INSERT INTO users_roles (user_id, role_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(3, 2),
(3, 3),
(4, 2),
(5, 2),
(6, 2);

-- 
-- Data for the `sellers` table
-- 
INSERT INTO sellers (user_id, shop_name, shop_description) VALUES
(1, 'Tech Titans', 'Your one-stop shop for high-performance custom PCs and electronics.'),
(3, 'Elite Builds', 'Custom gaming and workstation PCs crafted with the best components.'),
(6, 'Power House PCs', 'Pre-built and customizable desktops for gamers and professionals.');

-- 
-- Data for the `categories` table
-- 
INSERT into categories (category_name) VALUES
('GAMING'),
('WORK'),
('DAILY'),
('SERVER');

-- 
-- Data for the `products` table
-- 
INSERT INTO products (seller_id, category_id, product_name, product_description, product_image_url, product_price) VALUES
(1, 1, 'Vortex Pro Gaming PC', 'Intel Core i9-13900K, NVIDIA RTX 4090, 32GB DDR5 RAM, 2TB NVMe SSD.', 'http://localhost:8080/images/testImg1.jpg', 3499.99),
(1, 1, 'Aura Elite Gaming PC', 'AMD Ryzen 7 7800X3D, NVIDIA RTX 4070, 16GB DDR5 RAM, 1TB NVMe SSD.', 'http://localhost:8080/images/testImg2.jpg', 1899.99),
(2, 2, 'Professional Workstation', 'Intel Core i7-13700K, NVIDIA RTX A4500, 64GB DDR4 ECC RAM, 4TB SSD.', 'http://localhost:8080/images/testImg3.jpg', 2999.99),
(2, 1, 'Zenith Gaming Laptop', 'AMD Ryzen 9 7945HX, NVIDIA RTX 4080, 32GB DDR5 RAM, 1TB NVMe SSD.', 'http://localhost:8080/images/testImg4.jpg', 2599.99),
(3, 3, 'Nova Pre-built Gaming PC', 'Intel Core i5-12600K, NVIDIA RTX 3060, 16GB DDR4 RAM, 500GB SSD.', 'http://localhost:8080/images/testImg5.jpg', 1299.99);

-- 
-- Data for the `orders` table
-- 
INSERT INTO orders (buyer_id, total_amount, status) VALUES
(2, 4799.98, 'Delivered'),  -- 2 items (Gaming PC + Laptop)
(2, 1299.99, 'Shipped'),
(4, 3199.98, 'Delivered'),  -- 2 items (Gaming PC + Prebuilt)
(4, 2999.99, 'Shipped'),
(5, 2599.99, 'Delivered'),
(5, 2599.99, 'Processing'), -- changed total for variety
(3, 5399.98, 'Delivered'),  -- 3 items in one order
(3, 3499.99, 'Shipped'),
(1, 3199.98, 'Delivered'),  -- 2 items
(1, 8399.97, 'Delivered'),  -- 3 items
(1, 2999.99, 'Shipped'),
(1, 2599.99, 'Processing'),
(2, 2999.99, 'Pending'),
(2, 2599.99, 'Processing'),
(4, 3499.99, 'Delivered'),
(4, 2599.99, 'Pending');

-- 
-- Data for the `reviews` table
-- 
INSERT INTO reviews (product_id, user_id, rating, comment) VALUES
(1, 2, 5, 'Absolutely incredible performance. Worth every penny!'),
(2, 4, 4, 'Great PC for the price. Fast shipping and excellent build quality.'),
(3, 5, 5, 'Perfect for my video editing and rendering work.'),
(4, 2, 3, 'Good specs, but the battery life could be better.'),
(5, 4, 5, 'My first gaming PC. Handles all my favorite games with ease.'),
(1, 4, 5, 'The best PC I have ever owned. Super quiet and powerful.'),
(2, 5, 4, 'Solid build, no complaints so far.'),
(4, 3, 4, 'A very good laptop for gaming, but it does get a bit warm sometimes.');

-- 
-- Data for the `order_items` table
-- 
INSERT INTO order_items (order_id, product_id, seller_id, quantity, price) VALUES
-- Order 1 (buyer 2) → 2 items
(1, 1, 1, 1, 3499.99),
(1, 4, 2, 1, 1299.99),

-- Order 2 (buyer 2)
(2, 5, 3, 1, 1299.99),

-- Order 3 (buyer 4) → 2 items
(3, 2, 1, 1, 1899.99),
(3, 5, 3, 1, 1299.99),

-- Order 4 (buyer 4)
(4, 3, 2, 1, 2999.99),

-- Order 5 (buyer 5)
(5, 4, 2, 1, 2599.99),

-- Order 6 (buyer 5)
(6, 5, 3, 1, 2599.99),

-- Order 7 (buyer 3) → 3 items
(7, 1, 1, 1, 3499.99),
(7, 2, 1, 1, 1899.99),
(7, 5, 3, 1, 1299.99),

-- Order 8 (buyer 3)
(8, 1, 1, 1, 3499.99),

-- Order 9 (buyer 1) → 2 items
(9, 5, 3, 1, 1299.99),
(9, 2, 1, 1, 1899.99),

-- Order 10 (buyer 1) → 3 items
(10, 1, 1, 1, 3499.99),
(10, 2, 1, 1, 1899.99),
(10, 3, 2, 1, 2999.99),

-- Order 11 (buyer 1)
(11, 3, 2, 1, 2999.99),

-- Order 12 (buyer 1)
(12, 4, 2, 1, 2599.99),

-- Order 13 (buyer 2)
(13, 3, 2, 1, 2999.99),

-- Order 14 (buyer 2)
(14, 4, 2, 1, 2599.99),

-- Order 15 (buyer 4)
(15, 1, 1, 1, 3499.99),

-- Order 16 (buyer 4)
(16, 4, 2, 1, 2599.99);

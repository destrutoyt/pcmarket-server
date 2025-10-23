CREATE DATABASE IF NOT EXISTS pcmarketdb;

SET FOREIGN_KEY_CHECKS = 0; -- Disables Foreign Key checks. It kind of forces the tables to be deleted without checking if another table has a dependency with them

DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS sellers;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1; -- Enables back foreign key checks

CREATE TABLE users (
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    dob DATE,
    account_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    address_1 VARCHAR(255) NOT NULL,
    address_2 VARCHAR(255),
    state_code CHAR(2) NOT NULL,	-- Two letter code of state
    zip_code VARCHAR(5) NOT NULL,
    country_code CHAR(2) NOT NULL
);

CREATE TABLE roles (
	role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users_roles (
	role_id INT,
    user_id INT,
    PRIMARY KEY (role_id, user_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

CREATE TABLE sellers (
	seller_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    shop_name VARCHAR(255) NOT NULL UNIQUE,
    shop_description TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE categories (
	category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE products (
	product_id INT PRIMARY KEY AUTO_INCREMENT,
    seller_id INT,
    category_id INT,
    product_name VARCHAR(255) NOT NULL,
    product_description TEXT,
    product_image_url VARCHAR(255) NOT NULL,
    product_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES sellers(seller_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL
);

CREATE TABLE reviews (
	review_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    user_id INT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE orders (
	order_id INT PRIMARY KEY AUTO_INCREMENT,
    buyer_id INT, -- This is the same as user_id, but used it like this to avoid confusion
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
	FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE SET NULL -- FK is the same as filed name buyer_id
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    seller_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL, -- Price at the time of sale
	status ENUM("Pending", "Processing", "Shipped", "Delivered", "Canceled") NOT NULL DEFAULT "Pending",
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE SET NULL,
    FOREIGN KEY (seller_id) REFERENCES sellers(seller_id) ON DELETE SET NULL
);

CREATE TABLE cart (
  cart_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE cart_items (
  cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
  cart_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT DEFAULT 1,
  FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

/*
NOTE:
"orders" serve as the one that stores all orders made from users, but "order_items" serve as a way to store many items bought at the same time.

For example:
If an user orders 4 different items (let's assume one per each product), then those 4 items will be linked to ONE order_id. (1-TO-M)
Now, in the "order_items" table, four fields will be created. Each with their unique product_id and order_item_id, but all those fields will be attached to a single order_id.

So, order_item_id 1,2,3,4 were bought all at the same time, hence, they are attached to order_id 1
*/

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'CASHIER') NOT NULL
);

CREATE TABLE admin (
    admin_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
	user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
        ON DELETE CASCADE
);

CREATE TABLE cashier (
    cashier_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    shift VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    user_id INT,

    FOREIGN KEY (user_id) REFERENCES user(user_id)
        ON DELETE CASCADE
);

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    barcode VARCHAR(50) UNIQUE,
    selling_price DECIMAL(10,2) NOT NULL,
    is_deleted BOOLEAN
);

CREATE TABLE inventory_batch (
    batch_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    cost_price DECIMAL(10,2) NOT NULL,
    purchase_date DATE,
    stock INT DEFAULT 0,
    initial_purchase int,

    FOREIGN KEY (product_id) REFERENCES product(product_id)
        ON DELETE CASCADE
);

CREATE TABLE customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    last_visited date
);

CREATE TABLE bill (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    cashier_id INT,
    total_amt DECIMAL(10,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE SET NULL,

    FOREIGN KEY (cashier_id) REFERENCES cashier(cashier_id)
        ON DELETE SET NULL
);

CREATE TABLE bill_items (
    bill_item_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    selling_price DECIMAL(10,2) NOT NULL,
    cost_price DECIMAL(10,2) NOT NULL,
    batch_id INT NOT NULL,

    FOREIGN KEY (bill_id) REFERENCES bill(bill_id)
        ON DELETE CASCADE,

    FOREIGN KEY (product_id) REFERENCES product(product_id), 
    FOREIGN KEY (batch_id) REFERENCES inventory_batch(batch_id)
);

CREATE INDEX idx_bill_date ON bill(created_at);
CREATE INDEX idx_bill_cashier ON bill(cashier_id);
CREATE INDEX idx_product ON bill_items(product_id);
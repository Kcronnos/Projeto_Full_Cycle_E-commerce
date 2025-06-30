CREATE TABLE pedidos(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	status VARCHAR(10) NOT NULL,
	total  DECIMAL(20,2),
	datacriado TIMESTAMP,
	user_id BIGINT,
	FOREIGN KEY (user_id) REFERENCES usuarios(id)
);
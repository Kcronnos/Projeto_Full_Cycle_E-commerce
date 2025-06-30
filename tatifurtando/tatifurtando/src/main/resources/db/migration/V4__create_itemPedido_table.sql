CREATE TABLE items_pedidos(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	quantidade INT NOT NULL,
	precoitems  DECIMAL(20,2) NOT NULL,
	pedido_id BIGINT,
	jogo_id BIGINT,
	FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
	FOREIGN KEY (jogo_id) REFERENCES jogos(id)
);
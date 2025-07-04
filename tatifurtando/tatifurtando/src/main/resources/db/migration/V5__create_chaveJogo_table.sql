CREATE TABLE chaves(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	chave VARCHAR(255) NOT NULL,
	chavestatus  VARCHAR(14) NOT NULL,
	jogo_id BIGINT,
	item_pedido_id BIGINT,
	FOREIGN KEY (jogo_id) REFERENCES jogos(id),
	FOREIGN KEY (item_pedido_id) REFERENCES items_pedidos(id)
);
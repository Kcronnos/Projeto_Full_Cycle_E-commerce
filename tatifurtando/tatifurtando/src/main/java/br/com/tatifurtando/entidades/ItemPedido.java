package br.com.tatifurtando.entidades;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "items_pedidos")
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;                   
	private int quantidade;
	
	@Column(precision = 20, scale = 2)
	private BigDecimal precoitems;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	
	@ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;
	
	public ItemPedido() {}
	
	public ItemPedido(long id, int quantidade, BigDecimal precoitems, Pedido pedido, Jogo jogo) {
		this.id = id;
		this.quantidade = quantidade;
		this.precoitems = precoitems;
		this.pedido = pedido;
		this.jogo = jogo;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setPrecoItems(BigDecimal precoitems) {
		this.precoitems = precoitems;
	}
	
	public BigDecimal getPrecoItems() {
		return precoitems;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	
	public Jogo getJogo() {
		return jogo;
	}
}

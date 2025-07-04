package br.com.tatifurtando.entidades;

import br.com.tatifurtando.enuns.ChaveStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chaves")
public class ChaveJogo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;               
	private String chave;
	
	@Enumerated(EnumType.STRING)
	private ChaveStatus chavestatus;
	
	@ManyToOne
	@JoinColumn(name = "jogo_id")
	private Jogo jogo;
	
	@ManyToOne
    @JoinColumn(name = "item_pedido_id") 
    private ItemPedido itemPedido;
	
	public ChaveJogo() {}
	
	public ChaveJogo(String chave, ChaveStatus chavestatus, Jogo jogo, ItemPedido itemPedido) {
		this.chave = chave;
		this.chavestatus = chavestatus;
		this.jogo = jogo;
		this.itemPedido = itemPedido;
	}
	
	public ChaveJogo(long id, String chave, ChaveStatus chavestatus, Jogo jogo, ItemPedido itemPedido) {
		this.id = id;
		this.chave = chave;
		this.chavestatus = chavestatus;
		this.jogo = jogo;
		this.itemPedido = itemPedido;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public ChaveStatus getChaveStatus() {
		return chavestatus;
	}

	public void setChaveStatus(ChaveStatus chavestatus) {
		this.chavestatus = chavestatus;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	
	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}
	
}

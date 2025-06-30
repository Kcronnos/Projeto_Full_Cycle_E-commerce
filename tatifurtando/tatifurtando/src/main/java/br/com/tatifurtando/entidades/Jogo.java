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
@Table(name = "jogos")
public class Jogo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;              
	private String nome;           
	private String descricao;
	
	@Column(precision = 20, scale = 2)
	private BigDecimal preco;
	
	private String desenvolvedora;

	private String imagemurl;
	
	public Jogo() {}
	
	public Jogo(long id, String nome, String descricao, BigDecimal preco, String desenvolvedora,String imagemurl) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.desenvolvedora = desenvolvedora;
		this.imagemurl = imagemurl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getDesenvolvedora() {
		return desenvolvedora;
	}

	public void setDesenvolvedora(String desenvolvedora) {
		this.desenvolvedora = desenvolvedora;
	}
	
	public String getImagemUrl() {
		return imagemurl;
	}

	public void setImagemUrl(String imagemurl) {
		this.imagemurl = imagemurl;
	}
	
}

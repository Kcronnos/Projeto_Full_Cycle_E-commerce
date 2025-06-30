package br.com.tatifurtando.entidades;

import java.math.BigDecimal;
import java.sql.Date;

import br.com.tatifurtando.enuns.PedidoStatus;
import jakarta.persistence.Column;
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
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private PedidoStatus status;
	
	@Column(precision = 20, scale = 2)
	private BigDecimal total;
	private Date datacriado;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Pedido() {
		
	}
	
	public Pedido(long id, PedidoStatus status, BigDecimal total, Date datacriado, User user) {
		this.id = id;
		this.status = status;
		this.total = total;
		this.datacriado = datacriado;
		this.user = user;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setStatus(PedidoStatus status) {
		this.status = status;
	}
	
	public PedidoStatus getStatus() {
		return status;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setDataCriado(Date datacriado) {
		this.datacriado = datacriado;
	}
	
	public Date getDataCridado() {
		return datacriado;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}

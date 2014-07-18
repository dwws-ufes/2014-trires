package br.ufes.inf.lprm.trires.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class GrupoTrabalho implements Serializable {

	private static final long serialVersionUID = -1662434830893695320L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String descricao;
	private String objetivo;
	private String palavrasChave;
	@OneToOne
	private Usuario usuarioLider;
	@OneToMany(fetch = FetchType.EAGER)
	private List<Usuario> usuariosMembros;
	private boolean isAtivo;
	private Date dataHoraCriacao = new Date();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getPalavrasChave() {
		return palavrasChave;
	}
	public void setPalavrasChave(String palavrasChave) {
		this.palavrasChave = palavrasChave;
	}
	public Usuario getUsuarioLider() {
		return usuarioLider;
	}
	public void setUsuarioLider(Usuario usuarioLider) {
		this.usuarioLider = usuarioLider;
	}
	public List<Usuario> getUsuariosMembros() {
		return usuariosMembros;
	}
	public void setUsuariosMembros(List<Usuario> usuariosMembros) {
		this.usuariosMembros = usuariosMembros;
	}
	public boolean isAtivo() {
		return isAtivo;
	}
	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
	public Date getDataHoraCriacao() {
		return dataHoraCriacao;
	}
	public void setDataHoraCriacao(Date dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}	
}

package br.ufes.inf.lprm.trires.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class HistoricoBuscaTrabalho implements Serializable {
	
	private static final long serialVersionUID = 8007644870989801132L;
	
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 2400)
	private String textoInformado;
	@OneToOne
	private Usuario usuario;
	@OneToOne
	private GrupoTrabalho grupoTrabalho;
	private Date dataHora = new Date();
	private int quantidadeTrabalhosEncontrados;
	@OneToOne
	private BibliotecaLinkedData biblioteca;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTextoInformado() {
		return textoInformado;
	}
	public void setTextoInformado(String textoInformado) {
		this.textoInformado = textoInformado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public GrupoTrabalho getGrupoTrabalho() {
		return grupoTrabalho;
	}
	public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) {
		this.grupoTrabalho = grupoTrabalho;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public int getQuantidadeTrabalhosEncontrados() {
		return quantidadeTrabalhosEncontrados;
	}
	public void setQuantidadeTrabalhosEncontrados(int quantidadeTrabalhosEncontrados) {
		this.quantidadeTrabalhosEncontrados = quantidadeTrabalhosEncontrados;
	}
	public BibliotecaLinkedData getBiblioteca() {
		return biblioteca;
	}
	public void setBiblioteca(BibliotecaLinkedData biblioteca) {
		this.biblioteca = biblioteca;
	}

}

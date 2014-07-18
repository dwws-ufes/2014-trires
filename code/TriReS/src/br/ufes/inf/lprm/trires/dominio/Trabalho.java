package br.ufes.inf.lprm.trires.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Trabalho implements Serializable {
	
	private static final long serialVersionUID = -6632469769759028530L;
	
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	private String sameAs;
	private String titulo;
	@Column(length = 3000)
	private String resumo;
	private String data;
	@Column(length = 500)
	private String autores;
	private String evento;
	private int notaAvaliacao;
	private int marcadoParaLeitura;
	@ManyToOne
	private HistoricoBuscaTrabalho historicoBusca;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSameAs() {
		return sameAs;
	}
	public void setSameAs(String sameAs) {
		this.sameAs = sameAs;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getAutores() {
		return autores;
	}
	public void setAutores(String autores) {
		this.autores = autores;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public int getNotaAvaliacao() {
		return notaAvaliacao;
	}
	public void setNotaAvaliacao(int notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}
	public HistoricoBuscaTrabalho getHistoricoBusca() {
		return historicoBusca;
	}
	public void setHistoricoBusca(HistoricoBuscaTrabalho historicoBusca) {
		this.historicoBusca = historicoBusca;
	}
	public int getMarcadoParaLeitura() {
		return marcadoParaLeitura;
	}
	public void setMarcadoParaLeitura(int marcadoParaLeitura) {
		this.marcadoParaLeitura = marcadoParaLeitura;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}

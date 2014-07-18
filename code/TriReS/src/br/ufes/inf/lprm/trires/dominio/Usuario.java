package br.ufes.inf.lprm.trires.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 4314585748946808455L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private String descricao;
	private String instituicao;
	private String linkLattes;
	private String email;
	private String senha;
	@Enumerated
	private Perfil perfilAcesso = Perfil.ANONIMO;
	private boolean isAtivo = false;
	@Transient
	private boolean autenticado = false;
	
	
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
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public String getLinkLattes() {
		return linkLattes;
	}
	public void setLinkLattes(String linkLattes) {
		this.linkLattes = linkLattes;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfilAcesso() {
		return perfilAcesso;
	}
	public void setPerfilAcesso(Perfil perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	public boolean isAtivo() {
		return isAtivo;
	}
	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
	public boolean isAutenticado() {
		return autenticado;
	}
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public enum Perfil{
		ADMINISTRADOR("Administrador"),
		USUARIO_REGISTRADO("Usuário Registrado"),
		ANONIMO("Anônimo");
		
		Perfil(String descricao) {
			this.descricao = descricao;
		}
		
		private String descricao;
		
		public String getDescricao() {
			return descricao;
			}
	}
}

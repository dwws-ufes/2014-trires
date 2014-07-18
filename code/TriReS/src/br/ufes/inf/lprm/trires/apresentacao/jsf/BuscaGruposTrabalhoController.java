package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;
import br.ufes.inf.lprm.trires.dominio.Usuario;
import br.ufes.inf.lprm.trires.negocio.GruposTrabalhoService;
import br.ufes.inf.lprm.trires.negocio.UsuariosService;

@Named
@SessionScoped
public class BuscaGruposTrabalhoController extends BaseController implements Serializable {

	private static final long serialVersionUID = 7549894615519211578L;

	@EJB
	private GruposTrabalhoService gruposTrabalhoService;
	
	@EJB
	private UsuariosService usuariosService;
	
	private Long idSelecionado;
	private GrupoTrabalho grupoTrabalho;
	
	private String textoBusca;
	public String getTextoBusca() {
		return textoBusca;
	}
	public void setTextoBusca(String textoBusca) {
		this.textoBusca = textoBusca;
	}
	
	private List<GrupoTrabalho> gruposTrabalho;
	public List<GrupoTrabalho> getGruposTrabalho() {
		return gruposTrabalho;
	}
	public void setGruposTrabalho(List<GrupoTrabalho> gruposTrabalho) {
		this.gruposTrabalho = gruposTrabalho;
	}
	
	public void buscarGruposTrabalho(){
		gruposTrabalho = gruposTrabalhoService.buscarGruposTrabalho(getTextoBusca());
		isResultado = true;
	}
	
	public void inicializaBusca(){
		if (!isPostBack()){
			novaBusca();
		}
	}
	
	public void novaBusca(){
		textoBusca = "";
		gruposTrabalho = null;
		isResultado = false;
	}
	
	private boolean isResultado = false;
	public boolean isResultado(){
		return isResultado;
	}
	public Long getIdSelecionado() {
		return idSelecionado;
	}
	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}
	public GrupoTrabalho getGrupoTrabalho() {
		return grupoTrabalho;
	}
	public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) {
		this.grupoTrabalho = grupoTrabalho;
	}
	
	public void visualizar() {
		if (!isPostBack()){
			if (idSelecionado == null) {
				return;
			}
			grupoTrabalho = gruposTrabalhoService.buscar(idSelecionado);
			grupoTrabalho.getUsuariosMembros().size();
		}
	}
	
	private Long idUsuarioSelecionado;
	private Usuario usuario;
	public Long getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}
	public void setIdUsuarioSelecionado(Long idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void visualizarUsuario() {
		if (!isPostBack()){
			if (idUsuarioSelecionado == null) {
				return;
			}
			usuario = usuariosService.buscar(idUsuarioSelecionado);
		}
	}
		
}

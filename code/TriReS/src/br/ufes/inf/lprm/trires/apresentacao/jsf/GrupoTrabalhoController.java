package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;
import br.ufes.inf.lprm.trires.dominio.Usuario;
import br.ufes.inf.lprm.trires.negocio.GruposTrabalhoService;
import br.ufes.inf.lprm.trires.negocio.UsuariosService;

@Named
@SessionScoped 
public class GrupoTrabalhoController extends BaseController implements Serializable { 

	private static final long serialVersionUID = 4695684133933723036L;
	
	@EJB 
	private GruposTrabalhoService manterGruposTrabalhoService; 
	
	@EJB 
	private UsuariosService manterUsuariosService; 
	
	@Inject
	@Named(value="loginController")
	private LoginController loginController;
	
	private Long idSelecionado;
	private List<GrupoTrabalho> gruposTrabalho;
	private GrupoTrabalho grupoTrabalho;

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

	
	public void incluir(){
		if (!isPostBack()){
			grupoTrabalho = new GrupoTrabalho();
			grupoTrabalho.setUsuarioLider(new Usuario());
			grupoTrabalho.setUsuariosMembros(new ArrayList<Usuario>());
		}
	}
	
	public void editar() {
		if (!isPostBack()){
			if (idSelecionado == null) {
				return;
			}
			grupoTrabalho = manterGruposTrabalhoService.buscar(idSelecionado);
			grupoTrabalho.getUsuariosMembros().size();
		}
	}
	
	public List<GrupoTrabalho> getGruposTrabalho() {
		if (gruposTrabalho == null) {
			gruposTrabalho = manterGruposTrabalhoService.listarTodos(loginController.getUsuario());
		}
		return gruposTrabalho;
	}
	public void setGruposTrabalho(List<GrupoTrabalho> gruposTrabalho) {
		this.gruposTrabalho = gruposTrabalho;
	}

	public String salvar() {
		try {
			manterGruposTrabalhoService.salvar(grupoTrabalho);
			gruposTrabalho = manterGruposTrabalhoService.listarTodos(loginController.getUsuario());
		} catch(Exception ex) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar grupo de trabalho.", ex.getMessage());
			return "";
		}
		return "listarGruposTrabalho";
	}	
	
	public List<Usuario> usuariosPorNome(String textoDigitado) {
		return manterUsuariosService.buscarUsuarioPorNome(textoDigitado);
	}
	
	private Usuario usuarioMembroSelecionado;
	public Usuario getUsuarioMembroSelecionado() {
		return usuarioMembroSelecionado;
	}
	public void setUsuarioMembroSelecionado(Usuario usuarioMembroSelecionado) {
		this.usuarioMembroSelecionado = usuarioMembroSelecionado;
	}
}

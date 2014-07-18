package br.ufes.inf.lprm.trires.apresentacao.jsf;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;
import br.ufes.inf.lprm.trires.dominio.Usuario;
import br.ufes.inf.lprm.trires.dominio.Usuario.Perfil;
import br.ufes.inf.lprm.trires.negocio.GruposTrabalhoService;
import br.ufes.inf.lprm.trires.negocio.UsuariosService;

@Named
@SessionScoped
public class LoginController extends BaseController implements Serializable {
	
	private static final long serialVersionUID = 8658455870129642078L;

	@EJB 
	private UsuariosService manterUsuariosService; 
	
	@EJB 
	private GruposTrabalhoService manterGruposTrabalhoService; 
	
	private Usuario usuario = new Usuario();
	private GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
	private List<GrupoTrabalho> gruposTrabalho = new ArrayList<GrupoTrabalho>();
	
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

	public void autenticar(){
		Usuario usuarioAutenticacao = manterUsuariosService.autenticar(usuario.getEmail(), usuario.getSenha());
		if (usuarioAutenticacao != null && !usuarioAutenticacao.getNome() .isEmpty()){
			usuario = usuarioAutenticacao;
			usuario.setAutenticado(true);
			
			gruposTrabalho = manterGruposTrabalhoService.listarGruposTrabalhoMembro(usuario);
			if (gruposTrabalho != null && gruposTrabalho.size() > 0)
				goTo(getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/loginSelGrupoTrabalho.xhtml");
			else {
				usuario = new Usuario();
				addMessage(FacesMessage.SEVERITY_INFO, "É necessário que você seja convidado por algum grupo.", "");
			}
		} 
		else {
			addMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválida.", "");
			usuario = new Usuario();
			grupoTrabalho = new GrupoTrabalho();
		}
	}	
	public String selecionarGrupo() {
		if (!grupoTrabalho.getNome().isEmpty()){
			return "index";
		} 
		else {
			addMessage(FacesMessage.SEVERITY_WARN, "Selecione um grupo para navegar no sistema.", "");
			return "";
		}
	}
	
	public void verificaAutorizacao(){
		if (usuario.isAutenticado() && grupoTrabalho.getNome() != null && !grupoTrabalho.getNome().isEmpty()) {
			return;
		}
		else {
			if (usuario.isAutenticado()) {
				addMessage(FacesMessage.SEVERITY_WARN, "Selecione um grupo para navegar no sistema.", "");
				goTo(getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/loginSelGrupoTrabalho.xhtml");
			}
			else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Você não tem acesso a este recurso.", "");
				goTo(getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/acessoNegado.xhtml");
			}
		}
	}
	
	public void verificaAutorizacaoAdmin(){
		if (usuario.isAutenticado() && grupoTrabalho.getNome() != null && !grupoTrabalho.getNome().isEmpty()
			&& usuario.getPerfilAcesso().getDescricao().equals(Perfil.ADMINISTRADOR.getDescricao())) {
			return;
		}
		else {
			if (usuario.isAutenticado() && 
					usuario.getPerfilAcesso().getDescricao().equals(Perfil.ADMINISTRADOR.getDescricao())) {
				addMessage(FacesMessage.SEVERITY_WARN, "Selecione um grupo para navegar no sistema.", "");
				goTo(getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/loginSelGrupoTrabalho.xhtml");
			}
			else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Você não tem acesso a este recurso.", "");
				goTo(getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/acessoNegado.xhtml");
			}
		}
	}
	
	public void efetuarLogoff(){
		usuario = new Usuario();
		grupoTrabalho = new GrupoTrabalho();
		gruposTrabalho = new ArrayList<GrupoTrabalho>();
	}

	public boolean isPerfilAnonimo() {
		return usuario.getPerfilAcesso().equals(Perfil.ANONIMO);
	}
	public boolean isPerfilAdministrador() {
		return usuario.getPerfilAcesso().equals(Perfil.ADMINISTRADOR);
	}
	public boolean isPerfilUsuarioRegistrado() {
		return usuario.getPerfilAcesso().equals(Perfil.USUARIO_REGISTRADO);
	}

	public List<GrupoTrabalho> getGruposTrabalho() {
		return gruposTrabalho;
	}

	public void setGruposTrabalho(List<GrupoTrabalho> gruposTrabalho) {
		this.gruposTrabalho = gruposTrabalho;
	}
}

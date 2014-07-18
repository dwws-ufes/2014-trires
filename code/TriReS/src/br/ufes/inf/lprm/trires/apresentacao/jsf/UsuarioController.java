package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.lprm.trires.dominio.Usuario;
import br.ufes.inf.lprm.trires.dominio.Usuario.Perfil;
import br.ufes.inf.lprm.trires.negocio.UsuariosService;

@Named
@SessionScoped 
public class UsuarioController extends BaseController implements Serializable { 

	private static final long serialVersionUID = 4695684133933723036L;
	
	@EJB 
	private UsuariosService manterUsuariosService; 
	
	private Long idSelecionado;
	private List<Usuario> usuarios;
	private Usuario usuario;

	public Long getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
	public void incluir(){
		if (!isPostBack())
			usuario = new Usuario();
	}
	
	public void editar() {
		if (!isPostBack()){
			if (idSelecionado == null) {
				return;
			}
			usuario = manterUsuariosService.buscar(idSelecionado);
		}
	}
	
	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = manterUsuariosService.listarTodos();
		}
		return usuarios;
	}

	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String salvar() {
		try {
			manterUsuariosService.salvar(usuario);
			usuarios = manterUsuariosService.listarTodos();
		} catch(Exception ex) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar usuário.", ex.getMessage());
			return "";
		}
		return "listarUsuarios";
	}	
	
	public HashMap<String,Perfil> getPerfis(){
		HashMap<String,Perfil> ret = new HashMap<String,Perfil>();
		ret.put(Perfil.USUARIO_REGISTRADO.getDescricao(), Perfil.USUARIO_REGISTRADO);
		ret.put(Perfil.ADMINISTRADOR.getDescricao(), Perfil.ADMINISTRADOR);
		return ret;
	}

}

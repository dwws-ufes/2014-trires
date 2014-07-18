package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.lprm.trires.dominio.BibliotecaLinkedData;
import br.ufes.inf.lprm.trires.negocio.BibliotecasService;

@Named
@SessionScoped 
public class BibliotecaController extends BaseController implements Serializable { 

	private static final long serialVersionUID = 4695684133933723036L;
	
	@EJB 
	private BibliotecasService manterBibliotecasService; 
	
	private Long idSelecionado;
	private List<BibliotecaLinkedData> bibliotecas;
	private BibliotecaLinkedData biblioteca;

	public Long getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}

	public BibliotecaLinkedData getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(BibliotecaLinkedData biblioteca) {
		this.biblioteca = biblioteca;
	}

	
	
	public void incluir(){
		if (!isPostBack()){
			biblioteca = new BibliotecaLinkedData();
			StringBuffer exemplo = new StringBuffer();
			exemplo.append("### EXEMPLO:\n\r");
			exemplo.append(" \n\r");
			exemplo.append("PREFIX akt:  <http://www.aktors.org/ontology/portal#>\n\r");
			exemplo.append(" \n\r");
			exemplo.append("SELECT * WHERE { \n\r");
			exemplo.append("    ?publicacao akt:has-title ?titulo\n\r");
			exemplo.append("### Usar sempre o sujeito '?publicacao' e recuperar o objeto '?titulo'.\n\r");
			exemplo.append(" \n\r");
			exemplo.append("### 'Where' será fechado pelo programa após geração automatica do filtro\n\r");
			exemplo.append("### Remover os comentários.");
			biblioteca.setQuery(exemplo.toString());
		}
	}
	
	public void editar() {
		if (!isPostBack()){
			if (idSelecionado == null) {
				return;
			}
			biblioteca = manterBibliotecasService.buscar(idSelecionado);
		}
	}
	
	public List<BibliotecaLinkedData> getBibliotecas() {
		if (bibliotecas == null) {
			bibliotecas = manterBibliotecasService.listarTodos();
		}
		return bibliotecas;
	}

	
	public void setBibliotecas(List<BibliotecaLinkedData> bibliotecas) {
		this.bibliotecas = bibliotecas;
	}

	public String salvar() {
		try {
			manterBibliotecasService.salvar(biblioteca);
			bibliotecas = manterBibliotecasService.listarTodos();
		} catch(Exception ex) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar usuário.", ex.getMessage());
			return "";
		}
		return "listarBibliotecas";
	}	
	
}

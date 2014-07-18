package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datalist.DataList;
import org.primefaces.event.RateEvent;

import br.ufes.inf.lprm.trires.dominio.Trabalho;
import br.ufes.inf.lprm.trires.negocio.PesquisaTrabalhosBibliotecasService;

@Named
@SessionScoped 
public class PesquisaTrabalhosBibliotecasController extends BaseController implements Serializable{

	private static final long serialVersionUID = 3742702864498232533L; 

	@EJB 
	private PesquisaTrabalhosBibliotecasService pesquisaTrabalhosBibliotecasService; 
	
	@Inject
	@Named(value="loginController")
	private LoginController loginController;
	
	private String textoQuery;
	
	public String getTextoQuery() {
		return textoQuery;
	}
	public void setTextoQuery(String textoQuery) {
		this.textoQuery = textoQuery;
	}

	private List<Trabalho> trabalhos;
	public List<Trabalho> getTrabalhos() {
		return trabalhos;
	}
	public void setTrabalhos(List<Trabalho> trabalhos) {
		this.trabalhos = trabalhos;
	}
	
	private Trabalho trabalhoSelecionado;
	public Trabalho getTrabalhoSelecionado() {
		return trabalhoSelecionado;
	}
	public void setTrabalhoSelecionado(Trabalho trabalhoSelecionado) {
		try {
			pesquisaTrabalhosBibliotecasService.obterDetalhesTrabalho(trabalhoSelecionado);
		}
		catch (Exception ex){
		}
		this.trabalhoSelecionado = trabalhoSelecionado;
	}
	
	public void pesquisarTrabalhos(){
		try {
			trabalhos = pesquisaTrabalhosBibliotecasService.pesquisarTrabalhos(getTextoQuery(), loginController.getGrupoTrabalho(), loginController.getUsuario());
			isResultado = true;
		}
		catch (Exception ex) {
			addMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getStackTrace().toString());
		}
	}
	
	public void novaPesquisa(){
		textoQuery = "";
		trabalhos = null;
		isResultado = false;
	}
	
	public String getQuantidadeRegistrosEncontrados(){
		if (trabalhos == null)
			return "0";
		else
			return Integer.toString(trabalhos.size());
	}
	
	public void inicializaBusca(){
		if (!isPostBack()){
			novaPesquisa();
		}
	}
	
	private boolean isResultado = false;
	public boolean isResultado(){
		return isResultado;
	}
	
	public void inicializaPagina(){
		loginController.verificaAutorizacao();
		inicializaBusca();
	}
	
	public void marcarTrabalhoParaLeitura(RateEvent rateEvent) {
		int linha = ((DataList)rateEvent.getComponent().getParent().getParent()).getRowIndex();
		Trabalho trabalho = trabalhos.get(linha);
		try {
			pesquisaTrabalhosBibliotecasService.obterDetalhesTrabalho(trabalho);
		}
		catch (Exception ex) {
		}
		try {
			pesquisaTrabalhosBibliotecasService.marcarTrabalhoParaLeitura(trabalho);
		}
		catch (Exception ex) {
		}

	}
	public void desmarcarTrabalhoParaLeitura(AjaxBehaviorEvent event) {
		int linha = ((DataList)event.getComponent().getParent().getParent()).getRowIndex();
		Trabalho trabalho = trabalhos.get(linha);
		try {
			pesquisaTrabalhosBibliotecasService.desmarcarTrabalhoParaLeitura(trabalho.getId());
		}
		catch (Exception ex) {
		}
	}
	
}

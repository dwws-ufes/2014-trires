package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RateEvent;

import br.ufes.inf.lprm.trires.dominio.Trabalho;
import br.ufes.inf.lprm.trires.negocio.GruposTrabalhoService;

@Named
@SessionScoped 
public class TrabalhosGrupoController extends BaseController implements Serializable { 

	private static final long serialVersionUID = 4695684133933723036L;
	
	@EJB 
	private GruposTrabalhoService gruposTrabalhoService;
	
	@Inject
	@Named(value="loginController")
	private LoginController loginController;
	
	private List<Trabalho> trabalhos;
	
	public List<Trabalho> getTrabalhos() {
		if (trabalhos == null) {
			trabalhos = gruposTrabalhoService.listarTrabalhosGrupo(loginController.getGrupoTrabalho());
		}
		return trabalhos;
	}

	public void setTrabalhos(List<Trabalho> trabalhos) {
		this.trabalhos = trabalhos;
	};

	public void inicializaPagina(){
		loginController.verificaAutorizacao();
		trabalhos = null;
	}
	
	public void incluirAvaliacaoTrabalho(RateEvent rateEvent) {
		int linha = ((DataTable)rateEvent.getComponent().getParent().getParent()).getRowIndex();
		Trabalho trabalho = trabalhos.get(linha);
		try {
			gruposTrabalhoService.atualizarAvaliacao(trabalho);
		}
		catch (Exception ex) {
		}
	}
	
	public void removerAvaliacaoTrabalho(AjaxBehaviorEvent event) {
		int linha = ((DataTable)event.getComponent().getParent().getParent()).getRowIndex();
		Trabalho trabalho = trabalhos.get(linha);
		trabalho.setNotaAvaliacao(0);
		try {
			gruposTrabalhoService.atualizarAvaliacao(trabalho);
		}
		catch (Exception ex) {
		}
	}
}

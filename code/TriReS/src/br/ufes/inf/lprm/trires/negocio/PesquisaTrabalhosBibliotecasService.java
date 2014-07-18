package br.ufes.inf.lprm.trires.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.lprm.trires.dominio.BibliotecaLinkedData;
import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;
import br.ufes.inf.lprm.trires.dominio.HistoricoBuscaTrabalho;
import br.ufes.inf.lprm.trires.dominio.Trabalho;
import br.ufes.inf.lprm.trires.dominio.Usuario;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.util.FileManager;

@Stateless
@LocalBean 
public class PesquisaTrabalhosBibliotecasService implements Serializable {

	private static final long serialVersionUID = -1498281673899424123L;
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@EJB
	BibliotecasService bibliotecasService;
	
	@EJB
	HistoricoBuscaTrabalhosService historicoBuscaTrabalhosService;
	
	public List<Trabalho> pesquisarTrabalhos(String queryUsuario, GrupoTrabalho grupoTrabalho, Usuario usuario) throws Exception{
		
		List<Trabalho> trabalhosEncontrados = new ArrayList<Trabalho>();
		
		List<BibliotecaLinkedData> listaBibliotecas = bibliotecasService.listarTodos();
		if (listaBibliotecas == null || listaBibliotecas.size() == 0){
			throw new Exception("Não há biblioteca cadastrada ou estão desativadas.");
		}
		else {
			
			for (BibliotecaLinkedData biblioteca : listaBibliotecas) {
				if (biblioteca.isAtivo()){
					StringBuffer queryFormatada = new StringBuffer();
					queryFormatada.append(biblioteca.getQuery() + "\n\r");
					queryFormatada.append(montaCondicaoFiltro(queryUsuario, "?titulo") + "\n\r");
					queryFormatada.append("}");
					
					HistoricoBuscaTrabalho historicoBuscaTrabalho = new HistoricoBuscaTrabalho();
					historicoBuscaTrabalho.setGrupoTrabalho(grupoTrabalho);
					historicoBuscaTrabalho.setUsuario(usuario);
					historicoBuscaTrabalho.setTextoInformado(queryUsuario);
					historicoBuscaTrabalho.setBiblioteca(biblioteca);
					
					int quantidadeTrabalhosBase = 0;
					try {
						ResultSet results = execute(biblioteca.getUrl(), queryFormatada.toString());
						
						for ( ; results.hasNext() ; ) {
							QuerySolution querySolution = results.nextSolution();
						    RDFNode rdfNode = querySolution.get("?publicacao");
						    Trabalho trabalho = new Trabalho();
						    trabalho.setSameAs(rdfNode.toString());
						    trabalho.setTitulo(querySolution.getLiteral("?titulo").getString());
						    trabalho.setHistoricoBusca(historicoBuscaTrabalho);	
						    
						    Trabalho trabalhoGrupoTrabalho = historicoBuscaTrabalhosService.buscaTrabalhoHistorico(trabalho.getSameAs(), grupoTrabalho, usuario);
						    if (trabalhoGrupoTrabalho != null){
						    	trabalhosEncontrados.add(trabalhoGrupoTrabalho);
						    } 
						    else {
						    	trabalhosEncontrados.add(trabalho);
						    }
						    quantidadeTrabalhosBase++;
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					historicoBuscaTrabalho.setQuantidadeTrabalhosEncontrados(quantidadeTrabalhosBase);
					historicoBuscaTrabalhosService.salvarHistoricoBusca(historicoBuscaTrabalho);
				}
			}
		}    
		
		return trabalhosEncontrados;
	}
	
	private String montaCondicaoFiltro(String queryUsuario, String campoFiltro){
		String condicao = " FILTER ( ";
		String[] palavras = queryUsuario.split(" ");
		for (String palavra : palavras) {
			String palavraLimpa = limparStringQuery(palavra);
			
			if (palavraLimpa.equalsIgnoreCase("or")){
				condicao += palavra.replace(palavraLimpa, " || ");
			} else
				if (palavraLimpa.equalsIgnoreCase("and")){
					condicao += palavra.replaceAll(palavraLimpa, " && ");
				} else {
					condicao += palavra.replace(palavraLimpa, " regex(" + campoFiltro + ", \"" + palavraLimpa + "\") ");
				}
		}
		condicao += " ) ";
		return condicao;
	}
	
	private String limparStringQuery(String original){
		return original.replace(")", " ").replace("(", " ").trim().replace("_", " ");
	}
	
	private ResultSet execute(String endPoint, String queryString) throws Exception {
		 Query query = QueryFactory.create(queryString) ;
		 QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(endPoint, query); 
		 ResultSet results = qexec.execSelect() ;
		 return results;
	}
	
	public Trabalho marcarTrabalhoParaLeitura(Trabalho trabalho){
		if (trabalho.getId() != null)
			return entityManager.merge(trabalho);
		else {
			entityManager.persist(trabalho);
			return trabalho;
		}
	}
	
	public void desmarcarTrabalhoParaLeitura(long idTrabalho){
		Trabalho trabalhoRemover = entityManager.find(Trabalho.class, idTrabalho);
		entityManager.remove(trabalhoRemover);
	}
	
	public void obterDetalhesTrabalho(Trabalho trabalho) throws Exception{
		
		if (trabalho != null && trabalho.getSameAs() != null && !trabalho.getSameAs().isEmpty()) {
			FileManager fileManager = FileManager.get();
			fileManager.addLocatorURL();
	        
			Model linkedDataModel = fileManager.loadModel(trabalho.getSameAs());
	        StmtIterator iter = linkedDataModel.listStatements();
	        
	        String resumo = new String();
	        String data = new String();
	        String autores = new String();
	        String evento = new String();
	        
	        while (iter.hasNext()) {
	            Statement stmt = iter.nextStatement();
	            
	            try {
	            	String valorObjeto = new String();
	            	String uriObjeto = new String();
	            	if (stmt.getObject() != null && stmt.getObject().isLiteral()) {
	            		valorObjeto = stmt.getLiteral().getLexicalForm();
	            	}
	            	else {
	            		valorObjeto = stmt.getResource().getLocalName();
            			uriObjeto = stmt.getResource().getURI();
	            	}
	            	
		            switch (stmt.getPredicate().getLocalName()){
			            case "has-author" : {
			            	if (autores.length() > 0)
			            		autores += " ; " + obterDadoRecurso(uriObjeto, "full-name");
			            	else
			            		autores = obterDadoRecurso(uriObjeto, "full-name");
			            	}
			            	break;
			            case "has-date" : {
			            		if (valorObjeto.isEmpty())
			            			data = uriObjeto.substring(uriObjeto.indexOf("#") + 1);
			            		else
			            			data = valorObjeto;
			            	}
		            		break;
			            case "has-abstract" : resumo = valorObjeto;
		        			break;
			            case "paper-in-proceedings" : evento = obterDadoRecurso(uriObjeto, "has-title");
		        			break;
		            }
	            }
	            catch (Exception ex) {
	            }
	        }

	        linkedDataModel.close();

	        trabalho.setAutores(autores);
	        trabalho.setData(data);
	        trabalho.setEvento(evento);
	        trabalho.setResumo(resumo);
		}
	}
		
	private String obterDadoRecurso(String uri, String campo) throws Exception{
		FileManager fileManager = FileManager.get();
		fileManager.addLocatorURL();
        
		Model linkedDataModel = fileManager.loadModel(uri);
        StmtIterator iter = linkedDataModel.listStatements();
        
        String ret = new String();
        
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            
            try {
            	String valorObjeto = new String();
            	if (stmt.getObject() != null && stmt.getObject().isLiteral()) {
            		valorObjeto = stmt.getLiteral().getLexicalForm();
            	}
            	else {
            		valorObjeto = stmt.getResource().getLocalName();
            	}
            	
            	if (stmt.getPredicate().getLocalName().equals(campo))
            		ret = valorObjeto;
            }
            catch (Exception ex) {
            }
        }
        linkedDataModel.close();
        return ret;
	}
}

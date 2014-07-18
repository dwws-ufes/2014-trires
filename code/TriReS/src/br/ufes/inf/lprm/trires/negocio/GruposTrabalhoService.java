package br.ufes.inf.lprm.trires.negocio;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;
import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho_;
import br.ufes.inf.lprm.trires.dominio.HistoricoBuscaTrabalho;
import br.ufes.inf.lprm.trires.dominio.HistoricoBuscaTrabalho_;
import br.ufes.inf.lprm.trires.dominio.Trabalho;
import br.ufes.inf.lprm.trires.dominio.Trabalho_;
import br.ufes.inf.lprm.trires.dominio.Usuario;
import br.ufes.inf.lprm.trires.dominio.Usuario.Perfil;
import br.ufes.inf.lprm.trires.dominio.Usuario_;
import br.ufes.inf.lprm.trires.dominio.vocabulario.Constantes;
import br.ufes.inf.lprm.trires.dominio.vocabulario.VocabularioGrupoTrabalho;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


@Stateless
@LocalBean 
public class GruposTrabalhoService implements Serializable {

	private static final long serialVersionUID = 7409925728110373809L; 

	@PersistenceContext 
	private EntityManager entityManager; 
	
	public GrupoTrabalho buscar(Long id){
		return entityManager.find(GrupoTrabalho.class, id);
	}
	
	public List<GrupoTrabalho> listarTodos(Usuario filtroUsuario){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrupoTrabalho> criteriaQuery = criteriaBuilder.createQuery(GrupoTrabalho.class);
		Root<GrupoTrabalho> from = criteriaQuery.from(GrupoTrabalho.class);
		criteriaQuery.select(from);
		if (filtroUsuario != null && filtroUsuario.getPerfilAcesso().getDescricao().equals(Perfil.USUARIO_REGISTRADO.getDescricao())){ 
			criteriaQuery.where(criteriaBuilder.equal(from.get(GrupoTrabalho_.usuarioLider), filtroUsuario.getId()));
		}
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
		
	public GrupoTrabalho salvar(GrupoTrabalho grupoTrabalho){
		if (grupoTrabalho.getId() != null)
			return entityManager.merge(grupoTrabalho);
		else {
			entityManager.persist(grupoTrabalho);
			return grupoTrabalho;
		}
	}
	
	public List<GrupoTrabalho> buscarGruposTrabalho(String textoBusca){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrupoTrabalho> criteriaQuery = criteriaBuilder.createQuery(GrupoTrabalho.class);
		Root<GrupoTrabalho> from = criteriaQuery.from(GrupoTrabalho.class);
		criteriaBuilder.upper(from.get(GrupoTrabalho_.nome));
		criteriaBuilder.upper(from.get(GrupoTrabalho_.descricao));
		criteriaBuilder.upper(from.get(GrupoTrabalho_.objetivo));
		criteriaBuilder.upper(from.get(GrupoTrabalho_.palavrasChave));
		
		Predicate or = 
				criteriaBuilder.or(
						criteriaBuilder.like(from.get(GrupoTrabalho_.nome), "%" + textoBusca.toUpperCase() + "%"),
						criteriaBuilder.like(from.get(GrupoTrabalho_.descricao), "%" + textoBusca.toUpperCase() + "%"),
						criteriaBuilder.like(from.get(GrupoTrabalho_.objetivo), "%" + textoBusca.toUpperCase() + "%"),
						criteriaBuilder.like(from.get(GrupoTrabalho_.palavrasChave), "%" + textoBusca.toUpperCase() + "%")
				);
		criteriaQuery.where(or);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}	
	
	public List<GrupoTrabalho> listarGruposTrabalhoMembro(Usuario usuario){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrupoTrabalho> criteriaQuery = criteriaBuilder.createQuery(GrupoTrabalho.class);
		Root<GrupoTrabalho> from = criteriaQuery.from(GrupoTrabalho.class);
		Join<GrupoTrabalho, Usuario> membros = from.join(GrupoTrabalho_.usuariosMembros);
		
		Predicate or = 
				criteriaBuilder.or(
						criteriaBuilder.equal(from.get(GrupoTrabalho_.usuarioLider), usuario.getId()),
						criteriaBuilder.equal(membros.get(Usuario_.id), usuario.getId())
				);
		criteriaQuery.where(or).distinct(true);
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	public List<Trabalho> listarTrabalhosGrupo(GrupoTrabalho grupoTrabalho){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Trabalho> criteriaQuery = criteriaBuilder.createQuery(Trabalho.class);
		Root<Trabalho> from = criteriaQuery.from(Trabalho.class);
		Join<Trabalho, HistoricoBuscaTrabalho> membros = from.join(Trabalho_.historicoBusca);
		criteriaBuilder.upper(from.get(Trabalho_.sameAs));
		criteriaQuery.where(
				criteriaBuilder.equal(membros.get(HistoricoBuscaTrabalho_.grupoTrabalho), grupoTrabalho.getId())
				);
		criteriaQuery.select(from);
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	public Trabalho atualizarAvaliacao(Trabalho trabalho){
		return entityManager.merge(trabalho);
	}
	
	
	public OutputStream buscarGrupoTrabalhoRdf(Long id, String urlBase){
		OutputStream ret = new ByteArrayOutputStream();
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		model.setNsPrefix( "trires", Constantes.ONTO_URI );
		
		GrupoTrabalho grupoTrabalho = entityManager.find(GrupoTrabalho.class, id);
		if (grupoTrabalho != null){
			Resource recurso = model.createResource(Constantes.GRUPO_TRABALHO_URI + grupoTrabalho.getId().toString());
			recurso.addProperty(RDF.type, VocabularioGrupoTrabalho.RDF_TYPE_CLASS);
			recurso.addProperty(VocabularioGrupoTrabalho.ID, grupoTrabalho.getId().toString());
			if (grupoTrabalho.isAtivo())
				recurso.addProperty(VocabularioGrupoTrabalho.ATIVO, "Sim");
			else
				recurso.addProperty(VocabularioGrupoTrabalho.ATIVO, "Não");
			recurso.addProperty(VocabularioGrupoTrabalho.DATA_HORA_CRIACAO, grupoTrabalho.getDataHoraCriacao().toString());
			recurso.addProperty(VocabularioGrupoTrabalho.DESCRICAO, grupoTrabalho.getDescricao());
			recurso.addProperty(VocabularioGrupoTrabalho.NOME, grupoTrabalho.getNome());
			recurso.addProperty(VocabularioGrupoTrabalho.OBJETIVO, grupoTrabalho.getObjetivo());
			recurso.addProperty(VocabularioGrupoTrabalho.PALAVRAS_CHAVE, grupoTrabalho.getPalavrasChave());
			Resource recursoLider = model.createResource(Constantes.USUARIO_URI + grupoTrabalho.getUsuarioLider().getId().toString());
			recurso.addProperty(VocabularioGrupoTrabalho.LIDER, recursoLider);
			for (Usuario membro : grupoTrabalho.getUsuariosMembros()) {
				Resource recursoMembro = model.createResource(Constantes.USUARIO_URI + membro.getId().toString());
				recurso.addProperty(VocabularioGrupoTrabalho.MEMBRO, recursoMembro);
			}
		}
		
		model.write(ret, "RDF/XML");
		model.close();
		return ret;
	}
	
	public OutputStream listarTodosRdf(String urlBase){
		OutputStream ret = new ByteArrayOutputStream();
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		model.setNsPrefix( "trires", Constantes.ONTO_URI );
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrupoTrabalho> criteriaQuery = criteriaBuilder.createQuery(GrupoTrabalho.class);
		Root<GrupoTrabalho> from = criteriaQuery.from(GrupoTrabalho.class);
		criteriaQuery.select(from);
		List<GrupoTrabalho> gruposTrabalho = entityManager.createQuery(criteriaQuery).getResultList();
		for (GrupoTrabalho grupoTrabalho : gruposTrabalho) {
			Resource recurso = model.createResource(Constantes.GRUPO_TRABALHO_URI + grupoTrabalho.getId().toString());
			recurso.addProperty(RDF.type, VocabularioGrupoTrabalho.RDF_TYPE_CLASS);
			recurso.addProperty(VocabularioGrupoTrabalho.ID, grupoTrabalho.getId().toString());
			if (grupoTrabalho.isAtivo())
				recurso.addProperty(VocabularioGrupoTrabalho.ATIVO, "Sim");
			else
				recurso.addProperty(VocabularioGrupoTrabalho.ATIVO, "Não");
			recurso.addProperty(VocabularioGrupoTrabalho.DATA_HORA_CRIACAO, grupoTrabalho.getDataHoraCriacao().toString());
			recurso.addProperty(VocabularioGrupoTrabalho.DESCRICAO, grupoTrabalho.getDescricao());
			recurso.addProperty(VocabularioGrupoTrabalho.NOME, grupoTrabalho.getNome());
			recurso.addProperty(VocabularioGrupoTrabalho.OBJETIVO, grupoTrabalho.getObjetivo());
			recurso.addProperty(VocabularioGrupoTrabalho.PALAVRAS_CHAVE, grupoTrabalho.getPalavrasChave());
			Resource recursoLider = model.createResource(Constantes.USUARIO_URI + grupoTrabalho.getUsuarioLider().getId().toString());
			recurso.addProperty(VocabularioGrupoTrabalho.LIDER, recursoLider);
			for (Usuario membro : grupoTrabalho.getUsuariosMembros()) {
				Resource recursoMembro = model.createResource(Constantes.USUARIO_URI + membro.getId().toString());
				recurso.addProperty(VocabularioGrupoTrabalho.MEMBRO, recursoMembro);
			}
		}
		model.write(ret, "RDF/XML");
		model.close();
		return ret;
	}
	
}

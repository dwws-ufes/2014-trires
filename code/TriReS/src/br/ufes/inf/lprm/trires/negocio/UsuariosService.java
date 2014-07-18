package br.ufes.inf.lprm.trires.negocio;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.lprm.trires.dominio.Usuario;
import br.ufes.inf.lprm.trires.dominio.Usuario_;
import br.ufes.inf.lprm.trires.dominio.vocabulario.Constantes;
import br.ufes.inf.lprm.trires.dominio.vocabulario.VocabularioMembro;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


@Stateless
@LocalBean 
public class UsuariosService implements Serializable {

	private static final long serialVersionUID = 7409925728110373809L; 

	@PersistenceContext 
	private EntityManager entityManager; 
	
	public Usuario buscar(Long id){
		return entityManager.find(Usuario.class, id);
	}
	
	public List<Usuario> listarTodos(){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		criteriaQuery.select(criteriaQuery.from(Usuario.class));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	public Usuario salvar(Usuario usuario){
		if (usuario.getId() != null)
			return entityManager.merge(usuario);
		else {
			entityManager.persist(usuario);
			return usuario;
		}
	}
	
	public List<Usuario> buscarUsuarioPorNome(String nome){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> from = criteriaQuery.from(Usuario.class);
		criteriaBuilder.upper(from.get(Usuario_.nome));
		criteriaQuery.where(criteriaBuilder.like(from.get(Usuario_.nome), nome.toUpperCase() + "%"));
		criteriaQuery.select(from);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	public Usuario autenticar(String login, String senha){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> from = criteriaQuery.from(Usuario.class);
		criteriaQuery.where(
				criteriaBuilder.equal(from.get(Usuario_.email), login),
				criteriaBuilder.equal(from.get(Usuario_.senha), senha),
				criteriaBuilder.equal(from.get(Usuario_.isAtivo), true)
				);
		criteriaQuery.select(from);
		Usuario usuarioRet = null;
		try {
			usuarioRet = entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (NoResultException ex){
		}
		return usuarioRet;
	}
	
	
	public OutputStream buscarUsuarioRdf(Long id, String urlBase){
		OutputStream ret = new ByteArrayOutputStream();
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		model.setNsPrefix( "trires", Constantes.ONTO_URI );
		
		Usuario usuario = entityManager.find(Usuario.class, id);
		if (usuario != null){
			Resource recurso = model.createResource(Constantes.USUARIO_URI + usuario.getId().toString());
			recurso.addProperty(RDF.type, VocabularioMembro.RDF_TYPE_CLASS);
			recurso.addProperty(VocabularioMembro.ID, usuario.getId().toString());
			recurso.addProperty(VocabularioMembro.DESCRICAO, usuario.getDescricao());
			recurso.addProperty(VocabularioMembro.EMAIL, usuario.getEmail());
			recurso.addProperty(VocabularioMembro.INSTITUICAO, usuario.getInstituicao());
			recurso.addProperty(VocabularioMembro.LINK_LATTES, usuario.getLinkLattes());
			recurso.addProperty(VocabularioMembro.NOME, usuario.getNome());
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
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> from = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(from);
		List<Usuario> gruposTrabalho = entityManager.createQuery(criteriaQuery).getResultList();
		for (Usuario usuario : gruposTrabalho) {
			Resource recurso = model.createResource(Constantes.USUARIO_URI + usuario.getId().toString());
			recurso.addProperty(RDF.type, VocabularioMembro.RDF_TYPE_CLASS);
			recurso.addProperty(VocabularioMembro.ID, usuario.getId().toString());
			recurso.addProperty(VocabularioMembro.DESCRICAO, usuario.getDescricao());
			recurso.addProperty(VocabularioMembro.EMAIL, usuario.getEmail());
			recurso.addProperty(VocabularioMembro.INSTITUICAO, usuario.getInstituicao());
			recurso.addProperty(VocabularioMembro.LINK_LATTES, usuario.getLinkLattes());
			recurso.addProperty(VocabularioMembro.NOME, usuario.getNome());
		}
		model.write(ret, "RDF/XML");
		model.close();
		return ret;
	}
	
}

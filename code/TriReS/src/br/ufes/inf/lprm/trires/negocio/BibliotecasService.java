package br.ufes.inf.lprm.trires.negocio;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.ufes.inf.lprm.trires.dominio.BibliotecaLinkedData;


@Stateless
@LocalBean 
public class BibliotecasService implements Serializable {

	private static final long serialVersionUID = 7409925728110373809L; 

	@PersistenceContext 
	private EntityManager entityManager; 
	
	public BibliotecaLinkedData buscar(Long id){
		return entityManager.find(BibliotecaLinkedData.class, id);
	}
	
	public List<BibliotecaLinkedData> listarTodos(){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BibliotecaLinkedData> criteriaQuery = criteriaBuilder.createQuery(BibliotecaLinkedData.class);
		criteriaQuery.select(criteriaQuery.from(BibliotecaLinkedData.class));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	public BibliotecaLinkedData salvar(BibliotecaLinkedData bibliotecaLinkedData){
		if (bibliotecaLinkedData.getId() != null)
			return entityManager.merge(bibliotecaLinkedData);
		else {
			entityManager.persist(bibliotecaLinkedData);
			return bibliotecaLinkedData;
		}
	}	
}

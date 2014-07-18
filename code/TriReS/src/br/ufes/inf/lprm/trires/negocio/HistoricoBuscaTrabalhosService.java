package br.ufes.inf.lprm.trires.negocio;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;
import br.ufes.inf.lprm.trires.dominio.HistoricoBuscaTrabalho;
import br.ufes.inf.lprm.trires.dominio.HistoricoBuscaTrabalho_;
import br.ufes.inf.lprm.trires.dominio.Trabalho;
import br.ufes.inf.lprm.trires.dominio.Trabalho_;
import br.ufes.inf.lprm.trires.dominio.Usuario;

@Stateless
@LocalBean 
public class HistoricoBuscaTrabalhosService implements Serializable {

	private static final long serialVersionUID = 7140695439188465703L;

	@PersistenceContext 
	private EntityManager entityManager; 
	
	public HistoricoBuscaTrabalho salvarHistoricoBusca(HistoricoBuscaTrabalho historicoBuscaTrabalho){
		if (historicoBuscaTrabalho.getId() != null)
			return entityManager.merge(historicoBuscaTrabalho);
		else {
			entityManager.persist(historicoBuscaTrabalho);
			return historicoBuscaTrabalho;
		}
	}

	
	public Trabalho buscaTrabalhoHistorico(String sameAs, GrupoTrabalho grupoTrabalho, Usuario usuario){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Trabalho> criteriaQuery = criteriaBuilder.createQuery(Trabalho.class);
		Root<Trabalho> from = criteriaQuery.from(Trabalho.class);
		Join<Trabalho, HistoricoBuscaTrabalho> membros = from.join(Trabalho_.historicoBusca);
		criteriaBuilder.upper(from.get(Trabalho_.sameAs));
		criteriaQuery.where(
				criteriaBuilder.equal(membros.get(HistoricoBuscaTrabalho_.grupoTrabalho), grupoTrabalho.getId()),
				criteriaBuilder.equal(membros.get(HistoricoBuscaTrabalho_.usuario), usuario.getId()),
				criteriaBuilder.equal(from.get(Trabalho_.sameAs), sameAs.toUpperCase())
				);
		criteriaQuery.select(from);
		Trabalho trabalhoRet = null;
		try {
			trabalhoRet = entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (NoResultException ex){
		}
		return trabalhoRet;
	}
}

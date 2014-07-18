package br.ufes.inf.lprm.trires.dominio;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-05T14:31:16.126-0300")
@StaticMetamodel(HistoricoBuscaTrabalho.class)
public class HistoricoBuscaTrabalho_ {
	public static volatile SingularAttribute<HistoricoBuscaTrabalho, Long> id;
	public static volatile SingularAttribute<HistoricoBuscaTrabalho, Usuario> usuario;
	public static volatile SingularAttribute<HistoricoBuscaTrabalho, GrupoTrabalho> grupoTrabalho;
	public static volatile SingularAttribute<HistoricoBuscaTrabalho, Date> dataHora;
	public static volatile SingularAttribute<HistoricoBuscaTrabalho, String> textoInformado;
	public static volatile SingularAttribute<HistoricoBuscaTrabalho, Integer> quantidadeTrabalhosEncontrados;
	public static volatile SingularAttribute<HistoricoBuscaTrabalho, BibliotecaLinkedData> biblioteca;
}

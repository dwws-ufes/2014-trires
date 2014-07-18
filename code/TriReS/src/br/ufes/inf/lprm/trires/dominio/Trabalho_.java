package br.ufes.inf.lprm.trires.dominio;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-05T14:31:01.250-0300")
@StaticMetamodel(Trabalho.class)
public class Trabalho_ {
	public static volatile SingularAttribute<Trabalho, Long> id;
	public static volatile SingularAttribute<Trabalho, String> sameAs;
	public static volatile SingularAttribute<Trabalho, String> titulo;
	public static volatile SingularAttribute<Trabalho, String> data;
	public static volatile SingularAttribute<Trabalho, String> autores;
	public static volatile SingularAttribute<Trabalho, String> palavrasChave;
	public static volatile SingularAttribute<Trabalho, String> evento;
	public static volatile SingularAttribute<Trabalho, String> resumo;
	public static volatile SingularAttribute<Trabalho, Integer> notaAvaliacao;
	public static volatile SingularAttribute<Trabalho, Integer> marcadoParaLeitura;
	public static volatile SingularAttribute<Trabalho, Integer> marcadoLido;
	public static volatile SingularAttribute<Trabalho, HistoricoBuscaTrabalho> historicoBusca;
}

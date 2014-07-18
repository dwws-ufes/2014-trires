package br.ufes.inf.lprm.trires.dominio;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-05T14:31:30.501-0300")
@StaticMetamodel(BibliotecaLinkedData.class)
public class BibliotecaLinkedData_ {
	public static volatile SingularAttribute<BibliotecaLinkedData, Long> id;
	public static volatile SingularAttribute<BibliotecaLinkedData, String> nome;
	public static volatile SingularAttribute<BibliotecaLinkedData, String> url;
	public static volatile SingularAttribute<BibliotecaLinkedData, String> query;
	public static volatile SingularAttribute<BibliotecaLinkedData, Boolean> isAtivo;
}

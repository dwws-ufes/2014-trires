package br.ufes.inf.lprm.trires.dominio;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-05-19T09:01:10.623-0300")
@StaticMetamodel(GrupoTrabalho.class)
public class GrupoTrabalho_ {
	public static volatile SingularAttribute<GrupoTrabalho, Long> id;
	public static volatile SingularAttribute<GrupoTrabalho, String> nome;
	public static volatile SingularAttribute<GrupoTrabalho, String> descricao;
	public static volatile SingularAttribute<GrupoTrabalho, String> objetivo;
	public static volatile SingularAttribute<GrupoTrabalho, String> palavrasChave;
	public static volatile SingularAttribute<GrupoTrabalho, Usuario> usuarioLider;
	public static volatile ListAttribute<GrupoTrabalho, Usuario> usuariosMembros;
	public static volatile SingularAttribute<GrupoTrabalho, Boolean> isAtivo;
	public static volatile SingularAttribute<GrupoTrabalho, Date> dataHoraCriacao;
}

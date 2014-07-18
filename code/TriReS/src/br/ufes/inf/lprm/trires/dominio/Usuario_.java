package br.ufes.inf.lprm.trires.dominio;

import br.ufes.inf.lprm.trires.dominio.Usuario.Perfil;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-05-19T08:59:50.615-0300")
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Long> id;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, String> descricao;
	public static volatile SingularAttribute<Usuario, String> instituicao;
	public static volatile SingularAttribute<Usuario, String> linkLattes;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> senha;
	public static volatile SingularAttribute<Usuario, Perfil> perfilAcesso;
	public static volatile SingularAttribute<Usuario, Boolean> isAtivo;
}

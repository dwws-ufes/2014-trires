package br.ufes.inf.lprm.trires.dominio.vocabulario;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

public class VocabularioMembro {

	public static final Property ID = new PropertyImpl(Constantes.ONTO_URI, "id");
	public static final Property NOME = new PropertyImpl(Constantes.ONTO_URI, "nome");
	public static final Property EMAIL = new PropertyImpl(Constantes.ONTO_URI, "email");
	public static final Property DESCRICAO = new PropertyImpl(Constantes.ONTO_URI, "descricao");
	public static final Property INSTITUICAO = new PropertyImpl(Constantes.ONTO_URI, "instituicao");
	public static final Property LINK_LATTES = new PropertyImpl(Constantes.ONTO_URI, "linkLattes");
	
	public static final String RDF_TYPE_CLASS = Constantes.ONTO_URI + "membro";
	
}

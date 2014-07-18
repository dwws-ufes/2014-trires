package br.ufes.inf.lprm.trires.dominio.vocabulario;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

public class VocabularioGrupoTrabalho {
	
	public static final Property ID = new PropertyImpl(Constantes.ONTO_URI, "id");
	public static final Property NOME = new PropertyImpl(Constantes.ONTO_URI, "nome");
	public static final Property DATA_HORA_CRIACAO = new PropertyImpl(Constantes.ONTO_URI, "dataHoraCriacao");
	public static final Property DESCRICAO = new PropertyImpl(Constantes.ONTO_URI, "descricao");
	public static final Property OBJETIVO = new PropertyImpl(Constantes.ONTO_URI, "objetivo");
	public static final Property PALAVRAS_CHAVE = new PropertyImpl(Constantes.ONTO_URI, "palavrasChave");
	public static final Property ATIVO = new PropertyImpl(Constantes.ONTO_URI, "ativo");

	public static final Property LIDER = new PropertyImpl(Constantes.ONTO_URI, "lider");
	public static final Property MEMBRO = new PropertyImpl(Constantes.ONTO_URI, "membro");
	
	public static final String RDF_TYPE_CLASS = Constantes.ONTO_URI + "grupoTrabalho";
}

package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;

@FacesConverter(value="grupoTrabalhoConverter", forClass = GrupoTrabalho.class)
public class GrupoTrabalhoConverter implements Converter {
	
	 
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value)
	{
		if (value != null)
		{
			return this.getAttributesFrom(component).get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component, Object value)
	{
		if (value != null && !"".equals(value))
		{
			GrupoTrabalho grupoTrabalho = (GrupoTrabalho) value;
			if (grupoTrabalho.getId() != null){
				this.addAttribute(component, grupoTrabalho);
				return String.valueOf(grupoTrabalho.getId());
			}
			else
				return null;
		}
		return (String) value;
	}

	protected void addAttribute(UIComponent component, GrupoTrabalho grupoTrabalho)
	{
		String key = grupoTrabalho.getId().toString();
		this.getAttributesFrom(component).put(key, grupoTrabalho);
	}
	protected Map<String, Object> getAttributesFrom(UIComponent component)
	{
		return component.getAttributes();
	}

}
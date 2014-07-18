package br.ufes.inf.lprm.trires.apresentacao.jsf;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.ufes.inf.lprm.trires.dominio.Usuario;

@FacesConverter(value="usuarioConverter", forClass = Usuario.class)
public class UsuarioConverter implements Converter {
	
	 
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
			Usuario usuario = (Usuario) value;
			if (usuario.getId() != null){
				this.addAttribute(component, usuario);
				return String.valueOf(usuario.getId());
			}
			else
				return null;
		}
		return (String) value;
	}

	protected void addAttribute(UIComponent component, Usuario usuario)
	{
		String key = usuario.getId().toString();
		this.getAttributesFrom(component).put(key, usuario);
	}
	protected Map<String, Object> getAttributesFrom(UIComponent component)
	{
		return component.getAttributes();
	}

}
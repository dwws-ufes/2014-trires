package br.ufes.inf.lprm.trires.apresentacao.jsf;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;

public class BaseController implements Serializable {

	private static final long serialVersionUID = 319015444813264810L;
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, summary.concat("<br/>").concat(detail)));
	}
	
	public boolean isPostBack(){
		if (getCurrentInstance().isPostback())
			return true;
		else
			return false;
	}
	
	public void goTo(String url){
		try {
			getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUrlBase(){
		return getCurrentInstance().getExternalContext().getRequestScheme()
				+ "://" + getCurrentInstance().getExternalContext().getRequestServerName()
				  + ":" + getCurrentInstance().getExternalContext().getRequestServerPort()
				  + getCurrentInstance().getExternalContext().getRequestContextPath();
	}
}

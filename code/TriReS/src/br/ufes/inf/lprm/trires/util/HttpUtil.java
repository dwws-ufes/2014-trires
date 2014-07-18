package br.ufes.inf.lprm.trires.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
	
	public HttpUtil(String agenteUsuario){
		this.agenteUsuario = agenteUsuario;
	}
	
	private String agenteUsuario = "";
	
	private final String msie = "msie";
	private final String firefox = "firefox";
	private final String safari = "apple";
	private final String chrome = "chrome";
    private final String opera = "presto";
    private boolean isMsie() {
        return agenteUsuario.toLowerCase().indexOf(msie) != -1;
    }
	private boolean isFirefox() {
        return agenteUsuario.toLowerCase().indexOf(firefox) != -1;
    }
	private boolean isSafari() {
        return agenteUsuario.toLowerCase().indexOf(safari) != -1;
    }
	private boolean isChrome() {
        return agenteUsuario.toLowerCase().indexOf(chrome) != -1;
    }
	private boolean isOpera() {
        return agenteUsuario.toLowerCase().indexOf(opera) != -1;
    }

	public boolean isNavegadorDesktop(){
		return isMsie() || isFirefox() || isSafari() || isChrome() || isOpera();
	}
	
	public String getUrlBase(HttpServletRequest request){
		return request.getScheme()
				+ "://" + request.getServerName()
				  + ":" + request.getServerPort()
				  + request.getContextPath();
	}
}

package br.ufes.inf.lprm.trires.apresentacao.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufes.inf.lprm.trires.dominio.GrupoTrabalho;
import br.ufes.inf.lprm.trires.negocio.GruposTrabalhoService;
import br.ufes.inf.lprm.trires.util.HttpUtil;


@WebServlet(name = "GrupoTrabalhoServlet", urlPatterns = {"/grupo/uri/*"}) 
public class GrupoTrabalhoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GrupoTrabalhoServlet() {
        super();
    }
    
    @EJB
    private GruposTrabalhoService gruposTrabalhoService;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String agenteUsuario = request.getHeader("user-agent");
		HttpUtil httpUtil = new HttpUtil(agenteUsuario);
		 
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")){
        	response.setContentType("application/rdf+xml");
        	OutputStream gruposRdf = gruposTrabalhoService.listarTodosRdf(httpUtil.getUrlBase(request));
        	response.getOutputStream().write(gruposRdf.toString().getBytes());
        	gruposRdf.close();
        } 
        else {
        	GrupoTrabalho grupoTrabalho = null;
        	String idGrupoTrabalho = request.getPathInfo().substring(1);
        	String regex = "[0-9]+"; 
        	if (idGrupoTrabalho.matches(regex))
        		grupoTrabalho = gruposTrabalhoService.buscar(Long.parseLong(idGrupoTrabalho));

        	if (grupoTrabalho == null) {
        		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        		response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	}
        	else {
	    		if (httpUtil.isNavegadorDesktop()){
	    			response.setStatus(HttpServletResponse.SC_SEE_OTHER);
	    			response.setHeader("Location", httpUtil.getUrlBase(request) + "/faces/buscarGruposTrabalho/visualizarGrupoTrabalho.xhtml?id=" + grupoTrabalho.getId());
	    		} 
	    		else {
	    			response.setContentType("application/rdf+xml");
	    			OutputStream grupoRdf = gruposTrabalhoService.buscarGrupoTrabalhoRdf(Long.parseLong(idGrupoTrabalho), httpUtil.getUrlBase(request));
	    			response.getOutputStream().write(grupoRdf.toString().getBytes());
	    			grupoRdf.close();
	    		}
    		}
        }
	}
}

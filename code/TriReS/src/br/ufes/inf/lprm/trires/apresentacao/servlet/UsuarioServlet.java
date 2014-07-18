package br.ufes.inf.lprm.trires.apresentacao.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufes.inf.lprm.trires.dominio.Usuario;
import br.ufes.inf.lprm.trires.negocio.UsuariosService;
import br.ufes.inf.lprm.trires.util.HttpUtil;


@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuario/uri/*"}) 
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UsuarioServlet() {
        super();
    }
    
    @EJB
    private UsuariosService usuariosService;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String agenteUsuario = request.getHeader("user-agent");
		HttpUtil httpUtil = new HttpUtil(agenteUsuario);
		 
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")){
        	response.setContentType("application/rdf+xml");
        	OutputStream usuariosRdf = usuariosService.listarTodosRdf(httpUtil.getUrlBase(request));
        	response.getOutputStream().write(usuariosRdf.toString().getBytes());
        	usuariosRdf.close();
        } 
        else {
        	Usuario usuario = null;
        	String idUsuario = request.getPathInfo().substring(1);
        	String regex = "[0-9]+"; 
        	if (idUsuario.matches(regex))
        		usuario = usuariosService.buscar(Long.parseLong(idUsuario));

        	if (usuario == null) {
        		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        		response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	}
        	else {
	    		if (httpUtil.isNavegadorDesktop()){
	    			response.setStatus(HttpServletResponse.SC_SEE_OTHER);
	    			response.setHeader("Location", httpUtil.getUrlBase(request) + "/faces/buscarGruposTrabalho/visualizarUsuario.xhtml?id=" + usuario.getId());
	    		} 
	    		else {
	    			response.setContentType("application/rdf+xml");
	    			OutputStream grupoRdf = usuariosService.buscarUsuarioRdf(Long.parseLong(idUsuario), httpUtil.getUrlBase(request));
	    			response.getOutputStream().write(grupoRdf.toString().getBytes());
	    			grupoRdf.close();
	    		}
    		}
        }
	}	
}

package br.ufes.inf.lprm.trires.apresentacao.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufes.inf.lprm.trires.dominio.vocabulario.Constantes;


@WebServlet(name = "RdfSchemaServlet", urlPatterns = {"/rdf-schema"}) 
public class RdfSchemaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RdfSchemaServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(Constantes.OWL_PATH);
	}
}

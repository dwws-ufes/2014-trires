package br.ufes.inf.lprm.trires.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import br.ufes.inf.lprm.trires.dominio.vocabulario.Constantes;

public class RequestUriTrires {
	public static void main (String[] args) throws IOException{
		testarUsuario();
		System.out.println("--------------------------");
		testarGrupo();
	}	
	
	public static void testarGrupo() throws IOException{
		URL urlTrires = new URL(Constantes.GRUPO_TRABALHO_URI);
        URLConnection con = urlTrires.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
	}
	
	public static void testarUsuario() throws IOException{
		URL urlTrires = new URL(Constantes.USUARIO_URI);
        URLConnection con = urlTrires.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
	}
}

package com.example.site_items;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/CriarUtilizador")
public class CriarUtilizador extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        String nome = request.getParameter("nomec");
        String passe = request.getParameter("passec");
        System.out.println(nome);
        System.out.println(passe);
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        JSONObject utilizador = json.getJSONObject("utilizador");
        JSONObject resultado = new JSONObject();
        resultado.put("nome",nome);
        resultado.put("passe",passe);
        resultado.put("pontos",10);
        JSONObject itemsu = new JSONObject();
        JSONObject itemsuDefault = new JSONObject();
        itemsuDefault.put("id", "");
        itemsuDefault.put("quantidade", 0);
        itemsu.put("1", itemsuDefault);
        resultado.put("itemsu", itemsu);
        json.getJSONObject("utilizador").put(String.valueOf(utilizador.length() + 1),resultado);
        Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
        out.println("<h1>utilizador criado com sucesso</h1>");
        out.println("</body></html>");
    }
}

package com.example.site_items;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.*;

@WebServlet("/EfetuarLogin")
public class EfetuarLogin extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nomee = request.getParameter("nome");//substituir por request do nome do login
        String passe = request.getParameter("passe");
        String ficheiro = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(ficheiro);
        JSONObject outro = json.getJSONObject("utilizador");
        boolean encontrou = false;
        for (int i = 1; i <= outro.length(); i++) {
            JSONObject nome = outro.getJSONObject(String.valueOf(i));
            if (Objects.equals(nome.getString("nome"), nomee) && Objects.equals(nome.getString("passe"), passe)) {
                FileWriter ficheirologin = new FileWriter("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\sessao.txt");
                ficheirologin.write(nome.getString("nome"));
                ficheirologin.close();
                encontrou = true;
                break;
            }
        }
        if (encontrou) {
            response.sendRedirect("/site_items_war_exploded/");
        } else {
            response.sendRedirect("/site_items_war_exploded/login.jsp");
        }
    }

    public void destroy() {
    }
}
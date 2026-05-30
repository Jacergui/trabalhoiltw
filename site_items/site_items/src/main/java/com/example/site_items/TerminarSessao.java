package com.example.site_items;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;

@WebServlet("/TerminarSessao")
public class TerminarSessao extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileWriter ficheiro = new FileWriter("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\sessao.txt");
        ficheiro.write("");
        ficheiro.close();
        response.sendRedirect("/site_items_war_exploded/login.jsp");
    }
}

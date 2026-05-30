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
import java.util.Objects;

import static com.example.site_items.outrotestejson.sessaonome;
@WebServlet("/VenderItem")
public class VenderItem extends HttpServlet{
        private String message;

        public void init() {
            message = "Hello World!";
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/html");
            venderitem(request,response);
        }

        public void destroy() {
        }
        public void venderitem(HttpServletRequest request,HttpServletResponse response) throws IOException {

            String item= request.getParameter("nomev");
            int quantidade = Integer.parseInt(request.getParameter("quantidadev"));
            String ficheiro = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
            JSONObject json = new JSONObject(ficheiro);
            JSONObject items = json.getJSONObject("items");
            for (int i=1;i<=items.length();i++) {
                JSONObject nomeitem = items.getJSONObject(String.valueOf(i));
                if (Objects.equals(nomeitem.getString("nome"), item)) {
                    int aumento = nomeitem.optInt("quantidade") + quantidade;
                    nomeitem.put("quantidade",aumento);
                    JSONObject utilizador = json.getJSONObject("utilizador");
                    for (int e=1;e<=utilizador.length();e++) {
                        JSONObject nome = utilizador.getJSONObject(String.valueOf(e));
                        if (Objects.equals(nome.getString("nome"), sessaonome())) {
                            JSONObject itemutilizador = nome.getJSONObject("itemsu");
                            for (int a=1;a<=itemutilizador.length();a++) {
                                JSONObject objitemutilizador = itemutilizador.getJSONObject(String.valueOf(a));
                                if (Objects.equals(objitemutilizador.getString("id"), String.valueOf(i))) {
                                    int diferenca = objitemutilizador.getInt("quantidade") - quantidade;
                                    if (diferenca >=0) {
                                        objitemutilizador.put("quantidade",diferenca);
                                        itemutilizador.put(String.valueOf(a),objitemutilizador);
                                    }
                                }
                            }
                            nome.put("itemsu",itemutilizador);
                        }
                        utilizador.put(String.valueOf(e),nome);
                    }
                    json.put("utilizador",utilizador);
                }
                items.put(String.valueOf(i),nomeitem);
            }
            json.put("items",items);
            Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
            vendas(request,response);
        }

        public void vendas(HttpServletRequest request,HttpServletResponse response) throws IOException {
            PrintWriter out = response.getWriter();
            String itemm = request.getParameter("nomev");
            boolean existe = true;
            int quantidade = Integer.parseInt(request.getParameter("quantidadev"));
            String guardarid = "";
            String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
            JSONObject json = new JSONObject(conteudo);
            JSONObject vendas = json.getJSONObject("vendas");
            JSONObject item = json.getJSONObject("items");
            for (int i=1;i<=item.length();i++) {
                for (int e=1;e<=vendas.length();e++) {
                    JSONObject idvenda = vendas.getJSONObject(String.valueOf(e));
                    if (Objects.equals(idvenda.getString("nomeu"), sessaonome())) {
                        if (String.valueOf(i).equals(idvenda.getString("iditem"))) {
                            int aumento = idvenda.getInt("quantidade") + quantidade;
                            idvenda.put("quantidade", aumento);
                            existe = true;
                        }
                    } else {
                        existe = false;
                        guardarid = String.valueOf(i);
                    }
                }
            }
            if (!existe) {
                JSONObject objeto = new JSONObject();
                objeto.put("iditem",guardarid);
                objeto.put("quantidade",quantidade);
                objeto.put("nomeu",sessaonome());
                vendas.put(String.valueOf(vendas.length() + 1),objeto);
            }
            json.put("vendas",vendas);
            json.put("items",item);
            Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
            out.println("<html><body>");
            out.println("<h1>item vendido com sucesso</h1>");
            out.println("</body></html>");
        }
}

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
import static com.example.site_items.outrotestejson.validarcompra;

@WebServlet("/ComprarItem")
public class ComprarItem extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        compraritem(request);
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1> item comprado com sucesso </h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }

    public static void compraritem(HttpServletRequest request) throws IOException {
        String itemm = request.getParameter("nome");
        String guardarid = "";
        boolean existe = true;
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        if (validarcompra()) {
            JSONObject item = json.getJSONObject("items");
            for (int i = 1; i <= item.length(); i++) {
                JSONObject id = item.getJSONObject(String.valueOf(i));
                if (Objects.equals(id.getString("nome"), itemm)) {
                    System.out.println("nesta função");
                    int diferenca = id.getInt("quantidade") - quantidade;
                    if (diferenca >= 0) {
                        id.put("quantidade", diferenca);
                        JSONObject utilizador = json.getJSONObject("utilizador");
                        for (int e = 1; e <= utilizador.length(); e++) {
                            JSONObject idutilizador = utilizador.getJSONObject(String.valueOf(e));
                            System.out.println(sessaonome());
                            if (Objects.equals(idutilizador.getString("nome"), sessaonome())) {
                                JSONObject itemutilizador = idutilizador.getJSONObject("itemsu");
                                for (int a = 1; a <= itemutilizador.length(); a++) {
                                    System.out.println("também nesta função");
                                    JSONObject iditemutilizador = itemutilizador.getJSONObject(String.valueOf(a));
                                    if (Objects.equals(iditemutilizador.getString("id"), String.valueOf(i))) {
                                        System.out.println("loop aqui?");
                                        int diferencaquantidade = iditemutilizador.getInt("quantidade") + quantidade;
                                        iditemutilizador.put("quantidade", diferencaquantidade);
                                        itemutilizador.put(String.valueOf(a), iditemutilizador);
                                    } else {
                                        existe = false;
                                        guardarid = String.valueOf(i);
                                    }
                                }
                                if (!existe) {
                                    JSONObject itemutilizadore = new JSONObject();
                                    itemutilizadore.put("id", guardarid);
                                    itemutilizadore.put("quantidade", quantidade);
                                    itemutilizador.put(String.valueOf(itemutilizador.length() + 1), itemutilizadore);
                                }
                                idutilizador.put("itemsu", itemutilizador);
                            }
                            utilizador.put(String.valueOf(e), idutilizador);
                        }
                        json.put("utilizador", utilizador);
                    }
                    item.put(String.valueOf(i), id);
                }
            }
            json.put("items", item);
            Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"), json.toString().getBytes());
            retirarnasvendas(request);
            gerirpontosevendasdoutilizador(request);
        }
    }

    public static void retirarnasvendas(HttpServletRequest request) throws IOException {
        String itemm = request.getParameter("nome");
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        JSONObject vendas = json.getJSONObject("vendas");
        JSONObject items = json.getJSONObject("items");
        for(int e=1;e<= items.length();e++) {
            JSONObject iditem = items.getJSONObject(String.valueOf(e));
            if (Objects.equals(iditem.getString("nome"), itemm)) {
                for (int i = 1; i <= vendas.length(); i++) {
                    JSONObject idvendas = vendas.getJSONObject(String.valueOf(i));
                    if (Objects.equals(idvendas.getString("iditem"), String.valueOf(e))) {
                        int diferenca = idvendas.getInt("quantidade") - quantidade;
                        idvendas.put("quantidade",diferenca);
                    }
                    vendas.put(String.valueOf(i),idvendas);
                }
            }
        }
        json.put("vendas",vendas);
        Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
    }

    public static void gerirpontosevendasdoutilizador(HttpServletRequest request) throws IOException {
        JSONObject objeto = new JSONObject();
        int contador = 1;
        String guardaritem = "";
        String nomee = request.getParameter("vendedor");
        String itemm = request.getParameter("nome");
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        int total = 0;
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        JSONObject utilizador = json.getJSONObject("utilizador");
        JSONObject item = json.getJSONObject("items");
        JSONObject vendas = json.getJSONObject("vendas");
        for (int e=1;e<= item.length();e++) {
            JSONObject iditem = item.getJSONObject(String.valueOf(e));
            if (Objects.equals(iditem.getString("nome"), itemm)) {
                total = iditem.getInt("pontos") * quantidade;
                guardaritem = String.valueOf(e);
            }
        }
        for(int i=1;i<= utilizador.length();i++) {
            JSONObject iduser = utilizador.getJSONObject(String.valueOf(i));
            if (Objects.equals(iduser.getString("nome"), sessaonome())) {
                int diferenca = iduser.getInt("pontos") - total;
                if (diferenca < 0) {
                    break;
                } else {
                    iduser.put("pontos",diferenca);
                }
            }
        }
        for (int i=1;i<= vendas.length();i++) {
            JSONObject vendasnome = vendas.getJSONObject(String.valueOf(i));
            if (Objects.equals(vendasnome.getString("nomeu"), nomee) && Objects.equals(vendasnome.getString("iditem"), guardaritem)) {
                vendas.remove(String.valueOf(i));
                System.out.println("nesta funcao");
            } else {
                vendas.put(String.valueOf(i),vendasnome);
            }
        }
        for (String idvenda : vendas.keySet()) {
            JSONObject nomevenda = vendas.getJSONObject(idvenda);
            objeto.put(String.valueOf(contador),nomevenda);
            contador++;
        }

        json.put("vendas",objeto);
        json.put("utilizador",utilizador);
        Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
    }
}

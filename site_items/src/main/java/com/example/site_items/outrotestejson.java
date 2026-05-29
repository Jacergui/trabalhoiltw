package com.example.site_items;

import org.json.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class outrotestejson {
    public static void main(String[] args) throws IOException {
        //String nomee = "gui";//substituir por request do nome do login
        //String ficheiro = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        //JSONObject json = new JSONObject(ficheiro);
        //JSONObject outro = json.getJSONObject("utilizador");
        //for (int i=1;i<outro.length();i++) {
        //    JSONObject nome = outro.getJSONObject(String.valueOf(i));
        //    if (Objects.equals(nome.getString("nome"), nomee)) {
        //        FileWriter ficheirologin = new FileWriter("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\sessao.txt");
        //        ficheirologin.write(nome.getString("nome"));
        //        ficheirologin.close();
        //    }
        //}
        compraritem();
    }

    public static void compraritem() throws IOException {
        String itemm = "acessorio";//substituir por request do item
        String guardarid = "";
        boolean existe = true;
        int quantidade = 2;
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        if (validarcompra() ) {
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
                                        itemutilizador.put(String.valueOf(a),iditemutilizador);
                                    } else {
                                        existe = false;
                                        guardarid = String.valueOf(i);
                                    }
                                }
                                if (!existe) {
                                    JSONObject itemutilizadore = new JSONObject();
                                    itemutilizadore.put("id",guardarid);
                                    itemutilizadore.put("quantidade", quantidade);
                                    itemutilizador.put(String.valueOf(itemutilizador.length() + 1), itemutilizadore);
                                }
                                idutilizador.put("itemsu",itemutilizador);
                            }
                            utilizador.put(String.valueOf(e),idutilizador);
                        }
                        json.put("utilizador",utilizador);
                    }
                    item.put(String.valueOf(i),id);
                }
            }
            json.put("items",item);
            Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
            retirarnasvendas();
            gerirpontosevendasdoutilizador();
        }
    }

    public static String sessaonome() throws FileNotFoundException {
        FileReader ficheiro = new FileReader("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\sessao.txt");
        Scanner ler = new Scanner(ficheiro);
        while (ler.hasNextLine()) {
            String nomesessao = ler.nextLine();
            return nomesessao;
        }
        return null;
    }

    public static void venderitem() throws IOException {
        String item="acessorio";//substituir por request do item
        int quantidade = 2;
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
        vendas();
    }

    public static boolean validarcompra() throws IOException {
        String itemm = "acessorio";//substituir por request do item
        int quantidade = 2;
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        JSONObject utilizador = json.getJSONObject("utilizador");
        for (int n=1;n<=utilizador.length();n++) {
            JSONObject id = utilizador.getJSONObject(String.valueOf(n));
            if (Objects.equals(id.getString("nome"), sessaonome())) {
                JSONObject item = json.getJSONObject("items");
                for (int i=1;i<=item.length();i++) {
                    JSONObject iditem = item.getJSONObject(String.valueOf(i));
                    if (Objects.equals(iditem.getString("nome"), itemm)) {
                        int compra = iditem.getInt("pontos") * quantidade;
                        if (compra <= id.getInt("pontos")) {
                            System.out.println("verificou que sim");
                            return true;

                        } else {
                            System.out.println("verificou que não");
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void vendas() throws IOException {
        String itemm = "acessorio";//substituir por request do item
        boolean existe = true;
        int quantidade = 2;
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
    }

    public static void retirarnasvendas() throws IOException {
        String itemm = "acessorio";//substituir por request do item
        int quantidade = 2;
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

    public static void gerirpontosevendasdoutilizador() throws IOException {
        JSONObject objeto = new JSONObject();
        int contador = 1;
        String guardaritem = "";
        String nomee = "gui";//vendedor
        String itemm = "acessorio";//substituir por request do item
        int quantidade = 2;
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

package com.example.site_items;
import org.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;


public class testejson {

    public static void main(String[] args) throws IOException {
        //inserir utilizador
        //String nome = "martim";
        //String passe = "123123";
        //String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        //JSONObject json = new JSONObject(conteudo);
        //System.out.println(conteudo);
        //JSONObject utilizador = json.getJSONObject("utilizador");
        //System.out.println(utilizador.get("1"));
        //JSONObject resultado = new JSONObject();
        //resultado.put("nome",nome);
        //resultado.put("passe",passe);
        //json.getJSONObject("utilizador").put(String.valueOf(utilizador.length() + 1),resultado);
        //Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
        //System.out.println(utilizador.length());
        terminarsessao();
    }

    public static void teste() throws IOException {
        JSONObject objeto = new JSONObject();
        int contador = 1;
        boolean resulta = true;
        String nomee = "gui";
        String item = "acessorio";
        String guardaritem = "";
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        JSONObject vendas = json.getJSONObject("vendas");
        for (int i=1;i< vendas.length();i++) {
            JSONObject vendasnome = vendas.getJSONObject(String.valueOf(i));
            if (Objects.equals(vendasnome.getString("nomeu"), nomee) && Objects.equals(vendasnome.getString("iditem"), guardaritem)) {
                vendasnome.remove(String.valueOf(i));
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

        json.put("utilizador",objeto);
        Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
    }

    public static void terminarsessao() throws IOException {
        FileWriter ficheiro = new FileWriter("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\sessao.txt");
        ficheiro.write("");
        ficheiro.close();
    }
    //terminar sessão
}

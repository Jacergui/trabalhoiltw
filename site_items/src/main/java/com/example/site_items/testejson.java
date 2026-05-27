package com.example.site_items;
import org.json.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;


public class testejson {

    public static void main(String[] args) throws IOException {
        //inserir utilizador
        String nome = "martim";
        String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
        JSONObject json = new JSONObject(conteudo);
        System.out.println(conteudo);
        JSONObject utilizador = json.getJSONObject("utilizador");
        System.out.println(utilizador.get("1"));
        JSONObject resultado = new JSONObject();
        resultado.put("nome",nome);
        json.getJSONObject("utilizador").put(String.valueOf(utilizador.length() + 1),resultado);
        Files.write(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json"),json.toString().getBytes());
        System.out.println(utilizador.length());
    }
}

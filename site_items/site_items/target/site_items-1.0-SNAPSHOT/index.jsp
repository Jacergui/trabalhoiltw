<%@ page import="org.json.JSONObject" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.util.Objects" %>
<%@ page import="static com.example.site_items.outrotestejson.sessaonome" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
    JSONObject json =  new JSONObject(conteudo);
    JSONObject vendas = json.getJSONObject("vendas");
    JSONObject items = json.getJSONObject("items");
    JSONObject utilizador = json.getJSONObject("utilizador");
%>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<div class="container d-flex bg bg-primary  align-items-center justify-content-md-between">
    <div class="fs-4"> titulo</div>
    <ul class="nav nav-pills align-items-center">
        <div class="me-md-5"></div>
        <li class="nav-item">
            <a href="" class="nav-link text-white link-body-emphasis">inicio</a>
        </li>
        <li class="nav-item">
            <a href="#" class="nav-link text-white link-body-emphasis">
                items
            </a>
        </li>
    </ul>
        <input type="search" class="form-control align-items-center w-25" aria-label="pesquisa" placeholder="pesquisar item" id="pesquisa">
    <div class="me-md-10"></div>
    <% try { %>
    <% String nomeSessao = sessaonome();
        for (int a=1;a<= utilizador.length();a++) {
            JSONObject idutilizador = utilizador.getJSONObject(String.valueOf(a));
            if (Objects.equals(sessaonome(), idutilizador.getString("nome"))) { %>
    <div id="pontos" class="text-white"><%=idutilizador.getInt("pontos")%></div>
    <div class="flex-shrink-0 dropdown" id="caixinha">
        <p id="sessao"><%=nomeSessao%></p>
        <ul class="dropdown-menu dropdown-menu-end" id="logoutbox">
            <li>
                <a class="dropdown-item" href="/site_items_war_exploded/TerminarSessao" >terminar sessão</a>
            </li>
        </ul>
    </div>
    <% }
    }
        }catch(Exception e) {
        response.sendRedirect("login.jsp");
        return;
        }%>
</div>
<div class="container d-flex" id="grandecaixa">
    <div class="w-25 h-100 text-white">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link">
                    definições
                </a>
            </li>
        </ul>
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link">
                    <div>vender item</div>
                </a>
            </li>
        </ul>
    </div>
    <div class="col">
        <% for (int i=1;i<=vendas.length();i++) {
            for (int e=1;e <= items.length();e++) {
            JSONObject id = vendas.getJSONObject(String.valueOf(i));
            JSONObject iditem = items.getJSONObject(String.valueOf(e));
        %>
        <div class="row">
        <div class="card w-25">
            <% if (Objects.equals(id.getString("iditem"), String.valueOf(e))) { %>
            <div class="card-body">
                <img src="<%=iditem.getString("nome")%>.png" class="card-img-top" style="width:20%; height:40%;">
                <div class="card-text"></div>
                <div class="card-text">pontos: </div>
                <div class="card-text">vendedor:</div>
                <input class="bg-white text-primary form-control w-100" type="button" id="botaocard" value="comprar">
            </div>
            <% } %>

        </div>
        </div>
        <% } %>
        <% } %>

        <div id="tabelaclass">
        <h2>Classificação:</h2>
        <table class="table">
            <tr>
                <th>nome:</th>
                <th>pontos:</th>
            </tr>
            <tbody id="tabela"></tbody>
        </table>
        </div>
    </div>
</div>
<div class="container d-flex" id="teusitems">
    <div class="w-25"></div>
    <div class="row">
        <h2>Os teus items:</h2>
        <input type="button" name="item" id="item" value="adicionar item" class="bg-white text-primary form-control">
        <div class="table" id="">

        </div>
    </div>
</div>
<div class="container w-50" id="adicionaritem">
    <h1>adicionar item</h1>
    <form method="POST">
        <select class="form-select" id="itemselecionado">
        </select>
        <div id="quantidadebox" class="d-flex">
        <input type="number" placeholder="quantidade" id="quantidade-items" class="form-control w-50" name="quantidade-items">
        <div class="text-align-center" id="quanttexto"></div>
        </div>
        <div id="texto"></div>
        <input type="submit" name="submeter" id="submeter" value="submeter" class="btn-primary btn">
    </form>
    <img src="acessorio.png" id="imagem" class="w-25 h-25">
</div>
<div class="container w-50" id="venderitem">
    <h1>vender item</h1>
    <form method="POST">
        <select class="form-select" id="itemsavender">
        </select>
        <div id="quantidadebox" class="d-flex">
            <input type="number" placeholder="quantidade" id="quantidade-items-venda" class="form-control w-50" name="quantidade-items">
            <div class="text-align-center" id="quanttexto"></div>
        </div>
        <div id="textovendas"></div>
        <input type="submit" name="submeter" id="submeter" value="submeter" class="btn-primary btn">
    </form>
    <img src="acessorio.png" id="imagem" class="w-25 h-25">
</div>
<div id="msg"></div>
<script>
    $(document).ready(function () {
        var imagem = document.getElementById("imagem")
        $("#caixinha").click(function() {
            $("#logoutbox").addClass("show");
        })
        $("#grandecaixa").click(function() {
            $("#logoutbox").removeClass("show");
        })

        fetch("dados.json")
            .then(res => res.json())
            .then(obj => {
                Object.entries(obj["vendas"]).forEach(([key, value]) => {
                    Object.entries(obj["items"]).forEach(([keyitem, valueitem]) => {
                        if (value["iditem"] === keyitem) {
                            $("#itemselecionado").append("<option value='" + valueitem["nome"] + "'>" + valueitem["nome"] + "</option>");
                        }
                    });
                });
            });
        $("#adicionaritem").on("change",function() {
            fetch("dados.json")
                .then(res => res.json())
                .then(obj => {
                    Object.entries(obj["vendas"]).forEach(([key,value]) => {
                        Object.entries(obj["items"]).forEach(([keyitem,valueitem]) => {
                            if (obj["items"][keyitem.toString()]["nome"] == $(this).find("option:selected").val()) {
                                $("#texto").text("pontos: " + obj["items"][key.toString()]["pontos"] * parseInt($("#quantidade-items").val()))
                                console.log(obj["items"][keyitem.toString()]["pontos"] * parseInt($("#quantidade-items").val()))
                            }
                        })
                    })
                })
            imagem.src = $(this).find("option:selected").val()+".png";
        });
        fetch("dados.json")
            .then(res => res.json())
            .then(obj => {
                document.getElementById("texto").innerHTML += obj["utilizador"];
                Object.entries(obj["utilizador"]).forEach(([key,value]) => {
                    $("#tabela").append("<tr> <td>"+obj["utilizador"][key.toString()]["nome"]+"</td> <td>"+obj["utilizador"][key.toString()]["pontos"]+"</td> </tr>");
                })
            });
        $("#pesquisa").on("keypress", function(event) {
            if (event.which == 13) {
                console.log("deu");
                $("#tabelaclass").hide();
                $("#teusitems").hide();
            }
        })
        $()
        fetch("dados.json")
            .then(res => res.json())
            .then(obj => {
                Object.entries(obj["utilizador"]).forEach(([key,value]) => {
                    Object.entries(obj["utilizador"][key.toString()]["itemsu"]).forEach(([keyitemu,valueitemu]) => {
                        Object.entries(obj["items"]).forEach(([keyitem,valueitem])=> {
                        if (obj["utilizador"][key.toString()]["nome"] === $("#sessao").val()) {
                            if (obj["items"][keyitem.toString()] === obj["utilizador"][key.toString()]["itemsu"][keyitemu.toString()]["id"]) {
                            $("#itemsavender").html("<option value='"+obj["items"][keyitem.toString()]["nome"]+"'></option>")
                            if (obj["items"][keyitem.toString()]["nome"] == $(this).find("option:selected").val()) {
                                $("#textovendas").text("pontos: " + obj["items"][key.toString()]["pontos"] * $("#quantidade-itens-venda").val())
                                console.log(obj["items"][keyitem.toString()]["pontos"])
                            }
                            }
                        }
                        })
                    })
                })
            })
    })
</script>
</body>
</html>
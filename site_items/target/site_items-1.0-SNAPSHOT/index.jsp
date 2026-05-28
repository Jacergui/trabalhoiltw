<%@ page import="org.json.JSONObject" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String conteudo = new String(Files.readAllBytes(Paths.get("C:\\Users\\Utilizador\\IdeaProjects\\site_items\\src\\main\\webapp\\dados.json")));
    JSONObject json =  new JSONObject(conteudo);
    JSONObject vendas = json.getJSONObject("vendas");
    JSONObject items = json.getJSONObject("items");
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
    <form class="">
        <input type="search" class="form-control align-items-center" aria-label="pesquisa" placeholder="pesquisar item">
    </form>
    <div class="me-md-10"></div>
    <div id="pontos" class="text-white ">10</div>
    <div class="flex-shrink-0 dropdown" id="caixinha">
        <p><%="gui"%></p>
        <ul class="dropdown-menu dropdown-menu-end" id="logoutbox">
            <li>
                <a class="dropdown-item" href="#" >terminar sessão</a>
            </li>
        </ul>
    </div>

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
        <% for (int i=1;i<vendas.length();i++) {
            for (int e=1;e < items.length();e++) {
            JSONObject id = vendas.getJSONObject(String.valueOf(i));
            JSONObject iditem = items.getJSONObject(String.valueOf(e));
        %>
        <div class="card w-50">
            <% if (Objects.equals(id.getString("id"), String.valueOf(e))) { %>
            <img src="<%=iditem.getString("nome")%>" class="card-img-top" style="width:20%; height:40%;">
            <% } %>
            <div class="card-body">
                <div class="card-text"><= </div>
                <div class="card-text">pontos: </div>
                <div class="card-text">vendedor:</div>
                <input class="btn btn-primary" type="button" id="botaocard">
            </div>
        </div>
        <% } %>
        <% } %>
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
<div class="container d-flex">
    <div class="w-25"></div>
    <div class="row">
        <h2>Os teus items:</h2>
        <input type="button" name="item" id="item" value="adicionar item" class="bg-white text-primary form-control">
        <div class="table">

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
<script>
    $(document).ready(function () {
        var imagem = document.getElementById("imagem")
        $("#caixinha").click(function() {
            $("#logoutbox").addClass("show");
        })
        $("#grandecaixa").click(function() {
            $("#logoutbox").removeClass("show");
        })
        $("#adicionaritem").on("change",function() {

            fetch("dados.json")
                .then(res => res.json())
                .then(obj => {
                    Object.entries(obj["vendas"]).forEach(([key,value]) => {
                        Object.entries(obj["items"]).forEach(([keyitem,valueitem]) => {
                        if (obj["vendas"][key.toString()]["id"] == obj["items"][keyitem.toString()]) {
                            $("#itemselecionado").html("<option value='"+obj["items"][keyitem.toString()]["nome"]+"'></option>")
                            if (obj["items"][keyitem.toString()]["nome"] == $(this).find("option:selected").val()) {
                                $("#texto").text("pontos: " + obj["items"][key.toString()]["pontos"])
                                console.log(obj["items"][keyitem.toString()]["pontos"])
                            }
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
    })
</script>
</body>
</html>
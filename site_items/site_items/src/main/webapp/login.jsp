<%--
  Created by IntelliJ IDEA.
  User: Utilizador
  Date: 25/05/2026
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
<div class="container w-25 align-items-center" id="login">
    <div class="h-25"></div>
    <h1 class="front-weight-normal">Faz o login</h1>
    <form method="POST" action="/site_items_war_exploded/EfetuarLogin">
        <div>nome:  </div>
        <input class="form-control" type="text" name="nome" id="nome">
        <div>passe:</div>
        <input class="form-control" type="password" name="passe" id="passe">
        <input type="submit" id="enviologin" name="enviologin" class="btn btn-primary" value="enviar">
        <div>Não tem conta?<a href="#" id="criar_b"> Criar</a></div>
    </form>
</div>
<div id="criar" class="container w-25 align-items-center">
    <div class="h-25"></div>
    <h1 class="front-weight-normal">Faz o login</h1>
    <form method="POST">
        <div>nome:  </div>
        <input class="form-control" type="text" name="nomec" id="nomec">
        <div>passe:</div>
        <input class="form-control" type="password" name="passec" id="passec">
        <input type="button" id="enviocriar" name="enviocriar" class="btn btn-primary" value="criar">
        <div>tem conta?<a id="linkentrar" href="#"> Entrar</a></div>
    </form>
</div>
<div id="texto"></div>
<script>
    $("#criar").hide();
    $("#criar_b").click(function () {
        $("#criar").show();
        $("#login").hide();
    })
    $("#linkentrar").click(function () {
        $("#criar").hide();
        $("#login").show();
    })
    $("#enviocriar").click(function () {
        $.ajax({
            method: "POST",
            url: "/site_items_war_exploded/CriarUtilizador",
            data: { nomec: $("#nomec").val(),passec: $("#passec").val() }
        }).done(function( msg ) {
            $("#criar").hide();
            $("#login").hide();
            $("#texto").html(msg);
                setTimeout(() => {
                    $("#criar").hide();
                    $("#login").show();
                    $("#texto").hide();
                }, 3000);
            });
    })

</script>
</body>
</html>

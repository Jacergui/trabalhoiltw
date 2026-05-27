<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <p><%="gui"%>></p>
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
        <div class="card">
            <img src="https://s2-techtudo.glbimg.com/XGcynglSejDwwimMLYvgJg5RO0o=/0x0:1146x431/1000x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2018/Q/Q/cdfDMuQKm8niQpq0rWDg/awpdlore.jpg" class="card-img-top" style="width:20%; height:40%;">
            <div class="card-body">
                <div class="card-text">arma </div>
                <div class="card-text">pontos: </div>
            </div>
        </div>
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
            <option value="mascara" id="mascara">mascara</option>
            <option value="acessorio" id="acessorio">acessorio</option>
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
            imagem.src = $(this).find("option:selected").val()+".png";
            fetch("dados.json")
                .then(res => res.json())
                .then(obj => {
                    Object.entries(obj["items"]).forEach(([key,value]) => {
                        if (obj["items"][key.toString()]["nome"] == $(this).find("option:selected").val()) {
                            $("#texto").text("pontos: "+obj["items"][key.toString()]["pontos"])
                            console.log(obj["items"][key.toString()]["pontos"])
                        }
                    })
                })
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
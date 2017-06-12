<%-- 
    Document   : menu
    Created on : 12/06/2017, 03:09:44 PM
    Author     : Skynet
--%>

<%@page import="py.una.pol.par.entidades.Usuario"%>
<%@page import="py.una.pol.par.entidades.Categoria"%>
<%@page import="java.util.ArrayList"%>
<%@page import="py.una.pol.par.entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carrito</title>
        <link rel="stylesheet" type="text/css" href="vista1.css"/>
    </head>
    <body>
        <div class="caja1">
            <nav class="nav2"><ul>
                    Bienvenido: ${sessionScope.usuario}
                    <li><a href="/ParOnline/login.jsp"> LOGIN </a></li>
                        <%if (request.getSession().getAttribute("usuario") != null) { %>
                    <li><a href="/ParOnline/LoginServlet?logout=logout"> Salir </a></li>
                        <%}%>
                    <li><a href="/ParOnline/index.jsp"> INICIO </a></li>
                </ul></nav>
        </div>
        <div class="caja2">
            <nav class="nav">
                <ul>
                    <li><a href="/ParOnline/ProductoServlet?cate=1">Electrodomesticos</a></li>
                    <li><a href="/ParOnline/ProductoServlet?cate=2">Equipos de Gimnasia</a></li>
                    <li><a href="/ParOnline/ProductoServlet?cate=3">Insumos Informaticos</a></li>
                    <li><a href="/ParOnline/TransaccionABMServlet?vaccion=carrito">Ir al Carrito</a></li>
                        <%if (request.getSession().getAttribute("usuario") != null) {
                                Usuario usu = (Usuario) request.getSession().getAttribute("usuario");
                                if (usu.getTipo_usuario() == 0) {%>
                    <li><a href="/ParOnline/CategoriaABMServlet">Menu Categoria</a>
                        <ul>
                            <li><a href="/ParOnline/CategoriaABMServlet">Listar</a></li>
                            <li><a href="/ParOnline/CategoriaCrear.jsp">Crear</a></li>
                        </ul>
                    </li>
                    <li><a href="/Par_online/ProductoABMServlet">Menu Producto</a>  
                        <ul>
                            <li><a href="/Par_online/ProductoABMServlet">Listar</a></li>
                            <li><a href="/Par_online/ProductoCrear.jsp">Crear</a></li>
                        </ul>
                    </li>
                    <li><a href="/Par_online/ClienteABMServlet">Menu Cliente</a>
                        <ul>
                            <li><a href="/Par_online/UsuarioABMServlet">Listar</a></li>
                            <li><a href="/Par_online/UsuarioCrear.jsp">Crear</a></li>
                        </ul>
                    </li>
                    <%}
                        }%>
                </ul>
            </nav>
        </div>
    </body>
</html>
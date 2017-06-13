<%-- 
    Document   : ProductoEditar
    Created on : 12/06/2017, 03:29:30 PM
    Author     : Skynet
--%>

<%@page import="py.una.pol.par.entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Editar Producto</title>
    </head>
    <body>
        <header><h1 align = "center"> Editar Producto </h1></header> 
        <nav class="nav2" >
            <ul>
                <li><a href="/ParOnline/index.jsp">INICIO</a></li>            
            </ul>
        </nav>
        <div id="main">
            <article>
                <%
                    Producto c = (Producto) request.getAttribute("producto");
                %>
                <form action="/ParOnline/ProductoABMServlet" method="post">
                    <table>
                        <tr>
                            <td>ID: </td>
                            <td><%=c.getId_producto()%></td>
                        </tr>
                        <tr>
                            <td>Descripcion:</td>
                            <td><input type="text" name="descripcion" value="<%=c.getDescripcion()%>"/></td>
                        </tr>
                        <tr>
                            <td>Precio:</td>
                            <td><input type="text" name="precio" value="<%=c.getPrecio()%>"/></td>
                        </tr>
                        <tr>
                            <td>Id Categoria del Producto:</td>
                            <td><input type="text" name="id_cat" value="<%=c.get_categoria().getId_categoria()%>"/></td>
                        </tr>
                    </table>
                    <input type="hidden" name="vid" value="<%=c.getId_producto()%>"/>    
                    <input type="hidden" name="vaccion" value="GrabarModificado"/>
                    <input type="submit" value="Grabar"/>
                </form>
            </article>
        </div>
    </body>
</html>

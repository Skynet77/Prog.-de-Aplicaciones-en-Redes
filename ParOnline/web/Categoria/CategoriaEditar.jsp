<%-- 
    Document   : CategoriaEditar
    Created on : 12/06/2017, 03:36:42 PM
    Author     : Skynet
--%>

<%@page import="py.una.pol.par.entidades.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Editar Categoria</title>
    </head>
    <body>
        <header> <h1 align = "center"> Editar Categoria</h1></header>       
        <nav class="nav2" >
            <ul>
                <li><a href="/ParOnline/index.jsp">INICIO</a></li>            
            </ul>
        </nav>
        <div id="main">
            <article>       
                <%
                    Categoria c = (Categoria) request.getAttribute("categoria");
                %>
                <form action="/ParOnline/CategoriaABMServlet" method="post">            
                    <table>
                        <tr>
                            <td>ID: </td>
                            <td><%=c.getId_categoria()%></td>
                        </tr>
                        <tr>
                            <td>Descripcion:</td>
                            <td><input type="text" name="descripcion" value="<%=c.getDescripcion()%>"/></td>                   
                        </tr>
                    </table>
                    <input type="hidden" name="vid" value="<%=c.getId_categoria()%>"/>    
                    <input type="hidden" name="vaccion" value="GrabarModificado"/>
                    <input type="submit" value="Grabar"/>
                </form>
            </article>
        </div>
    </body>
</html>

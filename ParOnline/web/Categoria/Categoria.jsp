<%-- 
    Document   : Categoria
    Created on : 12/06/2017, 03:36:21 PM
    Author     : Skynet
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="py.una.pol.par.entidades.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Categorias</title>
    </head>
    <body>
        <header><h1 align = "center"> Listado de Categorias</h1></header> 
        <jsp:include page="/menu.jsp" flush="true"/>
        <div id="main">
            <article>
                <form action="/ParOnline/Categoria/CategoriaCrear.jsp" method="post">
                    <input type="submit" value="Crear"/>    
                </form>
                <table border=1 cellspacing=1 cellpadding=5 bordercolor=white style="text-align:center;">
                    <tr>
                        <th>Id</th>
                        <th>Descripcion</th>
                        <th colspan="2" >Acciones</th>
                    </tr>
                    <%

                        ArrayList<Categoria> categorias = (ArrayList<Categoria>) request.getAttribute("categorias");
                        if (categorias == null) {

                        } else {
                            for (Categoria c : categorias) {
                    %>
                    <tr>
                        <td><%=c.getId_categoria()%></td>
                        <td><%=c.getDescripcion()%></td>
                        <td>
                            <form action="/ParOnline/CategoriaABMServlet" method="post">
                                <input type="hidden" name="vaccion" value="Editar"/>
                                <input type="hidden" name="vid" value="<%=c.getId_categoria()%>"/>
                                <input type="submit" value="Editar"/>
                            </form>
                        </td>
                        <td>
                            <form action="/ParOnline/CategoriaABMServlet" method="post">
                                <input type="hidden" name="vaccion" value="Eliminar"/>
                                <input type="hidden" name="vid" value="<%=c.getId_categoria()%>"/>
                                <input type="submit" value="Eliminar"/>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </article>
        </div>
    </body>
</html>
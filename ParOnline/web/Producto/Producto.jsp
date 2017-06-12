<%-- 
    Document   : Producto
    Created on : 12/06/2017, 03:29:38 PM
    Author     : Skynet
--%>

<%@page import="py.una.pol.par.entidades.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Productos</title>
    </head>
    <body>
        <header><h1 align = "center">Listado de Productos</h1></header> 
        <jsp:include page="/menu.jsp" flush="true"/>
        <div id="main">
            <article>
                <form action="/ParOnline/Producto/ProductoCrear.jsp" method="post">
                    <input type="submit" value="Crear"/>    
                </form>
                <table border=1 cellspacing=1 cellpadding=5 bordercolor=white style="text-align:center;">
                    <tr>
                        <th>Id</th>
                        <th>Descripcion</th>
                        <th>Precio</th>
                        <th colspan="2" >Acciones</th>
                    </tr>
                    <%
                        ArrayList<Producto> productos = (ArrayList<Producto>) request.getAttribute("productos");
                        if (productos == null) {
                            out.print(productos.get(1));
                            System.out.println("null");
                        } else {
                            for (Producto c : productos) {
                    %>
                    <tr>
                        <td><%=c.getId_producto()%></td>
                        <td><%=c.getDescripcion()%></td>
                        <td><%=c.getPrecio()%></td>
                        <td>
                            <form action="/ParOnline/ProductoABMServlet" method="get">
                                <input type="hidden" name="vaccion" value="Editar"/>
                                <input type="hidden" name="vid" value="<%=c.getId_producto()%>"/>
                                <input type="submit" value="Editar"/>
                            </form>
                        </td>

                        <td>
                            <form action="/ParOnline/ProductoABMServlet" method="post">
                                <input type="hidden" name="vaccion" value="Eliminar"/>
                                <input type="hidden" name="vid" value="<%=c.getId_producto()%>"/>
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

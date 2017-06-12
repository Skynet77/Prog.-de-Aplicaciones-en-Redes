<%-- 
    Document   : index
    Created on : 12/06/2017, 02:48:04 PM
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
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
    </head>
    <body>
        <header><h1 align = "center"> Menú Principal - PAR ONLINE.</h1></header> 
            <jsp:include page="/menu.jsp" flush="true"/>
        <div id='main'>
            <aside>
                <form action="BuscarServlet" method="post">
                    <br/>
                    Filtrar:
                    <select name="categorias">
                        <option value="Todo"> Todo
                        <option value="1">Computadoras
                        <option value="2">Telefonos móviles
                        <option value="3">Accesorios de computadoras y moviles
                    </select>
                    <br><br>
                    Descripcion:
                    <input required type="text" size="20" name="descripcion" placeholder="buscar...">
                    <input type="submit" value="Buscar">
                </form>
            </aside>
            <article>
                <table>
                    <tr>
                        <th>ID:</th>
                        <th>Descripcion:</th>
                        <th>Precio:</th>
                    </tr>
                    <%
                        //aca vamos a poner al getALL() de producto y que vaya listando
                        ArrayList<Producto> p1 = (ArrayList<Producto>) request.getAttribute("array_producto"); //se guarda el array en p1 de productos por categoria  
                        if (p1 != null) {
                            for (Producto p3 : p1) {
                    %>
                    <tr>
                        <td><%=p3.getId_producto()%></td>
                        <td><%=p3.getDescripcion()%></td>
                        <td><%=p3.getPrecio()%></td>
                        <td>
                             <form action="/ParOnline/TransaccionABMServlet" method="post">
                                <input type="hidden" name="accion" value="agregar_carrito"/>
                                <input type="hidden" name="vid" value="<%=p3.getId_producto()%>"/>
                                <input type="submit" value="Añadir al carrito"/>
                            </form></td>
                    </tr>
                    <% }
                        }%>
                </table>    
            </article>
        </div>
        <div id="main">
            <article>
                <table>
                    <tr>
                        <th>Productos del Carrito:</th>
                    </tr>
                    <%
                        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
                        if (u != null) {
                            ArrayList<Producto> c = (ArrayList<Producto>) request.getSession().getAttribute("carritos");
                            if (c != null) {
                                for (Producto pr : c) {
                    %>
                    <tr>
                        <td> <% out.print(pr.getDescripcion());%></td>
                        <td>
                            <form action="/ParOnline/TransaccionABMServlet" method="post">
                                <input type="hidden" name="vaccion" value="quitar"/>
                                <input type="hidden" name="vid" value="<%=pr.getId_producto()%>"/>
                                <input type="submit" value="Quitar"/>
                            </form></td>
                    </tr>
                    <%
                                }
                            }
                        }
                    %>
                </table>
            </article>
        </div>
        <footer align="center">Contactos:<br> Luis Mendez: (0985)-867-966 <br>
            Analia Riquelme: (0961)-475-337
        </footer>
    </body>
</html>

<%-- 
    Document   : Transaccion
    Created on : 12/06/2017, 03:28:56 PM
    Author     : Skynet
--%>

<%@page import="py.una.pol.par.modelos.ProductoManager"%>
<%@page import="py.una.pol.par.entidades.Usuario"%>
<%@page import="py.una.pol.par.entidades.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Carrito</title>
    </head>
    <body
        <div>
            <header> <h1 align = "center"> Contenido de su Carrito</h1></header>          
        </div>     
        <nav class="nav">
            <ul>
                <li><a href="/ParOnline/index.jsp">INICIO</a></li>
                    <%if (request.getSession().getAttribute("carritos") != null) {
                            ArrayList<Producto> ar = (ArrayList<Producto>) request.getSession().getAttribute("carritos");
                            if (ar.size() != 0) {%>
                <li><a href="/ParOnline/ComprarServlet?vaccion=comprar">Comprar</a></li>
                    <%}
                        }%>
            </ul>
        </nav>     
        <div id='main'>
            <article>

                <table border=3 cellspacing=1 cellpadding=5 bordercolor=white style="text-align:center;">
                    <tr>
                        <th>Productos del Carrito:</th>
                        <th> Cant. a quitar del carrito</th>

                    </tr>
                    <%
                        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
                        ProductoManager pc = new ProductoManager();
                        int can = 0;
                        int suma = 0;
                        int index = 0;
                        if (u != null) {
                            ArrayList<Producto> c = (ArrayList<Producto>) request.getSession().getAttribute("carritos");
                            ArrayList<Producto> todo_producto = pc.getAll();
                            if (c != null) {
                                for (int j = 0; j < todo_producto.size(); j++) {
                                    can = 0;
                                    for (int i = 0; i < c.size(); i++) {
                                        if (todo_producto.get(j).getId_producto() == c.get(i).getId_producto()) {
                                            can += 1;
                                            index = i;
                                        }
                                    }

                                        if (can != 0) {
                                            int id = c.get(index).getId_producto();%>
                    <tr>
                        <td>Descripcion: <%=c.get(index).getDescripcion()%></td>
                    </tr>
                    <tr>
                        <td>Cantidad:<%=can%></td>
                        <td><form action="/ParOnline/TransaccionABMServlet" method="post">
                                <input type="number" size="10" name="cant" max="<%=can%>" min="0"/>
                                <input type="hidden" name="vaccion" value="quitar"/>
                                <input type="hidden" name="vid" value="<%=id%>"/>
                                <input type="submit" value="Quitar"/>
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>Precio: <%=c.get(index).getPrecio() * can%></td>
                    </tr>
                    <tr>
                        <td>+----------------------------------+</td>
                    </tr>
                    <%
                                    suma += c.get(index).getPrecio() * can;
                                }

                            }%>
                    <tr><td><em>Total a Pagar:</em> <%=suma%></td>
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
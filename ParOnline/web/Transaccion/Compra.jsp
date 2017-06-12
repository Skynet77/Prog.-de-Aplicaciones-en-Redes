<%-- 
    Document   : Compra
    Created on : 12/06/2017, 03:29:04 PM
    Author     : Skynet
--%>

<%@page import="py.una.pol.par.entidades.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="py.una.pol.par.modelos.ProductoManager"%>
<%@page import="py.una.pol.par.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <script type="text/javascript" src="validacion.js"></script>
        <title>Compras</title>
    </head>
    <body>
        <div>
            <header><h1 align = "center">Compras</h1></header>             
        </div>      
        <nav class="nav">
            <ul>
                <li><a href="/ParOnline/index.jsp">INICIO</a></li>
                <li><a href="/ParOnline/TransaccionABMServlet?vaccion=carrito">Carrito</a></li>
            </ul>
        </nav>
        <div id='main'>
            <article>
                <table border=3 cellspacing=1 cellpadding=5 bordercolor=white style="text-align:center;">
                    <tr>
                        <th>Productos del Carrito</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th>Total a Pagar</th>                       
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
                    %>
                    <tr>                     
                        <td style="text-align:left;"> <%=c.get(index).getDescripcion()%> <br>codigo: <%=c.get(index).getId_producto()%></td>
                        <td><%=can%></td>
                        <td><%=c.get(index).getPrecio()%></td>
                        <td><%=c.get(index).getPrecio() * can%></td>
                    </tr>                   
                    <tr>
                        <td colspan="4">+---------------------------------------------------------------------------------------+</td>
                    </tr>
                    <%
                            }
                            suma += c.get(index).getPrecio() * can;
                        }%>
                    <tr><td style="text-align:left;" colspan="3"><em>Total a Pagar:</em></td>
                        <td><%=suma%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </article>
            <aside>
                <h1>Formas de Pago</h1>
                <form action="/ParOnline/Transaccion/CompraServlet" method="post">
                    <input id ="tarjeta" type="radio" name="forma_pago" value="tarjeta" 
                           onclick="document.getElementById('numTarjeta').disabled = !document.getElementById('numTarjeta').disabled;" /> Tarjeta de Crédito
                    <input id="numTarjeta" type="text" name="codigo" size="10" disabled />
                    <br><input type="radio" name="forma_pago" value="efectivo"  
                               onclick="document.getElementById('numTarjeta').disabled = !document.getElementById('numTarjeta').disabled;"/>Efectivo
                    <input type="hidden" name="vaccion" value="Pagar">
                    <br>Dirección de envío<input required type="text" name="direccion" placeholder="Dirección..."/>
                    <br><input type="submit" value="Pagar"/>
                </form>
                <%if (request.getSession().getAttribute("carritos") == null) {%>
                <script type="text/javascript">
                    alert("Su compra fue realizada con exito");
                </script>
                <%}%>
            </aside>
        </div>
    </body>
</html>

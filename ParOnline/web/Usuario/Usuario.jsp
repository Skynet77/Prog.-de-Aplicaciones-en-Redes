<%-- 
    Document   : Usuario
    Created on : 12/06/2017, 03:29:58 PM
    Author     : Skynet
--%>

<%@page import="py.una.pol.par.entidades.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Clientes</title>
    </head>
    <body>
        <header> <h1 align = "center"> Listado de Clientes</h1> 
        </header>
        <jsp:include page="/menu.jsp" flush="true"/>
        <div id="main">
            <article>
                <form action="/ParOnline/Usuario/UsuarioCrear.jsp" method="post">
                    <input type="submit" value="Crear"/>    
                </form>
                <table border=1 cellspacing=1 cellpadding=5 bordercolor=black >
                    <tr>
                        <th>Id </th>
                        <th>Cedula</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Contraseña</th>
                        <th colspan="2" >Acciones</th>
                    </tr>

                    <%
                        ArrayList<Usuario> clientes = (ArrayList<Usuario>) request.getAttribute("clientes");
                        if (clientes == null) {
                            //js
                        } else {
                            for (Usuario c : clientes) {
                    %>
                    <tr>
                        <td><%=c.getId_usuario()%></td>
                        <td><%=c.getlogin_name()%></td>
                        <td><%=c.getNombre()%></td>
                        <td><%=c.getApellido()%></td>
                        <td><%=c.getContrasenha()%></td>
                        <td><%=c.getTipo_usuario()%></td>
                        <td>
                            <form action="/ParOnline/UsuarioABMServlet" method="get">
                                <input type="hidden" name="vaccion" value="Editar"/>
                                <input type="hidden" name="vid" value="<%=c.getId_usuario()%>"/>
                                <input type="submit" value="Editar"/>
                            </form>
                        </td>
                        <td>
                            <form action="/ParOnline/UsuarioABMServlet" method="post">
                                <input type="hidden" name="vaccion" value="Eliminar"/>
                                <input type="hidden" name="vid" value="<%=c.getId_usuario()%>"/>
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

<%-- 
    Document   : login
    Created on : 12/06/2017, 03:09:36 PM
    Author     : Skynet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Login</title>
    </head>
    <body>
        <script type="text/javascript">
            history.forward();//para no volver atras
        </script>
        <header><h1 align = "center">Login</h1></header> 
        <nav class="nav2">
            <ul>
                <li><a href="/ParOnline/index.jsp">INICIO</a></li>
                    <%if (request.getSession().getAttribute("usuario") != null) {%>
                <li><a href="/ParOnline/LoginServlet?logout=logout">Salir</a></li>
                    <%}%>
            </ul>
        </nav>
        <div id="main">
            <article>
                <form name="f1" action="/ParOnline/LoginServlet" method="post" onsubmit="return valida(this)">
                    <table>
                        <tr>
                            <td>Nombre de usuario:</td>
                            <td><input required type="text" name="login_name" placeholder=" " autofocus/></td>
                        </tr>
                        <tr>
                            <td>Contraseña:</td>
                            <td><input required type="password" name="pas" placeholder="pass"/></td>
                        </tr>
                    </table>
                    <input type="hidden" name=" " value="login"/>
                    <input type="submit" value="Login"/>
                </form>
                <%if (request.getAttribute("mensaje") != null) {
                        if (request.getAttribute("mensaje").equals("incorrecto")) {
                            out.print("Usuario incorrecto");
                        } else if (request.getAttribute("mensaje").equals("logueado")) {
                            out.print("Debe Desloguearse primeramente");
                    }
                }%>
                <form action="/ParOnline/LoginServlet" method="post">
                    <input type="hidden" name="registrar" value="registrar">
                    <input type="submit" value="Registrarse">
                </form>
            </article>
        </div>
        <footer align="center">Contactos:<br> Luis Mendez: (0985)-867-966 <br>
            Analia Riquelme: (0961)-475-337
        </footer>
    </body>
</html>

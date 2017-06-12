<%-- 
    Document   : CategoriaCrear
    Created on : 12/06/2017, 03:36:33 PM
    Author     : Skynet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Categorias Crear</title>
    </head>
    <header><h1 align = "center">Crear Categoria</h1></header> 
        <jsp:include page="/menu.jsp" flush="true"/>
    <body>
        <div id="main">
            <article>
                <form action="/ParOnline/CategoriaABMServlet" method="post">
                    <table>
                        <tr>
                            <td>ID Categoria</td>
                            <td><input type="text" name="id_cat"/></td>
                        </tr>
                        <tr>
                            <td>Descripcion</td>
                            <td><input type="text" name="descripcion"/></td>
                        </tr>
                    </table>
                    <input type="hidden" name="vaccion" value="Grabar"/>
                    <input type="submit" value="Grabar"/>
                </form>
            </article>
        </div>
    </body>
</html>

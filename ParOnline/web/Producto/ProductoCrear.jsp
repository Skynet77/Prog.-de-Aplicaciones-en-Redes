<%-- 
    Document   : ProductoCrear
    Created on : 12/06/2017, 03:29:21 PM
    Author     : Skynet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css"/>
        <title>Crear</title>
    </head>
    <header><h1 align = "center">Crear Producto</h1></header> 
        <jsp:include page="/menu.jsp" flush="true"/>
    <body>
        <div id="main">
            <article>
                <form action="/ParOnline/ProductoABMServlet" method="post">
                    <table>
                        <tr>
                            <td>ID Producto</td>
                            <td><input type="text" name="id_pro"/></td>
                        </tr>
                        <tr>
                            <td>Descripcion</td>
                            <td><input type="text" name="descripcion"/></td>
                        </tr>
                        <tr>
                            <td>Precio</td>
                            <td><input type="text" name="precio"/></td>
                        </tr>
                        <tr>
                            <td>Id Categoria</td>
                            <td><input type="text" name="id_cat"/></td>
                        </tr>
                    </table>
                    <input type="hidden" name="vaccion" value="Grabar"/>
                    <input type="submit" value="Grabar"/>
                </form>
            </article>
        </div>
    </body>
</html>

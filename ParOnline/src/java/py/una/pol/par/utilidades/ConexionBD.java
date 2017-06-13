/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.utilidades;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * @author José Alvarez
 */

public class ConexionBD {

    public static Connection getConnection()throws SQLException, ClassNotFoundException, NamingException, Exception{
        Connection conexion = null;
        InitialContext cxt = new InitialContext();
        if ( cxt == null ) {
            throw new Exception("Uh oh -- no context!");
        }

        DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

        if ( ds == null ) {
            throw new Exception("Data source not found!");
        }
        conexion = ds.getConnection();
        return conexion;
    }
    
    public static void closeConnection(Connection conexion)throws SQLException, ClassNotFoundException{
        conexion.close();
    }
}

package dbhandler;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBHandler
{
    private Connection getConnection(String url, String dbName, String user, String password)
    {
        Connection con = null;
        String URL ="jdbc:mysql://"+url+"/"+dbName;
        String DRIVER ="com.mysql.jdbc.Driver";
        String USUARIO =user;
        String SENHA =password;

        try
        {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return con;
    }
}

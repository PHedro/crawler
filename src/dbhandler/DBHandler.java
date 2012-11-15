package dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBHandler
{
    public Connection getConnection(String url, String dbName, String user, String password)
    {
        Connection con = null;
        String dburl ="jdbc:mysql://"+url+"/"+dbName;
        String DRIVER ="com.mysql.jdbc.Driver";

        try
        {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(dburl, user, password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("These aren't the droids you're looking for.");
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            System.out.println("Houston we have a problem...");
            e.printStackTrace();
        }

        return con;
    }
}

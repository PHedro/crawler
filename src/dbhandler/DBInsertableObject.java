package dbhandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBInsertableObject
{
    private HashMap<String, Object> values = new HashMap<String, Object>();

    public DBInsertableObject(String table)
    {
        this.values.put("table", table);
    }

    public void setValue(String key, Object value)
    {
        this.values.put(key, value);
    }

    public boolean saveInDatabase(Connection connection)
    {
        PreparedStatement statement = getInsertStatement(connection);
        try {
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PreparedStatement getInsertStatement(Connection connection)
    {
        PreparedStatement statement = null;

        String table = this.values.get("table").toString();

        String initialQuery = "INSERT INTO $tab ( $col ) VALUES ( $val )";
        String columnsNames = "";
        String val = "";

        try
        {
            if (table != null && !table.isEmpty())
            {
                initialQuery.replace("$tab", table);

                Map<String, String> temp = generateColumnsAndValuesSequence();
                columnsNames = temp.get("cols");
                val = temp.get("values");

                initialQuery.replace("$col", columnsNames).replace("'","");
                initialQuery.replace("$val", val).replace("'","");
                statement = connection.prepareStatement(initialQuery);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return statement;
    }

    private Map<String, String> generateColumnsAndValuesSequence()
    {
        Map<String, String> result = new HashMap<String, String>();

        String columns = "";
        List<String> val = new ArrayList<String>();
        Set<String> keys = this.values.keySet();

        columns = keys.toString().replace("table, ", "").replace("[", "").replace("]", "");
        result.put("cols", columns);

        for(String key : keys)
        {
            if(!key.equalsIgnoreCase("table"))
            {
                Object value = this.values.get(key);

                val.add(value.toString());
            }
        }
        result.put("values", val.toString().replace("[", "").replace("]", ""));

        return result;
    }
}

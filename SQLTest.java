import javax.swing.plaf.nimbus.State;
import java.sql.*;
public class SQLTest {
    public static String comma = " , ";
    public static String quotes = "\'";

    public static Connection createConnection() throws SQLException {
        Connection conn = DriverManager.getConnection
                ("jdbc:mysql://localhost/inventory", "root", "rootroot");
        return conn;
    }

    public static Statement createStatement() throws SQLException {
        Connection conn = createConnection();
        Statement st = conn.createStatement();
        return st;
    }
    public static void insert (Item item) throws SQLException {
        Statement st = createStatement();
        String query = "INSERT INTO items VALUES (" +
                item.getUniqueID() + comma +
                quotes + item.getType() + quotes + comma +
                quotes + item.getManufacturer() + quotes + comma +
                quotes + item.getNote() + quotes + comma +
                item.getPrice() + comma +
                item.getQuantity() +
                ")";
        System.out.println(query);
        st.execute(query);
    }

    public static void delete (long uniqueID) throws SQLException {
        Statement st = createStatement();
        String query = "DELETE FROM items WHERE uniqueID = " + uniqueID;
        st.execute(query);
    }

    public static void update (Item item) throws SQLException {
        Statement st = createStatement();
        String checkQuery = "SELECT uniqueID FROM items";
        ResultSet rs = st.executeQuery(checkQuery);

        boolean found = false;
        while(rs.next()) {
            if(rs.getInt("uniqueID") == item.getUniqueID()) {
                found = true;
            }
        }
        if (found == false) {
            insert(item);
        } else {
            String query = "UPDATE items SET " +
                    "type = " + quotes + item.getType() + quotes + comma +
                    "manufacturer = " + quotes + item.getManufacturer() + quotes + comma +
                    "note = " + quotes + item.getNote() + quotes +comma +
                    "price = " + item.getPrice() + comma +
                    "quantity = " + item.getQuantity() +
                    " where uniqueID = " + item.getUniqueID();
            System.out.println(query);
            st.execute(query);
        }
    }

    public static ResultSet getAllData() throws SQLException {
        Connection myConn = SQLTest.createConnection();
        Statement st = myConn.createStatement();
        String query = "SELECT * FROM items";
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public static ResultSet getUniqueIDData(int uniqueID) throws SQLException {
        Connection myConn = SQLTest.createConnection();
        Statement st = myConn.createStatement();
        String query = "SELECT * FROM items where uniqueID = " + uniqueID;
        return st.executeQuery(query);
    }

    public static ResultSet getTypeData(String data) throws SQLException {
        Connection myConn = SQLTest.createConnection();
        Statement st = myConn.createStatement();
        String query = "SELECT * FROM items where type LIKE " + quotes + "%" + data + "%" + quotes;
        return st.executeQuery(query);
    }

    public static ResultSet getManufacturerData(String data) throws SQLException {
        Connection myConn = SQLTest.createConnection();
        Statement st = myConn.createStatement();
        String query = "SELECT * FROM items where manufacturer LIKE " + quotes + "%" + data + "%" + quotes;
        return st.executeQuery(query);
    }

    public static ResultSet getNoteData(String data) throws SQLException {
        Connection myConn = SQLTest.createConnection();
        Statement st = myConn.createStatement();
        String query = "SELECT * FROM items where note LIKE " + quotes + "%" + data + "%" + quotes;
        return st.executeQuery(query);
    }

    public static ResultSet getPriceData(double val, boolean asc) throws SQLException {
        Connection myConn = SQLTest.createConnection();
        Statement st = myConn.createStatement();
        String query = "";
        if (asc) {
            query = "SELECT * FROM items WHERE price >= " + val;
        } else {
            query = "SELECT * FROM items WHERE price <= " + val;
        }
        return st.executeQuery(query);
    }

    public static ResultSet getQuantityData(double val, boolean asc) throws SQLException {
        Connection myConn = SQLTest.createConnection();
        Statement st = myConn.createStatement();
        String query = "";
        if (asc) {
            query = "SELECT * FROM items WHERE quantity >= " + val;
        } else {
            query = "SELECT * FROM items WHERE quantity <= " + val;
        }
        return st.executeQuery(query);
    }
}

package za.co.fnb.coffeshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {


    public void connect() {
        Connection connection = null;

//        try {
//            String url = "jdbc:postgresql://localhost:8081/coffeeshop";
//            String user = "postgres";
//            String password = "postgres";
//            connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Connected to the postgreSql server success");
//        } catch (SQLException e){
//            System.out.println(e.getMessage());
//        }

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5433/coffeeshop?currentSchema=public";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","mysecretpassword");
            connection = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }

    public static void main(String[] args) {
        Database database = new Database();
        database.connect();
    }
}

package northalley.com.ar4all;

/**
 * Created by saipavan on 10-10-2016.
 */
import android.content.Context;
import android.database.*;
import android.os.NetworkOnMainThreadException;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.SQLException;

class UserData {
    private static final String JDBC_DRIVER= "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://rds-mysql-10mintutorial.chmype33ilkx.us-east-1.rds.amazonaws.com:3306/userdetails";
    //private static final String DB_NAME="userdetails";
    private static final String USER_NAME = "masterUsername";
    private static final String PASSWORD = "pavan123";
    Context cont;
    private String password;
    Connection conn = null;
    PreparedStatement stmt = null;
    protected UserData(Context context)
    {
      cont = context;
    }
    public void startConnection()
    {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
//            stmt=conn.createStatement();
        }catch (SQLException e){e.printStackTrace();} catch(NetworkOnMainThreadException n){n.printStackTrace();}
        catch(Exception e){e.printStackTrace();}
    }
    public void insertTODb(String usrnm,String email,String phone, String password)
    {
        try
        {
//            String sql = "INSERT INTO userreg(username,email,phone,password)"+" VALUES("+"'"+usrnm+"'"+", "+"'"+email+"'"+", "+"'"+phone+"'"+", "+"'"+password+"'"+");";
            String sql = "INSERT INTO userreg(username,email,phone,password)"+" VALUES(?,?,?,?);";
//            stmt.executeUpdate(sql);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,usrnm);
            pstmt.setString(2,email);
            pstmt.setString(3,phone);
            pstmt.setString(4,password);
            pstmt.execute();
            Toast.makeText(cont,"You are sucessfully registered",Toast.LENGTH_SHORT).show();
            pstmt.close();
        }catch(SQLException e){e.printStackTrace();}
    }
    public int fromDb(String email,String pass)
    {
        ResultSet rs;

        try {
            /*String sql = "SELECT password from userreg where email = "+"'"+email+"' ;";
             rs = stmt.executeQuery(sql);*/
            PreparedStatement pstmt = conn.prepareStatement("SELECT password from userreg where email = ?");
            pstmt.setString(1, email);
            rs=pstmt.executeQuery();
//            rs = pstmt.getResultSet();
            if (!rs.next()) {
                Toast.makeText(cont, "please enter a valid email id or Register", Toast.LENGTH_SHORT).show();
                return 2;
            } else {
                do {
                    password = rs.getString("password");
                    Log.d("password", password);

                }while (rs.next());
                pstmt.close();
            }
            }catch(SQLException e){
                e.printStackTrace();
            }
            if (password.equals(pass)) {
                return 1;
            } else {
                return 0;
            }

    }
    public void closeConnection()
    {
        try{
            if(stmt!=null)
                conn.close();
        }catch(SQLException se){
        }
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}

import java.sql.*;
import java.util.Objects;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?useUnicode = true&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static final String DatabaseName = "Project_DataBase" ;

    private static Connection DBconnect = null;

    private static Statement DBstmt = null;

    private static void Init_Database()
    {
        try {
            ResultSet rs = DBstmt.executeQuery("show databases;");

            while (rs.next())
            {
                System.out.println(rs.getString("Database"));
                //这一句的作用是比较查询到数据库引擎内的字段是否和我们要创建的数据库名字相等
                if(rs.getString("Database").equals("project_database"))
                {

                    //如果数据库已经存在，则重新链接到该数据库
                    try {
                        DBconnect.close();
                        DBstmt.close();
                        DBconnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_DataBase?useUnicode = true&characterEncoding=utf-8", USER, PASSWORD);
                        DBstmt = DBconnect.createStatement();
                        System.out.println("打开数据库");
                        return;
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            //如果数据库不存在，则创建后链接
            try {
                DBstmt.execute("create database Project_dataBase");
                DBconnect.close();
                DBstmt.close();
                DBconnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_DataBase?useUnicode = true&characterEncoding=utf-8", USER, PASSWORD);
                DBstmt = DBconnect.createStatement();
                System.out.println("创建数据库");
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            DBconnect = DriverManager.getConnection(URL,USER,PASSWORD);
            DBstmt = DBconnect.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Init_Database();
    }
}


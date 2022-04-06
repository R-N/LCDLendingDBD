/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.database;

import dbd_lcd.Config;
import java.sql.SQLException;
import dbd_lcd.Util;
import java.util.Properties;

/**
 *
 * @author Lenovo2
 */
public class Database implements AutoCloseable{
    
    java.sql.Connection conn = null;
    
    private static String getConnectionString(){
        return String.format(Config.get("CONNECTION_STRING"), Config.get("DATABASE_SERVER"), Config.get("DATABASE_NAME"));
    }
    
    private static String getUsername(){
        return Config.get("DATABASE_USERNAME");
    }
    
    private static String getPassword(){
        return Config.get("DATABASE_PASSWORD");
    }
    
    public static Database getConnection(){
        Database ret = new Database();
        ret.open();
        return ret;
    }
    
    public java.sql.Connection getConnectionRaw(){
        return conn;
    }
    
    
    public static PreparedStatement prepareStatement(String sql){
        try{
            Database conn = getConnection();
            return new PreparedStatement(conn, conn.conn.prepareStatement(sql));
        }catch(java.sql.SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public static CallableStatement prepareCall(String sql){
        try{
            Database conn = getConnection();
            return new CallableStatement(conn, conn.conn.prepareCall(sql));
        }catch(java.sql.SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void open() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Properties props = new Properties();
            props.setProperty("user", getUsername());
            props.setProperty("password", getPassword());
            props.setProperty("noAccessToProcedureBodies", "true");
            props.setProperty("allowMultiQueries", "true");
            conn = java.sql.DriverManager.getConnection(getConnectionString(), props);
        }catch(ClassNotFoundException | SQLException ex){
            conn = null;
            ex.printStackTrace();
            Util.showError(null, "Fatal Error", "Tidak dapat menyambung ke database.\nDetail:\n" + ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }catch(java.sql.SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }finally{
            conn = null;
        }
    }
    
    
}

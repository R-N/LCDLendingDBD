/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system;

import dbd_lcd.PopupRunnable;
import java.sql.Timestamp;
import dbd_lcd.Util;
import dbd_lcd.database.CallableStatement;
import dbd_lcd.database.Database;
import dbd_lcd.system.structs.SesiAdmin;

/**
 *
 * @author Lenovo2
 */
public class LoginModule {
    private static SesiAdmin sesiAdmin = null;
    
    private static final String SALT = "Petugas";
    
    public static boolean cekSesi(){
        if(sesiAdmin == null) return false;
        try{
            return sesiAdmin.cekSesi() != null;
        }catch(Exception ex){
            return false;
        }
    }
    
    public static String saltPassword(String password){
        return Util.md5(password + SALT);
    }
    
    public static String login(String username, String password){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL LOGIN_PETUGAS(?, ?, ?, ?, ?)"
        )){
            stmt.setString(1, username);
            stmt.setString(2, saltPassword(password));
            
            stmt.registerOutParameter(3, java.sql.Types.CHAR);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(5, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            SesiAdmin sesi = new SesiAdmin(
                    stmt.getString(3),
                    stmt.getString(4),
                    stmt.getDateTime(5)
            );
            
            LoginModule.sesiAdmin = sesi;
            
            return sesi.getNama();
        }
        
    }
    public static void setTimeout(Timestamp timeout){
        sesiAdmin.setTimeout(timeout);
    }
    public static String getSesi(){
        if(sesiAdmin == null) return null;
        return sesiAdmin.getSesi();
    }
    public static Timestamp getTimeout(){
        if(sesiAdmin == null) return null;
        return sesiAdmin.getTimeout();
    }
    public static String getNama(){
        if(sesiAdmin == null) return null;
        return sesiAdmin.getNama();
    }
}

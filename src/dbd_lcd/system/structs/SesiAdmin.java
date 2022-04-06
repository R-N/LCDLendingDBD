/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs;

import dbd_lcd.Util;
import dbd_lcd.database.CallableStatement;
import dbd_lcd.database.Database;
import java.sql.Timestamp;

/**
 *
 * @author Lenovo2
 */
public class SesiAdmin {
    private String nama;
    private String sesi;
    private Timestamp timeout;
    
    public SesiAdmin(String nama, String sesi, Timestamp timeout){
        this.nama = nama;
        this.sesi = sesi;
        this.timeout=timeout;
        
        pingSesi();
    }
    
    private Timestamp pingSesi(){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL PING_SESI(?, ?, ?)"
        )){
            
            stmt.setString(1, sesi);
            
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(3, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            setTimeout(stmt.getDateTime(3));
        }
        return timeout;
    }
    public Timestamp cekSesi(){
        if(timeout.before(Util.now())) return null;
        try{
            return pingSesi();
        }catch(RuntimeException ex){
            return null;
        }
    }
    public String getSesi(){
        return sesi;
    }
    public Timestamp getTimeout(){
        return timeout;
    }
    public void setTimeout(Timestamp timeout){
        this.timeout = timeout;
    }
    public String getNama(){
        return nama;
    }
}

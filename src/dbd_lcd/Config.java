/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd;

import java.util.HashMap;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author user
 */
public class Config {
    private static Properties config = new Properties();
    
    private static final String CONFIG_FILE_PATH = "./config.properties";
    
    public static void init(){
        config.put("CONNECTION_STRING", "jdbc:mysql://%s/%s");
        config.put("DATABASE_SERVER", "localhost");
        config.put("DATABASE_NAME", "lcddbd");
        config.put("DATABASE_USERNAME", "client");
        config.put("DATABASE_PASSWORD", "asdfasdf");
        config.put("TIME_FORMAT", "HH:mm");
        config.put("DATE_FORMAT", "dd/MM/yyyy");
        config.put("DATETIME_FORMAT", "dd/MM/yyyy HH:mm");
        config.put("YES", "Ya");
        config.put("NO", "Tidak");
        config.put("FIRST_RUN", "0");
        load();
    }
    
    public static String get(String property){
        return (String)config.get(property);
    }
    
    public static void set(String property, String value){
        config.put(property, value);
        save();
    }
    
    public static void save(){
        try{
            config.store(new FileOutputStream(CONFIG_FILE_PATH), "Config Program Peminjaman LCD");
        }catch(IOException ex){
            Util.handleError(null, ex);
        }
    }
    
    public static void load(){
        try{
            config.load(new FileInputStream(CONFIG_FILE_PATH));
            System.out.println("Run dir: " + System.getProperty("user.dir"));
            System.out.println(config);
        }catch(IOException ex){
            ex.printStackTrace();
            Util.showNotification(null, "Not found", "Config file not found. Creating one...");
        }finally{
            save();
        }
    }
}

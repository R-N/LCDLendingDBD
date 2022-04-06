/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd;
import dbd_lcd.database.Database;
import dbd_lcd.gui.MyFrame;
import dbd_lcd.report.MyJasperViewer;
import dbd_lcd.system.structs.Barang;
import dbd_lcd.system.structs.IdBarang;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Lenovo2
 */
public class Util {
    public static String[] MONTH = new String[]{
        "Januari", 
        "Februari", 
        "Maret", 
        "April", 
        "Mei", 
        "Juni", 
        "Juli", 
        "Agustus", 
        "September", 
        "Oktober", 
        "November", 
        "Desember"
    };
    public static class Color{
        public static final java.awt.Color GREEN_LIGHT = new java.awt.Color (150, 255, 150);
        public static final java.awt.Color RED_LIGHT = new java.awt.Color (255, 150, 150);
        public static final java.awt.Color YELLOW_LIGHT = new java.awt.Color (255, 255, 150);
        public static final java.awt.Color WHITE_LIGHT = new java.awt.Color (255, 255, 255);
        
        public static final java.awt.Color GREEN_DARK = new java.awt.Color (100, 180, 100);
        public static final java.awt.Color RED_DARK = new java.awt.Color (255, 100, 100);
        public static final java.awt.Color YELLOW_DARK = new java.awt.Color (155, 155, 100);
        public static final java.awt.Color WHITE_DARK = new java.awt.Color (100, 100, 100);
    }
    public static class ColorPack{
        public static final ColorPack GREEN = new ColorPack(Color.GREEN_LIGHT, Color.GREEN_DARK);
        public static final ColorPack RED = new ColorPack(Color.RED_LIGHT, Color.RED_DARK);
        public static final ColorPack YELLOW = new ColorPack(Color.YELLOW_LIGHT, Color.YELLOW_DARK);
        public static final ColorPack WHITE = new ColorPack(Color.WHITE_LIGHT, Color.WHITE_DARK);
        
        public ColorPack(java.awt.Color light, java.awt.Color dark){
            this.light = light;
            this.dark = dark;
        }
        
        public java.awt.Color light;
        public java.awt.Color dark;
        
        public java.awt.Color get(boolean selected){
            if(selected) return dark;
            else return light;
        }
    }
    public static SimpleDateFormat getTimeFormat(){
        return new SimpleDateFormat(Config.get("TIME_FORMAT"));
    }
    public static SimpleDateFormat getDateFormat(){
        return new SimpleDateFormat(Config.get("DATE_FORMAT"));
    }
    public static SimpleDateFormat getDateTimeFormat(){
        return new SimpleDateFormat(Config.get("DATETIME_FORMAT"));
    }
    public static String[] getBooleanCBString(){
        return new String[]{"", Config.get("YES"), Config.get("NO")};
    }
    public static void showError(MyFrame context, String title, String text){
        JOptionPane.showMessageDialog(context, text, title, JOptionPane.ERROR_MESSAGE);
    }
    public static void showNotification(MyFrame context, String title, String text){
        JOptionPane.showMessageDialog(context, text, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static String md5(String source){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(StandardCharsets.UTF_8.encode(source));
            md.update(source.getBytes());
            byte[] digest = md.digest();
            String myHash = String.format("%032x", new BigInteger(1, digest));
            return myHash;
        }catch(NoSuchAlgorithmException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public static Timestamp now(){
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new Timestamp(calendar.getTimeInMillis());
        return currentTimestamp;
    }
    public static Timestamp addMinute(Timestamp time, int minute){
        return new Timestamp(time.getTime() + (1000 * 60 * minute));
    }
    public static boolean isNullOrEmpty(String s){
        return s == null || s.trim().isEmpty();
    }
    public static String preparePhoneNumber(String number){
        if(isNullOrEmpty(number)) return null;
        return number.replace("\\-", "").replace("\\+62", "").replace("\\)", "").replace("\\(", "").replace(" ", "").trim();
    }
    public static String prepareFilter(String s){
        if(isNullOrEmpty(s)) return null;
        return String.format("%%%s%%", s);
    }
    public static String nullify(String s){
        if(isNullOrEmpty(s)) return null;
        return s.trim();
    }
    public static void handleError(MyFrame context, Exception exception){
        exception.printStackTrace();
        showError(context, "Error", exception.getLocalizedMessage());
    }
    public static void handleException(MyFrame context, Exception exception){
        handleError(context, exception);
    }
    public static String formatDate(Date datetime){
        if(datetime == null) return "";
        return new SimpleDateFormat("dd/MM/yyyy").format(datetime);
    }
    public static <T> boolean contains (T[] arr, T val){
        return Arrays.asList(arr).contains(val);
    }
    public static String formatTime(Date datetime){
        if(datetime == null) return "";
        return getTimeFormat().format(datetime);
    }
    public static String formatDateTime(Date datetime){
        if(datetime == null) return "";
        return getDateTimeFormat().format(datetime);
    }
    public static Timestamp parseDateTime(String datetime){
        if(datetime == null) return null;
        try{
            Date parsedDateTime = getDateTimeFormat().parse(datetime.trim());
            Timestamp timestamp = new java.sql.Timestamp(parsedDateTime.getTime());
            return timestamp;
        }catch(ParseException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    public static Timestamp parseTime(String time){
        if(time == null) return null;
        try{
            return Util.parseDateTime(time);
        }catch(Exception ex){
            return parseDateTime(formatDate(now()) + " " + time.trim());
        }
    }
    public boolean inRange(int x, int min, int max){
        return min <= x && x <= max;
    }
    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }
    public static String stringify(String s){
        if(s == null) return "";
        return s.trim();
    }
    public static String stringify(Integer i){
        if(i == null) return "";
        return i.toString();
    }
    public static String stringify(Boolean b){
        if(b == null) return "";
        return b ? Config.get("YES") : Config.get("NO");
    }
    public static Boolean booleanify(String b){
        if(isNullOrEmpty(b)) return null;
        b = b.trim();
        if(Config.get("YES").equalsIgnoreCase(b)) return true;
        if(Config.get("NO").equalsIgnoreCase(b)) return false;
        if(Boolean.TRUE.toString().equalsIgnoreCase(b)) return true;
        if(Boolean.FALSE.toString().equalsIgnoreCase(b)) return false;
        throw new RuntimeException("Invalid string");
    }
    public static Integer integerify(String s){
       if(isNullOrEmpty(s)) return null;
       return Integer.valueOf(s.trim());
    }
    
    public static Barang readBarang(JTable table, int i){
        String tipeString = Util.nullify((String)table.getValueAt(i, 0));
        String tipe = null;
        try{
            tipe = Barang.Tipe.FROM_STRING.get(tipeString);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(String.format("Barang ke-%d tidak valid", i+1));
        }
        if(tipe == null) throw new RuntimeException(String.format("Barang ke-%d tidak valid", i+1));
        int nomor = -1;
        try{
            nomor = (Integer)table.getValueAt(i, 1);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(String.format("Barang ke-%d tidak valid", i+1));
        }
        IdBarang id = new IdBarang(nomor, tipe);
        Barang b = new Barang(id, Barang.Status.DIPINJAM, null, null);
        return b;
    }
    public static boolean equals(Object a, Object b){
        return a == b || (a != null && a.equals(b));
    }
    public static Object determineReturnDateClass(Object value, boolean cellEditable){
        if(cellEditable){
            if(value instanceof Date){
                return Boolean.TRUE;
            }else if (value == null){
                return Boolean.FALSE;
            }
        }else{
            if(value instanceof Date){
                return Util.formatDateTime((Date)value);
            }
        }
        return (Boolean)value;
    }
    public static String readSQL(String name){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Util.class.getResourceAsStream(
                                String.format("/dbd_lcd/database/sql/%s.sql", name)
                        )
                )
        );
        StringBuffer fileData = new StringBuffer();
        char[] buf = new char[1024];
        int numRead=0;
        try{
            while((numRead=reader.read(buf)) != -1){
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
        String file = fileData.toString();
        
        return file;
    }
    
    public static String[] parseSQL(String name){
        String file = readSQL(name);
        
        String line;
        StringBuffer fileData = new StringBuffer();
        List<String> statements = new LinkedList<String>();
        String[] byDelimiters = file.split("DELIMITER ");
        boolean first = true;
        String delimiter = ";";
        for(String s : byDelimiters){
            if(!first){
                String[] s1 = s.split("\n", 1);
                String del = s1[0].trim();
                if(del.equals(";")){
                    
                }else{
                    s = s1[1];
                }
            }
        }
        return statements.toArray(new String[0]);
    }
    
    public static MyJasperViewer viewReport(String reportName, Map<String, Object> parameters){
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    ClassLoader.class.getResourceAsStream(
                            String.format("/dbd_lcd/report/jrxml/%s.jrxml", reportName)
                    )
            );

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, 
                    parameters, 
                    Database.getConnection().getConnectionRaw()
            );
            return new MyJasperViewer(jasperPrint);
        }catch(JRException ex){
            ex.printStackTrace();
            
            throw new RuntimeException(ex.getMessage());
        }
    }
}

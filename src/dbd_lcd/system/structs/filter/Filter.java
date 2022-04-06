/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs.filter;

import dbd_lcd.database.PreparedStatement;
import java.util.List;

/**
 *
 * @author user
 */
public abstract class Filter {
    public Integer limit;
    public Integer offset;
    
    public Filter(){}
    public Filter(Filter f){
        copyFrom(f);
    }
    
    public String getLimitOffset(){
        String ret = "";
        if(limit != null) ret += String.format(" LIMIT %d ", limit);
        if(offset != null) ret += String.format(" OFFSET %d ", offset);
        return ret;
    }
    
    public void copyFrom(Filter f){
        this.limit = f.limit;
        this.offset = f.offset;
    }
    
    public abstract boolean isWhereEmpty();
    public abstract void addWhereClause(List<String> where);
    public abstract int applyWhereClause(PreparedStatement stmt, int i);
    
    public boolean isPagingEmpty(){
        return limit == null && offset == null;
    }
    
    public static boolean isNullOrWhereEmpty(Filter f){
        return f == null || f.isWhereEmpty();
    }
    public static boolean isNullOrEmpty(Filter f){
        return f == null || (f.isWhereEmpty() && f.isPagingEmpty());
    }
    public static boolean isNullOrPagingEmpty(Filter f){
        return f == null || f.isPagingEmpty();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs.filter;

import dbd_lcd.Util;
import dbd_lcd.database.PreparedStatement;
import dbd_lcd.system.structs.RangeWaktu;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Lenovo2
 */
public class FilterPeminjaman extends Filter {
    
    public String status;
    public FilterPeminjam peminjam = new FilterPeminjam();
    public FilterBarang barang = new FilterBarang();
    public FilterKeperluanPinjam keperluan = new FilterKeperluanPinjam();
    public String keterangan;
    public RangeWaktu rangeWaktu;
    public Boolean terlambat;
    
    public FilterPeminjaman(){}
    public FilterPeminjaman(FilterPeminjaman filter){
        super(filter);
        copyFrom(filter);
    }
    
    public void copyFrom(Filter f){
        if(f instanceof FilterPeminjaman){
            copyFrom((FilterPeminjaman)f);
        }else{
            super.copyFrom(f);
        }
    }
    
    public void copyFrom(FilterPeminjaman f){
        super.copyFrom(f);
        this.status = f.status;
        this.barang = new FilterBarang(f.barang);
        this.peminjam = new FilterPeminjam(f.peminjam);
        this.keperluan = new FilterKeperluanPinjam(f.keperluan);
        this.keterangan = f.keterangan;
        this.rangeWaktu = f.rangeWaktu;
        this.terlambat = f.terlambat;
    }
    
    
    @Override
    public void addWhereClause(List<String> where){
        if(!Util.isNullOrEmpty(status)) where.add("Status_Peminjaman=?");
        if(!Filter.isNullOrWhereEmpty(barang)) where.add(barang.getSubquery());
        if(!Filter.isNullOrWhereEmpty(peminjam)) where.add(peminjam.getSubquery());
        if(!Filter.isNullOrWhereEmpty(keperluan)) where.add(keperluan.getSubquery());
        if(!Util.isNullOrEmpty(keterangan)) where.add("Keterangan_Peminjaman LIKE ?");
        if(!RangeWaktu.isNullOrEmpty(rangeWaktu)){
            rangeWaktu.cekValid();
            if(rangeWaktu.awal != null){
                where.add(
                        "(P.Waktu_Pinjam > ? "
                                + " OR (P.Waktu_Kembali IS NULL AND P.Waktu_Harus_Kembali > ?) "
                                + " OR (P.Waktu_Kembali IS NOT NULL AND P.Waktu_Kembali > ?) "
                );
            }
            if(rangeWaktu.akhir != null){
                where.add(
                        "(P.Waktu_Pinjam < ? "
                                + " OR (P.Waktu_Kembali IS NULL AND P.Waktu_Harus_Kembali < ?) "
                                + " OR (P.Waktu_Kembali IS NOT NULL AND P.Waktu_Kembali < ?) "
                );
            }
            
        }
        if(terlambat != null){
            String s = "((Waktu_Kembali IS NULL AND Waktu_Harus_Kembali < NOW()) OR (Waktu_Kembali IS NOT NULL AND Waktu_Harus_Kembali < Waktu_Kembali))";
            if(terlambat == true){
                where.add(s);
            }else{
                where.add("NOT " + s);
            }
        }
        
    }

    @Override
    public int applyWhereClause(PreparedStatement stmt, int i) {
        if(!Util.isNullOrEmpty(status)) stmt.setString(++i, status);
        if(!Filter.isNullOrWhereEmpty(barang)) i = barang.applyWhereClause(stmt, i);
        if(!Filter.isNullOrWhereEmpty(peminjam)) i = peminjam.applyWhereClause(stmt, i);
        if(!Filter.isNullOrWhereEmpty(keperluan)) i = keperluan.applyWhereClause(stmt, i);
        if(!Util.isNullOrEmpty(keterangan)) stmt.setString(++i, Util.prepareFilter(keterangan));
        if(!RangeWaktu.isNullOrEmpty(rangeWaktu)){
            if(!rangeWaktu.isValid()){
                throw new RuntimeException("Range waktu tidak valid");
            }
            for(int j = 0; j < 3; ++j){
                stmt.setDateTime(++i, rangeWaktu.awal);
            }
            for(int j = 0; j < 3; ++j){
                stmt.setDateTime(++i, rangeWaktu.akhir);
            }
        }
        return i;
    }

    @Override
    public boolean isWhereEmpty() {
        return Util.isNullOrEmpty(status)
                && Filter.isNullOrWhereEmpty(barang)
                && Filter.isNullOrWhereEmpty(peminjam)
                && Filter.isNullOrWhereEmpty(keperluan)
                && RangeWaktu.isNullOrEmpty(rangeWaktu)
                && terlambat == null;
    }
}

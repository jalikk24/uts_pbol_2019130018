/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uts_pbol_2019130018.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uts_pbol_2019130018.Koneksi;
import uts_pbol_2019130018.Model.ModelKecamatan;
import uts_pbol_2019130018.Model.ModelKelurahan;
import uts_pbol_2019130018.Model.ModelKotaKabs;
import uts_pbol_2019130018.Model.ModelProvinsi;

/**
 *
 * @author rijalkhalik
 */
public class ControllerRegion {
 
    public ObservableList<ModelProvinsi> getProvinsi() {
        try {
            ObservableList<ModelProvinsi> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            final String query = "SELECT * FROM provinsi";
            ResultSet rs = con.statement.executeQuery(query);
            int i = 1;
            while (rs.next()) {
                ModelProvinsi model = new ModelProvinsi();
                model.setProvId(rs.getInt("prov_id"));
                model.setProvName(rs.getString("prov_name"));
                tableData.add(model);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ObservableList<ModelKotaKabs> getKotaKabs(final int provId) {
        try {
            ObservableList<ModelKotaKabs> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            final String query = "SELECT * FROM kotaKabs WHERE prov_id = '" + provId + "'";
            ResultSet rs = con.statement.executeQuery(query);
            int i = 1;
            while (rs.next()) {
                ModelKotaKabs model = new ModelKotaKabs();
                model.setCityId(rs.getInt("city_id"));
                model.setCityName(rs.getString("city_name"));
                model.setProvId(rs.getInt("prov_id"));
                tableData.add(model);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ObservableList<ModelKecamatan> getKecamatan(final int cityId) {
        try {
            ObservableList<ModelKecamatan> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            final String query = "SELECT * FROM kecamatan WHERE city_id = '" + cityId + "'";
            ResultSet rs = con.statement.executeQuery(query);
            int i = 1;
            while (rs.next()) {
                ModelKecamatan model = new ModelKecamatan();
                model.setKecId(rs.getInt("dis_id"));
                model.setKecName(rs.getString("dis_name"));
                model.setCityId(rs.getInt("city_id"));
                tableData.add(model);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ObservableList<ModelKelurahan> getKelurahan(final int kecId) {
        try {
            ObservableList<ModelKelurahan> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            final String query = "SELECT * FROM kelurahan WHERE dis_id = '" + kecId + "'";
            ResultSet rs = con.statement.executeQuery(query);
            int i = 1;
            while (rs.next()) {
                ModelKelurahan model = new ModelKelurahan();
                model.setKelId(rs.getInt("subdis_id"));
                model.setKelName(rs.getString("subdis_name"));
                tableData.add(model);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }
    
}

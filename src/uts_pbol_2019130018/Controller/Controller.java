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
import uts_pbol_2019130018.Model.ModelCiri;

/**
 *
 * @author rijalkhalik
 */
public class Controller {

    public boolean insertCiri(final ModelCiri model) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            final String query = "INSERT INTO ciri_ciri "
                    + "(nama, gender, warna_kulit, postur, suara, berkacamata,"
                    + "idProv, idKotaKabs, idKecamatan, idKelurahan) VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?)";
            con.preparedStatement = con.dbKoneksi.prepareStatement(query);
            con.preparedStatement.setString(1, model.getNama());
            con.preparedStatement.setString(2, model.getGender());
            con.preparedStatement.setString(3, model.getWarnaKulit());
            con.preparedStatement.setString(4, model.getPostur());
            con.preparedStatement.setString(5, model.getSuara());
            con.preparedStatement.setString(6, model.getKacamata());
            con.preparedStatement.setInt(7, model.getIdProv());
            con.preparedStatement.setInt(8, model.getIdKotaKans());
            con.preparedStatement.setInt(9, model.getIdKec());
            con.preparedStatement.setInt(10, model.getIdKel());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public ObservableList<ModelCiri> getListCiriFilter(final String name) {
        try {
            ObservableList<ModelCiri> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            final String query = "SELECT * FROM ciri_ciri INNER JOIN provinsi ON ciri_ciri.idProv = provinsi.prov_id " +
                    "INNER JOIN kotaKabs ON ciri_ciri.idKotaKabs = kotaKabs.city_id " +
                    "INNER JOIN kecamatan ON ciri_ciri.idKecamatan = kecamatan.dis_id " +
                    "INNER JOIN kelurahan ON ciri_ciri.idKelurahan = kelurahan.subdis_id"
                    + "WHERE nama = '" + name + "'";
            ResultSet rs = con.statement.executeQuery(query);
            int i = 1;
            while (rs.next()) {
                ModelCiri model = new ModelCiri();
                model.setId(rs.getInt(i));
                model.setNama(rs.getString("nama"));
                model.setGender(rs.getString("gender"));
                model.setWarnaKulit(rs.getString("warna_kulit"));
                model.setPostur(rs.getString("postur"));
                model.setSuara(rs.getString("suara"));
                model.setKacamata(rs.getString("berkacamata"));
                model.setProvinsi(rs.getString("prov_name"));
                model.setKotaKabs(rs.getString("city_name"));
                model.setKecamatan(rs.getString("dis_name"));
                model.setKelurahan(rs.getString("subdis_name"));
                tableData.add(model);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }

    private String filtering(final int filterType, final String filter) {
        String result = "";

        switch (filterType) {
            case 0:
                result = "SELECT * FROM ciri_ciri WHERE gender = '" + filter + "'";
                break;
            case 1:
                result = "SELECT * FROM ciri_ciri WHERE warna_kulit = '" + filter + "'";
                break;
            case 2:
                result = "SELECT * FROM ciri_ciri WHERE postur = '" + filter + "'";
                break;
            case 3:
                result = "SELECT * FROM ciri_ciri WHERE suara = '" + filter + "'";
                break;
            case 4:
                result = "SELECT * FROM ciri_ciri WHERE berkacamata = '" + filter + "'";
                break;
        }

        return result;
    }

    public ObservableList<ModelCiri> getListCiri() {
        try {
            ObservableList<ModelCiri> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            final String query = "SELECT * FROM ciri_ciri INNER JOIN provinsi ON ciri_ciri.idProv = provinsi.prov_id " +
                    "INNER JOIN kotaKabs ON ciri_ciri.idKotaKabs = kotaKabs.city_id " +
                    "INNER JOIN kecamatan ON ciri_ciri.idKecamatan = kecamatan.dis_id " +
                    "INNER JOIN kelurahan ON ciri_ciri.idKelurahan = kelurahan.subdis_id";
            ResultSet rs = con.statement.executeQuery(query);
            int i = 1;
            while (rs.next()) {
                ModelCiri model = new ModelCiri();
                model.setId(rs.getInt("id"));
                model.setNama(rs.getString("nama"));
                model.setGender(rs.getString("gender"));
                model.setWarnaKulit(rs.getString("warna_kulit"));
                model.setPostur(rs.getString("postur"));
                model.setSuara(rs.getString("suara"));
                model.setKacamata(rs.getString("berkacamata"));
                model.setProvinsi(rs.getString("prov_name"));
                model.setKotaKabs(rs.getString("city_name"));
                model.setKecamatan(rs.getString("dis_name"));
                model.setKelurahan(rs.getString("subdis_name"));
                tableData.add(model);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ObservableList<ModelCiri> searchUser (final String nama) {
        try {
            ObservableList<ModelCiri> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            final String query = "SELECT * FROM ciri_ciri INNER JOIN provinsi ON ciri_ciri.idProv = provinsi.prov_id " +
                    "INNER JOIN kotaKabs ON ciri_ciri.idKotaKabs = kotaKabs.city_id " +
                    "INNER JOIN kecamatan ON ciri_ciri.idKecamatan = kecamatan.dis_id " +
                    "INNER JOIN kelurahan ON ciri_ciri.idKelurahan = kelurahan.subdis_id "+ 
                    "WHERE nama LIKE '%" + nama + "%'";
            ResultSet rs = (ResultSet) con.statement.executeQuery(query);
            int i = 1;
            while (rs.next()) {
                ModelCiri modelCiri = new ModelCiri();
                modelCiri.setNama(rs.getString("nama"));
                modelCiri.setGender(rs.getString("gender"));
                modelCiri.setWarnaKulit(rs.getString("warna_kulit"));
                modelCiri.setPostur(rs.getString("postur"));
                modelCiri.setSuara(rs.getString("suara"));
                modelCiri.setKacamata(rs.getString("berkacamata"));
                modelCiri.setProvinsi(rs.getString("prov_name"));
                modelCiri.setKotaKabs(rs.getString("city_name"));
                modelCiri.setKecamatan(rs.getString("dis_name"));
                modelCiri.setKelurahan(rs.getString("subdis_name"));
                tableData.add(modelCiri);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean update(final ModelCiri model) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            final String query = "UPDATE ciri_ciri set nama = ?, gender = ?,"
                    + "warna_kulit = ?, postur = ?, suara = ?, berkacamata = ?"
                    + "WHERE id = ?";
            con.preparedStatement = con.dbKoneksi.prepareStatement(query);
            con.preparedStatement.setString(1, model.getNama());
            con.preparedStatement.setString(2, model.getGender());
            con.preparedStatement.setString(3, model.getWarnaKulit());
            con.preparedStatement.setString(4, model.getPostur());
            con.preparedStatement.setString(5, model.getSuara());
            con.preparedStatement.setString(6, model.getKacamata());
            con.preparedStatement.setInt(7, model.getId());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (SQLException e) {
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean delete(final int id) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            final String query = "DELETE FROM ciri_ciri WHERE id = ?";
            con.preparedStatement = con.dbKoneksi.prepareStatement(query);
            con.preparedStatement.setInt(1, id);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (SQLException e) {
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

}

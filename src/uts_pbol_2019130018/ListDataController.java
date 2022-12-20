/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_pbol_2019130018;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import uts_pbol_2019130018.Controller.Controller;
import uts_pbol_2019130018.Controller.ControllerRegion;
import uts_pbol_2019130018.Helper.MyMessage;
import uts_pbol_2019130018.Model.ModelCiri;
import uts_pbol_2019130018.Model.ModelKecamatan;
import uts_pbol_2019130018.Model.ModelKelurahan;
import uts_pbol_2019130018.Model.ModelKotaKabs;
import uts_pbol_2019130018.Model.ModelProvinsi;

/**
 * FXML Controller class
 *
 * @author rijalkhalik
 */
public class ListDataController implements Initializable {

    @FXML
    private TableView<ModelCiri> tblData;

    private List<String> listFilter = new ArrayList<>();
    private List<String> listCatFilter = new ArrayList<>();
    private List<ModelCiri> listData = new ArrayList<>();
    private final String titleFilter = "Pilih Kategori";
    private final String titleSubFilter = "Pilih Sub Kategori";
    private int selectedCatFiltIndex = 0;
    @FXML
    private TextField edtSearch;
    @FXML
    private ComboBox<String> cmbGender;
    @FXML
    private ComboBox<String> cmbKulit;
    @FXML
    private ComboBox<String> cmbPostur;
    @FXML
    private ComboBox<String> cmbSuara;
    @FXML
    private ComboBox<String> cmKacamata;
    @FXML
    private ComboBox<String> comboProv;
    @FXML
    private ComboBox<String> comboKotaKabs;
    @FXML
    private ComboBox<String> comboKec;
    @FXML
    private ComboBox<String> comboKel;
    @FXML
    private Button btnSearch;

    private List<ModelProvinsi> listProv = new ArrayList<>();
    private List<ModelKotaKabs> listKota = new ArrayList<>();
    private List<ModelKecamatan> listKec = new ArrayList<>();
    private List<ModelKelurahan> listKel = new ArrayList<>();

    private int idItem = 0;
    private String selectedGender;
    private String selectedKulit;
    private String selectedPostur;
    private String selectedSuara;
    private String selectedKacamata;
    private String selectedProv;
    private String selectedKotaKabs;
    private String selectedKec;
    private String selectedKel;

    private final String titleGender = "Pilih Gender";
    private final String titleKulit = "Pilih Warna Kulit";
    private final String titlePostur = "Pilih Postur Badan";
    private final String titleSuara = "Pilih Jenis Suara";
    private final String titleKacamata = "Apakah Berkacamata ?";

    private final String titleProv = "Pilih Provinsi";
    private final String titleKotaKabs = "Pilih Kota/Kabupaten";
    private final String titleKec = "Pilih Kecamatan";
    private final String titleKel = "Pilih Kelurahan";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupCombobox();
        getListData();
        getProvinsi();
    }

    @FXML
    private void onSearchTextChange(KeyEvent event) {
        if (!edtSearch.getText().isEmpty()) {
            Controller cont = new Controller();
            ObservableList<ModelCiri> data = cont.searchUser(edtSearch.getText());
            listData = data;

            if (data != null) {
                tblData.getColumns().clear();
                tblData.getItems().clear();

                TableColumn col = new TableColumn("Nama");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("nama"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Gender");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("gender"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Warna Kulit");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("warnaKulit"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Postur");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("postur"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Suara");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("suara"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Berkacamata");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kacamata"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Provinsi");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("provinsi"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Kota / Kabupaten");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kotaKabs"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Kecamatan");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kecamatan"));
                tblData.getColumns().addAll(col);

                col = new TableColumn("Kelurahan");
                col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kelurahan"));
                tblData.getColumns().addAll(col);

                tblData.setItems(data);

            }
        } else {
            getListData();
        }
    }

    @FXML
    private void selecProv(ActionEvent event) {
        listKota.clear();
        comboKotaKabs.getItems().clear();

        listKec.clear();
        comboKec.getItems().clear();

        listKel.clear();
        comboKel.getItems().clear();

        selectedProv = comboProv.getSelectionModel().getSelectedItem();

        getKota(listProv.get(comboProv.getSelectionModel().getSelectedIndex()).getProvId());
    }

    @FXML
    private void selectKotaKabs(ActionEvent event) {
        listKec.clear();
        comboKec.getItems().clear();

        listKel.clear();
        comboKel.getItems().clear();

        selectedKotaKabs = comboKotaKabs.getSelectionModel().getSelectedItem();

        getKecamatan(listKota.get(comboKotaKabs.getSelectionModel().getSelectedIndex()).getCityId());
    }

    @FXML
    private void selectKecamatan(ActionEvent event) {
        listKel.clear();
        comboKel.getItems().clear();

        selectedKec = comboKec.getSelectionModel().getSelectedItem();

        getKelurahan(listKec.get(comboKec.getSelectionModel().getSelectedIndex()).getKecId());
    }

    @FXML
    private void selectKelurahan(ActionEvent event) {
        selectedKel = comboKel.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onSearchClick(ActionEvent event) {
    }

    private void getListData() {
        Controller cont = new Controller();
        listData = cont.getListCiri();
        ObservableList<ModelCiri> data = cont.getListCiri();
        if (data != null) {
            tblData.getColumns().clear();
            tblData.getItems().clear();

            TableColumn col = new TableColumn("Nama");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("nama"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Gender");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("gender"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Warna Kulit");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("warnaKulit"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Postur");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("postur"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Suara");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("suara"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Berkacamata");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kacamata"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Provinsi");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("provinsi"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Kota / Kabupaten");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kotaKabs"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Kecamatan");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kecamatan"));
            tblData.getColumns().addAll(col);

            col = new TableColumn("Kelurahan");
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("kelurahan"));
            tblData.getColumns().addAll(col);

            tblData.setItems(data);

        } else {
            MyMessage.Message(Alert.AlertType.ERROR, "Data kosong");
            tblData.getScene().getWindow().hide();;
        }

        if (listCatFilter.isEmpty()) {
            listCatFilter.add("Gender");
            listCatFilter.add("Warna Kulit");
            listCatFilter.add("Postur");
            listCatFilter.add("Suara");
            listCatFilter.add("Berkacamata");
        }
    }

    private void setupCombobox() {
        cmbGender.getItems().add("Laki-laki");
        cmbGender.getItems().add("Perempuan");
        cmbGender.setValue(titleGender);

        cmbKulit.getItems().add("Hitam");
        cmbKulit.getItems().add("Coklat");
        cmbKulit.getItems().add("Putih");
        cmbKulit.setValue(titleKulit);

        cmbPostur.getItems().add("Tinggi");
        cmbPostur.getItems().add("Pendek");
        cmbPostur.setValue(titlePostur);

        cmbSuara.getItems().add("Tinggi");
        cmbSuara.getItems().add("Berat");
        cmbSuara.setValue(titleSuara);

        cmKacamata.getItems().add("Ya");
        cmKacamata.getItems().add("Tidak");
        cmKacamata.setValue(titleKacamata);

    }

    private void getProvinsi() {
        ControllerRegion controllerRegion = new ControllerRegion();
        listProv = controllerRegion.getProvinsi();
        comboProv.getItems().clear();
        for (int x = 0; x < listProv.size(); x++) {
            comboProv.getItems().add(listProv.get(x).getProvName());
        }
        comboProv.setValue(titleProv);
    }

    private void getKota(final int provId) {
        ControllerRegion controllerRegion = new ControllerRegion();
        listKota = controllerRegion.getKotaKabs(provId);
        comboKotaKabs.getItems().clear();
        for (int x = 0; x < listKota.size(); x++) {
            comboKotaKabs.getItems().add(listKota.get(x).getCityName());
        }
        comboKotaKabs.setValue(titleKotaKabs);
    }

    private void getKecamatan(final int cityId) {
        ControllerRegion controllerRegion = new ControllerRegion();
        listKec = controllerRegion.getKecamatan(cityId);
        comboKec.getItems().clear();
        for (int x = 0; x < listKec.size(); x++) {
            comboKec.getItems().add(listKec.get(x).getKecName());
        }
        comboKec.setValue(titleKec);
    }

    private void getKelurahan(final int kecId) {
        ControllerRegion controllerRegion = new ControllerRegion();
        listKel = controllerRegion.getKelurahan(kecId);
        comboKel.getItems().clear();
        for (int x = 0; x < listKel.size(); x++) {
            comboKel.getItems().add(listKel.get(x).getKelName());
        }
        comboKel.setValue(titleKel);
    }

}

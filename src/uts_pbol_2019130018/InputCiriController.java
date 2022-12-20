/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_pbol_2019130018;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
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
public class InputCiriController implements Initializable {

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
    private Button btnSimpan;
    @FXML
    private Button btnBatal;
    @FXML
    private TextField edtNama;

    @FXML
    private TableView<ModelCiri> tblData;

    @FXML
    private Button btnTambahData;
    @FXML
    private Button btnPerbData;
    @FXML
    private Button btnHapus;

    @FXML
    private ComboBox<String> comboProv;
    @FXML
    private ComboBox<String> comboKotaKabs;
    @FXML
    private ComboBox<String> comboKec;
    @FXML
    private ComboBox<String> comboKel;

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

    private boolean isAdd = false;
    private boolean isUpdate = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupCombobox();
        getListData();
        getProvinsi();
        isAdd();
    }

    @FXML
    private void btnBatalClick(ActionEvent event) {
        clearInput();
    }

    @FXML
    private void btnSimpanClick(ActionEvent event) {

        selectedGender = cmbGender.getValue();
        selectedKulit = cmbKulit.getValue();
        selectedPostur = cmbPostur.getValue();
        selectedSuara = cmbSuara.getValue();
        selectedKacamata = cmKacamata.getValue();

        if (fieldComplete()) {
            ModelCiri model = new ModelCiri();
            model.setId(idItem);
            model.setNama(edtNama.getText());
            model.setGender(selectedGender);
            model.setWarnaKulit(selectedKulit);
            model.setPostur(selectedPostur);
            model.setSuara(selectedSuara);
            model.setKacamata(selectedKacamata);
            model.setIdProv(listProv.get(comboProv.getSelectionModel().getSelectedIndex()).getProvId());
            model.setIdKotaKans(listKota.get(comboKotaKabs.getSelectionModel().getSelectedIndex()).getCityId());
            model.setIdKec(listKec.get(comboKec.getSelectionModel().getSelectedIndex()).getKecId());
            model.setIdKel(listKel.get(comboKel.getSelectionModel().getSelectedIndex()).getKelId());

            Controller cont = new Controller();
            if (isAdd) {
                if (cont.insertCiri(model)) {
                    MyMessage.Message(Alert.AlertType.INFORMATION, "Data berhasil disimpan");
                    getListData();
                    clearInput();
                } else {
                    MyMessage.Message(Alert.AlertType.ERROR, "Data gagal disimpan");
                }
            } else if (isUpdate) {
                if (cont.update(model)) {
                    MyMessage.Message(Alert.AlertType.INFORMATION, "Data berhasil diupdate");
                    getListData();
                    clearInput();
                } else {
                    MyMessage.Message(Alert.AlertType.ERROR, "Data gagaldiupdate");
                }
            }
        } else {
            MyMessage.Message(Alert.AlertType.WARNING, "Data yang anda masukkan tidak valid");
        }
    }
    
    @FXML
    private void btnInsertClick(ActionEvent event) {
        clearInput();
        isAdd();
    }

    @FXML
    private void btnUpdateClick(ActionEvent event) {
        isUpdate();
    }

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        ModelCiri model = tblData.getSelectionModel().getSelectedItem();
        Controller cont = new Controller();
        if (cont.delete(model.getId())) {
            MyMessage.Message(Alert.AlertType.INFORMATION, "Data berhasil dihapus");
            getListData();
            clearInput();
        } else {
            MyMessage.Message(Alert.AlertType.ERROR, "Data gagal dihapus");
        }
    }
    
    @FXML
    private void clickItem(MouseEvent event) {
        ModelCiri model = tblData.getSelectionModel().getSelectedItem();
        idItem = model.getId();
        edtNama.setText(model.getNama());
        cmbGender.setValue(model.getGender());
        cmbKulit.setValue(model.getWarnaKulit());
        cmbPostur.setValue(model.getPostur());
        cmbSuara.setValue(model.getSuara());
        cmKacamata.setValue(model.getKacamata());
        comboProv.setValue(model.getProvinsi());
        comboKotaKabs.setValue(model.getKotaKabs());
        comboKec.setValue(model.getKecamatan());
        comboKel.setValue(model.getKelurahan());

        edtNama.setDisable(true);
        cmbGender.setDisable(true);
        cmbKulit.setDisable(true);
        cmbPostur.setDisable(true);
        cmbSuara.setDisable(true);
        cmKacamata.setDisable(true);
        comboProv.setDisable(true);
        comboKotaKabs.setDisable(true);
        comboKec.setDisable(true);
        comboKel.setDisable(true);

        rowSelectBtnSetup();
    }

    private void clearInput() {
        edtNama.setText("");
        cmbGender.setValue(titleGender);
        cmbKulit.setValue(titleKulit);
        cmbPostur.setValue(titlePostur);
        cmbSuara.setValue(titleSuara);
        cmKacamata.setValue(titleKacamata);
        
        comboProv.setValue(titleProv);
        comboKotaKabs.setValue(null);
        comboKec.setValue(null);
        comboKel.setValue(null);

        selectedGender = titleGender;
        selectedKulit = titleKulit;
        selectedPostur = titlePostur;
        selectedSuara = titleSuara;
        selectedKacamata = titleKacamata;

        clearBtn();
        isAdd();
    }

    private boolean fieldComplete() {
        return selectedProv != null && selectedKotaKabs != null && selectedKec != null && selectedKel != null
                && !selectedGender.equals(titleGender)
                && !selectedKulit.equals(titleKulit)
                && !selectedPostur.equals(titlePostur)
                && !selectedSuara.equals(titleSuara)
                && !selectedKacamata.equals(titleKacamata);
    }

    private void getListData() {
        Controller cont = new Controller();
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
            col.setCellValueFactory(new PropertyValueFactory<ModelCiri, String>("Kelurahan"));
            tblData.getColumns().addAll(col);

            tblData.setItems(data);
        } else {
            MyMessage.Message(Alert.AlertType.ERROR, "Data kosong");
            tblData.getScene().getWindow().hide();
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

    private void isAdd() {
        btnTambahData.setDisable(false);
        btnPerbData.setDisable(true);
        btnHapus.setDisable(true);

        edtNama.setDisable(false);
        cmbGender.setDisable(false);
        cmbKulit.setDisable(false);
        cmbPostur.setDisable(false);
        cmbSuara.setDisable(false);
        cmKacamata.setDisable(false);
        
        comboProv.setDisable(false);
        comboKotaKabs.setDisable(false);
        comboKec.setDisable(false);
        comboKel.setDisable(false);

        isAdd = true;
        isUpdate = false;
    }

    private void isUpdate() {
        btnTambahData.setDisable(true);
        btnPerbData.setDisable(false);
        btnHapus.setDisable(true);

        edtNama.setDisable(false);
        cmbGender.setDisable(false);
        cmbKulit.setDisable(false);
        cmbPostur.setDisable(false);
        cmbSuara.setDisable(false);
        cmKacamata.setDisable(false);
        
        comboProv.setDisable(false);
        comboKotaKabs.setDisable(false);
        comboKec.setDisable(false);
        comboKel.setDisable(false);

        isUpdate = true;
        isAdd = false;
    }

    private void rowSelectBtnSetup() {
        btnTambahData.setDisable(false);
        btnPerbData.setDisable(false);
        btnHapus.setDisable(false);
    }

    private void clearBtn() {
        btnTambahData.setDisable(false);
        btnPerbData.setDisable(true);
        btnHapus.setDisable(true);
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
}

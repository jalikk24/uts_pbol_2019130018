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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import uts_pbol_2019130018.Controller.Controller;
import uts_pbol_2019130018.Helper.MyMessage;
import uts_pbol_2019130018.Model.ModelCiri;

/**
 * FXML Controller class
 *
 * @author rijalkhalik
 */
public class ListDataController implements Initializable {

    @FXML
    private TableView<ModelCiri> tblData;
    @FXML
    private ComboBox<String> comboFilters;
    @FXML
    private ComboBox<String> comboCatFilter;

    private List<String> listFilter = new ArrayList<>();
    private List<String> listCatFilter = new ArrayList<>();
    private List<ModelCiri> listData = new ArrayList<>();
    private final String titleFilter = "Pilih Kategori";
    private final String titleSubFilter = "Pilih Sub Kategori";
    private int selectedCatFiltIndex = 0;
    @FXML
    private TextField edtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getListData();

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

            comboCatFilter.getItems().add(listCatFilter.get(0));
            comboCatFilter.getItems().add(listCatFilter.get(1));
            comboCatFilter.getItems().add(listCatFilter.get(2));
            comboCatFilter.getItems().add(listCatFilter.get(3));
            comboCatFilter.getItems().add(listCatFilter.get(4));
            comboCatFilter.setValue(titleFilter);
            comboFilters.setValue(titleSubFilter);
        }
    }

    @FXML
    private void onFilterSelected(ActionEvent event) {
        String selectedVal = comboFilters.getSelectionModel().getSelectedItem();

        if (selectedVal != null && !selectedVal.equals(titleSubFilter)) {
            Controller cont = new Controller();
            ObservableList<ModelCiri> data = cont.getListCiriFilter(selectedCatFiltIndex, selectedVal);
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

                tblData.setItems(data);

            } else {
                MyMessage.Message(Alert.AlertType.ERROR, "Data kosong");
                tblData.getScene().getWindow().hide();
            }
        } else {
            comboFilters.getItems().clear();
            listFilter.clear();
            comboFilters.setValue(titleSubFilter);
        }
    }

    @FXML
    private void onCatFilterSelected(ActionEvent event) {

        selectedCatFiltIndex = comboCatFilter.getSelectionModel().getSelectedIndex();
        switch (selectedCatFiltIndex) {
            case 0:
                findItem(0);
                break;
            case 1:
                findItem(1);
                break;
            case 2:
                findItem(2);
                break;
            case 3:
                findItem(3);
                break;
            case 4:
                findItem(4);
                break;
        }
    }

    private void findItem(final int category) {
        listFilter.clear();
        for (int x = 0; x < listData.size(); x++) {
            if (listFilter.isEmpty()) {
                findCategoryType(category, x);
            } else {
                findCurrCat(category, x);
            }
        }
    }

    private void findCategoryType(final int category, final int x) {
        switch (category) {
            case 0:
                listFilter.add(listData.get(x).getGender());
                break;
            case 1:
                listFilter.add(listData.get(x).getWarnaKulit());
                break;
            case 2:
                listFilter.add(listData.get(x).getPostur());
                break;
            case 3:
                listFilter.add(listData.get(x).getSuara());
                break;
            case 4:
                listFilter.add(listData.get(x).getKacamata());
                break;
        }
    }

    private void findCurrCat(final int category, final int x) {
        String result = "";
        switch (category) {
            case 0:
                result = listData.get(x).getGender();
                break;
            case 1:
                result = listData.get(x).getWarnaKulit();
                break;
            case 2:
                result = listData.get(x).getPostur();
                break;
            case 3:
                result = listData.get(x).getSuara();
                break;
            case 4:
                result = listData.get(x).getKacamata();
                break;
        }
        boolean isDataExist = false;
        for (int i = 0; i < listFilter.size(); i++) {
            if (result.equalsIgnoreCase(listFilter.get(i))) {
                isDataExist = true;
                break;
            }
        }
        if (!isDataExist) {
            listFilter.add(result);
        }

        setupComboFilter();

    }

    private void setupComboFilter() {
        comboFilters.getItems().clear();
        for (int x = 0; x < listFilter.size(); x++) {
            comboFilters.getItems().add(listFilter.get(x));
        }
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

                tblData.setItems(data);

            }
        } else {
            getListData();
        }
    }

}

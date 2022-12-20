/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uts_pbol_2019130018.Helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author rijalkhalik
 */
public class MyMessage {

    public static void Message(final Alert.AlertType type, final String message) {
        Alert a = new Alert(type, message, ButtonType.OK);
        a.showAndWait();
    }

}

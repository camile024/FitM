package ui.utils;

import java.util.ArrayList;

import data.objects.Customer;
import engine.CONST;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class CustomerOptions implements Callback<TableColumn.CellDataFeatures<Customer, HBox>,ObservableValue<HBox>> {

    @Override
    public ObservableValue<HBox> call(CellDataFeatures<Customer, HBox> param) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);
        ArrayList<Button> btnList = new ArrayList<Button>();
        Button btnInfo = new Button();
        Button btnEdit = new Button();
        Button btnDelete = new Button();
        btnList.add(btnInfo);
        btnList.add(btnDelete);
        btnList.add(btnEdit);
        for (Button b : btnList) {
            b.setMaxWidth(32);
            b.setMaxHeight(32);
            b.setPrefWidth(32);
            b.setPrefHeight(32);
            hbox.getChildren().add(b);
        }
        ImageView imgInfo = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_INFO_FILENAME));
        ImageView imgDelete = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_EDIT_FILENAME));
        ImageView imgEdit = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_DELETE_FILENAME));
        imgInfo.setFitWidth(24);
        imgInfo.setFitHeight(24);
        imgDelete.setFitWidth(24);
        imgDelete.setFitHeight(24);
        imgEdit.setFitWidth(24);
        imgEdit.setFitHeight(24);
        
        btnInfo.setGraphic(imgInfo);
        btnEdit.setGraphic(imgEdit);
        btnDelete.setGraphic(imgDelete);

        return new ReadOnlyObjectWrapper<HBox>(hbox);
    }







}

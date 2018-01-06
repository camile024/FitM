package ui.mains;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import engine.CONST;
import engine.Locale;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UI_Splash {
    @FXML
    Text FXID_VERSION;
    @FXML
    Text FXID_APP_VERSION;
    @FXML
    Text FXID_STATUS_TITLE;
    @FXML
    Text FXID_STATUS;
    @FXML
    Text FXID_DATE;
    @FXML
    ImageView FXID_LOGO;
    @FXML
    GridPane FXID_GRIDPANE_SPLASH;
    
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public void init(Stage self) {
        FXID_APP_VERSION.setText(String.valueOf(CONST.VERSION));
        FXID_VERSION.setText(Locale.getString(CONST.TXT_VERSION));
        FXID_STATUS_TITLE.setText(Locale.getString(CONST.TXT_LOADING_SETTINGS));
        FXID_STATUS.setText(CONST.APP_DIR);
        date = new Date();
        FXID_DATE.setText(dateFormat.format(date).toString());
    }
    
    public void setStatus(String status) {
        FXID_STATUS.setText(Locale.getString(status));
    }
    
    public void setStatusTitle(String title) {
        FXID_STATUS_TITLE.setText(Locale.getString(title));
    }
}

package ui.utils;

import java.util.HashMap;

import engine.CONST;
import javafx.scene.image.Image;

public abstract class ResourceLocalizer {
    private static HashMap<String, Image> images;
    
    public static void loadImages() {
        images = new HashMap<String, Image>();
        images.put(CONST.RES_IMG_LOGO_FILENAME, initImage(CONST.RES_IMG_LOGO_PATH));
        images.put(CONST.RES_IMG_BGGRADIENT_FILENAME, initImage(CONST.RES_IMG_BGGRADIENT_PATH));
        images.put(CONST.RES_IMG_BTN_UK_FILENAME, initImage(CONST.RES_IMG_BTN_UK_PATH));
        images.put(CONST.RES_IMG_BTN_PL_FILENAME, initImage(CONST.RES_IMG_BTN_PL_PATH));
        images.put(CONST.RES_IMG_BTN_INFO_FILENAME, initImage(CONST.RES_IMG_BTN_INFO_PATH));
        images.put(CONST.RES_IMG_BTN_EDIT_FILENAME, initImage(CONST.RES_IMG_BTN_EDIT_PATH));
        images.put(CONST.RES_IMG_BTN_DELETE_FILENAME, initImage(CONST.RES_IMG_BTN_DELETE_PATH));
        
        images.put(CONST.RES_IMG_INFO_FILENAME, initImage(CONST.RES_IMG_INFO_PATH));
        images.put(CONST.RES_IMG_ERROR_FILENAME, initImage(CONST.RES_IMG_ERROR_PATH));
        images.put(CONST.RES_IMG_SUCCESS_FILENAME, initImage(CONST.RES_IMG_SUCCESS_PATH));
        images.put(CONST.RES_IMG_CONFIRM_FILENAME, initImage(CONST.RES_IMG_CONFIRM_PATH));
        images.put(CONST.RES_IMG_PLUS_FILENAME, initImage(CONST.RES_IMG_PLUS_PATH));
        images.put(CONST.RES_IMG_MINUS_FILENAME, initImage(CONST.RES_IMG_MINUS_PATH));
    }
    
    
    public static Image getImage(String key) {
        return images.get(key);
    }
    
    private static Image initImage(String path) {
        return new Image("file:" + path);
    }
    
}

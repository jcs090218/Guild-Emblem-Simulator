package com.aldes.jcs;

import java.io.File;
import java.io.IOException;

import com.aldes.jcsqtj.util.JCSQtJ_FilenameUtils;
import com.aldes.jcsqtj.util.JCSQtJ_Util;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFileDialog;

/**
 * $File: Model.java $
 * $Date: 2017-08-18 13:45:33 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


/**
 * All the function put here...
 */
public class Model {

    private static final String FG_PATH = ProgramMain.EMBLEM_PATH + "Foreground/";
    private static final String BG_PATH = ProgramMain.EMBLEM_PATH + "Background/";

    private int currentFrameIndex = 0;
    private int currentEmblemIndex = 0;
    
    private String currentFramePath = "";
    private String currentEmblemPath = "";
    
    private enum COLOR_TYPE {
        BLACK(16), 
        BROWN(5), 
        DARK_BLUE(11),
        DARK_CYAN(9),
        DARK_GREEN(7),
        DARK_PURPLE(13),
        LIGHT_BLUE(12),
        LIGHT_CYAN(10),
        LIGHT_GREEN(8),
        LIGHT_PURPLE(14),
        OLIVE(6),
        ORANGE(2),
        PEACH(4),
        RED(1),
        WHITE(15),
        YELLOW(3); 
        
        int id = 0;
        
        COLOR_TYPE(int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
        
        public static int getIdByString(String type) {
            // add color id.
               switch (type) {
               case "Black":
                   return COLOR_TYPE.BLACK.getId();
               case "Brown":
                   return COLOR_TYPE.BROWN.getId();
               case "Dark Blue":
                   return COLOR_TYPE.DARK_BLUE.getId();
               case "Dark Cyan":
                   return COLOR_TYPE.DARK_CYAN.getId();
               case "Dark Green":
                   return COLOR_TYPE.DARK_GREEN.getId();
               case "Dark Purple":
                   return COLOR_TYPE.DARK_PURPLE.getId();
               case "Light Blue":
                   return COLOR_TYPE.LIGHT_BLUE.getId();
               case "Light Cyan":
                   return COLOR_TYPE.LIGHT_CYAN.getId();
               case "Light Green":
                   return COLOR_TYPE.LIGHT_GREEN.getId();
               case "Light Purple":
                   return COLOR_TYPE.LIGHT_PURPLE.getId();
               case "Olive":
                   return COLOR_TYPE.OLIVE.getId();
               case "Orange":
                   return COLOR_TYPE.ORANGE.getId();
               case "Peach":
                   return COLOR_TYPE.PEACH.getId();
               case "Red":
                   return  COLOR_TYPE.RED.getId();
               case "White":
                   return COLOR_TYPE.WHITE.getId();
               case "Yellow":
                   return COLOR_TYPE.YELLOW.getId();
               }
               
               return 0;
           }
    }
    
    private enum EMBLEM_TYPE {
        ANIMAL(2),
        ETC(9),
        LETTER(5),
        PATTERN(4),
        PLANT(3),
        
        BACKGROUND(1);
        
        int id = 0;
        
        EMBLEM_TYPE(int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
        
        public static int getIdByString(String type) {
            switch (type) {
            case "BackGround":
                return EMBLEM_TYPE.BACKGROUND.getId();
            case "Animal":
                return EMBLEM_TYPE.ANIMAL.getId();
            case "Etc":
                return EMBLEM_TYPE.ETC.getId();
            case "Letter":
                return EMBLEM_TYPE.LETTER.getId();
            case "Pattern":
                return EMBLEM_TYPE.PATTERN.getId();
            case "Plant":
                return EMBLEM_TYPE.PLANT.getId();
            }
            
            return 0;
        }
        
        public String toString() {
            switch (this.id) {
            case 1:
                return "BackGround";
            case 2:
                return "Animal";
            case 9:
                return "Etc";
            case 5:
                return "Letter";
            case 4:
                return "Pattern";
            case 3:
                return "Plant";
            }
            
            return "";
        }
    }
    
    public Model() {
        // empty..
    }

    public void save() throws IOException {
        String output_file = QFileDialog.getSaveFileName();
        
        JCSQtJ_FilenameUtils.safeAddExtensionPNG(output_file);
        
        // create the image.
        Util.combineImages(output_file, currentFramePath, currentEmblemPath);
    }

    public void prevFrame() {
        --currentFrameIndex;
        
        bgChanged();
    }

    public void nextFrame() {
        ++currentFrameIndex;
        
        bgChanged();
    }

    public void prevEmblem() {
        --currentEmblemIndex;
        
        fgChanged();
    }

    public void nextEmblem() {
        ++currentEmblemIndex;
        
        fgChanged();
    }
    
    /* Emblem type combo box changed. */ 
    public void typecbChanged() {
        String path = getCurrentFGPath();
        
        JCSQtJ_Util.addImageToLabel(ProgramMain.FOREGROUND_IMAGE, path);
        
        currentEmblemIndex = 0;
    }
    
    /* Foreground color combo box changed. */
    public void fgChanged() {
        String path = getCurrentFGPath();
        
        JCSQtJ_Util.addImageToLabel(ProgramMain.FOREGROUND_IMAGE, path);
        
        currentEmblemPath = path;
    }
    
    /* Background color combo box changed. */
    public void bgChanged() {
        String path = getCurrentBGPath();
        
        JCSQtJ_Util.addImageToLabel(ProgramMain.BACKGROUND_IMAGE, path);
        
        currentFramePath = path;
    }

    /**
     * Get the foreground image path base on the current
     * selected bg color and current selected emblem type.
     * 
     * @param { int } val : get full image path by adding up or down the index.
     */
    private String getCurrentFGPath() {
        
        String dir = FG_PATH + 
                ProgramMain.FOREGROUND_TYPE_CB.currentText() + "/" + 
                ProgramMain.FOREGROUND_COLOR_CB.currentText() + "/";
        String path = dir + 
                ProgramMain.FOREGROUND_TYPE_CB.currentText() + 
                ".0000" + 
                EMBLEM_TYPE.getIdByString(ProgramMain.FOREGROUND_TYPE_CB.currentText()) + 
                String.format("%03d", currentEmblemIndex) + "." + 
                COLOR_TYPE.getIdByString(ProgramMain.FOREGROUND_COLOR_CB.currentText()) +
                ".png";
        
        File f = new File(path);
        if(f.exists() && !f.isDirectory()) { 
            return path;
        }
        
        // 檢查上界
        if (currentEmblemIndex > 0) {
            currentEmblemIndex = 0;
            return getCurrentFGPath();
        }
        // 檢查下界
        else {
            currentEmblemIndex = new File(dir).listFiles().length - 1;
            return getCurrentFGPath();
        }
    }

    /**
     * Get the background image path base on the current
     * selected bg color.
     */
    private String getCurrentBGPath() {
        String dir = BG_PATH + 
                ProgramMain.BACKGROUND_COLOR_CB.currentText() + "/";
        String path = dir + 
                EMBLEM_TYPE.BACKGROUND.toString() + 
                ".0000" + 
                EMBLEM_TYPE.BACKGROUND.getId() + 
                String.format("%03d", currentFrameIndex) + "." + 
                COLOR_TYPE.getIdByString(ProgramMain.BACKGROUND_COLOR_CB.currentText()) + 
                ".png";
        
        
        
        File f = new File(path);
        if(f.exists() && !f.isDirectory()) { 
            return path;
        }
        
        // 檢查上界
        if (currentFrameIndex > 0) {
            currentFrameIndex = 0;
            return getCurrentFGPath();
        }
        // 檢查下界
        else {
            currentFrameIndex = new File(dir).listFiles().length - 1;
            return getCurrentBGPath();
        }
    }

    @SuppressWarnings("static-access")
    public void exit() {
        QApplication.instance().quit();
    }

}

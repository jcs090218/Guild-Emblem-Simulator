package com.aldes.jcs;

import com.aldes.jcsqtj.core.JCSQtJ_Window;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QLabel;

/**
 * $File: ProgramMain.java $
 * $Date: 2017-08-18 11:39:36 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class ProgramMain {

    public static JCSQtJ_Window MAIN_WINDOW = null;

    public static final String EMBLEM_PATH = "./res/Guild Emblem Simulator/";

    public static final String SCREEN_TITLE = "Guild Emblem Simulator";
    public static final String ICON_IMAGE_PATH = "./res/icon.ico";

    public static final int SCREEN_WIDTH = 193 + 50;
    public static final int SCREEN_HEIGHT = 258 + 80;

    public static QComboBox BACKGROUND_COLOR_CB = null;

    public static QComboBox FOREGROUND_COLOR_CB = null;
    public static QComboBox FOREGROUND_TYPE_CB = null;

    public static QLabel FOREGROUND_IMAGE = null;
    public static QLabel BACKGROUND_IMAGE = null;

    public static String[] COLOR_LIST = {
        "Black", "Brown", "Dark Blue", "Dark Cyan", "Dark Green",
        "Dark Purple", "Light Blue", "Light Cyan", "Light Green",
        "Light Purple", "Olive", "Orange", "Peach", "Red", "White",
        "Yellow"
    };


    /**
     * Program Entry Point
     *
     * @param { Stirng[] } args : arguments pass in.
     */
    public final static void main(String[] args) {

        MAIN_WINDOW = new JCSQtJ_Window();
        MAIN_WINDOW.startFrame(args);

        MAIN_WINDOW.setWindowTitle(SCREEN_TITLE);
        MAIN_WINDOW.setWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        MAIN_WINDOW.setIconImage(ICON_IMAGE_PATH);

        int firstColumn = 30;
        int secondColumn = 130;

        int firstRow = 100;
        int secondRow = 170;

        int gapWithComboBox = 25;

        int screenCenter = SCREEN_WIDTH / 2 - 34 / 2;
        
        // All functions stored.
        Model model = new Model();

        /* Image Label */
        MAIN_WINDOW.addImageToWindow("./res/img_bg.png",
                                     screenCenter,
                                     35,
                                     34,
                                     34);

        BACKGROUND_IMAGE = MAIN_WINDOW.addImageToWindow(
            EMBLEM_PATH + "Background/Black/BackGround.00001000.16.png",
            screenCenter,
            35,
            34,
            34);

        FOREGROUND_IMAGE = MAIN_WINDOW.addImageToWindow(
            EMBLEM_PATH + "Foreground/Animal/Black/Animal.00002000.16.png",
            screenCenter,
            35,
            34,
            34);

        /* Label */
        MAIN_WINDOW.addLabelToWindow("Select Frame ",
                                     firstColumn,
                                     firstRow);
        MAIN_WINDOW.addLabelToWindow("Frame Color ",
                                     secondColumn,
                                     firstRow);

        MAIN_WINDOW.addLabelToWindow("Select Emblem ",
                                     firstColumn,
                                     secondRow);
        MAIN_WINDOW.addLabelToWindow("Emblem Color ",
                                     secondColumn,
                                     secondRow);

        /* Combo Box */
        MAIN_WINDOW.setDefaultComboBoxWidth(90);
        MAIN_WINDOW.setDefaultComboBoxHeight(30);
        BACKGROUND_COLOR_CB = MAIN_WINDOW.addComboBoxToWindow(
            secondColumn,
            firstRow + gapWithComboBox);
        addArrayToComboBox(BACKGROUND_COLOR_CB, COLOR_LIST);

        FOREGROUND_COLOR_CB = MAIN_WINDOW.addComboBoxToWindow(
            secondColumn,
            secondRow + gapWithComboBox);
        addArrayToComboBox(FOREGROUND_COLOR_CB, COLOR_LIST);

        FOREGROUND_TYPE_CB = MAIN_WINDOW.addComboBoxToWindow(
            firstColumn,
            secondRow + 70);
        FOREGROUND_TYPE_CB.addItem("Animal");
        FOREGROUND_TYPE_CB.addItem("Etc");
        FOREGROUND_TYPE_CB.addItem("Letter");
        FOREGROUND_TYPE_CB.addItem("Pattern");
        FOREGROUND_TYPE_CB.addItem("Plant");
        
        FOREGROUND_COLOR_CB.currentStringChanged.connect(model, "fgChanged()");
        BACKGROUND_COLOR_CB.currentStringChanged.connect(model, "bgChanged()");
        
        FOREGROUND_TYPE_CB.currentStringChanged.connect(model, "typecbChanged()");

        /* Button */
        MAIN_WINDOW.setDefaultButtonWidth(50);
        MAIN_WINDOW.setDefaultButtonHeight(30);


        MAIN_WINDOW.addButtonToWindow("Prev",
                                      firstColumn - 10,
                                      firstRow + gapWithComboBox,
                                      model,
                                      "prevFrame()");
        MAIN_WINDOW.addButtonToWindow("Next",
                                      firstColumn + 40,
                                      firstRow + gapWithComboBox,
                                      model,
                                      "nextFrame()");

        MAIN_WINDOW.addButtonToWindow("Prev",
                                      firstColumn - 10,
                                      secondRow + gapWithComboBox,
                                      model,
                                      "prevEmblem()");
        MAIN_WINDOW.addButtonToWindow("Next",
                                      firstColumn + 40,
                                      secondRow + gapWithComboBox,
                                      model,
                                      "nextEmblem()");

        MAIN_WINDOW.addButtonToWindow("Save",
                                      firstColumn + 45,
                                      secondRow + 120,
                                      model,
                                      "save()");
        MAIN_WINDOW.addButtonToWindow("Quit",
                                      secondColumn + 5,
                                      secondRow + 120,
                                      model,
                                      "exit()");
        
        // initialize the path once.
        model.fgChanged();
        model.bgChanged();

        // -----------------------------------------------------
        MAIN_WINDOW.endFrame();
    }

    /**
     * Add a array into a combo box object.
     *
     * @param { QComboBox } inComboBox : combo box object.
     * @param { String[] } inArray : array to add into 'inComboBox'.
     */
    private static void addArrayToComboBox(QComboBox inComboBox, String[] inArray) {
        for (String item : inArray) {
            inComboBox.addItem(item);
        }
    }

}

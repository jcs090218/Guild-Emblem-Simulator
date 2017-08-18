package com.aldes.jcs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

/**
 * $File: Util.java $
 * $Date: 2017-08-18 12:45:38 $
 * $Revision: $
 * $Creator: Jen-Chieh Shen $
 * $Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright (c) 2017 by Shen, Jen-Chieh $
 */


public class Util {
    public final static void main(final String[] args) throws IOException {
        combineImages(
            "res/combined",
            "res/half_black",
            "res/half_white");

        System.out.println(safeAddExtension("helloWorld", ".png"));
    }

    /**
     * Check if needed add the extension on it.
     * @param filePath : file path.
     * @param ext : extension to add.
     * @return full path that added extension.
     */
    public static String safeAddExtension(String filePath, String ext) {
        String checkExt = FilenameUtils.getExtension(filePath);
        String checkExt2 = "." + checkExt;

        // check valid extension format.
        //  ↓ First check the regular extension
        //                             ↓ Second check extension with dot.
        if (!checkExt.equals(ext) && !checkExt2.equals(ext)) {

            // check if there are dot infront.
            if (ext.startsWith("."))
                filePath += ext;
            else
                filePath +=  "." + ext;
        }
        return filePath;
    }

    /**
     * Safe way adding the extension.
     * @param filePath
     * @return full path that added .png extension.
     */
    public static String safeAddExtensionPNG(String filePath) {
        return safeAddExtension(filePath, ".png");
    }


    public static void combineImage2(
        final String outputFileName,
        final String layer0,
        final String layer1)
        throws IOException {

        // load source images
        BufferedImage baseImage = ImageIO.read(new File(safeAddExtensionPNG(layer0)));
        BufferedImage overlay = ImageIO.read(new File(safeAddExtensionPNG(layer1)));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(baseImage.getWidth(), overlay.getWidth());
        int h = Math.max(baseImage.getHeight(), overlay.getHeight());

        // create image file
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(baseImage, 0, 0, null);
        g.drawImage(overlay, 0, 0, null);

        // Save as new image
        ImageIO.write(combined, "PNG", new File(safeAddExtensionPNG(outputFileName)));
    }

    /**
     * Merge multiple layer of images into one image.
     * @param outputFileName : output path.
     * @param layers : ellipse path.
     * @throws IOException : IO file system exception handle.
     */
    public static void combineImages(
        final String outputFileName,
        final String... layers)
        throws IOException {
        
        int w = -1, h = -1;

        ArrayList<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();

        for(String layer : layers) {
            // load source images
            BufferedImage overlay = ImageIO.read(new File(safeAddExtensionPNG(layer)));

            bufferedImages.add(overlay);

            // get min and max canvas size.
            w = Math.max(w, overlay.getWidth());
            h = Math.max(h, overlay.getHeight());
        }

        // create image file
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();

        for(int index = 0; index < bufferedImages.size(); ++index) {
            g.drawImage(bufferedImages.get(index), 0, 0, null);
        }

        // Save as new image
        ImageIO.write(combined, "PNG", new File(safeAddExtensionPNG(outputFileName)));

        g.dispose();
    }
}

package org.diabloeffect;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.time.LocalTime;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Main {
    public static void main(String[] args) {

        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            int timer = 0;
            while (timer < 500) {
                long startTime = System.currentTimeMillis();

                // Capture the screen
                BufferedImage screenshot = robot.createScreenCapture(screenRect);

                extractText(screenshot);

                // Calculate the remaining time to wait
                long elapsedTime = System.currentTimeMillis() - startTime;
                long remainingTime = Math.max(1400 - elapsedTime, 0);

                // Delay before capturing the next screenshot
                Thread.sleep(remainingTime);

                timer++;
                System.out.println(timer);
                System.out.println(LocalTime.now());
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void extractText(BufferedImage image) {

        // Basic Tesseract Image Extraction

        // String inputFilePath = "C:/Users/jovan/Downloads/eventcomplete.PNG";
        Tesseract tesseract =  new Tesseract();

        try {
            tesseract.setDatapath("C:/Users/jovan/Downloads/tessdata/tessdata-4.1.0");
            String text = tesseract.doOCR(image);

            System.out.print(text);

            Pattern pattern = Pattern.compile("(?:EVENT COMPLETE)");
            Matcher matcher = pattern.matcher(text);

            if(matcher.find()){
                System.out.println( "'EVENT COMPLETE' text found.");
                sound();
            }
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }


    }

    public static void sound(){
        try {
            File sound = new File("C:/Users/jovan/Downloads/finalfantasy.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(sound));
            c.start();

            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
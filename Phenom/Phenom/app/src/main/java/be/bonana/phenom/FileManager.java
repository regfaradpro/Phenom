package be.bonana.phenom;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liron.bonana on 24/05/2017.
 */

public class FileManager {

    File file;
    FileOutputStream outputStream;
    String filename;

    public FileManager(){
        this.filename = "/sdcard/Phenom/Data/data";
        file = new File(filename);

        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public FileManager(String filename){
        this.filename = filename;
        file = new File(filename);
        file.mkdirs();
    }

    public void writeLine(String text){

        if(file.exists()) {
            try {
                FileWriter writer = new FileWriter(filename, true);
                writer.write(text + "\r\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String[] readFile(){
        List<String> lines = new ArrayList<String>();

        try {
            FileReader reader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] linesArray = lines.toArray(new String[lines.size()]);
        return linesArray;
    }


}

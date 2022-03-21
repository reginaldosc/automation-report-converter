import Utils.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

    File source, dest;

    public void copyFile(String origin, String destination) {
        source = new File(origin);
        dest = new File(destination);

        try {
            Files.copy(source.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        // creates a file object
        File file = new File(Constants.elkReportPath + fileName);

        // deletes the file
        boolean value = file.delete();
        if(value) {
            System.out.println("JavaFile.java is successfully deleted.");
        }
        else {
            System.out.println("File doesn't exit");
        }
    }

    public void renameFile() {
        // create a file object
        File file = new File("oldName");

        // create a file
        try {
            file.createNewFile();
        }
        catch(Exception e) {
            e.getStackTrace();
        }

        // create an object that contains the new name of file
        File newFile = new File("newName");

        // change the name of file
        boolean value = file.renameTo(newFile);

        if(value) {
            System.out.println("The name of the file is changed.");
        }
        else {
            System.out.println("The name cannot be changed.");
        }
    }

    public void appendNewLine(String fileName) {
        try
        {
            FileWriter fw = new FileWriter(fileName,true); //the true will append the new data
            fw.write(System.lineSeparator());//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

        Path path = Paths.get(fileName);
        Charset charset = StandardCharsets.UTF_8;

        String content = null;
        try {
            content = new String(Files.readAllBytes(path), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert content != null;
        content = content.replaceAll("},", "},\n");
        try {
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

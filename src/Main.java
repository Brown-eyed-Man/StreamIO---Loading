import java.io.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress = null;
        String zipFile = "D:/Games/savegames/Saves.zip";
        String unzipDirectory = "D:/Games/savegames/";
        String save1 = "D:/Games/savegames/save1.dat";
        String save2 = "D:/Games/savegames/save2.dat";
        String save3 = "D:/Games/savegames/save3.dat";

        openZip(zipFile, unzipDirectory);
        gameProgress = openProgress(save1);
        System.out.println(gameProgress);
        gameProgress = openProgress(save2);
        System.out.println(gameProgress);
        gameProgress = openProgress(save3);
        System.out.println(gameProgress);

    }

    public static void openZip(String zipFilePath, String unzippingPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(unzippingPath + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String saveAbsolutePath) {
        try (FileInputStream fis = new FileInputStream(saveAbsolutePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Two {

    public static void saveGame(String path, GameProgress save){
        try(FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(save);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, List<String> paths){
        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))){
            for (String path : paths) {
                try(FileInputStream fis = new FileInputStream(path)){
                    ZipEntry zipEntry = new ZipEntry(new File(path).getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0){
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    System.out.println("Ошибка при упаковке файла: " + path);
                }
            }
        }  catch (IOException e) {
            System.out.println("Ошибка при создании архива: " + zipFilePath);
        }
    }

    public static void deleteFiles(List<String> filePaths){
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.delete()){
                System.out.println("Файл удален: " + filePath);
            } else {
                System.out.println("Ошибка при удалении файла: " + filePath);
            }
        }
    }

    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 2, 73, 54.3);
        GameProgress save2 = new GameProgress(97, 4, 82, 63.7);
        GameProgress save3 = new GameProgress(146, 3, 125, 73.6);

        String savePath1 = One.MY_PATH + "Games/savegames/save1.dat";
        String savePath2 = One.MY_PATH + "Games/savegames/save2.dat";
        String savePath3 = One.MY_PATH + "Games/savegames/save3.dat";

        saveGame(savePath1, save1);
        saveGame(savePath2, save2);
        saveGame(savePath3, save3);

        List<String> saveFiles = new ArrayList<>();
        saveFiles.add(savePath1);
        saveFiles.add(savePath2);
        saveFiles.add(savePath3);

        zipFiles(One.MY_PATH + "Games/savegames/savegames.zip", saveFiles);

        deleteFiles(saveFiles);

    }
}

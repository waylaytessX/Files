import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Three {
   public static void openZip(String zipFilePath, String destDir) {
            File dir = new File(destDir);
            if (!dir.exists()) dir.mkdirs();
            byte[] buffer = new byte[1024];
            try {
                FileInputStream fis = new FileInputStream(zipFilePath);
                ZipInputStream zis = new ZipInputStream(fis);
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    File newFile = newFile(dir, zipEntry);
                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Не удалось создать директорию " + newFile);
                        }
                    } else {
                        File parent = newFile.getParentFile();
                        if (!parent.isDirectory() && !parent.mkdirs()) {
                            throw new IOException("Не удалось создать директорию " + parent);
                        }
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Архив содержит файл с некорректным путем: " + zipEntry.getName());
        }
        return destFile;
    }

    public static GameProgress openProgress(String saveFilePath) {
        GameProgress gameProgress = null;
        try {
            FileInputStream fis = new FileInputStream(saveFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameProgress = (GameProgress) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }

        public static void main(String[] args) {
            openZip(One.MY_PATH + "Games/savegames/savegames.zip", One.MY_PATH + "Games/savegames");

            GameProgress gameProgress = openProgress(One.MY_PATH + "Games/savegames/save2.dat");

            if (gameProgress != null) {
                System.out.println(gameProgress);
            }

        }
    }


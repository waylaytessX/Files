import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class One {

    public static final String MY_PATH = "/Users/wlts/Desktop/";

    public static void main(String[] args) throws IOException {
        StringBuilder log = new StringBuilder();

       String[] directories = {
                "Games/src", "Games/res", "Games/savegames", "Games/temp",
                "Games/src/main", "Games/src/test",
                "Games/res/drawables", "Games/res/vectors", "Games/res/icons"
        };

        String[] files = {
                "Games/src/main/Main.java", "Games/src/main/Utils.java", "Games/temp/temp.txt"
        };


        for (String dir : directories) {
            File directory = new File(MY_PATH + dir);
            if (directory.mkdirs()) {
                log.append("Директория создана: ").append(dir).append("\n");
            } else {
                log.append("Ошибка при создании директории: ").append(dir).append("\n");
            }
        }

        for (String file : files) {
            try {
                File newFile = new File(MY_PATH + file);
                if (newFile.createNewFile()) {
                    log.append("Файл создан: ").append(file).append("\n");
                } else {
                    log.append("Ошибка при создании файла: ").append(file).append("\n");
                }
            } catch (IOException e) {
                log.append("Исключение при создании файла: ").append(file).append(" - ").append(e.getMessage()).append("\n");
            }
        }

        try (FileWriter writer = new FileWriter("Games/temp/temp.txt")) {
            writer.write(log.toString());
        } catch (IOException e) {
            System.out.println("Исключение при записи лога: " + e.getMessage());
        }

        System.out.println(log.toString());
    }
}
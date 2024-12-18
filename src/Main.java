import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    private static final String DIRECTORY_NAME = "UsersDirectory";
    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) {
        try {
            // Створення директорії
            createDirectory(DIRECTORY_NAME);

            // Запис інформації про користувачів
            writeUsersToFile(DIRECTORY_NAME + File.separator + FILE_NAME);

            // Підрахунок кількості рядків у файлі
            int lineCount = countLinesInFile(DIRECTORY_NAME + File.separator + FILE_NAME);
            System.out.println("Кількість рядків у файлі: " + lineCount);

            // Копіювання даних в інший файл
            String destinationPath = getDestinationPath();
            copyFile(DIRECTORY_NAME + File.separator + FILE_NAME, destinationPath);

            // Заміна слова "student" на "kursant"
            replaceWordInFile(destinationPath, "student", "kursant");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory(String directoryName) throws IOException {
        Path path = Paths.get(directoryName);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
            System.out.println("Директорію створено: " + directoryName);
        } else {
            System.out.println("Директорія вже існує: " + directoryName);
        }
    }

    private static void writeUsersToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Ім'я, Вік, Стать\n");
            writer.write("Олександр, 25, чоловік\n");
            writer.write("Марія, 22, жінка\n");
            writer.write("Іван, 30, чоловік\n");
            writer.write("Анна, 28, жінка\n");
        }
        System.out.println("Дані про користувачів записано у файл: " + filePath);
    }

    private static int countLinesInFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        }
    }

    private static String getDestinationPath() throws IOException {
        // Використовуємо System.console() для зчитування введення без Scanner
        Console console = System.console();
        if (console == null) {
            throw new IOException("Консоль недоступна для вводу.");
        }

        String destinationPath = console.readLine("Введіть адресу для копії файлу: ");

        // Додати перевірку на коректність шляху
        return destinationPath;
    }

    private static void copyFile(String sourcePath, String destinationPath) throws IOException {
        Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Файл скопійовано до: " + destinationPath);
    }

    private static void replaceWordInFile(String filePath, String targetWord, String replacementWord) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i).replace(targetWord, replacementWord));
        }

        Files.write(Paths.get(filePath), lines);
        System.out.println("Слово \"" + targetWord + "\" замінено на \"" + replacementWord + "\" у файлі: " + filePath);
    }
}

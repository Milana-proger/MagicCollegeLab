package source;

import exception.MissionLoadException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMissionDataSourse implements MissionDataSourse{
    private String baseDirectory;

    public FileMissionDataSourse(String baseDiractory) {
        this.baseDirectory = baseDiractory;
    }
    public FileMissionDataSourse() {
        this.baseDirectory = "";
    }

    @Override
    public String load(String path) throws MissionLoadException {
        Path fullPath = Paths.get(baseDirectory, path);
        File file = new File(fullPath.toUri());
        if (!file.exists()) {
            throw new MissionLoadException("Файл не найден: " + file.getName());
        }
        if (!file.isFile()) {
            throw new MissionLoadException("Не является файлом: " + file.getName());
        }
        try {
            return new String(Files.readAllBytes(file.toPath()), "UTF-8");
        } catch (IOException e) {
            throw new MissionLoadException("Ошибка чтения файла");
        }
    }
}

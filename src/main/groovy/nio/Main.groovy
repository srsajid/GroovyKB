package nio

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class Main {
    public static void main(String[] args) {
        Files.move(Paths.get("D:\\test\\star.sql"), Paths.get("E:\\test\\test.sql"), StandardCopyOption.REPLACE_EXISTING)
    }
}

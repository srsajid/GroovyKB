package zip

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

public class FolderZipper {

    static public void zipFolder(File srcFolder, ZipOutputStream zipOutputStream) throws Exception {
        addFolderToZip("", srcFolder, zipOutputStream);
        zipOutputStream.close();
    }

    static private void addFileToZip(String path, File srcFile, ZipOutputStream zipOutputStream) throws Exception {
        if (srcFile.isDirectory()) {
            addFolderToZip(path + srcFile.getName() + "/", srcFile, zipOutputStream);
        } else {
            FileInputStream fis = new FileInputStream(srcFile);
            zipOutputStream.putNextEntry(new ZipEntry(path + srcFile.getName()));
            zipOutputStream << fis
        }
    }

    static private void addFolderToZip(String path, File srcFolder, ZipOutputStream zipOutputStream) throws Exception {
        srcFolder.list().each { fileName ->
            File file = new File(srcFolder.absolutePath + File.separator + fileName)
            addFileToZip(path, file, zipOutputStream);
        }
    }
}
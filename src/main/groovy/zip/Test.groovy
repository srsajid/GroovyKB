package zip

import java.util.zip.ZipOutputStream

class Test {
    public static void main(String[] args) {
        File out = new File("C:\\MyDrive\\temp.zip")
        if(!out.exists()) out.createNewFile()
        FolderZipper.zipFolder(new File("C:\\MyDrive\\temp"), new ZipOutputStream(new FileOutputStream(out)))
    }
}

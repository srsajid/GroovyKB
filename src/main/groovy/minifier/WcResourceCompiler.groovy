package minifier

import com.compiler.ClosureCompilerProcessor
import minifier.util.FileNameUtils


public class WcResourceCompiler {
    String basePath
    static String DIRECTIVE_FILE_SEPARATOR = '/'

    public String relativePathToResolver(File file, String scanDirectoryPath) {
        def filePath = file.canonicalPath
        return filePath.substring(scanDirectoryPath.size() + 1).replace(File.separator, DIRECTIVE_FILE_SEPARATOR)

    }

    public WcResourceCompiler(String basePath) {
        this.basePath = basePath
        File targetDir = new File(basePath, "target/compiled");
        if(!targetDir.exists()) {
            targetDir.mkdirs()
        }
    }

    public void compileJs() {
        ClosureCompilerProcessor compiler = new ClosureCompilerProcessor();
        File targetDir = new File(basePath, "target/compiled");
        File jsTargetDir = new File(targetDir, "/js");
        if(jsTargetDir.exists()) {
            jsTargetDir.deleteDir()
        }
        jsTargetDir.mkdirs()
        File jsDirectory = new File(basePath, "web-app/js")
        jsDirectory.traverse {
            if (it.isFile()) {
                String relativePath = relativePathToResolver(it, jsDirectory.absolutePath)
                File compiledFile = new File(jsTargetDir, relativePath)
                File compiledDir = compiledFile.parentFile
                if(!compiledDir.exists()) {
                    compiledDir.mkdirs()
                }
                String fileName = it.name, fileData = it.text
                if(FileNameUtils.getExtension(fileName) == "js") {
                    try {
                        String newFileData = compiler.process(fileName, fileData, compiledDir)
                        fileData = newFileData
                    } catch (Exception ignore) { }
                }
                if(!compiledFile.exists()) {
                    compiledFile.createNewFile()
                }
                compiledFile.text = fileData
            }
        }
    }

    public void compile() {
        compileJs()
    }

    public static void main(String[] args) {
        new WcResourceCompiler("D:\\codes\\extreme\\extreme2").compile()
    }
}

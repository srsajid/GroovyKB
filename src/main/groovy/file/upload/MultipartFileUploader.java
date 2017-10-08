package file.upload;

/**
 * Created by sajedur on 3/31/2015.
 */
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 * @author www.codejava.net
 *
 */
public class MultipartFileUploader {

    public static void main(String[] args) {
        String charset = "UTF-8";
        File uploadFile1 = new File("d:/1.jpg");
        File uploadFile2 = new File("d:/2.jpg");
        String requestURL = "http://localhost/test/index";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("groovy.Test-Header", "Header-Value");
            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");
            multipart.addFilePart("fileUpload", uploadFile1);
            multipart.addFilePart("fileUpload", uploadFile2);
            List<String> response = multipart.finish();
            System.out.println("SERVER REPLIED:");
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
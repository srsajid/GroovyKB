package log4j

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Logger;

import java.io.*;
import java.sql.SQLException;

class Main {
    /* Get actual class name to be printed on */
    static Logger log = LogManager.getLogger(Main.class)

    public static void main(String[] args)throws IOException, SQLException{
        log.debug("Hello this is a debug message");
        log.info("Hello this is an info message");
    }
}

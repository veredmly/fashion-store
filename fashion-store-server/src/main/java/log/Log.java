package log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    FileHandler fh;

    public Log()  {
        try {
            fh = new FileHandler("C:\\Users\\mlyne\\IdeaProjects\\client_server_project\\src\\server\\Logs.log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

    }
    public void makeLog(Level level,String action)
    {
     logger.log(level,action);
    }

}
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Log {
//    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
//    public void makeLog () {
//        LOGGER.log(Level.INFO,"shopAction");
//    }
//
//}
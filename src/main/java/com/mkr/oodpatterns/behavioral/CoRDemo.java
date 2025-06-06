package com.mkr.oodpatterns.behavioral;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class LoggerRequest {

    private final String message;
    private final LoggerType loggerType;

    public LoggerRequest(String message, LoggerType loggerType) {
        this.message = message;
        this.loggerType = loggerType;
    }

    public String getMessage() {
        return this.message;
    }

    public LoggerType getLoggerType() {
        return this.loggerType;
    }

    public enum LoggerType {
        CONSOLE,
        FILE
    }

}

abstract class Logger {
    protected final Logger nextLogger;

    public Logger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public abstract void log(LoggerRequest request);
}

class ConsoleLogger extends Logger {

    public ConsoleLogger(Logger nextLogger) {
        super(nextLogger);
    }

    @Override
    public void log(LoggerRequest request) {
        if(request.getLoggerType() == LoggerRequest.LoggerType.CONSOLE) {
            System.out.println(request.getMessage());
        } else if(nextLogger != null) {
            nextLogger.log(request);
        }
    }
}

class FileLogger extends Logger {

    public FileLogger(Logger nextLogger) {
        super(nextLogger);
    }

    @Override
    public void log(LoggerRequest request) {
        if(request.getLoggerType() == LoggerRequest.LoggerType.FILE) {
            try {
                var writer = new BufferedWriter(new FileWriter("src/main/resources/log.txt", true));
                writer.write(request.getMessage() + "\n");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if(nextLogger != null) {
            nextLogger.log(request);
        }
    }
}

public class CoRDemo {
    public static void main(String[] args) {
        LoggerRequest request = new LoggerRequest("This is a log message", LoggerRequest.LoggerType.CONSOLE);
        Logger logger = buildChain();
        logger.log(request);

        request = new LoggerRequest("This is a log message", LoggerRequest.LoggerType.FILE);
//        logger = new FileLogger();
        logger.log(request);
    }

    public static Logger buildChain() {
        var fileLogger = new FileLogger(null);
        var consoleLogger = new ConsoleLogger(fileLogger);
        return consoleLogger;
    }
}

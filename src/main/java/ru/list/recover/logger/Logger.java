package ru.list.recover.logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * класс реализует журналирование
 * реализован шаблон Синглтон
 */
public class Logger {
    private static final Logger instance = new Logger();
    private List<Record> log;

    private Logger(){
        log = new ArrayList<>();
    }

    public static Logger getInstance(){
        return instance;
    }

    public void addRecord(String line, boolean result){
        log.add(new Record(LocalDateTime.now(), line, result));
    }

    public List<Record> getLog(){
        return log;
    }
}

package ru.list.recover.logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * класс реализует журналирование
 * реализован шаблон Синглтон
 */
public class Logger {
    private List<Record> log;

    public Logger() {
        log = new ArrayList<>();
    }

    /**
     * добавляем запись в журнал
     * @param line - описание события
     * @param result - результат события true - успех, false - неуспех
     */
    public void addRecord(String line, boolean result) {
        log.add(new Record(LocalDateTime.now(), line, result));
    }

    /**
     * получить журнал
     * @return список записей журнала
     */
    public List<Record> getLog() {
        return log;
    }
}

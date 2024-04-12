package ru.list.recover.services;

/**
 * интерфейс, реализующий шаблон "Наблюдатель"
 */
@FunctionalInterface
public interface IObserve {
    void Observe(Object o);
}

package ru.list.recover.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User - класс, описывающий пользователя программы
 * id - уникальный идентификатор пользователя
 * name - имя пользователя
 * login - логин пользователя
 * password - пароль пользователя
 * role - роль пользователя (0 - спортсмен, 1 - администратор)
 */

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String login;
    private String password;
    private int role;

    /**
     * переопределен метод toString
     * 
     * @return String - строковое представление объекта
     */
    @Override
    public String toString() {
        return String.format("%s - %s", this.getName(), this.getRole() == 0 ? "спортсмен" : "администратор");
    }

    /**
     * переопределили метод equal
     * 
     * @param объект класса User
     * @return true - объекты равны, иначе - false
     */
    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof User user) {
            if (this.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    /**
     * переопределили метод hashCode
     * 
     * @return - возвращает hashCode объекта
     */
    @Override
    public int hashCode() {
        return this.getLogin().hashCode();
    }

}

package se.sti.TDD.project.Interfaces;

import java.sql.SQLException;

public interface User {

    int getUserIdByUsername(String username) throws SQLException;
    int getBankIdByUserId(int userId) throws SQLException;
    int getATMIdByUserId(int userId) throws SQLException;

}

package service.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Selvin
 * on 06.07.2014.
 */

public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}

package service.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Selvin
 * on 06.07.2014.
 */
public interface SqlExecutor<T> {
    T execute(PreparedStatement st) throws SQLException;
}

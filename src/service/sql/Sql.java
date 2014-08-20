package service.sql;

import service.model.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Selvin
 * on 06.07.2014.
 */
public class Sql {
    public static ConnectionFactory CONN_FACTORY;

    static {
        try {
            CONN_FACTORY = new DirectConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = Sql.CONN_FACTORY.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new ServiceException("SQL failed!", e);
        }
    }
}

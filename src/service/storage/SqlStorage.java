package service.storage;

import service.sql.Sql;
import service.sql.SqlExecutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Selvin
 * on 25.07.2014.
 */
public class SqlStorage {
    private long existValue = 0L;
    private long addAmountTotal;
    private long getAmountTotal;

    public SqlStorage() {
        Sql.execute("SELECT value FROM total WHERE id IN (1, 2)",
                new SqlExecutor<Void>() {
                    @Override
                    public Void execute(PreparedStatement st) throws SQLException {
                        ResultSet rs = st.executeQuery();
                        if(rs.next()) {
                            getAmountTotal = rs.getLong("value");
                        }
                        if(rs.next()) {
                            addAmountTotal = rs.getLong("value");
                        }
                        return null;
                    }
                });
    }

    /*public Long get(final Integer id) {
        return Sql.execute("SELECT value FROM amounts WHERE id=?",
                new SqlExecutor<Long>() {
                    @Override
                    public Long execute(PreparedStatement st) throws SQLException {
                        st.setLong(1, id);
                        ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                            return rs.getLong("value");
                        }
                        return 0L;
                    }
                });
    }*/

    private void add(final Integer id, final Long value) {
        if(exist(id)) {
            Sql.execute("UPDATE amounts SET value=? WHERE id=?",
                new SqlExecutor<Integer>() {
                    @Override
                    public Integer execute(PreparedStatement st) throws SQLException {
                        st.setLong(1, value);
                        st.setInt(2, id);
                        return st.executeUpdate();
                    }
                });
        }
        else Sql.execute("INSERT INTO amounts (id, value) VALUES(?,?)",
                new SqlExecutor<Void>() {
                    @Override
                    public Void execute(PreparedStatement st) throws SQLException {
                        st.setInt(1, id);
                        st.setLong(2, value);
                        st.execute();
                        return null;
                    }
                }
        );
    }

    public Map<Integer, Long> getAll() {
        return Sql.execute("SELECT id, value FROM amounts",
                new SqlExecutor<Map<Integer, Long>>() {
                    @Override
                    public Map<Integer, Long> execute(PreparedStatement st) throws SQLException {
                        Map<Integer, Long> map = new HashMap<>();
                        ResultSet rs = st.executeQuery();
                        while (rs.next()) {
                            map.put(new Integer(rs.getString("id")), new Long(rs.getString("value")));
                        }
                        return map;
                    }
                });
    }

    public void updateAll(Map<Integer, Long> map) {
        for(final Map.Entry<Integer, Long> entry : map.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    private boolean exist(final Integer id) {
        return Sql.execute("SELECT value FROM amounts WHERE id=?",
                new SqlExecutor<Boolean>() {
                    @Override
                    public Boolean execute(PreparedStatement st) throws SQLException {
                        st.setLong(1, id);
                        ResultSet rs = st.executeQuery();
                        if(rs.next()) {
                            existValue = rs.getLong("value");
                            return true;
                        }
                        existValue = 0L;
                        return false;
                    }
                });
    }

    public long getAddAmountTotal() {
        return addAmountTotal;
    }

    public long getGetAmountTotal() {
        return getAmountTotal;
    }

    public void updateSt(final Long getAmountTotal, final Long addAmountTotal) {
        Sql.execute("UPDATE total SET value=? WHERE id=1",
                new SqlExecutor<Integer>() {
                    @Override
                    public Integer execute(PreparedStatement st) throws SQLException {
                        st.setLong(1, getAmountTotal);
                        return st.executeUpdate();
                    }
                });
        Sql.execute("UPDATE total SET value=? WHERE id=2",
                new SqlExecutor<Integer>() {
                    @Override
                    public Integer execute(PreparedStatement st) throws SQLException {
                        st.setLong(1, addAmountTotal);
                        return st.executeUpdate();
                    }
                });
    }
}

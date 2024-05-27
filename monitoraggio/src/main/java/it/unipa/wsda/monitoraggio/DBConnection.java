package it.unipa.wsda.monitoraggio;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DBConnection {
    public static Connection getConnection() throws MonitoraggioException {
        Context ctx;
        DataSource ds;
        Connection connection;
        try {
            ctx = new InitialContext();
            try {
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/monitoraggio");
                try {
                    connection = ds.getConnection();
                } catch (SQLException exc) {
                    throw new MonitoraggioException("ds.getConnection()");
                }
            } catch (NamingException exc) {
                throw new MonitoraggioException("ctx.lookup()");
            }
        } catch (NamingException exc) {
            throw new MonitoraggioException("new InitialContext()");
        }
        return connection;
    }

    public static List<Impianto> eseguiQuery(String query) throws MonitoraggioException {
        List<Impianto> impianti = new LinkedList<>();
        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Impianto impianto = new Impianto(rs.getInt("cod_impianto"), rs.getString("descrizione"),
                                rs.getBigDecimal("latitudine"), rs.getBigDecimal("longitudine"),
                                rs.getTimestamp("ultimo_segnale"), rs.getBoolean("stato"));
                        impianti.add(impianto);
                    }
                } catch (SQLException e) {
                    throw new MonitoraggioException("pstmt.executeQuery()");
                }
            } catch (SQLException e) {
                throw new MonitoraggioException("connection.prepareStatement()");
            }
        } catch (SQLException e) {
            throw new MonitoraggioException("DBConnection.getConnection()");
        }
        return impianti;
    }

    public static int eseguiUpdate(String query, List<String> parametri) throws MonitoraggioException {
        if (parametri.contains(null)) {
            throw new MonitoraggioException("parametri.contains(null)");
        }
        int numRighe;
        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                try {
                    for (int i = 0; i < parametri.size(); i++) {
                        pstmt.setString(i + 1, parametri.get(i));
                    }
                    numRighe = pstmt.executeUpdate();
                } catch (SQLException e) {
                    throw new MonitoraggioException("pstmt.executeUpdate()");
                }
            } catch (SQLException e) {
                throw new MonitoraggioException("connection.prepareStatement()");
            }
        } catch (SQLException e) {
            throw new MonitoraggioException("DBConnection.getConnection()");
        }
        return numRighe;
    }
}

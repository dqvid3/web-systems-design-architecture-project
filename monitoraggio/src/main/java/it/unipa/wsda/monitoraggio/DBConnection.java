package it.unipa.wsda.monitoraggio;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class DBConnection {
    public static Connection getConnection() {
        Context ctx;
        DataSource ds;
        Connection connection;
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/monitoraggio");
            connection = ds.getConnection();
        }
        catch (NamingException | SQLException exc) {
            System.err.println("Errore durante la connessione: " + exc.getMessage());
            connection = null;
        }
        return connection;
    }

    public static List<Impianto> eseguiQuery(String query) {
        List<Impianto> impianti = null;
        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    impianti = new LinkedList<>();
                    while (rs.next()) {
                        // Package org.springframework.jdbc.core
                        Impianto impianto = new Impianto(rs.getInt("cod_impianto"), rs.getString("descrizione"),
                            rs.getBigDecimal("latitudine"), rs.getBigDecimal("longitudine"), rs.getTimestamp("ultimo_segnale"));
                        impianti.add(impianto);
                    }
                }
            }
        }
        catch (SQLException eccezione) { // Si potrebbe mettere un catch per ogni try in modo da differenziare i messaggi di errore...
            System.err.println("Errore durante l'esecuzione della query: " + eccezione.getMessage());
        }
        return impianti;
    }

    public static int eseguiUpdate(String query, List<String> parametri) {
        return 0;
    }
}

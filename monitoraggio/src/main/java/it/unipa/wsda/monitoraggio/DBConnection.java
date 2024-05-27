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

/**
 * Classe che gestisce la connessione al database e l'esecuzione di query e update.
 * <p>
 * Questa classe fornisce metodi statici per ottenere una connessione al database,
 * eseguire query e aggiornamenti SQL, gestendo eventuali eccezioni e errori di connessione.
 * </p>
 * 
 * @version 1.0
 */
public class DBConnection {

    /**
     * Ottiene una connessione al database utilizzando il DataSource JNDI.
     *
     * @return la connessione al database
     * @throws MonitoraggioException se si verifica un errore durante l'ottenimento della connessione
     */
    public static Connection getConnection() throws MonitoraggioException {
        Context ctx;
        DataSource ds;
        Connection connection;
        try {
            // Crea un nuovo contesto iniziale
            ctx = new InitialContext();
            try {
                // Effettua il lookup del DataSource
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/monitoraggio");
                try {
                    // Ottiene una connessione dal DataSource
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

    /**
     * Esegue una query SQL e restituisce una lista di oggetti Impianto.
     *
     * @param query la query SQL da eseguire
     * @return una lista di oggetti Impianto ottenuti dalla query
     * @throws MonitoraggioException se si verifica un errore durante l'esecuzione della query
     */
    public static List<Impianto> eseguiQuery(String query) throws MonitoraggioException {
        List<Impianto> impianti;
        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    impianti = new LinkedList<>();
                    // Itera sui risultati della query e crea oggetti Impianto
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

    /**
     * Esegue un'operazione di aggiornamento (INSERT, UPDATE, DELETE) su una query SQL.
     *
     * @param query     la query SQL da eseguire
     * @param parametri i parametri da inserire nella query
     * @return il numero di righe aggiornate
     * @throws MonitoraggioException se si verifica un errore durante l'esecuzione dell'aggiornamento
     */
    public static int eseguiUpdate(String query, List<String> parametri) throws MonitoraggioException {
        // Verifica che nessun parametro sia null
        if (parametri.contains(null)) {
            throw new MonitoraggioException("parametri.contains(null)");
        }
        int numRighe;
        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                try {
                    // Imposta i parametri nella query preparata
                    for (int i = 0; i < parametri.size(); i++) {
                        pstmt.setString(i + 1, parametri.get(i));
                    }
                    // Esegue l'aggiornamento e ottiene il numero di righe interessate
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

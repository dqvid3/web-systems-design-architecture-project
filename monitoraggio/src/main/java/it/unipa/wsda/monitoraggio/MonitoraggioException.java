package it.unipa.wsda.monitoraggio;

public class MonitoraggioException extends Exception {
    private static final long serialVersionUID = 1234568L;

    public MonitoraggioException(String messaggio) {
        super(messaggio);
    }

    @Override
    public String toString() {
        return "{\"status\": \"error\", \"message\": \"" + this.getMessage() + "\"}";
    }
}

// Simulation of a MySQL database library (assume it is third-party code that must not be modified).
// No real server is needed: SQL statements are simply printed to the screen.
public class MySqlDatabase {
    public void connect(String host, String user, String password) {
        System.out.println("[MySQL] Connected to " + host + " as " + user);
    }

    public void executeSql(String sql) {
        System.out.println("[MySQL] " + sql);
    }
}

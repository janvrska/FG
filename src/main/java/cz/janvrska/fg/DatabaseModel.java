package cz.janvrska.fg;

import java.sql.*;

public class DatabaseModel {

    Plugin plugin;

    public DatabaseModel(Plugin plugin) {
        this.plugin = plugin;
    }

    public void createPlayerEvent(String playerName, String executorName, String reason, Timestamp length, int type) {
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM authme WHERE username='" + playerName + "';");
            ps.execute();
            ResultSet result = ps.getResultSet();

            if(!result.next()){
                return;
            }

            int playerId = result.getInt("id");

            Integer executorId = null;
            if(executorName != null){
                PreparedStatement ps1 = connection.prepareStatement("SELECT id FROM authme WHERE username='" + executorName + "';");
                ps1.execute();
                ResultSet result1 = ps1.getResultSet();

                if(!result1.next()){
                    return;
                }

                executorId = result1.getInt("id");
            }

            PreparedStatement ps2 = connection.prepareStatement(
                    "INSERT INTO player_events (player_id,executor_id,reason,type, length ) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps2.setInt(1, playerId);
            ps2.setObject(2, executorId, Types.INTEGER);
            ps2.setString(3, reason);
            ps2.setInt(4, type);
            ps2.setObject(5, length, Types.TIMESTAMP);
            ps2.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

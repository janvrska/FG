package cz.janvrska.fg;

public class AuthCode {

    private String playerName;
    private int code;

    public AuthCode(int code, String player) {
        this.playerName = player;
        this.code = code;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

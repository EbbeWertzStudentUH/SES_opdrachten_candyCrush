package be.kuleuven.candycrush.model;

public class UserInfoModel {
    private int highScore;
    private final String password;

    public UserInfoModel(String password) {
        highScore = 0;
        this.password = password;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getPassword() {
        return password;
    }
}

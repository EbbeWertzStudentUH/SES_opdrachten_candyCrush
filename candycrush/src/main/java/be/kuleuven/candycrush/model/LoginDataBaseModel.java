package be.kuleuven.candycrush.model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class LoginDataBaseModel {

    private class Data{ //door deze wrapper class kan GSON werken.
        // Direct naar HashMap deserialisen gaat niet door de unieke class
        //in de <> haakjes die niet worden meegegeven door .getClass() of .class
        protected HashMap<String, UserInfoModel> users;
        protected Data(){
            users = new HashMap<>();
        }
    }

    Data data;

    //key = naam
    //value = wachtwoord

    public LoginDataBaseModel() {
        File file = new File("loginDataBase.json");
        data = file.exists() ? readFromFile() : new Data();
    }

    public boolean nameExists(String name){
        return data.users.containsKey(name);
    }
    public boolean passWordIsCorrect(String name, String password){
        UserInfoModel user = data.users.get(name);
        return user.getPassword().equals(password);
    }

    public int getHighscore(String user){
        return data.users.get(user).getHighScore();
    }
    public void setHighscore(String user, int highscore){
        data.users.get(user).setHighScore(highscore);
        save();
    }

    public void addNewUser(String name, String password){
        UserInfoModel user = new UserInfoModel(password);
        data.users.put(name, user);
        save();
    }

    private void save(){
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("loginDataBase.json")) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Data readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("loginDataBase.json")) {
            return gson.fromJson(reader, Data.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

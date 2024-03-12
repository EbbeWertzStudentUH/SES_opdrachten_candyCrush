package be.kuleuven.candycrush.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class LoginDataBaseModel {

    private HashMap<String, String> allUsers;
    //key = naam
    //value = wachtwoord

    public LoginDataBaseModel() {
        File file = new File("loginDataBase.json");
        allUsers = file.exists() ? readFromFile() : new HashMap<>();
    }

    public boolean nameExists(String name){
        return allUsers.containsKey(name);
    }
    public boolean passWordIsCorrect(String name, String password){
        return allUsers.get(name).equals(password);
    }

    public void addNewUser(String name, String password){
        allUsers.put(name, password);
        save();
    }

    private void save(){
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("loginDataBase.json")) {
            gson.toJson(allUsers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("loginDataBase.json")) {
            return gson.fromJson(reader, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

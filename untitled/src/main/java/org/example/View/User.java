package org.example.View;

import org.example.Resources.Resources;
import java.io.*;
import java.util.LinkedList;

public class User implements Serializable {
    private String username;
    private String password;
    private static LinkedList<User> users = new LinkedList<>();
    private int coin = 0;
    private int maxScore = 0;
    private LinkedList<Integer> scores = new LinkedList<>();
    private Game[] games = new Game[3];

    public User(String username , String password) throws FileNotFoundException {
        this.username = username;
        this.password = password;
    }
    public static LinkedList<User> sortResultUsers(){
        LinkedList<User> sortUsers = new LinkedList<>();
        for(int i = 0; i < users.size(); i++){
            sortUsers.add(users.get(i));
        }
        for (int i = 0; i < users.size(); i++) {
            for (int j = i+1 ; j < users.size(); j++) {
                if (sortUsers.get(i).maxScore < sortUsers.get(j).maxScore) {
                    User user = sortUsers.get(i);
                    sortUsers.set(i , sortUsers.get(j));
                    sortUsers.set(j , user);
                }
            }
        }
        return sortUsers;
    }
    public int highestScore(){
        for(int i =0 ; i < this.scores.size(); i++){
            if(this.scores.get(i) > maxScore){
                maxScore = this.scores.get(i);
            }
        }
        return maxScore;
    }
    public static void writeUser(User user) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("untitled/saveUser.txt" , false));
        for(int i = 0; i < User.users.size(); i++){
            objectOutputStream.writeObject(User.users.get(i));
        }
        try {
            User.users.add(user);
            objectOutputStream.writeObject(user);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
    public static void readUser() throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("untitled/saveUser.txt"));
        Object obj = null;
        try {
            while (((obj = objectInputStream.readObject()) instanceof User)) {
                User.users.add((User) obj);
            }
        } catch (Exception e) {

        }
    }
    public static void resetUser() throws IOException {
        Resources.stopSounds();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("untitled/saveUser.txt" , false));
        for(int i = 0; i < User.users.size(); i++){
            objectOutputStream.writeObject(User.users.get(i));
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static LinkedList<User> getUsers() {
        return users;
    }

    public LinkedList<Integer> getScores() {
        return scores;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public Game[] getGames() {
        return games;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}

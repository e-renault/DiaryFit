package ca.uqac.diaryfit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.uqac.diaryfit.datas.Session;

public class User {
    private String email;
    private HashMap<String, List<Session>> sessions = new HashMap<>();
    private List<String> nameListExercice = new ArrayList<>();

    public User() {
    }

    public User(String email) {
        this.email = email;
        this.nameListExercice = new ArrayList<String>();
        this.sessions = new HashMap<>();
    }

    public User(String email, HashMap<String, List<Session>> sessions, List<String> nameListExercice) {
        this.email = email;
        this.nameListExercice = nameListExercice;
        this.sessions = sessions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, List<Session>> getSessions() {
        return sessions;
    }

    public void setSessions(HashMap<String, List<Session>> sessions) {
        if(sessions == null)
            this.sessions = new HashMap<>();
        else
            this.sessions = sessions;
    }

    public List<String> getNameListExercice() {
        return nameListExercice;
    }

    public void setNameListExercice(List<String> nameListExercice) {
        if(nameListExercice == null)
            this.nameListExercice = new ArrayList<String>();
        else
            this.nameListExercice = nameListExercice;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", sessions=" + sessions +
                ", nameListExercice=" + nameListExercice +
                '}';
    }
}

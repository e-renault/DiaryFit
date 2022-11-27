package ca.uqac.diaryfit;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.diaryfit.datas.Session;

public class User {
    private String email;
    private List<Session> sessions = new ArrayList<Session>();
    private List<String> nameListExercice = new ArrayList<String>();
    public User() {
    }

    public User(String email) {
        this.email = email;
        this.nameListExercice = new ArrayList<String>();
        this.sessions = new ArrayList<Session>();
    }

    public User(String email, List<Session> sessions, List<String> nameListExercice) {
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

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        if(sessions == null)
            this.sessions = new ArrayList<Session>();
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

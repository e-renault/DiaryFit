package ca.uqac.diaryfit;

import java.util.List;

import ca.uqac.diaryfit.datas.Session;

public class User {
    private String email;
    private List<Session> sessions;
    private List<String> nameListExercice;
    public User() {
    }

    public User(String email) {
        this.email = email;
        this.nameListExercice = null;
        this.sessions = null;
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
        this.sessions = sessions;
    }

    public List<String> getNameListExercice() {
        return nameListExercice;
    }

    public void setNameListExercice(List<String> nameListExercice) {
        this.nameListExercice = nameListExercice;
    }
}

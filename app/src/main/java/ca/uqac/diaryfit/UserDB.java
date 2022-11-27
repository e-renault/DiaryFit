package ca.uqac.diaryfit;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ca.uqac.diaryfit.datas.Session;
import ca.uqac.diaryfit.datas.exercices.Exercice;

public class UserDB {

    public UserDB(){

    }

    /*public void getUser(User profil) throws InterruptedException {
        final User[] user = {null};

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profil = snapshot.getValue(User.class);
                Log.println(Log.DEBUG, "TEST", profil.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                profil = null;
            }
        });

    }*/

    public static void updateUserEmail(String email){

        FirebaseUser profil = FirebaseAuth.getInstance().getCurrentUser();

        assert profil != null;
        profil.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                            .child("email").setValue(email);
                }
            }
        });
    }

    public static void deleteUser(){

        FirebaseUser profil = FirebaseAuth.getInstance().getCurrentUser();

        assert profil != null;
        profil.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //is delete from Auth
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .removeValue();
                        }
                    }
                });
    }

    public static void addExercice(User user, String nameEx){

        assert user!=null;
        Log.println(Log.DEBUG, "addExo", user.toString());
        if(user.getNameListExercice() == null)
            user.setNameListExercice(new ArrayList<String>());
        user.getNameListExercice().add(nameEx);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("nameListExercice")
                .setValue(user.getNameListExercice());
    }

    public static void updateExercie(User user, String nameExOld, String nameExNew){

        assert user!=null;
        int idEx = user.getNameListExercice().indexOf(nameExOld);
        user.getNameListExercice().set(idEx, nameExNew);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("nameListExercice").child(String.valueOf(idEx))
                .setValue(nameExNew);
    }

    public static void deleteExercice(User user, String nameEx){

        assert user!=null;
        user.getNameListExercice().remove(nameEx);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("nameListExercice")
                .setValue(user.getNameListExercice());
    }

    public static void addSession(User user, Session session){
        assert user!=null;

        if(user.getSessions() == null)
            user.setSessions(new ArrayList<Session>());

        user.getSessions().add(session);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .setValue(user.getSessions());
    }

    public static void updateSession(User user, Session sessionOld, Session sessionNew){
        assert user!=null;
        int idSession = user.getSessions().indexOf(sessionOld);
        user.getSessions().set(idSession, sessionNew);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .child(String.valueOf(idSession))
                .setValue(sessionNew);
    }

    public static void deleteSession(User user, Session session){
        assert user!=null;
        user.getSessions().remove(session);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .setValue(user.getSessions());
    }

    public static String getExerciceName(User user, int index) {

        Log.println(Log.DEBUG, "getExoName", String.valueOf(index));
        try{
            return user.getNameListExercice().get(index);
        }catch(Exception ignored){}

        return "Error";
    }

    public static List<String> getExerciceList(User user) {
        assert user!=null;

        if(user.getNameListExercice() != null)
            return user.getNameListExercice();
        else
            return new ArrayList<String>();
    }

    public static Exercice getExercice(User user, int sessionID, int exerciceID) {
        assert user!=null;
        return (Exercice) user.getSessions().get(sessionID).getExerciceList().get(exerciceID);
    }

    public static void setExercice(User user, int sessionID, int exerciceID, Exercice exercie) {
        assert user!=null;

        user.getSessions().get(sessionID).exerciceListGet().set(exerciceID, exercie);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .child(String.valueOf(sessionID))
                .child("exerciceListe2")
                .child(String.valueOf(exerciceID))
                .setValue(exercie);
    }

    public static Session getSession(User user, int sessionID) {
        assert user != null;
        return user.getSessions().get(sessionID);
    }

    public static void setSession(User user, int sessionID, Session session) {
        assert user!=null;

        user.getSessions().set(sessionID, session);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .child(String.valueOf(sessionID))
                .setValue(session);
    }

    public static ArrayList<Session> getTodaySessions(User user) {
        ArrayList<Session> todaySessions = new ArrayList<Session>();

        @SuppressLint("SimpleDateFormat")
        Format formatDate = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatDate.format(new Date());


        for(Session session : user.getSessions()){
            Log.println(Log.DEBUG, "Date", session.getTimeDate());

            if(session.getTimeDate().equals(today)){
                todaySessions.add(session);
            }
        }
        return todaySessions;
    }
}

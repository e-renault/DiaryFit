package ca.uqac.diaryfit;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ca.uqac.diaryfit.datas.Session;
import ca.uqac.diaryfit.datas.exercices.Exercice;

public class UserDB {

    public UserDB(){

    }

    public User getUser(){
        final User[] user = {null};
        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                user[0] = null;
            }
        });

        return user[0];
    }

    public void updateUserEmail(String email){

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

    public void deleteUser(){

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

    public void addExercice(User user, String nameEx){

        user.getNameListExercice().add(nameEx);

        FirebaseDatabase.getInstance().getReference("User")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("nameListExercice")
                .setValue(user.getNameListExercice());
    }

    public void updateExercie(User user, String nameExOld, String nameExNew){
        int idEx = user.getNameListExercice().indexOf(nameExOld);
        user.getNameListExercice().set(idEx, nameExNew);

        FirebaseDatabase.getInstance().getReference("User")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("nameListExercice").child(String.valueOf(idEx))
                .setValue(nameExNew);
    }

    public void deleteExercice(User user, String nameEx){
        user.getNameListExercice().remove(nameEx);

        FirebaseDatabase.getInstance().getReference("User")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("nameListExercice")
                .setValue(user.getNameListExercice());
    }

    public void addSession(User user, Session session){
        user.getSessions().add(session);

        FirebaseDatabase.getInstance().getReference("User")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .setValue(user.getSessions());
    }

    public void updateSession(User user, Session sessionOld, Session sessionNew){
        int idSession = user.getSessions().indexOf(sessionOld);
        user.getSessions().set(idSession, sessionNew);

        FirebaseDatabase.getInstance().getReference("User")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .child(String.valueOf(idSession))
                .setValue(sessionNew);
    }

    public void deleteSession(User user, Session session){
        user.getSessions().remove(session);

        FirebaseDatabase.getInstance().getReference("User")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("sessions")
                .setValue(user.getSessions());
    }

    public static String getExerciceName(int index) {
        return "Error";
    }

    public static List<String> getExerciceList() {
        return new ArrayList<String>();
    }

    public static Exercice getExercice(int sessionID, int exerciceID) {
        return null;
    }

    public static void setExercice(int sessionID, int exerciceID, Exercice exercie) {

    }

    public static Session getSession(int sessionID) {
        return null;
    }

    public static void setSession(int sessionID, Session session) {

    }

    public static void addSession(Session session) {

    }

    public static ArrayList<Session> getTodaySessions() {
        return new ArrayList<Session>();
    }
}

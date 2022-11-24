package ca.uqac.diaryfit;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

import ca.uqac.diaryfit.datas.Session;
import ca.uqac.diaryfit.datas.exercices.Exercice;

public class UserDB {

    public UserDB(){

    }

    public User getUser(){
        final User[] user = {null};
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
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

    public void updateUserEmail(User user, String email){

        FirebaseUser profil = FirebaseAuth.getInstance().getCurrentUser();

        assert profil != null;
        profil.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getUid()).child("email").setValue(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                    }
                                    else{

                                    }
                                }
                            });
                }
                else{

                }
            }
        });
    }

    public void deleteUser(User user){

        FirebaseUser profil = FirebaseAuth.getInstance().getCurrentUser();

        profil.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //is delete from Auth
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                //is delete from database
                                            }
                                            else{
                                                //cannot be removed from database
                                            }
                                        }
                                    });
                        }
                        else{
                            //cannot be removed from Auth
                        }
                    }
                });
    }

    public void addExercice(User user, String nameEx){

        user.getNameListExercice().add(nameEx);

        FirebaseDatabase.getInstance().getReference("User")
                .child(FirebaseAuth.getInstance().getUid()).child("nameListExercice")
                .setValue(user.getNameListExercice()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{

                        }
                    }
                });
    }

    public void updateExercie(User user, String nameExOld, String nameExNew){
        int idEx = user.getNameListExercice().indexOf(nameExOld);
        user.getNameListExercice().set(idEx, nameExNew);

        FirebaseDatabase.getInstance().getReference("User")
                .child(FirebaseAuth.getInstance().getUid()).child("nameListExercice").child(String.valueOf(idEx))
                .setValue(nameExNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{

                        }
                    }
                });
    }

    public void deleteExercice(User user, String nameEx){
        user.getNameListExercice().remove(nameEx);

        FirebaseDatabase.getInstance().getReference("User")
                .child(FirebaseAuth.getInstance().getUid()).child("nameListExercice")
                .setValue(user.getNameListExercice()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{

                        }
                    }
                });
    }

    public void addSession(User user, Session session){
        user.getSessions().add(session);

        FirebaseDatabase.getInstance().getReference("User")
                .child(FirebaseAuth.getInstance().getUid()).child("sessions")
                .setValue(user.getSessions()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{

                        }
                    }
                });
    }

    public void updateSession(User user, Session sessionOld, Session sessionNew){
        int idSession = user.getNameListExercice().indexOf(sessionOld);
        user.getSessions().set(idSession, sessionNew);

        FirebaseDatabase.getInstance().getReference("User")
                .child(FirebaseAuth.getInstance().getUid()).child("sessions").child(String.valueOf(idSession))
                .setValue(sessionNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{

                        }
                    }
                });
    }

    public void deleteSession(User user, Session session){
        user.getNameListExercice().remove(session);

        FirebaseDatabase.getInstance().getReference("User")
                .child(FirebaseAuth.getInstance().getUid()).child("sessions")
                .setValue(user.getSessions()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{

                        }
                    }
                });
    }

}

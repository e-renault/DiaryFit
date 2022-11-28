package ca.uqac.diaryfit;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ca.uqac.diaryfit.datas.MTime;
import ca.uqac.diaryfit.datas.MWeigth;
import ca.uqac.diaryfit.datas.Session;
import ca.uqac.diaryfit.datas.exercices.Exercice;
import ca.uqac.diaryfit.datas.exercices.ExerciceRepetition;
import ca.uqac.diaryfit.datas.exercices.ExerciceTabata;
import ca.uqac.diaryfit.datas.exercices.ExerciceTime;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();


        String email = jsonObject.get("email").getAsString();
        List<String> listNameExo = new ArrayList<>();
        List<Session> listSession = new ArrayList<>();


        try{
            JsonArray jsonNameExo = jsonObject.getAsJsonArray("nameListExercice");
            //Ajout des noms d'exo
            for(JsonElement jsonName : jsonNameExo){
                String nameExo = jsonName.getAsString();
                listNameExo.add(nameExo);
            }
        }catch(Exception ignored){}

        try{
            JsonArray jsonSessions = jsonObject.getAsJsonArray("sessions");
            //Ajout des sesssion
            for(JsonElement jsonSession : jsonSessions){
                JsonObject jsonObject1 = jsonSession.getAsJsonObject();

                String name = jsonObject1.get("name").getAsString();
                String date = jsonObject1.get("timeDate").getAsString();
                ArrayList<Exercice> listExo = new ArrayList<>();

                JsonArray jsonListExo = jsonObject1.getAsJsonArray("exerciceList");
                //Ajout d'exo dans session
                for(JsonElement jsonExo : jsonListExo){
                    JsonObject jsonObject2 = jsonExo.getAsJsonObject();

                    //Exercice Rep
                    if(jsonObject2.has("nbRepetition")){
                        boolean done = jsonObject2.get("done").getAsBoolean();
                        int id = jsonObject2.get("exerciceNameID").getAsInt();
                        int nbSerie = jsonObject2.get("nbSerie").getAsInt();
                        int nbRepetition = jsonObject2.get("nbRepetition").getAsInt();

                        JsonObject jsonWeight = jsonObject2.get("weigth").getAsJsonObject();

                        boolean isKg = jsonWeight.get("kg").getAsBoolean();
                        float weight = jsonWeight.get("weigthkg").getAsFloat();

                        MWeigth mWeigth = new MWeigth(weight, isKg);

                        JsonObject jsonRest = jsonObject2.getAsJsonObject("rest");
                        int timeInSec = jsonRest.get("timeInSec").getAsInt();
                        MTime mRest = new MTime(timeInSec);

                        ExerciceRepetition exo = new ExerciceRepetition(id, nbSerie, nbRepetition, mWeigth, mRest);
                        exo.setDone(done);

                        listExo.add(exo);
                    }
                    //Exercice Time
                    else if(!jsonObject2.has("nbCycle")){
                        boolean done = jsonObject2.get("done").getAsBoolean();
                        int id = jsonObject2.get("exerciceNameID").getAsInt();
                        int nbSerie = jsonObject2.get("nbSerie").getAsInt();

                        JsonObject jsonTime = jsonObject2.get("effortTime").getAsJsonObject();
                        int timeInSecEffort = jsonTime.get("timeInSec").getAsInt();
                        MTime efforTime = new MTime(timeInSecEffort);

                        JsonObject jsonWeight = jsonObject2.get("weigth").getAsJsonObject();

                        boolean isKg = jsonWeight.get("kg").getAsBoolean();
                        float weight = jsonWeight.get("weigthkg").getAsFloat();

                        MWeigth mWeigth = new MWeigth(weight, isKg);

                        JsonObject jsonRest = jsonObject2.getAsJsonObject("rest");
                        int timeInSecRest = jsonRest.get("timeInSec").getAsInt();
                        MTime mRest = new MTime(timeInSecRest);

                        ExerciceTime exo = new ExerciceTime(id, nbSerie, efforTime, mWeigth, mRest);
                        exo.setDone(done);

                        listExo.add(exo);
                    }
                    //Tabata
                    else{
                        boolean done = jsonObject2.get("done").getAsBoolean();
                        int nbCycle = jsonObject2.get("nbCycle").getAsInt();

                        JsonObject jsonTime = jsonObject2.get("effortTime").getAsJsonObject();
                        int timeInSecEffort = jsonTime.get("timeInSec").getAsInt();
                        MTime efforTime = new MTime(timeInSecEffort);


                        JsonObject jsonRest = jsonObject2.getAsJsonObject("rest");
                        int timeInSecRest = jsonRest.get("timeInSec").getAsInt();
                        MTime mRest = new MTime(timeInSecRest);

                        List<Integer> listExoTabata = new ArrayList<>();

                        JsonArray jsonArray = jsonObject2.getAsJsonArray("otherExerciceList");
                        for(JsonElement element:jsonArray){
                            int nameExo = element.getAsInt();
                            listExoTabata.add(nameExo);
                        }

                        ExerciceTabata exo = new ExerciceTabata(listExoTabata, nbCycle, mRest, efforTime);
                        exo.setDone(done);
                        listExo.add(exo);
                    }
                }

                listSession.add(new Session(name, listExo, date));
            }
        }catch(Exception ignored){
            Log.println(Log.DEBUG, "Gson", ignored.toString());
        }

        return new User(email, listSession, listNameExo);
    }
}

package be.bonana.phenom;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by liron.bonana on 25/05/2017.
 */

public class ChallengesToJSon {

    public static String toJSon(Challenges challenges) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("username", challenges.getUserName()); // Set the first name/pair

            Iterator<Challenges.Challenge> iterator = challenges.getChallenges().iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
                JSONObject jsonAdd = new JSONObject(); // we need another object to store the address
                jsonAdd.put("title", iterator.next().getTitle());
                jsonAdd.put("description", iterator.next().getDescription());
                // We add the object to the main object
                jsonObj.put("challenge", jsonAdd);
            }

            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}

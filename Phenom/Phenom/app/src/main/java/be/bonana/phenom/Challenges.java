package be.bonana.phenom;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liron.bonana on 25/05/2017.
 */

public class Challenges {

    private String username;
    private List<Challenge> challenges;

    public Challenges(){

    }

    public Challenges(String username, List<String> titles, List<String> descriptions){
        this.username = username;
        challenges = new ArrayList<Challenge>();
    }

    public String getUserName(){return this.username;}

    public void addChallenge(String title, String description,String endate){
        challenges.add(new Challenge(title,description,endate));
    }

    public String[] getChallenge(int i){
        String[] c = new String[2];
        c[0] = challenges.get(i).getTitle();
        c[1] = challenges.get(i).getDescription();
        return c;
    }

    public List<Challenge> getChallenges(){
        return this.challenges;
    }

    public class Challenge {

        private String title;
        private String description;
        private String endate;

        public Challenge(String title, String description,String endate){
            this.title = title;
            this.description = description;
            this.endate = endate;
        }

        public String getTitle () {return this.title;}
        public String getDescription(){return this.description;}
        public String getEndate(){return this.endate;}
        public void setTitle(String title){ this.title = title;}
        public void setDescription(String description){ this.description = description;}
        public void setEndate(String endate){ this.endate = endate;}
    }



}

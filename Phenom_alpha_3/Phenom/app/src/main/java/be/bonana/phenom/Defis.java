package be.bonana.phenom;

import java.util.Date;

/**
 * Created by liron.bonana on 21/05/2017.
 */

public class Defis {
    String userName;
    String description;
    String  endDate;


    public Defis(){

    }

    public Defis(String userName,String description,String endDate){

        this.userName = userName;
        this.description = description;
        this.endDate = endDate;
    }

    public String getUserName(){return this.userName;}
    public String getDescription(){return this.description ;}
    public String getEndDate(){return this.endDate;}
    public void  setUserName(String userName){ this.userName = userName;}
    public void  setDescription(String description){this.description = description;}
    public void  setEndDate(String endDate){ this.endDate = endDate;}

}

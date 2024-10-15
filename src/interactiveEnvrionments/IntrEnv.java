package interactiveEnvrionments;

import generalInterfaces.Describable;

import java.io.Serializable;

public class IntrEnv implements Describable, Serializable {

    String intrEnvName;
    String intrEnvDescription;
    String intrEnvID;

    public IntrEnv(String intrEnvName, String intrEnvDescription, String intrEnvID){
        this.intrEnvName = intrEnvName;
        this. intrEnvDescription = intrEnvDescription;
        this.intrEnvID = intrEnvID;
    }


    /////////////
    // Getters //
    /////////////

    public String getDescribableName(){
        return intrEnvName;
    }

    public String getIntrEnvName(){
        return intrEnvName;
    }

    @Override
    public String getDescription(){
        return intrEnvDescription;
    }

    public String getIntrEnvID(){
        return intrEnvID;
    }


    /////////////
    // Setters //
    /////////////
    // IMPORTANT: Setters for all attributes, except of IntrEnvID. It must not be changed.

    public void setIntrEnvName(String name){
        this.intrEnvName = name;
    }

    public void setIntrEnvDescription(String description){
        this.intrEnvDescription = description;
    }

}

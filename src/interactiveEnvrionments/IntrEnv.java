package interactiveEnvrionments;

public class IntrEnv {

    String intrEnvName;
    String intrEnvDescription;
    String intrEnvID;
    boolean intrEnvLocked;
    boolean intrEnvEnterable;

    public IntrEnv(String intrEnvName, String intrEnvDescription, String intrEnvID, boolean intrEnvlocked, boolean intrEnvEnterable){
        this.intrEnvName = intrEnvName;
        this. intrEnvDescription = intrEnvDescription;
        this.intrEnvID = intrEnvID;
        this.intrEnvLocked = intrEnvlocked;
        this.intrEnvEnterable = intrEnvEnterable;
    }

    // Getters
    public String getIntrEnvName(){
        return intrEnvName;
    }

    public String getIntrEnvDescription(){
        return intrEnvDescription;
    }

    public String getIntrEnvID(){
        return intrEnvID;
    }

    public boolean isIntrEnvlocked() {
        return intrEnvLocked;
    }

    public boolean isIntrEnterable() {
        return intrEnvEnterable;
    }

    // Setters
    // IMPORTANT: Setters for all attributes, except of IntrEnvID. It must not be changed.
    public void setIntrEnvName(String name){
        this.intrEnvName = name;
    }

    public void setIntrEnvDescription(String description){
        this.intrEnvDescription = description;
    }

    public void setIntrEnvLocked(boolean lock){
        this.intrEnvLocked = lock;
    }

    public void setIntrEnvEnterable(boolean enterable){
        this.intrEnvEnterable = enterable;
    }

}

package interactiveEnvrionments;

import inventoryItems.Key;

import java.util.Objects;

public class LockIntrEnv extends IntrEnv{

    boolean lockIntrEnvLocked;
    String lockIntrEnvKeyID;

    public LockIntrEnv(String lockIntrEnvName, String lockIntrEnvDescription, String lockIntrEnvID, boolean lockIntrEnvLocked, String lockIntrEnvKeyID){
        super(lockIntrEnvName, lockIntrEnvDescription, lockIntrEnvID);
        this.lockIntrEnvLocked = lockIntrEnvLocked;
        this.lockIntrEnvKeyID = lockIntrEnvKeyID;
    }


    /////////////
    // Getters //
    /////////////

    public boolean getLockIntrEnvLocked(){
        return lockIntrEnvLocked;
    }

    public String getLockIntrEnvKeyID(){
        return lockIntrEnvKeyID;
    }


    /////////////
    // Setters //
    /////////////

    public void setLockIntrEnvLocked(boolean locked){
        this.lockIntrEnvLocked = locked;
    }

    public void setLockIntrEnvKeyID(String keyID){
        this.lockIntrEnvKeyID = keyID;
    }


    /////////////////////////////////
    // Post Initialization Setters //
    /////////////////////////////////

    public void addKeyIDtoLockIntrEnv(String keyID){
        this.lockIntrEnvKeyID = keyID;
    }

    //////////////////
    // Game methods //
    //////////////////

    public void unlockLockIntrEnv(){
        this.lockIntrEnvLocked = false;
    }
}


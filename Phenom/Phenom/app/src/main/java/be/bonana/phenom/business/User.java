package be.bonana.phenom.business;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by faradre on 26-03-18.
 */

@DatabaseTable(tableName = "T_User")
public class User {
    @DatabaseField (columnName = "idUser", generatedId = true)
    private String idUser;
    @DatabaseField (canBeNull = false, foreign = true, foreignColumnName = "idChallenge", foreignAutoCreate = true)
    private Challenge challenge;

    public User () {
    }

    public User(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}

package be.bonana.phenom.business;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by faradre on 26-03-18.
 */
@DatabaseTable(tableName = "T_Challenge")
public class Challenge {
    @DatabaseField(columnName = "idChallenge", generatedId = true)
    private int idChallenge;
    @DatabaseField
    private String titre;
    @DatabaseField
    private String desc;
    @DatabaseField
    private Date dateCreation;
    @DatabaseField
    private Date dateFin;
    @DatabaseField
    private String path;

    public Challenge(String titre, String desc, Date dateCreation, Date dateFin, String path) {
        this.titre = titre;
        this.desc = desc;
        this.dateCreation = dateCreation;
        this.dateFin = dateFin;
        this.path = path;
    }

    public int getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(int idChallenge) {
        this.idChallenge = idChallenge;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

package splashopserver.database;


import java.io.Serializable;

/**
 * Created by ismael on 24/01/2016.
 */

public class Item {

    private String nom;
    private double lat , lng ;
    private double prix;
    private String description;
    private String type;
    private String sub_type; // ??? peut être ??
    private double nombre;
    private boolean active = true;
    private String shopId;

    public Item(String nom , double nombre , double prix, String description, String type , String shopId) {

        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.type = type;
        this.nombre = nombre;
        this.shopId = shopId;
    }

    public void setPosition(double lng , double lat )
    {
        this.lng = lng;
        this.lat = lat;
    }
    public double getNombre() {
        return nombre;
    }

    public void setNombre(double nombre) {
        this.nombre = nombre;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }
}

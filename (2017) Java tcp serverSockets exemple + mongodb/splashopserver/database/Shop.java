package splashopserver.database;



import java.io.Serializable;
import java.util.ArrayList;


import java.util.List;

/**
 * Created by ismael on 23/01/2016.
 */
public class Shop implements Serializable {


    protected String          shop_name  ;
    protected String          description ;
    protected String          type;
    protected  int            max_item_slots ;  // nombre max d'items dans la boutique
    protected int             ratings_number;   // nombre de personnes ayant noté la boutique
    protected float           ratings;          // la somme des notes sur la boutique ex : 3 + 4 +0 +5 ...

    protected List<Item>      item_list  ;      // items stockés dans la boutique
    protected List<Comment>   comments  ;       // liste des commentaires sur la boutiques
    protected List<User>      subscribers ;     // liste des abonnés dans la boutique

    /*--------------------  TYPES DE SHOP  --------------------*/


    /*--------------------  LE 1ER TYPE DE SHOP DEDIE AUX VENTES DE PRODUITS  --------------------*/

    public static String      INFORMATIQUE   = "informatique",
                              MEDICAL        = "medical",
                              DECORS         = "decors",
                              MOBILE         = "mobile",
                              MIXED          = "mixed",
                              ELECTROMENAGER = "electromenager",
                              AUDIOVISUEL    = "photos",
                              CUISINE        = "cuisine",
                              COSMETIQUE     = "cosmetique",
                              MUSIQUE        = "musique",
                              BIBLIOTHEQUE   = "bibliotheque",
                              BIJOUX         = "bijoux",
                              SPORT          = "sport",
                              JEUX           = "jeux",
                              TABAC          = "tabaco",
                              VETEMENTS      = "fashion",
                              ACCESSOIRES    = "accessoires",


    /*--------------------      LE 2EME TYPE DE SHOP DEDIE AUX SERVICES       --------------------*/

                              RESTAURANT     = "resto",
                              CAFE           = "cafe",
                              BAR            = "bar"

    ;

    protected Shop(String name , String description){

        max_item_slots = 5;
        shop_name = name;
        this.description = description;
        ratings_number=0;
        ratings=0;

        item_list = new ArrayList<Item>();
        comments = new ArrayList<Comment>();
        subscribers = new ArrayList<User>();
    }
    public Shop(String name , String type , String description){

        this(name , description);
        this.type = type;
    }
    public Shop(String name , String type , String description  , double lat, double lng) {

        this(name,description);
        this.type = type;

        //cover_img =  ContextCompat.getDrawable(.getApplicationContext(), R.drawable.unknown_cover);



    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax_item_slots() {
        return max_item_slots;
    }

    public void setMax_item_slots(int max_item_slots) {
        this.max_item_slots = max_item_slots;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Item> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<Item> item_list) {
        this.item_list = item_list;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void addRating(float rating)
    {
        ratings_number++;
        ratings += rating;
    }
    public float getRating(){
        if(ratings_number == 0)
            return 0;
        return ratings/ratings_number;
    }
}

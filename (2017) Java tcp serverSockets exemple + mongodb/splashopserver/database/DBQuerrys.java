/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashopserver.database;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;
import org.bson.Document;
import org.bson.types.ObjectId;



public class DBQuerrys {

    public static final String HOST       = "127.0.0.1" ;
    public static final int    PORT       = 27017;
    public static final String DATABASE   = "splashop";
    public static final String COLLECTION_UTILISATEURS = "users";
    public static final String COLLECTION_ITEMS        = "items";
    public static final String COLLECTION_SHOPS        = "shops";
    public static final String COLLECTION_COMMENTAIRES  = "comments"; 
    public static final String COLLECTION_SNDATA  = "data";
    
    private int cpt_shops;

    private  String sum="",obj="";
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> user_collection , item_collection , shop_collection , comment_collection;
    
    public DBQuerrys() {
    
        client     = new MongoClient(HOST,PORT);
        database   = client.getDatabase(DATABASE);
        shop_collection = database.getCollection(COLLECTION_SHOPS);
        item_collection = database.getCollection(COLLECTION_ITEMS);
        user_collection = database.getCollection(COLLECTION_UTILISATEURS);
        comment_collection = database.getCollection(COLLECTION_COMMENTAIRES);
        
        shop_collection.createIndex(new BasicDBObject("location", "2dsphere"));
        
    }

    public FindIterable findUser(BasicDBObject object) {
        return user_collection.find(object);
    }
    public FindIterable findItem(BasicDBObject object) {
        
        
        
        return item_collection.find(object);
    }
    
    public void FindShop(double lg , double lat)
    {
        BasicDBObject criteria = new BasicDBObject("$near", new double[] { lg,lat});
        criteria.put("$maxDistance", 1000);

        BasicDBObject query = new BasicDBObject("loc", criteria);

        FindIterable cur = item_collection.find(query);
        
        if(cur.first() != null)
        {
            System.out.println(cur.first());
        }
    }
    
    public void disableShop(String id)
    {
        ObjectId oid = new ObjectId(id);
        shop_collection.updateOne(new Document("_id", oid),
        new Document("$set", new Document("isActive", false)));
    }
    
    @SuppressWarnings("unchecked")
    public String FindwithinCircle(double latitude , double longitude , double rayon , String type , String atype) {
        cpt_shops = 0;
        sum="shopquerry"; obj="";
        final LinkedList circle = new LinkedList();
        
        circle.addLast(new double[] {latitude, longitude} );

        circle.addLast(rayon);

        final BasicDBObject query= new BasicDBObject("location",
                new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", circle)));
        query.put("isActive",true);
        FindIterable cur = shop_collection.find(query);
        
        cur.forEach(new BlockImpl());
        System.out.println(cpt_shops+" shops found");
        return sum;
    }
    
    @SuppressWarnings("unchecked")
    public String findUserShops(String userid)
    {
        sum="user shops"; obj="";
        BasicDBObject object = new BasicDBObject();
        object.put("owner",userid);
        object.put("isActive",true);
        
        FindIterable cursor = getShopCollection().find(object);
        cursor.forEach(new BlockImpl());
        return sum;
    }
    public String findShopItems(String shopid) {
    
        sum="user items"; obj="";
        
        BasicDBObject object = new BasicDBObject();
        object.put("shopid",shopid);
        object.put("isActive",true);
        
        FindIterable cursor = getItemCollection().find(object);
        cursor.forEach(new BlockImpl1());
        return sum;
    }
    public String findComments(String shopid)
    {//TODO
        sum="shop comments"; obj="";
        
        BasicDBObject object = new BasicDBObject();
        object.put("shopid",shopid);
        
        FindIterable cursor = comment_collection.find(object);
        cursor.forEach(new Block() {
            @Override
            public void apply(Object t) {
                
                Document doc = (Document) t;
                
                ObjectId id =(ObjectId)(doc.get("_id"));
                String obj =
                doc.getString("username")+"+EPICOBJECTSPLITTER"+
                doc.getDouble("rating")+"+EPICOBJECTSPLITTER"+
                id.toHexString()+"+EPICOBJECTSPLITTER"+
                doc.getString("message");
                
                sum+="+SPLITTEXTSAM3OUL"+obj;
            }
        });
        return sum;
    }
    
    public FindIterable findShop(BasicDBObject object) {
        return shop_collection.find(object);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Document> getUserCollection() {
        return user_collection;
    }
    
    public MongoCollection<Document> getItemCollection() {
        return item_collection;
    }
    
    public MongoCollection<Document> getShopCollection() {
        return shop_collection;
    }
    public void insertUser (Document d) {
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        String product[]=
        {
           "electronics", "comics", "music instrument","movies","books","fashion" 
        };
        int r = (int) (Math.random()*(product.length-0.00001f));
        d.put("registered",format.toString());
        d.put("latitude", (Math.random()*180-90));
        d.put("longitude", (Math.random()*180-90));
        d.put("favoriteProduct", product[r]);
        
        user_collection.insertOne(d);
    }

    public void insertItem (Document d) {
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formatted = format1.format(cal.getTime());
        
        d.put("registered",formatted);
        d.put("isActive",true);
        item_collection.insertOne(d);
    }
    public void insertShop (Document d) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formatted = format1.format(cal.getTime());
        
        d.put("registered",formatted);
        d.put("isActive",true);
        d.put("ratings",0.0);
        d.put("ratings_number",0.0);
        shop_collection.insertOne(d);
    }
       
    
    public void insertUser (User user) {
        
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formatted = format1.format(cal.getTime());
        
        Document d = new Document();
        
        d.put("name",user.getName());
        d.put("country",user.getCountry());
        d.put("city",user.getCity());
        d.put("gender",user.getGender());
        d.put("birth",user.getBirth());
        d.put("picture",user.getPicture());
        d.put("phone",user.getPhone_number());
        d.put("adress",user.getAdress());
        d.put("registered",formatted);
        d.put("latitude",user.getPosX());
        d.put("longitude",user.getPosY());
        d.put("_id",user.getEmail());
        d.put("password",user.getPassword());
        d.put("isActive",true);
        
        String product[]=
        {
           "electronics", "comics", "music instrument","movies","books","fashion" 
        };
        int r = (int) (Math.random()*(product.length-0.00001f));
        d.put("favoriteProduct", product[r]);
        
        user_collection.insertOne(d);
    }

    public String getItems(String titre, String type, int min, int max) { 
    
        sum = "rech items";
        
        BasicDBObject object = new BasicDBObject();
        if(!titre.trim().equals(""))
            object.put("titre",titre);
        
        object.put("isActive",true);
        object.put("type",type);
        object.put("price",new Document("$gte", min).append("$lte", max));
        
        System.out.println(object);
        
        FindIterable cursor = getItemCollection().find(object);
        cursor.forEach(new BlockImpl1());
        
        return sum;
    }
    public String findOneComment(String userid, String shopid) {
        
        sum="shop comment";
                
        BasicDBObject object = new BasicDBObject();
        object.put("userid",userid);
        object.put("shopid",shopid);
        FindIterable cur = comment_collection.find(object);
        
        
        cur.forEach(new Block() {
            @Override
            public void apply(Object t) {
                Document doc = (Document)t;
                ObjectId id =(ObjectId)(doc.get("_id"));
                sum+="+SPLITTEXTSAM3OUL"+
                doc.getString("userid")+"+SPLITTEXTSAM3OUL"+
                doc.getString("shopid")+"+SPLITTEXTSAM3OUL"+
                id.toHexString()+"+SPLITTEXTSAM3OUL"+
                doc.getString("message");
            }
        });
        System.out.println(sum);
        return sum;
    }

    public void InsertComment(String mail, String shopid, String msg, double rating, String nom) {
    
        if( !findOneComment(mail, shopid).equals("shop comment") )
            return;
        
        shop_collection.updateOne(new Document("_id", new ObjectId(shopid)),
        new Document("$inc", new Document("ratings", rating)));
        
        shop_collection.updateOne(new Document("_id", new ObjectId(shopid)),
        new Document("$inc", new Document("ratings_number", 1.0)));
        
        Document d = new Document();
        d.put("userid", mail);
        d.put("shopid", shopid);
        d.put("message", msg);
        d.put("rating",rating);
        d.put("username",nom);
        
        comment_collection.insertOne(d);
    }

    
    //public Comment(String userid, String shopid, String id, String message)
    private class BlockImpl implements Block<Document> {

        public BlockImpl() {
        }
        @SuppressWarnings("unchecked")
        @Override
        public void apply(final Document document) {
            cpt_shops++;
            Document doc = (Document)document.get("location");
            ArrayList<Double> coords =  (ArrayList<Double>) doc.get("coordinates");
            
            ObjectId id = (ObjectId) document.get("_id");
            
            obj =id.toHexString()+"+EPICOBJECTSPLITTER"+
                    document.getString("owner")+"+EPICOBJECTSPLITTER"+
                    document.getBoolean("isActive")+"+EPICOBJECTSPLITTER"+
                    document.getString("picture")+"+EPICOBJECTSPLITTER"+
                    document.getString("name")+"+EPICOBJECTSPLITTER"+
                    document.getString("phone")+"+EPICOBJECTSPLITTER"+
                    document.getDouble("ratings_number")+"+EPICOBJECTSPLITTER"+
                    document.getDouble("ratings")+"+EPICOBJECTSPLITTER"+
                    coords.get(0)+"+EPICOBJECTSPLITTER"+
                    coords.get(1)+"+EPICOBJECTSPLITTER"+
                    document.getString("type")+"+EPICOBJECTSPLITTER"+
                    document.getString("description")
                    ;
            
            sum=sum+"+SPLITTEXTSAM3OUL"+obj;
        }
    }

    private class BlockImpl1 implements Block {

        public BlockImpl1() {
        }

        @SuppressWarnings("unchecked")
        @Override
        public void apply(Object t) {
            Document document = (Document)t;
            Document doc = (Document)document.get("location");
            ArrayList<Double> coords =  (ArrayList<Double>) doc.get("coordinates");
            
            ObjectId id = (ObjectId) document.get("_id");
            
            obj =id.toHexString()+"+EPICOBJECTSPLITTER"+
                    document.getString("shopid")+"+EPICOBJECTSPLITTER"+
                    document.getString("title")+"+EPICOBJECTSPLITTER"+
                    coords.get(0)+"+EPICOBJECTSPLITTER"+
                    coords.get(1)+"+EPICOBJECTSPLITTER"+
                    document.getString("type")+"+EPICOBJECTSPLITTER"+
                    document.getString("description")+"+EPICOBJECTSPLITTER"+
                    document.getDouble("number")+"+EPICOBJECTSPLITTER"+
                    document.getDouble("price")
                    ;
            sum=sum+"+SPLITTEXTSAM3OUL"+obj;
        }
    }
    
}
/*
db.collectionname.find({"loc":{$near:{$geometry:
    {type:"Point", coordinates:[50.0 , -0.1330]}, $maxDistance:500}}})

db.collectionname.find({ "loc" : { $near : [50.0 , -0.1330] , $maxDistance : 10000 }})

db.places.find( { loc :
                  { $geoWithin :
                     { $box : [ [ 20 , -5 ] ,
                                [ 40 , 10 ] ]
                 } } } )


db.shops.find( { loc : 
                  { $near : [ 100 , 100 ],
                    $maxDistance: 10 }

} )
*/
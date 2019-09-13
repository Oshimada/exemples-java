

package splashopserver.server;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.Document;
import splashopserver.database.User;


public class clientThread extends Thread{
    
    
    
    
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private User user;
    private static int client_number = 0;
    
    public static final Pattern REGEX =   Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  public clientThread(Socket clientSocket, clientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
  }
  @SuppressWarnings("deprecation")
  public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;

    try {
        
        is = new DataInputStream(clientSocket.getInputStream());
        os = new PrintStream(clientSocket.getOutputStream());
        String logs;
        while (true) 
        {
            if( is == null) break;  
            logs = is.readLine();
            if (logs.startsWith("/quit"))  break;
            
            String log[]=logs.trim().split("\\+SPLITTEXTSAM3OUL");
            
            System.out.println("received : mail:"+log[0]+"   password:"+log[1]);
            
            if (log.length == 2 && isEmailValid(log[0]) && log[1].length()>3) {
                
                BasicDBObject object = new BasicDBObject();
                Pattern regex = Pattern.compile(log[0]);
                object.put("_id",regex);
                
                
                FindIterable cursor = ServerSync.getDB().findUser(object);
                
                if(cursor.first()!=null)
                {
                    Document d = (Document) cursor.first();
                    
                    if(!d.getString("password").equals(log[1]))
                    {
                        os.println("bad password");
                        System.out.println("mauvais mot de passe");
                    }
                    else 
                    if(d.getBoolean("isActive")== false)
                    {
                        os.println("inactif account");
                        System.out.println("compte inactif");
                    }
                    else
                    {
                        System.out.println("clients : "+(++client_number));
                        
                        os.println("connected"+"+SPLITTEXTSAM3OUL"+
                            d.getString("name")+"+SPLITTEXTSAM3OUL"+
                            d.getString("gender")+"+SPLITTEXTSAM3OUL"+
                            d.getString("birth")+"+SPLITTEXTSAM3OUL"+
                            d.getString("picture")+"+SPLITTEXTSAM3OUL"+
                            d.getString("phone")+"+SPLITTEXTSAM3OUL"+
                            d.getString("address")+"+SPLITTEXTSAM3OUL"+
                            d.getString("registered")+"+SPLITTEXTSAM3OUL"+
                            d.getDouble("latitude")+"+SPLITTEXTSAM3OUL"+
                            d.getDouble("longitude")+"+SPLITTEXTSAM3OUL"+
                            d.getString("favoriteProduct")
                            );
                        break;
                    }    
                }
                else
                {
                    user = new User();

                    user.setEmail(log[0]);
                    user.setPassword(log[1]);

                    os.println("new user");
                    System.out.println(clientSocket.getInetAddress()+" procede à l'inscription");
                    
                     break;
                }
                
                System.out.println(clientSocket.getInetAddress()+" connecté");
            
            } else {
              os.println("not connected");
            }
        }
        if(user != null)
        while (true) {
            
            if( is == null) break;  
            logs = is.readLine();
            if (logs.startsWith("/quit"))  break;
            
            String log[]=logs.trim().split("\\+SPLITTEXTSAM3OUL");
            
            if (log.length >= 3) {
                
                user.setName(log[0]);
                user.setCountry(log[1]);
                user.setPhone_number(log[2]);
                user.setGender(log[3]);
                user.setBirth(log[4]);
                user.setPicture("null");
                user.setPhone_number("null");
                user.setAdress(log[5]);
                user.setRegisterDate("null");
                user.setPosX(20);
                user.setPosY(20);
                user.setFavProduct("null");
                os.println("form received");
                System.out.println(user.toString());
                ServerSync.getDB().insertUser(user);
                break;
            } else {
              os.println("bad form");
            }
        }
        while(true){
            
            logs = is.readLine();
            if (logs == null || logs.startsWith("/quit"))  break;
        
            String log[]=logs.trim().split("\\+SPLITTEXTSAM3OUL");
            System.out.println(log[0]);
            if(log[0].equals("get shops at"))
            {            
                double lat = Double.parseDouble(log[1]);
                double lg = Double.parseDouble(log[2]);
                double rad = Double.parseDouble(log[3]);
                double rating = Double.parseDouble(log[5]);
                
                String msg = ServerSync.getDB().FindwithinCircle(lg,lat, rad ,log[4],log[6]);
                
                os.println(msg);
            }
            else if(log[0].equals("get user shops"))
            {   
                String msg = ServerSync.getDB().findUserShops(log[1]);
                os.println(msg);
                
            }
            else if(log[0].equals("insert shop"))
            {
                ArrayList<Double> cords = new ArrayList<>();
                cords.add(Double.parseDouble(log[5]));
                cords.add(Double.parseDouble(log[4]));
                
                Document location = new Document();
                location.put("coordinates",cords);
                location.put("type", "Point");
                
                Document d = new Document();
                d.put("owner",log[1]);
                d.put("name",log[2]);
                d.put("phone",log[3]);
                d.put("location",location);
                d.put("type",log[6]);
                d.put("description",log[7]);
                
                ServerSync.getDB().insertShop(d);
                
            }
            else if (log[0].equals("delete shop ")){
                
                ServerSync.getDB().disableShop(log[1]);
            }
            else if(log[0].equals("insert item"))
            {
                ArrayList<Double> cords = new ArrayList<>();
                cords.add(Double.parseDouble(log[4]));
                cords.add(Double.parseDouble(log[5]));
                Document location = new Document();
                location.put("coordinates",cords);
                location.put("type", "Point");
                
                Document d = new Document();
                d.put("title",log[1]);
                d.put("phone",log[2]);
                d.put("shopid",log[3]);
                d.put("location",location);
                d.put("type",log[6]);
                d.put("description",log[7]);
                d.put("number",Double.parseDouble(log[8]));
                d.put("price",Double.parseDouble(log[9]));
                
                ServerSync.getDB().insertItem(d);
                    /*
                        titre.getText().toString()+"+SPLITTEXTSAM3OUL"+
                        shop.getNumtel()+"+SPLITTEXTSAM3OUL"+
                        shop.getId()+"+SPLITTEXTSAM3OUL"+
                        shop.getLng()+"+SPLITTEXTSAM3OUL"+
                        shop.getLat()+"+SPLITTEXTSAM3OUL"+5
                        shop.getType()+"+SPLITTEXTSAM3OUL"+
                        description.getText().toString()+"+SPLITTEXTSAM3OUL"+
                        quantite.getText().toString()+"+SPLITTEXTSAM3OUL"+
                        prix.getText().toString();
            
                        if (log[0].equals("user items"))
                        */
            }
            else if(log[0].equals("get user items"))
            {
                String msg = ServerSync.getDB().findShopItems(log[1]);
                os.println(msg);
                String ms  = ServerSync.getDB().findComments(log[1]);
                os.println(ms);
            }
            else if(log[0].equals("get comment"))
            {
                String msg = ServerSync.getDB().findOneComment(log[1],log[2]);
                os.println(msg);
            }
            else if(log[0].equals("insert comment"))
            {
                double rate = Double.parseDouble(log[4]);
                ServerSync.getDB().InsertComment(log[1],log[2],log[3],rate,log[5]);
            }
            else if(log[0].equals("get items at"))
            {
                int min = Integer.parseInt(log[3]);
                int max = Integer.parseInt(log[4]);
                
                System.out.println(""+log[1]);
                
                String msg = ServerSync.getDB().getItems(log[1],log[2],min,max);
                os.println(msg);
                
                System.out.println(""+msg);
               
            }
            else if(log[0].equals("stopmap"))
                break;
        }
        
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
      System.out.println("clients : "+(--client_number));
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
   private boolean isEmailValid(String email) {
        Matcher matcher = REGEX.matcher(email);
        return matcher.matches();
    }

}

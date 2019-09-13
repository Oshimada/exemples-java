package splashopserver.database;




import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ismael on 23/01/2016.
 */
public class User implements Serializable {

    protected String      name  ;
    protected String      phone_number  ;
    protected String      id; //email
    protected String      password;
    protected String      profile_img;
    protected FacebookData idata;
    protected List<Shop>  subscription  ; /* a reviser */
    protected String        birth  ;
    protected List<Shop>  shops  ;
    protected double      posX,posY; 
    protected String      country;
    protected String      city;
    protected String      gender;
    protected String      picture;
    protected String      adress;
    protected String      registerDate;
    protected String      favProduct;

    
    
    
    
    @Override
    public String toString() {
        return "User{" + "name=" + name + ", phone_number=" + phone_number + ", id=" + id + ", password=" + password + ", profile_img=" + profile_img + ", idata=" + idata + ", subscription=" + subscription + ", birth=" + birth + ", shops=" + shops + ", posX=" + posX + ", posY=" + posY + ", country=" + country + ", city=" + city + ", gender=" + gender + ", picture=" + picture + ", adress=" + adress + ", registerDate=" + registerDate + ", favProduct=" + favProduct + '}';
    }

    public String getFavProduct() {
        return favProduct;
    }

    public void setFavProduct(String favProduct) {
        this.favProduct = favProduct;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return id;
    }

    public void setEmail(String email) {
        this.id = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosX() {
        return posX;
    }


    public void setShops(List<Shop> shops) {
        if(shops != null)
            this.shops = shops;
    }
    public List<Shop> getShops(){
        return shops;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public List<Shop> getSubscription() {
        return subscription;
    }

    public void setSubscription(List<Shop> subscription) {
        this.subscription = subscription;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String bi) {
        this.birth = birth;
    }
    public int getAge(){
        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.US);
        Calendar dob = Calendar.getInstance();
        try {  
            dob.setTime(format1.parse(birth));
        
        } catch (ParseException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Calendar today = Calendar.getInstance();  
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
          age--;  
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
            && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
          age--;  
        }
        return age;
        
    }
}

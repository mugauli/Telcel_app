package Utitilies;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mugauli on 01/06/2016.
 */
public class Comunication  {

    public String Post (String dato,String pass,String token,String campo){

        HttpClient httpClient = new DefaultHttpClient();
        //Create an object of HttpPost

        HttpPost httpPost = new HttpPost("http://internetencaja.com.mx/telcel/WServices/GetLogin.php");
        //Add POST parameters

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("dato", "ANDROID"));
        nameValuePair.add(new BasicNameValuePair("password", "09721ab88e0a552087391be1ef0c6826"));
        nameValuePair.add(new BasicNameValuePair("token", "67d6b32e8d96b8542feda3df334c04f5"));
        nameValuePair.add(new BasicNameValuePair("campo", "E"));
        //Encode POST data

        //We need to encode our data into valid URL format before making HTTP request.

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return "a";
    }
}

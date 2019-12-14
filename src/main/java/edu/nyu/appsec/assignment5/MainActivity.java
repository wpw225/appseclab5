package edu.nyu.appsec.assignment5;

//import android.Manifest;
//import android.content.Context;
import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
import android.net.Uri;
//import android.net.http.SslError;
//import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.SerializablePermission;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;

//public class MainActivity extends AppCompatActivity implements LocationListener {
public class MainActivity extends AppCompatActivity {
    private static final String SPELL_CHECK_URL = "http://appsecclass.report:8080/";
    private static final String KNOWN_HOST = "appsecclass.report";

    //Private class definition for loading spell check web app on an external http server
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = String.valueOf(request.getUrl());
            String host = Uri.parse(url).getHost();

            if (KNOWN_HOST.equals(host)) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    /* Get location data to provide language localization
    *  Supported languages ar-DZ zh-CN en-US en-IN en-AU fr-FR
    */
   // @Override
    //Used for receiving notifications from the LocationManager when the location has changed
  /*
    public void onLocationChanged(Location location) {

        //Creates URL string with location info
        URL url = null;
        try {
            url = new URL(SPELL_CHECK_URL + "metrics"
                    +"?lat="
                    +location.getLatitude()+"&long=" + location.getLongitude()
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        //Opens the connection to specified URL with location info
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            //Disconnects immediately without attempting to do anything.
            // This appears to be the app uploading location info
            // tracker of the device that uses this App
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    /* Necessary to implement the LocationListener interface

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
    */

    @Override
    //Every Activity made starts through a sequence of method calls. onCreate() is the first of these calls.
    protected void onCreate(Bundle savedInstanceState) {
        //The savedInstanceState bundle contains information recorded by the Android framework that the super method will then use to restore state
        super.onCreate(savedInstanceState);

        //Set XML file for main layout when app starts
        setContentView(R.layout.activity_main);

        //WebView class is an extension of Android's View class that allows the display of web pages as a part of an activity layout
        WebView view = new WebView(this);
        //Instantiates private class defined above to connect to webserver hosting spellchecker app
        view.setWebViewClient(new MyWebViewClient());

        //Manages settings state for a WebView. When a WebView is first created, it obtains a set of default settings
        WebSettings settings = view.getSettings();
        //Sets whether JavaScript running in the context of a file scheme URL should be allowed to access content from other file scheme URLs.
        //To enable the most restrictive, and therefore secure, policy this setting should be disabled.
        settings.setAllowFileAccessFromFileURLs(true);
        //Tells the WebView to enable JavaScript execution. The default is false.
        settings.setJavaScriptEnabled(true);
        //Sets whether JavaScript running in the context of a file scheme URL should be allowed to access content from any origin.
        settings.setAllowUniversalAccessFromFileURLs(true);
/*
        //LocationManager is the main class through which your application can access location services on Android.
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Ensure that the required permission before calling an locationManager API
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            //This class provides access to the system location services. These services allow applications to obtain periodic updates of the device's geographical location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
*/
        //Set the activity content from a layout resource
        setContentView(view);
        //Loads the given URL, spell check app registration page
        view.loadUrl(SPELL_CHECK_URL + "register");
    }
}

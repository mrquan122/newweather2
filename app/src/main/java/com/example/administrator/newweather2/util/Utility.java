package com.example.administrator.newweather2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/3/15.
 */
public class Utility {

public static boolean handleWeatherResponse(Context context,InputStream response)
        throws XmlPullParserException, IOException, JSONException {
    BufferedReader br = new BufferedReader(new InputStreamReader(response, "utf-8"));
    String line = null;
    StringBuilder builder=new StringBuilder();
    while ((line = br.readLine()) != null) {
        builder.append(line);
    }


    Log.i("newweater",builder.toString());

    JSONObject jsonObject = new JSONObject(builder.toString());
    String cityName=jsonObject.getJSONObject("data").getString("city");
    JSONArray numberList = jsonObject.getJSONArray("forecast");

      String[] weather=new String[6];
      String[] degree1=new String[6];
      String[]degree2=new  String[6];
      String[]windy1=new String[6];
      String[]windy2=new String[6];
      String[]date=new String[6];

     for (int i=0;i<numberList.length();i++) {
          weather[i]=numberList.getJSONObject(i).getString("type");
          degree1[i]=numberList.getJSONObject(i).getString("高温");
          degree1[i]=numberList.getJSONObject(i).getString("低温");
          windy1[i]=numberList.getJSONObject(i).getString("fengxiang");
          windy2[i]=numberList.getJSONObject(i).getString("fengli");
          date[i]=numberList.getJSONObject(i).getString("date");
         Log.i("wendu",degree1[i]);
         saveWeatherInfo(context, cityName, weather[i],degree1[i] ,degree2[i] , windy1[i],windy2[i],date[i]);

     }



 //   Log.i("weatherinfo",cityName+temp1+"--");

    return true;
}


    /**
     *将服务器返回的的所有天气信息存储到 SharedPreference文件中。
     *
     */
    public static void saveWeatherInfo(Context context,String cityName,String weather,  String temp1,
                                     String temp2,String windy1,String windy2,String date){
      //  SimpleDateFormat sdf =new SimpleDateFormat("yyy年M月D日", Locale.CANADA);
        int j=0;
;       SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather"+j,weather);
        editor.putString("temp1"+j,temp1);
        editor.putString("temp2"+j,temp2);
        editor.putString("windy1"+j,windy1);
        editor.putString("windy2"+j,windy2);
        editor.putString("date"+j,date);



      //  editor.putString("current_data",sdf.format(new Date()));
        editor.commit();
        j=j+1;

    }
}


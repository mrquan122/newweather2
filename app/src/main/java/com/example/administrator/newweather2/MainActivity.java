package com.example.administrator.newweather2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.administrator.newweather2.util.HttpCallbacListener;
import com.example.administrator.newweather2.util.HttpUtil;
import com.example.administrator.newweather2.util.Utility;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity implements View.OnClickListener
        //   implements View.OnClickListener
{

    private LinearLayout weatherInfoLayout;

    /**
     * 用于显示城市名
     */
    private TextView cityNameText;
    /**
     * 用于显示发布时间
     */
    private TextView publishText;
    /**
     * 用于显示天气描述
     */
    private TextView weatherDespText;
    /**
     * 用于显示气温1
     */
    private TextView temp1Text;
    /**
     * 用于显示气温2
     */
    private TextView temp2Text;
    /**
     * 用于显示当前日期
     */
    private TextView currentDateText;
    /**
     * 用于显示切换城市按钮
     */
    private Button swithCity;
    /**
     * 更新天气按钮
     */
    private Button refreshWeather;


    private ImageView image01;
    private ImageView img0;
    private ImageView img1;
    private ImageView img2;
    private TextView weather0;
    private TextView weather1;
    private TextView weather2;
    private TextView temp0;
    private TextView temp1;
    private TextView temp2;
    private TextView windy0;
    private TextView windy1;
    private TextView windy2;
    private TextView windyCount0;
    private TextView windyCount1;
    private TextView windyCount2;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化各空件
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather);
        temp1Text = (TextView) findViewById(R.id.temp1);
        currentDateText = (TextView) findViewById(R.id.current_date);
        swithCity = (Button) findViewById(R.id.swith_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);

        image01 = (ImageView) findViewById(R.id.image);

        img0 = (ImageView) findViewById(R.id.img0);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);

        weather0 = (TextView) findViewById(R.id.weather0);
        weather1 = (TextView) findViewById(R.id.weather1);
        weather2 = (TextView) findViewById(R.id.weather2);

        temp0 = (TextView) findViewById(R.id.weather0);
        temp1 = (TextView) findViewById(R.id.weather1);
        temp2 = (TextView) findViewById(R.id.weather2);

        windy0 = (TextView) findViewById(R.id.windy0);
        windy1 = (TextView) findViewById(R.id.windy1);
        windy2 = (TextView) findViewById(R.id.windy2);

        windyCount0 = (TextView) findViewById(R.id.windyCount0);
        windyCount1 = (TextView) findViewById(R.id.windyCount1);
        windyCount2 = (TextView) findViewById(R.id.windyCount2);


        swithCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);

   /*     fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(WeatherActivity.this,searchActivity.class);
                startActivity(intent1);

            }
        });  */

        queryWeatherInfo();



        ;



    }


    @Override
    public void onClick(View v) {
        queryWeatherInfo();
    }


    /**
     * 查询天气代号所对应的天气
     */
    private void queryWeatherInfo() {
        //  String address ="http://flash.weather.com.cn/wmaps/xml/" + cityCode + ".xml";
        //   String address="http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode="+cityCode+"&weatherType=0";
        String address = "http://wthrcdn.etouch.cn/weather_mini?city=北京 ";
        queryFromServer(address);

    }

    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
    private void queryFromServer(final String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbacListener() {
            @Override
            public void onFinish(InputStream response) throws IOException, XmlPullParserException, JSONException {
                //处理服务器返回的天气信息
                Utility.handleWeatherResponse(MainActivity.this, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                        Log.i("showweather", "hello world");
                    }
                });

            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });

            }
        });


    }

    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name","")
        );
        weather0.setText(prefs.getString("weather0",""));
        weather1.setText(prefs.getString("weather1",""));
        weather2.setText(prefs.getString("weather2",""));

        temp0.setText(prefs.getString("temp10",""));
        temp1.setText(prefs.getString("temp11",""));
        temp2.setText(prefs.getString("temp12",""));

        windy0.setText(prefs.getString("windy10",""));
        windy1.setText(prefs.getString("windy11",""));
        windy2.setText(prefs.getString("windy12",""));

        windyCount0.setText(prefs.getString("windy20",""));
        windyCount1.setText(prefs.getString("windy21",""));
        windyCount2.setText(prefs.getString("windy22",""));



        currentDateText.setText(prefs.getString("d","")
        );
        weatherInfoLayout.setVisibility(View.VISIBLE);
        String imgCode=prefs.getString("img1","");
        String weathercount="晴多云阴小雨中雨大雨暴雨大暴雨特大暴雨阵雨雷阵雨雷阵雨伴有冰雹雨夹雪阵雪小雪中雪大雪暴雪" +
                "雾冻雨沙尘暴小雨转中雨中雨转大雨大雨转暴雨暴雨转大暴雨大暴雨转特大暴雨小雪转中雪中雪转大雪大雪转暴雪浮尘扬沙强沙尘暴霾";
          String str=prefs.getString("weather0","");
        int imgCode=weathercount.indexOf();
        queryWeatherView(imgCode);
    }
    private void queryWeatherView(String imgCode) {
        String address = "http://m.weather.com.cn/img/a" + imgCode + ".gif";
        try {
            InputStream inputStream = getResources().getAssets().open("wimg/w" + imgCode + ".png");
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    image01.setImageBitmap(bitmap);


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
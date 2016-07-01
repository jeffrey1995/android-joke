package com.example.mrtian.joke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrtian.joke.utils.ReadFile;
import com.example.mrtian.joke.view.CalendarView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CalendarView calendar;
    private Button btn_lastMonth;
    private Button btn_nextMonth;
    private TextView tv_currentDate;
    private TextView tv_jokes;
    private int num_jokes = 0; //笑话个数
    private String markDate = "2016-3-10";
    private String[] jokes;
    private ArrayList<String> jokesList;
    private String str_nowDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tv_currentDate.setText(calendar.getYearAndmonth());
        //设置监听事件
        MyOnclickListener myOnclickListener = new MyOnclickListener();
        btn_lastMonth.setOnClickListener(myOnclickListener);
        btn_nextMonth.setOnClickListener(myOnclickListener);

        InputStream inputStream = getResources().openRawResource(R.raw.jokes);

        //读取文件存入数组
        jokesList = ReadFile.getString(inputStream);
        num_jokes = jokesList.size();
        if(num_jokes != 0)
        {
            //初始化数组
            jokes = new String[num_jokes];
            for (int i=0;i<jokes.length;i++)
            {
                jokes[i] = jokesList.get(i);
            }
            //获取当前时间
            Calendar c = Calendar.getInstance();
            str_nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            Log.w("nowDate",str_nowDate);
            Date date = c.getTime();//获取当天日期
            showJoke(date);

            MyOnItemClickListener myOnItemClickListener = new MyOnItemClickListener();
            calendar.setOnItemClickListener(myOnItemClickListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initView() {
        calendar = (CalendarView)findViewById(R.id.calendar);
        btn_lastMonth = (Button)findViewById(R.id.btn_lastMonth);
        btn_nextMonth = (Button)findViewById(R.id.btn_nextMonth);
        tv_currentDate = (TextView)findViewById(R.id.tv_currentDate);
        tv_jokes = (TextView)findViewById(R.id.tv_jokes);

    }
    private int compareToStartDay(String str_date)
    {

        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        long temp;
        long d = 0;
        try {
            beginDate = format.parse(markDate);
            endDate = format.parse(str_date);
            temp = endDate.getTime() - beginDate.getTime();
            d = temp/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("d***"+d);

        return ((int) d);
    }

    private boolean compareToNowDay(String str_date)
    {

        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        long temp;
        long d = 0;
        try {
            beginDate = format.parse(str_nowDate);
            endDate = format.parse(str_date);
            temp = endDate.getTime() - beginDate.getTime();
            d = temp/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("d***"+d);

        return (d>0);
    }

    private void showJoke(Date date)
    {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");

        int c = compareToStartDay(format.format(calendar.getSelectedStartDate()));
        System.out.println("c|||||" + c);
        if (c < 0)
        {
            tv_jokes.setText("这里没有哦，亲！");
        }
        else if(c > num_jokes - 1)
        {
            tv_jokes.setText("哈哈，还不错吧，尽请期待~~");
        }
        else if(compareToNowDay(format.format(calendar.getSelectedStartDate())))
        {
            tv_jokes.setText("没到日期还不能看哦，亲！");
        }
        else
        {
            tv_jokes.setText(jokes[c]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class MyOnclickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btn_lastMonth:
                    calendar.clickLeftMonth();
                    tv_currentDate.setText(calendar.getYearAndmonth());
                    break;
                case R.id.btn_nextMonth:
                    calendar.clickRightMonth();
                    tv_currentDate.setText(calendar.getYearAndmonth());
                    break;
                default:
                    break;
            }
        }
    }

    class MyOnItemClickListener implements CalendarView.OnItemClickListener
    {

        @Override
        public void OnItemClick(Date date) {
            showJoke(date);

        }
    }

}

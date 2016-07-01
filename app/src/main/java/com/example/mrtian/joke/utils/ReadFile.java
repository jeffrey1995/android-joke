package com.example.mrtian.joke.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by mrtian on 2016/3/10.
 */
public class ReadFile {
    public static ArrayList<String> getString(InputStream inputStream) {
        ArrayList<String> jokesList = new ArrayList<String>();
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        String temp="";
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println("Line----------"+line);
                if (line.equals(""))
                {
                    jokesList.add(temp);
                    temp = "";
                }
                else
                {
                    temp += line + "\n";
                }
                sb.append(line);
                sb.append("\n");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jokesList;
    }
}

package com.example.algomasterapp;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSV_Helper {

    InputStream inputStream;

    public CSV_Helper(InputStream inputStream){
        this.inputStream = inputStream;
    }

    private List<String[]> ReadFile() {

        List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return resultList;
    }

    public String[] GetLine(int id){

        return ReadFile().get(id);
    }

    public int CheckForUser(String uName, String pWord){

        List<String[]> resultList = ReadFile();
        String[] match;

        for (String[] user: resultList) {

            // check for matching username
            if (user[0].equalsIgnoreCase(uName)){

                // check for matching password
                if (user[1].equals(pWord)){
                    // return 0 SUCCESS
                    return 0;
                }
                else{
                    // return 1 INCORRECT PASSWORD
                    return 1;
                }
            }

        }

        // return 2 WRONG USERNAME
        return 2;
    }
}

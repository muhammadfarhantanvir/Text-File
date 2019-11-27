package com.example.textfileexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText etFname, etLname;
    Button btnAdd, btnSaveFille;
    TextView tvResult;

    ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);

        tvResult = findViewById(R.id.tvResult);
        persons = new ArrayList<Person>();
        loadData();
    }

    public void btnAddData(View v)
    {

        String fname = etFname.getText().toString().trim();
        String lname = etLname.getText().toString().trim();

        Person person = new Person(fname, lname);
        persons.add(person);

        setTextToTextView();

    }

    private void setTextToTextView() {

        String txt = "";

        for(int i = 0 ; i < persons.size() ; i++)
        {
            txt = txt + persons.get(i).getFname() + " " +
                    persons.get(i).getLanme() + "\n";
        }

        tvResult.setText(txt);
    }
    public void loadData()
    {
        persons.clear();

        File file = getApplicationContext().getFileStreamPath("Data.txt");
        String lineFromFile;
        if(file.exists())
        {
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));
                while((lineFromFile = reader.readLine()) != null)
                {
                    StringTokenizer token = new StringTokenizer(lineFromFile, ",");
                    Person person = new Person(token.nextToken(), token.nextToken());
                    persons.add(person);
                }
                reader.close();
                setTextToTextView();
            }
            catch (Exception e)
            {

            }

        }

    }

    public void btnSaveData(View v)
    {
        try{
            FileOutputStream file = openFileOutput("Data.txt", MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(file);

            for(int i = 0 ; i < persons.size() ; i++)
            {
                outputFile.write(persons.get(i).getFname() + ","+persons.get(i).getLanme() + "\n");
            }
            outputFile.flush();
            outputFile.close();

            Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {

        }

    }
}

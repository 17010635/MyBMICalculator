package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etHeight;
    Button btnCalc, btnReset;
    TextView tvDate, tvBmi, tvDisplay;

    String datetime;
    float bmi;
    String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etWeight.setSelection(0);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBmi = findViewById(R.id.textViewBMI);
        tvDisplay=findViewById(R.id.textViewDisplay);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());

                bmi = weight / (height * height);
                if(bmi < 18.5) {
                    msg="You are Underweight";
                }else if(bmi < 25){
                    msg="Your BMI is Normal";
                }else if(bmi < 30) {
                    msg="You are Overweight";
                }else {
                    msg="You are Obese";
                }
                bmi = 0;
                bmi = weight / (height * height);
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText(datetime);
                tvBmi.setText(String.format("%.3f",bmi));
                tvDisplay.setText(msg);
                etHeight.setText("");
                etWeight.setText("");

            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText("");
                tvBmi.setText("");
                bmi = 0;
                tvDisplay.setText("");
                etWeight.setText("");
                etHeight.setText("");

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("datetime",datetime);
        prefEdit.putFloat("bmi", bmi);
        prefEdit.putString("msg", msg);
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date = prefs.getString("datetime", "");
        Float bmiResult = prefs.getFloat("bmi",0);
        String displayResult = prefs.getString("msg","");
        tvDate.setText(date);
        tvBmi.setText(Float.toString(bmiResult));
        tvDisplay.setText(displayResult);

    }

}

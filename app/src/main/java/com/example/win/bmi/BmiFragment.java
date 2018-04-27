package com.example.win.bmi;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


public class BmiFragment extends Fragment {
    private TextView heightTxt;
    private TextView weightTxt;
    private EditText heightEdt;
    private EditText weightEdt;
    private Button calculateBtn;
    private TextView bmiValueTxt;
    private TextView adviceTxt;
    private TextView[] advices;
    private String[] item;
    private View view;
    private BMI bmi_system = new BMI(0);
    public static final String PREFERENCE_NAME = "SaveSetting";
    public static int MODE = MODE_PRIVATE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bmi, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        setListeners();
    }

    private void findViews() {
        heightTxt = view.findViewById(R.id.txt_height);
        weightTxt = view.findViewById(R.id.txt_weight);
        heightEdt = view.findViewById(R.id.edt_height);
        weightEdt = view.findViewById(R.id.edt_weight);
        calculateBtn = view.findViewById(R.id.btn_calculate);
        bmiValueTxt = view.findViewById(R.id.txt_bmi);
        adviceTxt = view.findViewById(R.id.txt_advice);
        advices = new TextView[6];
        advices[0] = view.findViewById(R.id.advise1_1);
        advices[1] = view.findViewById(R.id.advise1_2);
        advices[2] = view.findViewById(R.id.advise2_1);
        advices[3] = view.findViewById(R.id.advise2_2);
        advices[4] = view.findViewById(R.id.advise3_1);
        advices[5] = view.findViewById(R.id.advise3_2);
    }

    private void setListeners() {
        if (bmi_system.getSystem() == 0) {
            heightTxt.setText(getString(R.string.height));
            weightTxt.setText(getString(R.string.weight));
        } else {
            heightTxt.setText(getString(R.string.height_fin));
            weightTxt.setText(getString(R.string.weigh_flb));
        }
        Resources res = getResources();
        item = res.getStringArray(R.array.advice);
        String bmiString = String.format(getResources().getString(R.string.bmi_value), " ");
        String adviceString = String.format(getResources().getString(R.string.advice), " ");
        bmiValueTxt.setText(bmiString);
        adviceTxt.setText(adviceString);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heightEdt.getText().toString().equals("") || weightEdt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), R.string.please_input_message, Toast.LENGTH_LONG).show();
                    return;
                }
                String heightStr = heightEdt.getText().toString();
                String weightStr = weightEdt.getText().toString();
                double height = Double.parseDouble(heightStr);
                double weight = Double.parseDouble(weightStr);
                BMI bmi = new BMI(height, weight, bmi_system.getSystem());
                String bmiValue = bmi.getBmiValueOfString();
                int advice = bmi.getBmiAdvice();
                String bmiString = getString(R.string.bmi_value, bmiValue);
                if (advice == 0) {
                    adviceTxt.setText(getString(R.string.advice, item[0]));
                } else if (advice == 1) {
                    adviceTxt.setText(getString(R.string.advice, item[1]));
                } else if (advice == 2) {
                    adviceTxt.setText(getString(R.string.advice, item[2]));
                }
                setAdviceColor(advice);
                bmiValueTxt.setText(bmiString);
            }
        });
    }

    private void setAdviceColor(int advice){
        for (int i = 0; i < 6; i++) {
            advices[i].setBackgroundColor(Color.parseColor("#ffffff"));
            advices[i].setTextColor(Color.parseColor("#000000"));
        }
        if(advice==2){
            advices[0].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_button_back));
            advices[1].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_button_back));
            advices[0].setTextColor(Color.parseColor("#ffffff"));
            advices[1].setTextColor(Color.parseColor("#ffffff"));
        }else if(advice==0){
            advices[2].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_button_back));
            advices[3].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_button_back));
            advices[2].setTextColor(Color.parseColor("#ffffff"));
            advices[3].setTextColor(Color.parseColor("#ffffff"));
        }else if(advice==1){
            advices[4].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_button_back));
            advices[5].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_button_back));
            advices[4].setTextColor(Color.parseColor("#ffffff"));
            advices[5].setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("BMI", bmiValueTxt.getText().toString().split("：")[1]);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            String sbmi = savedInstanceState.getString("BMI", bmiValueTxt.getText().toString());
            bmiValueTxt.setText(getString(R.string.bmi_value, sbmi));
            Float bmi = Float.parseFloat(sbmi);
            int s;
            if (bmi < 20) {
                s = 2;
            } else if (bmi > 25) {
                s = 1;
            } else {
                s = 0;
            }
            adviceTxt.setText(getString(R.string.advice, item[s]));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveSharedPreferences();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadSharedPreferences();
    }

    private void saveSharedPreferences(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_NAME,MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Unit",getBmi_system());
        editor.commit();
    }
    private void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_NAME,MODE);
        setBmi_system(sharedPreferences.getInt("Unit",0));
        setSystem();
    }

    public void setBmi_system(int i) {
        bmi_system.setSystem(i);
    }

    public int getBmi_system() {
        return bmi_system.getSystem();
    }

    public void setSystem() {
        if (bmi_system.getSystem() == 0) {
            heightTxt.setText(getString(R.string.height));
            weightTxt.setText(getString(R.string.weight));
        } else {
            heightTxt.setText(getString(R.string.height_fin));
            weightTxt.setText(getString(R.string.weigh_flb));
        }
    }
}
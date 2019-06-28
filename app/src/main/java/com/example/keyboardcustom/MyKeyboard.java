package com.example.keyboardcustom;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {


    private Button button1, button2, button3, button4, button5;
    private ImageButton buttonBack;
    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;
    private int[] allInt = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private List<String> pwd = new ArrayList<>();

    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        shuffleArray(allInt);

        LayoutInflater.from(context).inflate(R.layout.keyboard_custom, this, true);
        button1 = findViewById(R.id.button);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);

        button1.setText(allInt[0] + " - " + allInt[1]);
        button2.setText(allInt[2] + " - " + allInt[3]);
        button3.setText(allInt[4] + " - " + allInt[5]);
        button4.setText(allInt[6] + " - " + allInt[7]);
        button5.setText(allInt[8] + " - " + allInt[9]);

        keyValues.put(R.id.button, button1.getText().toString());
        keyValues.put(R.id.button2, button2.getText().toString());
        keyValues.put(R.id.button3, button3.getText().toString());
        keyValues.put(R.id.button4, button4.getText().toString());
        keyValues.put(R.id.button5, button5.getText().toString());
    }


    @Override
    public void onClick(View v) {

        if (inputConnection == null)
            return;

        if (v.getId() == R.id.buttonBack) {
            CharSequence sequence = inputConnection.getSelectedText(0);

            if (TextUtils.isEmpty(sequence)) {
                inputConnection.deleteSurroundingText(1, 0);
                if (pwd.size() > 0) {
                    pwd.remove(pwd.size() - 1);
                }
                Log.i("Keyboard", pwd.toString());
            } else {
                inputConnection.commitText("", 1);
                Log.i("Keyboard", pwd.toString());
            }
        } else {
            String value = keyValues.get(v.getId());
            if(pwd.size() < 4) {
                pwd.add(value);
            }
            Log.i("Keyboard", pwd.toString());
            inputConnection.commitText("*", 1);
        }
    }

    public void setInputConnection(InputConnection i) {
        inputConnection = i;
    }

    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}

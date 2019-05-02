package com.tech4use.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtxt;
    Button btn;
    TextToSpeech tts;
    SeekBar pitch_skbr, speed_skbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtxt = findViewById(R.id.edtxt);
        btn = findViewById(R.id.speak_btn);
        pitch_skbr = findViewById(R.id.pitch_seekbar);
        speed_skbr = findViewById(R.id.speed_seekbar);

        //adding init
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //checking if status is successful or not
                if (status == TextToSpeech.SUCCESS) {
                    //checking if language set is successful or not in the int value
                    int result = tts.setLanguage(Locale.US);

                    //if any error occurs in language then...
                    if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "You have entered an " +
                                "invalid language", Toast.LENGTH_SHORT).show();
                    } else {
                        btn.setEnabled(true);
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Initialisation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //now adding the TextToSpeech function on the click of button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    public void speak() {
        String text = edtxt.getText().toString();

        //getting the progress of the pitch & speed seekbar
        float pitch = (float) pitch_skbr.getProgress() / 50;
        float speed = (float) speed_skbr.getProgress() / 50;

        //adding the setting on the seekbars
        if (pitch < 0.1) pitch = 0.1f;
        if (speed < 0.1) speed = 0.1f;

        //setting the pitch and speed seekbar in TextToSpeech
        tts.setPitch(pitch);
        tts.setSpeechRate(speed);

        //setting the speak function
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    //closing the TextToSpeech function on the close of the app


    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}

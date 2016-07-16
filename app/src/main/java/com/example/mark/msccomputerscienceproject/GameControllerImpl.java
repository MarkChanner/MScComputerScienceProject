package com.example.mark.msccomputerscienceproject;

import android.app.Activity;
import android.os.Bundle;

public class GameControllerImpl extends Activity implements GameController {

    /**
     * @author Mark Channer for Birkbeck MSc Computer Science project
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

package com.example.fyproject;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Test extends AppCompatActivity {

    // initialize variables
    TextView[] textViews = new TextView[8];
    boolean[][] selectedLanguages = new boolean[8][];
    ArrayList<Integer>[] langLists = new ArrayList[8];
    String[] langArray = {"PCV13Box1", "MenBBox1", "RotavirusBox1", "sixINoneBox1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // assign variables
        textViews[0] = findViewById(R.id.textView1);
        textViews[1] = findViewById(R.id.textView2);
        textViews[2] = findViewById(R.id.textView3);
        textViews[3] = findViewById(R.id.textView4);
        textViews[4] = findViewById(R.id.textView5);
        textViews[5] = findViewById(R.id.textView6);
        textViews[6] = findViewById(R.id.textView7);
        textViews[7] = findViewById(R.id.textView8);

        for (int i = 0; i < 8; i++) {
            // initialize selected language array
            selectedLanguages[i] = new boolean[langArray.length];
            langLists[i] = new ArrayList<>();

            final int index = i;
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Initialize alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Test.this);

                    // set title
                    builder.setTitle("Select Language " + (index + 1));

                    // set dialog non cancelable
                    builder.setCancelable(false);

                    builder.setMultiChoiceItems(langArray, selectedLanguages[index], new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            // check condition
                            if (b) {
                                // when checkbox selected
                                // Add position  in lang list
                                langLists[index].add(i);
                                // Sort array list
                                Collections.sort(langLists[index]);
                            } else {
                                // when checkbox unselected
                                // Remove position from langList
                                langLists[index].remove(Integer.valueOf(i));
                            }
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Initialize string builder
                            StringBuilder stringBuilder = new StringBuilder();
                            // use for loop
                            for (int j = 0; j < langLists[index].size(); j++) {
                                // concat array value
                                stringBuilder.append(langArray[langLists[index].get(j)]);
                                // check condition
                                if (j != langLists[index].size() - 1) {
                                    // When j value  not equal
                                    // to lang list size - 1
                                    // add comma
                                    stringBuilder.append(", ");
                                }
                            }
                            // set text on textView
                            textViews[index].setText(stringBuilder.toString());
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // dismiss dialog
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
// Clear all selections
                            for (int j = 0; j < selectedLanguages[index].length; j++) {
                                selectedLanguages[index][j] = false;
                                langLists[index].clear();
                                textViews[index].setText("");
                            }
                        }
                    });
                    // create and show dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }
}
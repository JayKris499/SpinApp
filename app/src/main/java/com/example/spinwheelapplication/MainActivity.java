package com.example.spinwheelapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnSpin;
    private Button btnAdd;
    private Button btnRemove;
    private Button btnClear;
    private EditText etNumInputs;
    private EditText etInput;
    private TextView tvResult;
    private ImageView imageView;
    private ListView listView;

    private List<String> wheelItems;
    private ArrayAdapter<String> wheelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpin = findViewById(R.id.btnSpin);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnClear = findViewById(R.id.btnClear);
        etNumInputs = findViewById(R.id.etNumInputs);
        etInput = findViewById(R.id.etInput);
        tvResult = findViewById(R.id.tvResult);
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.listView);

        // Initialize the wheel items
        wheelItems = new ArrayList<>();

        // Set up the ArrayAdapter
        wheelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wheelItems);
        listView.setAdapter(wheelAdapter);

        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinWheel();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInputs();
            }
        });
    }

    private void spinWheel() {
        int numInputs = Integer.parseInt(etNumInputs.getText().toString().trim());

// Check if the number of inputs is valid
        if (numInputs <= 0) {
            Toast.makeText(this, "Please enter a valid number of inputs", Toast.LENGTH_SHORT).show();
            return;
        }

// Combine manually added items with newly entered items
        List<String> allItems = new ArrayList<>(wheelItems);
        String userInput = etInput.getText().toString().trim();
        if (!userInput.isEmpty()) {
            allItems.add(userInput);
            etInput.getText().clear();
        }

// Check if there are items to select
        if (!allItems.isEmpty()) {
            // Create a copy of the combined list for shuffling
            List<String> shuffledList = new ArrayList<>(allItems);

            // Shuffle the copied list
            Collections.shuffle(shuffledList);

            // Get random items based on the number of inputs
            List<String> selectedItems = shuffledList.subList(0, Math.min(numInputs, shuffledList.size()));

            // Display a single randomly selected item
            String selectedItem = selectedItems.get(new Random().nextInt(selectedItems.size()));
            tvResult.setText("Selected: " + selectedItem);

        } else {
            // If there are no items, show a message
            tvResult.setText("Please add items before spinning");
        }

    }


    private void addItem() {
        String userInput = etInput.getText().toString().trim();
        int numInputs = Integer.parseInt(etNumInputs.getText().toString().trim());
        // Check if the number of inputs is reached
        if (wheelItems.size() >= numInputs) {
            Toast.makeText(this, "Number of inputs reached. Cannot add more.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!userInput.isEmpty()) {
            wheelItems.add(userInput);
            etInput.getText().clear();
            wheelAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "Please enter a valid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeItem() {
        String userInput = etInput.getText().toString().trim();
        if (!userInput.isEmpty()) {
            wheelItems.remove(userInput);
            etInput.getText().clear();
            wheelAdapter.notifyDataSetChanged();
        }
    }

    private void clearInputs() {
        etInput.getText().clear();
        tvResult.setText("");
        wheelItems.clear();
        wheelAdapter.notifyDataSetChanged();
    }
}

package com.example.wojtek.bontouchprogrammingtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private static final String WORDS_URI = "http://runeberg.org/words/ss100.txt";
    private static final String NETWORK_ERROR = "NetworkError";

    private String filterPhrase = "";


    private RecyclerView mRecyclerView;
    private WordsViewAdapter mAdapter;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            MainActivity.this.setFilterPhrase(charSequence.toString());
            MainActivity.this.filterPhrase = charSequence.toString();
            mAdapter.filter(MainActivity.this.filterPhrase);
            if (mAdapter.hasEmptySet()) {
                MainActivity.this.setNetworkErrorText("No words found!");
            } else {
                MainActivity.this.setNetworkErrorText("");
            }
            mRecyclerView.setAdapter(mAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fetchAutoCompleteWords();
        EditText textInput = findViewById(R.id.textInput);
        textInput.addTextChangedListener(textWatcher);

        mRecyclerView = findViewById(R.id.wordsListView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WordsViewAdapter(new String []{});
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setNetworkErrorText(String text) {
        TextView errorTextView = findViewById(R.id.errorMessageTextView);
        errorTextView.setText(text);
    }

    private void setFilterPhrase(String filterPhrase) {
        this.filterPhrase = filterPhrase;
    }

    private void setAutoCompleteWords(String[] words) {
        mAdapter.updateWords(words);
        mAdapter.filter(this.filterPhrase);
    }

    private void fetchAutoCompleteWords() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, WORDS_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MainActivity.this.setAutoCompleteWords(response.split("\\r?\\n"));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.this.setNetworkErrorText("Network request failed.");
                Log.e(NETWORK_ERROR, error.toString());
            }
        });

        queue.add(stringRequest);
    }
}

package com.example.wojtek.bontouchprogrammingtask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class WordsViewAdapter extends RecyclerView.Adapter<WordsViewAdapter.WordsViewHolder> {
    private String[] allWords;
    private ArrayList<String> filteredWords = new ArrayList<>();

    static class WordsViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        WordsViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }
    WordsViewAdapter(String[] myDataset) {
        allWords = myDataset;
    }

    public void updateWords(String[] words) {
        this.allWords = words;
        notifyDataSetChanged();
    }

    @Override
    public WordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_view_layout, parent, false);
        return new WordsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WordsViewHolder holder, int position) {
        holder.mTextView.setText(filteredWords.get(position));

    }

    @Override
    public int getItemCount() {
        return filteredWords.size();
    }

    public void filter(String filterPhrase) {
        String formattedFilterPhrase = filterPhrase.replaceAll("[^A-Za-z0-9]", "");
        this.filteredWords.clear();
        if (filterPhrase.equals("")) {
            this.filteredWords.addAll(Arrays.asList(allWords));
        } else {
            for (String word : allWords) {
                if (word.contains(formattedFilterPhrase)) {
                    this.filteredWords.add(word);
                }
            }
        }
        notifyDataSetChanged();
    }

    public boolean hasEmptySet() {
        return this.filteredWords.isEmpty();
    }
}
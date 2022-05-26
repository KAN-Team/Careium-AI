package com.example.careium.frontend.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.careium.R;
import com.example.careium.databinding.FragmentAddNoteBinding;

import java.util.ArrayList;

public class AddNote_Fragment extends Fragment {

    static ArrayList<NoteInformation> noteList = new ArrayList<>();
    FragmentAddNoteBinding binding ;


    public AddNote_Fragment() {
        // Required empty public constructor
    }

    public static AddNote_Fragment newInstance() {
        return new AddNote_Fragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note_, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddNoteBinding.bind(view);
        EditText TitleInput = view.findViewById(R.id.TitleText);
        EditText DescriptionInput = view.findViewById(R.id.DescriptionText);
        Button SaveNoteBtn = view.findViewById(R.id.SaveNote_Btn);

        SaveNoteBtn.setOnClickListener(v -> {
            String Title = TitleInput.getText().toString();
            String Description = DescriptionInput.getText().toString();
            long CreatedTime = System.currentTimeMillis();

            NoteInformation note = new NoteInformation(Title , Description ,CreatedTime);
            noteList.add(note);

            note.setTitle(Title);
            note.setDescription(Description);
            note.setCreatedTime(CreatedTime);
            Toast.makeText(getContext(),"Note Saved",Toast.LENGTH_SHORT).show();
        });
    }
}
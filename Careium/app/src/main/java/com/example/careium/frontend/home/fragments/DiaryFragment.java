package com.example.careium.frontend.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.careium.R;
import com.example.careium.core.adapters.DiaryAdapter;
import com.example.careium.databinding.FragmentDiaryBinding;


public class DiaryFragment extends Fragment {
    public DiaryFragment() {
        // Required empty public constructor
    }

    public static DiaryFragment newInstance() {
        return new DiaryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        com.example.careium.databinding.FragmentDiaryBinding binding = FragmentDiaryBinding.bind(view);

        binding.AddNoteBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_main_frame, AddNoteFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DiaryAdapter dairyAdapter = new DiaryAdapter(this.getContext(), AddNoteFragment.noteList);
        recyclerView.setAdapter(dairyAdapter);
    }
}
package com.example.careium.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.careium.R;
import com.example.careium.frontend.home.fragments.NoteInformation;

import java.text.DateFormat;
import java.util.ArrayList;

public class DiaryAdapter extends  RecyclerView.Adapter<DiaryAdapter.MyViewHolder>{

    Context context;
    ArrayList<NoteInformation> noteList;

    public DiaryAdapter(Context context, ArrayList<NoteInformation> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_note_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoteInformation note =noteList.get(position);
        holder.TitleOut.setText(note.getTitle());
        holder.DescriptionOut.setText(note.getDescription());

        String Time = DateFormat.getTimeInstance().format(note.getCreatedTime());
        holder.CreatedTimeOut.setText(Time);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TitleOut;
        TextView DescriptionOut;
        TextView CreatedTimeOut;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleOut = itemView.findViewById(R.id.text_note_title_output);
            DescriptionOut = itemView.findViewById(R.id.text_note_description_output);
            CreatedTimeOut = itemView.findViewById(R.id.text_note_time_output);
        }
    }
}

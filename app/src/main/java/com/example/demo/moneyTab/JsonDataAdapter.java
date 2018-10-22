package com.example.demo.moneyTab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by anju on 22-10-2018.
 */

public class JsonDataAdapter extends RecyclerView.Adapter<JsonDataAdapter.ViewHolder> {


    public static final String TITLE = "title";
    public static final String SOURCE = "source";
    public static final String DESCRIPTION = "description";


    private List<JSONData> jsonDatas;
    private Context context;

    public JsonDataAdapter(List<JSONData> developersLists, Context context) {
        this.jsonDatas = developersLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.developers_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final JSONData developersList = jsonDatas.get(position);
        holder.title.setText(developersList.getTitle());

        Picasso.with(context)
                .load(developersList.getSource())
                .resize(600, 400)
                .into(holder.source);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONData developersList1 = jsonDatas.get(position);
                Intent skipIntent = new Intent(v.getContext(), ProfileActivity.class);
                skipIntent.putExtra(TITLE, developersList1.getTitle());
                skipIntent.putExtra(DESCRIPTION, developersList1.getDescription());
                skipIntent.putExtra(SOURCE, developersList1.getSource());
                v.getContext().startActivity(skipIntent);
            }
        });

        holder.description.setText(developersList.getDescription());

    }

    @Override

    //return the size of the listItems (developersList)

    public int getItemCount() {
        return jsonDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView source;
        public TextView description;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            source = (ImageView) itemView.findViewById(R.id.source);
            description = (TextView) itemView.findViewById(R.id.description);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }

    }
}


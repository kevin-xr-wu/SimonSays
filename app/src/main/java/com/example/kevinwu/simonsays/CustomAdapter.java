package com.example.kevinwu.simonsays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Kevin on 6/26/2016.
 */
public class CustomAdapter extends ArrayAdapter<PlayerInfo> {

    public CustomAdapter(Context context, PlayerInfo[] players){
        super(context, R.layout.custom_row, players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_row, parent, false);

        PlayerInfo singlePlayer = getItem(position);

        TextView view = (TextView) customView.findViewById(R.id.name);
        view.setText(singlePlayer.getPlayerName());

        TextView view2 = (TextView) customView.findViewById(R.id.score);
        view2.setText(Integer.toString(singlePlayer.getPlayerScore()));

        return customView;
    }


}

package com.example.acha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {
  private Context context;
  private ArrayList<MovieList> dataModelArrayList;

  public MovieAdapter(Context context, ArrayList<MovieList> dataModelArrayList) {
    this.context = context;
    this.dataModelArrayList = dataModelArrayList;
  }
  @Override
  public int getViewTypeCount() {
    return getCount();
  }
  @Override
  public int getItemViewType(int position) {

    return position;
  }
  @Override
  public int getCount() {
    return dataModelArrayList.size();
  }

  @Override
  public Object getItem(int position) {
    return dataModelArrayList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;

    if (convertView == null) {
      holder = new ViewHolder();
      LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.movielist, null, true);

      holder.tvname = (TextView) convertView.findViewById(R.id.movieTitle);
      holder.tvcountry = (TextView) convertView.findViewById(R.id.movieGenre);
      holder.tvcity = (TextView) convertView.findViewById(R.id.Rating);

      convertView.setTag(holder);
    }else {
      // the getTag returns the viewHolder object set as a tag to the view
      holder = (ViewHolder)convertView.getTag();
    }

    holder.tvname.setText(dataModelArrayList.get(position).getTitle());
    holder.tvcountry.setText(dataModelArrayList.get(position).getGenre());
    holder.tvcity.setText(dataModelArrayList.get(position).getRating());

    /*convertView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(context.getApplicationContext(), holder.tvname.getText(),Toast.LENGTH_LONG).show();
      }
    });*/

    return convertView;
  }

  private class ViewHolder {

    protected TextView tvname, tvcountry, tvcity;
  }
}

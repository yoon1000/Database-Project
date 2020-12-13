package com.example.acha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieQueueAdapter extends BaseAdapter {
  private Context context;
  private ArrayList<MovieList> dataModelArrayList;

  public MovieQueueAdapter(Context context, ArrayList<MovieList> dataModelArrayList) {
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
    MovieQueueAdapter.ViewHolder holder;

    if (convertView == null) {
      holder = new MovieQueueAdapter.ViewHolder();
      LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.movielist, null, true);

      holder.tvname = (TextView) convertView.findViewById(R.id.movieTitle);
      holder.tvcountry = (TextView) convertView.findViewById(R.id.movieGenre);
      holder.tvcity = (TextView) convertView.findViewById(R.id.Rating);

      convertView.setTag(holder);
    }else {
      // the getTag returns the viewHolder object set as a tag to the view
      holder = (MovieQueueAdapter.ViewHolder)convertView.getTag();
    }

    holder.tvname.setText(dataModelArrayList.get(position).getTitle());
    holder.tvcountry.setText(dataModelArrayList.get(position).getGenre());
    holder.tvcity.setText(dataModelArrayList.get(position).getRating());

    convertView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(context.getApplicationContext(), holder.tvname.getText(),Toast.LENGTH_LONG).show();
      }
    });

    return convertView;
  }

  private class ViewHolder {

    protected TextView tvname, tvcountry, tvcity;
  }
}

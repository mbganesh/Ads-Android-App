package mb.ganesh.ads;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AdAdapter extends RecyclerView.Adapter<AdAdapter.ViewHolder> {

    Context context;
    JSONArray array;

    public AdAdapter(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.ad_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            JSONObject object = new JSONObject(String.valueOf(array.getJSONObject(position)));
            holder.ad_text.setText(object.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject object = new JSONObject(String.valueOf(array.getJSONObject(position)));

            holder.ad_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    try {
                        customTabsIntent.launchUrl(context, Uri.parse(object.getString("url")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            JSONObject object = new JSONObject(String.valueOf(array.getJSONObject(position)));

            Glide
                    .with(context)
                    .load(object.getString("src"))
                    .centerCrop()
                    .into(holder.ad_img);
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ad_text;
        ImageView ad_img;
        MaterialCardView ad_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ad_text =  itemView.findViewById(R.id.ad_text);
            ad_img =  itemView.findViewById(R.id.ad_img);
            ad_card =  itemView.findViewById(R.id.ad_card);
        }
    }
}

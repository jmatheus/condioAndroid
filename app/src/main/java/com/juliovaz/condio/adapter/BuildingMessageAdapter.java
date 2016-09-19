package com.juliovaz.condio.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juliovaz.condio.R;
import com.juliovaz.condio.activity.BuildingMessageDetailActivity;
import com.juliovaz.condio.model.BuildingMessage;

import java.util.ArrayList;

/**
 * Created by julio on 27/06/16.
 */
public class BuildingMessageAdapter extends RecyclerView.Adapter<BuildingMessageAdapter.ViewHolder> {

    private ArrayList<BuildingMessage> listBuildingMessages = new ArrayList<>();
    private Context context;
    private Drawable[] mBoardPicture;

    public BuildingMessageAdapter(Context context, ArrayList<BuildingMessage> listBuildingMessages) {
        this.context = context;
        this.listBuildingMessages = listBuildingMessages;

        Resources resources = context.getResources();
        TypedArray a = resources.obtainTypedArray(R.array.message_board_documemt);
        mBoardPicture = new Drawable[a.length()];
        for (int i = 0; i < mBoardPicture.length; i++) {
            mBoardPicture[i] = a.getDrawable(i);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BuildingMessage buildingMessage = listBuildingMessages.get(position);
        if (buildingMessage != null) {
//            holder.picture.setImageDrawable(mBoardPicture[0]);
            holder.messageTitle.setText(buildingMessage.getMessageDescription());
            holder.messageDescription = buildingMessage.getMessageDescription();
            holder.createdAt = buildingMessage.getCreatedAt();
        }
    }

    @Override
    public int getItemCount() {
        return listBuildingMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ImageView picture;
        public TextView messageTitle;
        public String messageDescription;
        public String createdAt;

        public ViewHolder(View itemView) {
            super(itemView);
//
//            picture = (TextView) itemView.findViewById(R.id.profile_photo);
            messageTitle = (TextView) itemView.findViewById(R.id.message_text);
//            messageDescription = (DTextView) itemView.findViewById(R.id.card_text);
//            messageDescription = (DÎTextView) itemView.findViewById(R.id.card_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, BuildingMessageDetailActivity.class);
                    intent.putExtra("MESSAGE_DESCRIPTION", messageDescription);
                    intent.putExtra("CREATED_AT", createdAt);
                    intent.putExtra(BuildingMessageDetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }
}

package com.thingnoy.thingnoy500v3.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;

/**
 * Created by HBO on 23/2/2561.
 */

public class SectionHolder extends RecyclerView.ViewHolder {
    public TextView tvSection;

    public SectionHolder(View itemView) {
        super(itemView);
        tvSection = itemView.findViewById(R.id.tv_section);
    }


}

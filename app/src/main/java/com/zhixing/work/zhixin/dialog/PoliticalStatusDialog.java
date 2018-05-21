package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;


/**

 */
public class PoliticalStatusDialog extends ButtomDialog {
    private TextView citizen;
    private TextView party_member_of_cpc;
    private TextView league_member;
    private TextView democratic_progressive_party;
    private TextView non_partisan;

    private LinearLayout llClose;
    private OnItemClickListener clickListener;
    public static final int TYPE_CITIZEN = 0;
    public static final int TYPE_PARTY_MEMBER_OF_CPC = 1;
    public static final int TYPE_LEAGUE_MEMBER = 2;
    public static final int TYPE_DEMOCRATIC_PROGRESSIVE_PARTY = 3;
    public static final int TYPE_NON_PARTISAN = 4;


    public interface OnItemClickListener {

        void onClick(PoliticalStatusDialog dialog, int index);
    }

    public PoliticalStatusDialog(Context context, OnItemClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        setContentView(R.layout.dialog_political_status);
        bindViews();
    }

    private void bindViews() {
        citizen = (TextView) findViewById(R.id.citizen);
        party_member_of_cpc = (TextView) findViewById(R.id.party_member_of_cpc);
        league_member = (TextView) findViewById(R.id.league_member);
        democratic_progressive_party = (TextView) findViewById(R.id.democratic_progressive_party);
        non_partisan = (TextView) findViewById(R.id.non_partisan);

        llClose = (LinearLayout) findViewById(R.id.llClose);
        llClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }

        });

        citizen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(PoliticalStatusDialog.this, TYPE_CITIZEN);
            }

        });
        party_member_of_cpc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(PoliticalStatusDialog.this, TYPE_PARTY_MEMBER_OF_CPC);
            }

        });
        league_member.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(PoliticalStatusDialog.this, TYPE_LEAGUE_MEMBER);
            }

        });
        democratic_progressive_party.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(PoliticalStatusDialog.this, TYPE_DEMOCRATIC_PROGRESSIVE_PARTY);
            }

        });
        non_partisan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(PoliticalStatusDialog.this, TYPE_NON_PARTISAN);
            }

        });

    }
}

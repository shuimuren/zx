package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.PersonalJobCardBean;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.ZxTextUtils;

/**
 * Created by lhj on 2018/8/24.
 * Description: 用户详情弹窗
 */

public class StaffInfoDialog extends BaseDialog {


    private ImageView userAvatar;
    private TextView userName;
    private TextView level;
    private ImageView constellation;//星座
    private TextView nikeName;//昵称
    private TextView phone;//电话
    private TextView mail;
    private TextView motto;
    private TextView tvJobNikeName;
    private TextView tvJobPosition;
    private TextView tvJobDepartment;
    private TextView tvUserMark;
    private ScaleRatingBar stars;
    private ImageView imgPersonalType;
    private ImageView imgHide;

    private Context mContext;

    public StaffInfoDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_staff_info);
        userAvatar = findViewById(R.id.avatar);
        userName = findViewById(R.id.name);
        level = findViewById(R.id.level);
        constellation = findViewById(R.id.constellation);
        nikeName = findViewById(R.id.nikeName);
        phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        motto = findViewById(R.id.motto);
        tvJobNikeName = findViewById(R.id.tv_job_nike_name);
        tvJobPosition = findViewById(R.id.tv_job_position);
        tvJobDepartment = findViewById(R.id.tv_job_department);
        tvUserMark = findViewById(R.id.tv_user_mark);
        stars = findViewById(R.id.stars);
        imgPersonalType = findViewById(R.id.img_personal_type);
        imgHide = findViewById(R.id.img_hide);
        imgHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setPersonalJobCardBean(PersonalJobCardBean bean) {
        if (bean != null) {
            userName.setText(bean.getRealName());
            tvJobNikeName.setText(ZxTextUtils.getTextWithDefault(bean.getNickName()));
            tvJobDepartment.setText(ZxTextUtils.getTextWithDefault(bean.getDepartmentName()));
            tvJobPosition.setText(ZxTextUtils.getTextWithDefault(bean.getJobType()));
            tvUserMark.setText(ZxTextUtils.getTextWithDefault(bean.getMotto()));
        }
        GlideUtils.getInstance().loadPublicRoundTransformWithDefault(mContext,
                ResourceUtils.getDrawable(R.drawable.img_company_default), bean.getCardAvatar(), userAvatar);
        GlideUtils.getInstance().loadPublicCircleWithDefault(mContext,
                ResourceUtils.getDrawable(R.drawable.img_company_default), bean.getCompanyLogo(), imgPersonalType);
    }
}

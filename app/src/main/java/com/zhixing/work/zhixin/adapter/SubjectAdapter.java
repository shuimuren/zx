package com.zhixing.work.zhixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.TestPaper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工作经历
 */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    @BindView(R.id.a_subject)
    TextView aSubject;
    @BindView(R.id.b_subject)
    TextView bSubject;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.a_add)
    TextView aAdd;
    @BindView(R.id.neutral)
    TextView neutral;
    @BindView(R.id.b)
    TextView b;
    @BindView(R.id.b_add)
    TextView bAdd;
    @BindView(R.id.ll_answer)
    LinearLayout llAnswer;

    public List<TestPaper.QuestionsBean> getList() {
        return list;
    }

    public void setList(List<TestPaper.QuestionsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<TestPaper.QuestionsBean> list;
    private Context context;
    private LayoutInflater mInflater;

    public SubjectAdapter(List<TestPaper.QuestionsBean> list, Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @OnClick({R.id.a, R.id.a_add, R.id.neutral, R.id.b, R.id.b_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.a:

                break;
            case R.id.a_add:
                aAdd.setSelected(true);
                break;
            case R.id.neutral:
                neutral.setSelected(true);
                break;
            case R.id.b:
                b.setSelected(true);
                break;
            case R.id.b_add:
                bAdd.setSelected(true);
                break;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final TestPaper.QuestionsBean bean = list.get(position);
        holder.aSubject.setText(bean.getTopicA());
        holder.bSubject.setText(bean.getTopicB());


        if (bean.getOption() != null) {
            switch (bean.getOption()) {
                case 0:
                    holder.a.setSelected(true);
                    holder.aAdd.setSelected(false);
                    holder.neutral.setSelected(false);
                    holder.b.setSelected(false);
                    holder.bAdd.setSelected(false);
                    break;
                case 1:
                    holder.a.setSelected(false);
                    holder.aAdd.setSelected(true);
                    holder.neutral.setSelected(false);
                    holder.b.setSelected(false);
                    holder.bAdd.setSelected(false);
                    break;
                case 2:
                    holder.a.setSelected(false);
                    holder.aAdd.setSelected(false);
                    holder.neutral.setSelected(true);
                    holder.b.setSelected(false);
                    holder.bAdd.setSelected(false);
                    break;
                case 3:
                    holder.a.setSelected(false);
                    holder.aAdd.setSelected(false);
                    holder.neutral.setSelected(false);
                    holder.b.setSelected(true);
                    holder.bAdd.setSelected(false);
                    break;
                case 4:
                    holder.a.setSelected(false);
                    holder.aAdd.setSelected(false);
                    holder.neutral.setSelected(false);
                    holder.b.setSelected(false);
                    holder.bAdd.setSelected(true);
                    break;

            }
        } else {
            holder.a.setSelected(false);
            holder.aAdd.setSelected(false);
            holder.neutral.setSelected(false);
            holder.b.setSelected(false);
            holder.bAdd.setSelected(false);
        }
        holder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bean.setOption(0);
                list.set(position, bean);
                mOnItemClickListener.onItemClick(holder.a, position);
                notifyDataSetChanged();
            }
        });
        holder.aAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bean.setOption(1);
                list.set(position, bean);
                mOnItemClickListener.onItemClick(holder.a, position);
                notifyDataSetChanged();
            }
        });

        holder.neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bean.setOption(2);
                list.set(position, bean);
                mOnItemClickListener.onItemClick(holder.a, position);
                notifyDataSetChanged();
            }
        });
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bean.setOption(3);
                list.set(position, bean);
                mOnItemClickListener.onItemClick(holder.a, position);
                notifyDataSetChanged();
            }
        });
        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bean.setOption(4);
                list.set(position, bean);
                mOnItemClickListener.onItemClick(holder.a, position);
                notifyDataSetChanged();
            }
        });

        if (mOnItemClickListener != null) {
            holder.llOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.llOption, pos);
                }
            });

            holder.llOption.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.llOption, pos);
                    return false;
                }
            });
        }

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.a_subject)
        TextView aSubject;
        @BindView(R.id.b_subject)
        TextView bSubject;
        @BindView(R.id.a)
        TextView a;
        @BindView(R.id.a_add)
        TextView aAdd;
        @BindView(R.id.neutral)
        TextView neutral;
        @BindView(R.id.b)
        TextView b;
        @BindView(R.id.b_add)
        TextView bAdd;
        @BindView(R.id.ll_answer)
        LinearLayout llAnswer;
        @BindView(R.id.ll_option)
        LinearLayout llOption;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
        return new ViewHolder(view);
    }


}

package com.zhixing.work.zhixin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.ChatMessageAdapter;
import com.zhixing.work.zhixin.base.BaseMainFragment;
import com.zhixing.work.zhixin.bean.MessageTestBean;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主页-正聊
 */
public class MessageFragment extends BaseMainFragment implements ChatMessageAdapter.ItemClickedInterface{

    @BindView(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @BindView(R.id.title)
    TextView title;
    private List<MessageTestBean> dataList;
    private ChatMessageAdapter messageAdapter;

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        title.setText("消息");
        initView();
        return view;
    }

    private void initView() {
        dataList= new ArrayList<>();
        MessageTestBean testBean1 = new MessageTestBean(0,"C罗",0,"我去尤文了...");
        dataList.add(testBean1);
        MessageTestBean testBean2 = new MessageTestBean(1,"梅西",0,"我最近比较慌");
        dataList.add(testBean2);
        MessageTestBean testBean3 = new MessageTestBean(2,"内马尔",3,"今年进球又没我");
        dataList.add(testBean3);
        MessageTestBean testBean4 = new MessageTestBean(1,"姆巴佩",0,"神龟来也...");
        dataList.add(testBean4);
        MessageTestBean testBean5 = new MessageTestBean(1,"马塞洛",5,"C罗去尤文了谁帮我拧瓶盖");
        dataList.add(testBean5);
        MessageTestBean testBean6 = new MessageTestBean(2,"贝尔",0,"我是踢足球的,不是搞野外冒险的那个..");
        dataList.add(testBean6);
        MessageTestBean testBean7 = new MessageTestBean(1,"本泽马",1,"哪里有锅哪里就有我,专业背锅20年");
        dataList.add(testBean7);
        messageAdapter = new ChatMessageAdapter(getActivity(),dataList);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.setAdapter(messageAdapter);
        messageAdapter.setItemClickedListener(this);

    }


    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void itemClicked(String name) {
        AlertUtils.show(name+"说: 开发忙不过来啊");
    }
}

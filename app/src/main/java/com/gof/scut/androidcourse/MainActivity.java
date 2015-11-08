package com.gof.scut.androidcourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gof.scut.androidcourse.activity.CheckResultActivity;
import com.gof.scut.androidcourse.activity.CreateCardActivity;
import com.gof.scut.androidcourse.activity.CreateQRcodeActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	RecyclerView recyclerView;
	//下面四个是右下角的button
	FloatingActionsMenu floatingMenu;
	FloatingActionButton mBtntop;
	FloatingActionButton mBtnmiddle;
	FloatingActionButton mBtnbottom;
	List<Card> cardList;
	MyAdapter adapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iniData();
		iniUI();
		iniAdapter();
		iniListener();
	}

	protected void iniUI(){
		recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
		mBtntop =(FloatingActionButton)findViewById(R.id.btn_top);
		mBtnmiddle=(FloatingActionButton)findViewById(R.id.btn_middle);
		mBtnbottom=(FloatingActionButton)findViewById(R.id.btn_bottom);
		floatingMenu=(FloatingActionsMenu)findViewById(R.id.floatingmenu);
		mBtntop.setTitle("扫描二维码");
		mBtntop.setIconDrawable(getResources().getDrawable(R.drawable.scan));
		mBtnmiddle.setTitle("生成二维码");
		mBtnmiddle.setIconDrawable(getResources().getDrawable(R.drawable.qrcode));
		mBtnbottom.setTitle("新建名片");
		mBtnbottom.setIconDrawable(getResources().getDrawable(R.drawable.card));
	}

	protected void iniAdapter(){
		adapter =new MyAdapter();
		adapter.setCards(cardList);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	protected void  iniData(){
		//TODO 显示什么数据
		cardList =new ArrayList<>();
		cardList.add(new Card("刘德华","18814166584"));
		cardList.add(new Card("张学友","18816467834"));
		cardList.add(new Card("周润发","15487624684"));
	}

	protected void iniListener(){
		mBtntop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent();
				intent.setClass(MainActivity.this, CheckResultActivity.class);
				startActivity(intent);
			}
		});
		mBtnbottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, CreateCardActivity.class));
			}
		});
		mBtnmiddle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, CreateQRcodeActivity.class));
			}
		});
	}

	class MyAdapter extends RecyclerView.Adapter {
		List<Card> cards;

		public void setCards(List<Card> cards) {
			this.cards = cards;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			//TODO definelistitem
			View v=LayoutInflater.from(parent.getContext()).inflate(
					R.layout.listitem,parent,false
			);
			return new MyHolder(v);
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			((MyHolder)holder).textView.setText(cards.get(position).getName());
			((MyHolder)holder).mTvphone.setText(cards.get(position).getPhonenumber1());
		}

		@Override
		public int getItemCount() {
			return cards.size();
		}

		class MyHolder extends RecyclerView.ViewHolder{
			TextView textView;
			TextView mTvphone;
			public MyHolder(View itemView) {
				super(itemView);
				textView=(TextView)itemView.findViewById(R.id.tv_name);
				mTvphone=(TextView)itemView.findViewById(R.id.tv_phone);
			}
		}

	}

}

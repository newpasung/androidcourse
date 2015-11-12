package com.gof.scut.androidcourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gof.scut.androidcourse.activity.CheckResultActivity;
import com.gof.scut.androidcourse.activity.CardinfoActivity;
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
		mBtnmiddle.setTitle("我的二维码");
		mBtnmiddle.setIconDrawable(getResources().getDrawable(R.drawable.qrcode));
		mBtnbottom.setTitle("我的名片");
		mBtnbottom.setIconDrawable(getResources().getDrawable(R.drawable.card));
	}

	protected void iniAdapter(){
		adapter = new MyAdapter();
		adapter.setCards(cardList);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
	}

	protected void iniData(){
		this.cardList=((MyApplication)getApplicationContext()).getCardList();
	}

	protected void iniListener(){
		mBtntop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CheckResultActivity.class);
				startActivity(intent);
			}
		});
		mBtnbottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, CardinfoActivity.class));
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
		public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
			CardColor cardColor = CardColor.getCardColor();
			int cardBgColor = getResources().getColor(cardColor.backgroundColor);
			int cardTextColor = getResources().getColor(cardColor.textColor);
			((MyHolder)holder).cardView.setCardBackgroundColor(cardBgColor);
			((MyHolder)holder).textView.setTextColor(cardTextColor);
			((MyHolder)holder).mTvphone.setTextColor(cardTextColor);
			((MyHolder)holder).mTvcompany.setTextColor(cardTextColor);
			((MyHolder)holder).textView.setText(cards.get(position).getName());
			((MyHolder)holder).mTvphone.setText(cards.get(position).getPhonenumber1());
			((MyHolder)holder).cardView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent =new Intent();
					intent.setClass(MainActivity.this, CardinfoActivity.class);
					Bundle bundle =new Bundle();
					bundle.putInt("index", position);
					intent.putExtra("data", bundle);
					startActivity(intent);
				}
			});
		}

		@Override
		public int getItemCount() {
			return cards.size();
		}

		class MyHolder extends RecyclerView.ViewHolder{
			CardView cardView;
			TextView textView;
			TextView mTvphone;
			TextView mTvcompany;
			public MyHolder(View itemView) {
				super(itemView);
				cardView = (CardView)itemView.findViewById(R.id.card_view);
				textView = (TextView)itemView.findViewById(R.id.tv_name);
				mTvcompany = (TextView)itemView.findViewById(R.id.tv_company);
				mTvphone=(TextView)itemView.findViewById(R.id.tv_phone);
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if (floatingMenu.isExpanded()){
				floatingMenu.collapse();
				return true;
			}
		}
		return false;
	}
}

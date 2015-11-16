package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gof.scut.androidcourse.Card2;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.net.HttpClient;
import com.gof.scut.androidcourse.net.JsonResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    RecyclerView recyclerView;
    //下面四个是右下角的button
    FloatingActionsMenu floatingMenu;
    FloatingActionButton mBtnScan;
    FloatingActionButton mBtnAddCard;
    FloatingActionButton mBtnMyCard;
    List<Card2> newCardList;
    MyAdapter adapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniData();
        iniUI();
        iniAdapter();
        iniListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //低级更新方式
        //TODO 添加新名片后未刷新
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected void iniUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mBtnScan = (FloatingActionButton) findViewById(R.id.btn_scan);
        mBtnAddCard = (FloatingActionButton) findViewById(R.id.btn_add_card);
        mBtnMyCard = (FloatingActionButton) findViewById(R.id.btn_my_card);
        floatingMenu = (FloatingActionsMenu) findViewById(R.id.floatingmenu);
    }

    protected void iniAdapter() {
        adapter = new MyAdapter();
//		adapter.setCards(cardList);
        adapter.setCard2s(newCardList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    protected void iniData() {
//		this.cardList=((MyApplication)getApplicationContext()).getCardList();
        this.newCardList = new ArrayList<>();
        getNetData();
        dialog = ProgressDialog.show(this, "tip", "waiting", true, false);
    }

    protected void iniListener() {
        mBtnScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CheckResultActivity.class);
                startActivity(intent);
            }
        });
        mBtnAddCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(new String[]{"二维码添加", "手动输入名片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                startActivity(new Intent(MainActivity.this, CheckResultActivity.class));
                            }
                            break;
                            case 1: {
                                startActivity(new Intent(MainActivity.this, AddCardActivity.class));
                            }
                            break;
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        mBtnMyCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CardinfoActivity.class));
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (floatingMenu.isExpanded()) {
                    floatingMenu.collapse();
                }
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter {
        List<Card2> card2s;


        public void setCard2s(List<Card2> card2s) {
            this.card2s = card2s;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.listitem, parent, false
            );
            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            int cardBgColor = getResources().getColor(card2s.get(position).getBackgroundColor());
            int cardTextColor = getResources().getColor(card2s.get(position).getTextColor());
            ((MyHolder) holder).cardView.setCardBackgroundColor(cardBgColor);
            ((MyHolder) holder).textView.setTextColor(cardTextColor);
            ((MyHolder) holder).mTvphone.setTextColor(cardTextColor);
            ((MyHolder) holder).mTvcompany.setTextColor(cardTextColor);
            ((MyHolder) holder).textView.setText(card2s.get(position).getName());
            ((MyHolder) holder).mTvphone.setText(card2s.get(position).getPhonenumber());
            ((MyHolder) holder).mTvcompany.setText(card2s.get(position).getCompany());
            ((MyHolder) holder).cardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CardinfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("cardid", card2s.get(position).getCardid());
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return card2s.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView textView;
            TextView mTvphone;
            TextView mTvcompany;

            public MyHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.card_view);
                textView = (TextView) itemView.findViewById(R.id.tv_name);
                mTvcompany = (TextView) itemView.findViewById(R.id.tv_company);
                mTvphone = (TextView) itemView.findViewById(R.id.tv_phone);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (floatingMenu.isExpanded()) {
                floatingMenu.collapse();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getNetData() {
        RequestParams params = new RequestParams();
        HttpClient.get(this, "card/list", params, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    List<Card2> newdata = Card2.insertOrupdate(data);
                    if (newdata.size() > 0) {
                        adapter.setCard2s(newdata);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(String message, String for_param) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

}

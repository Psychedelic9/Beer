package com.bai.psychedelic.beer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button mBtnReturned,mBtnNoReturned;
    private EditText mBeerPrice,mTotalMoney,mStrategyCap,mStrategyBottle,mBeerReturned;
    private RecyclerView mRecyclerView;
    private Beer mBeer;
    private RvResultAdapter mAdapter;
    private ArrayList<String> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mBeer = new Beer();
        mList = new ArrayList<>();
        mAdapter = new RvResultAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        mBtnReturned = findViewById(R.id.main_bt_count_returned);
        mBtnNoReturned = findViewById(R.id.main_bt_count_no_returned);
        mBeerPrice = findViewById(R.id.main_ed_beer_price);
        mTotalMoney = findViewById(R.id.main_ed_total_money);
        mStrategyCap = findViewById(R.id.main_ed_beer_strategy_cap);
        mStrategyBottle = findViewById(R.id.main_ed_beer_strategy_bottle);
        mBeerReturned = findViewById(R.id.main_ed_beer_returned);
        mRecyclerView = findViewById(R.id.main_rv_result);
    }

    public void noReturnedClick(View view) {
        mBeer.clean();
        int beerPrice = Integer.parseInt(mBeerPrice.getText().toString());
        int totalMoney = Integer.parseInt(mTotalMoney.getText().toString());
        mBeer.setmTotalMoney(totalMoney);
        mBeer.setmUnitPrice(beerPrice);
        mBeer.setmCapToBeer(Integer.parseInt(mStrategyCap.getText().toString()));
        mBeer.setmBottleToBeer(Integer.parseInt(mStrategyBottle.getText().toString()));
        mList.clear();
        mBeer.buyBeer(mList);
        mAdapter.notifyDataSetChanged();
    }

    public void returnedClick(View view) {
        mBeer.clean();
        int count = Integer.parseInt(mBeerReturned.getText().toString());
        int beerPrice = Integer.parseInt(mBeerPrice.getText().toString());
        int totalMoney = Integer.parseInt(mTotalMoney.getText().toString());
        mBeer.setmTotalMoney(totalMoney);
        mBeer.setmUnitPrice(beerPrice);
        mBeer.setmCapToBeer(Integer.parseInt(mStrategyCap.getText().toString()));
        mBeer.setmBottleToBeer(Integer.parseInt(mStrategyBottle.getText().toString()));
        int result = mBeer.countBeer(count);
        mList.clear();
        mList.add("预支"+count+"瓶啤酒可兑换"+result+"瓶啤酒，还需买"+(count-result)+"瓶啤酒还回去");
        mList.add("买"+(count-result)+"瓶啤酒需要"+(Integer.parseInt(mBeerPrice.getText().toString()))*(count-result)+"元");
        mAdapter.notifyDataSetChanged();
    }
}

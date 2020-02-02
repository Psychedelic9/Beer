昨儿看爱情公寓5，剧里面偷了道题目
啤酒2块钱一瓶，4个瓶盖或者2个空瓶能再换1瓶啤酒，问10块钱最多能喝多少瓶啤酒。

这道题明显是一道递归算法题，弹幕里很多人算出来是15瓶，但是赵海棠最后得出的答案是20瓶。

其实都是正确答案，15瓶是指所有的啤酒都是自己买的情况下，最后剩下3个瓶盖和一个空酒瓶，喝了15瓶啤酒。
而20瓶的答案是通过先预支20瓶啤酒得出的。预支20瓶啤酒喝掉后剩下的瓶盖和酒瓶可以兑换15瓶啤酒。这个时候你还没有付钱，10块钱正好可以再买5瓶啤酒，这5瓶啤酒加上兑换的15瓶正好可以还20瓶啤酒回去。也就是说只花了10元但是喝了20瓶酒，最后所有的酒瓶和瓶盖都兑换掉了。

这里要注意的是酒瓶，瓶盖加上啤酒才是完整的一瓶酒。

用java实现一下,这题用面向过程的编程思想方便一些
```java
package com.bai.psychedelic.bear;

import java.util.ArrayList;

public class Beer {
    private int mUnitPrice = 2;
    private int mCapCount = 0;
    private int mBottleCount = 0;
    private int mTotalMoney = 100;
    private int mBeerSize = 0;
    private int mSurplusMoney = 0;
    private int mBottleToBeer = 0;
    private int mCapToBeer = 0;
	 /**
     * 设置多少瓶盖能换一瓶啤酒
     */
    public void setmCapToBeer(int mCapToBeer) {
        this.mCapToBeer = mCapToBeer;
    }
 	/**
     * 设置多少空瓶能换一瓶啤酒
     */
 	public void setmBottleToBeer(int mBottleToBeer) {
        this.mBottleToBeer = mBottleToBeer;
    }

    /**
     * 设置啤酒单价
     */
    public void setmUnitPrice(int mUnitPrice) {
        this.mUnitPrice = mUnitPrice;
    }

    /**
     * 设置总共有多少钱
     * @param mTotalMoney
     */
    public void setmTotalMoney(int mTotalMoney) {
        this.mTotalMoney = mTotalMoney;
        this.mSurplusMoney = mTotalMoney;
    }

    /**
     * 每次计算前clean之前的数据
     */
    public void clean() {
        mUnitPrice = 0;
        mCapCount = 0;
        mBottleCount = 0;
        mBeerSize = 0;
        mSurplusMoney = 0;
        mCountBeerSize = 0;
        mBottleToBeer = 0;
        mCapToBeer = 0;
    }

    /**
     *
     * @param list
     */
    public void buyBeer(ArrayList<String> list) {
        if (mSurplusMoney >= mUnitPrice) {
            mSurplusMoney = mSurplusMoney - mUnitPrice;
            addOneBeer();
        }
        if (mBottleCount >= mBottleToBeer) {
            mBottleCount = mBottleCount - mBottleToBeer;
            addOneBeer();
        }
        if (mCapCount >= mCapToBeer) {
            mCapCount = mCapCount - mCapToBeer;
            addOneBeer();
        }
        list.add("啤酒数量：" + mBeerSize + " 剩余：" + mSurplusMoney + "元  " +
                "酒瓶数量：" + mBottleCount + " " +
                "瓶盖数量：" + mCapCount);
        if (mSurplusMoney < mUnitPrice && mBottleCount < 2 && mCapCount < 4) {
        } else {
            buyBeer(list);
        }
    }

    private int mCountBeerSize = 0;

    /**
     * 计算指定数量的啤酒可以兑换多少啤酒，对count()的封装
     * @param size
     * @return
     */
    public int countBeer(int size) {
        count(size, size, size);
        return mCountBeerSize - size;
    }

    /**
     * 计算给定数量的酒瓶、瓶盖或者完整的啤酒可以兑换多少瓶啤酒
     * @param capsize
     * @param bottlesize
     * @param beersize
     */
    private void count(int capsize, int bottlesize, int beersize) {
        if (capsize >= 4) {
            beersize++;
            capsize = capsize - 4;
        }
        if (bottlesize >= 2) {
            beersize++;
            bottlesize = bottlesize - 2;
        }
        if (!(capsize < 4 && bottlesize < 2)) {
            count(capsize, bottlesize, beersize);
        } else {
            mCountBeerSize = beersize;
        }
    }

    /**
     * 兑换或者买一瓶完整的啤酒，同时获得酒瓶瓶盖
     */
    private void addOneBeer() {
        mBeerSize++;
        mBottleCount++;
        mCapCount++;
    }

}
```
就不放在Main方法里测试了，干脆写一个Android计算器demo
MainActivity：
```java
package com.bai.psychedelic.bear;

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

```
activity_main.xml:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/main_ed_total_money"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="输入买酒的钱"
        android:inputType="number"
        app:layout_constraintBottom_toTopOf="@+id/main_ed_beer_price"
        app:layout_constraintLeft_toLeftOf="parent"/>
    <EditText
        android:id="@+id/main_ed_beer_price"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="输入啤酒单价"
        android:inputType="number"
        app:layout_constraintTop_toBottomOf="@+id/main_ed_total_money"
        app:layout_constraintLeft_toLeftOf="parent"
        />
    <EditText
        android:id="@+id/main_ed_beer_strategy_cap"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="输入能兑换啤酒的瓶盖数量"
        android:inputType="number"
        app:layout_constraintLeft_toRightOf="@+id/main_ed_beer_price"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintRight_toRightOf="parent"/>
    <EditText
        android:id="@+id/main_ed_beer_strategy_bottle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="输入能兑换啤酒的空酒瓶数量"
        android:inputType="number"
        app:layout_constraintLeft_toRightOf="@+id/main_ed_beer_price"
        app:layout_constraintTop_toBottomOf="@+id/main_ed_beer_strategy_cap"
        app:layout_constraintRight_toRightOf="parent"
        />
    <Button
        android:id="@+id/main_bt_count_no_returned"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="不能退换情况下计算总共能喝多少啤酒"
        app:layout_constraintTop_toBottomOf="@+id/main_ed_beer_strategy_bottle"
        android:onClick="noReturnedClick"
        />
    <EditText
        android:id="@+id/main_ed_beer_returned"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="输入需要预支多少瓶啤酒"
        android:inputType="number"
        app:layout_constraintTop_toBottomOf="@+id/main_bt_count_no_returned"
        app:layout_constraintRight_toRightOf="parent"
        />
    <Button
        android:id="@+id/main_bt_count_returned"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="能退换情况下计算总共能喝多少啤酒"
        app:layout_constraintTop_toBottomOf="@+id/main_ed_beer_returned"
        android:onClick="returnedClick"
        />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/main_rv_result"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintTop_toBottomOf="@+id/main_bt_count_returned"
        app:layout_constraintBottom_toBottomOf="parent"
        />
</androidx.constraintlayout.widget.ConstraintLayout>
```
ResultAdapter:
```java
package com.bai.psychedelic.bear;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvResultAdapter extends RecyclerView.Adapter<RvResultAdapter.ViewHolder> {
    private ArrayList<String> mList;

    public RvResultAdapter(ArrayList<String> mList) {
        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.rv_item_tv);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_result_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

```
rv_result_item.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <TextView
        android:id="@+id/rv_item_tv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="结果"
        android:gravity="center"
        />
</LinearLayout>
```

APP运行效果如下~
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200121195148760.gif)

Video:

[video(video-LJNQiGLH-1579607750339)(type-bilibili)(url-https://player.bilibili.com/player.html?aid=84445480)(image-https://ss.csdn.net/p?http://i0.hdslb.com/bfs/archive/4cc2afcaa1c2e3717043fdd47f779dccf975e475.jpg)(title-018f3fb77015e03ea82520829008e42c)]


其实我认为这题不用递归用生产者消费者模式也可以搞定，但是麻烦一些。马上8点了我要去看狗了，不写了，有兴趣自己试嗷！

package com.bai.psychedelic.beer;

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

    public void setmCapToBeer(int mCapToBeer) {
        this.mCapToBeer = mCapToBeer;
    }

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

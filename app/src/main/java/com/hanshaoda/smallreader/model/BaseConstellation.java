package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/10/10 下午4:19
 * description:
 */
public class BaseConstellation extends RootConstellation{
    private String health;
    private String love;
    private String money;
    private String work;

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "BaseConstellation{" +
                "health='" + health + '\'' +
                ", love='" + love + '\'' +
                ", money='" + money + '\'' +
                ", work='" + work + '\'' +
                '}';
    }
}

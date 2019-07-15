package com.threey.guard.api.domain;

/**
 * <desc>
 * Created by The Moss on 2018/9/13.
 */

public class AccountData {
    /*id*/
    String id;
    /*卡号*/
    String card;
    /*添加、删除 默认0 添加*/
    int isAdd;
    /**
     * 单门双门权限 0 1门  1  2门  3双开
     */
    int jurisdiction;

    String no;

    String phone;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(int isAdd) {
        this.isAdd = isAdd;
    }

    public int getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(int jurisdiction) {
        this.jurisdiction = jurisdiction;
    }
}

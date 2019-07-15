package com.threey.guard.api.domain;

/**
 * <desc>
 * Created by The Moss on 2018/9/13.
 */

public class HousePhoneData {
    /*id*/
    String id;
    /*添加、删除 默认0 添加*/
    int isAdd;
    /**
     * 单门双门权限 0 1门  1  2门  3双开
     */
    int jurisdiction;

    String no;

    String p;

//    String pp;
//
//    String ppp;


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

//    public String getPp() {
//        return pp;
//    }
//
//    public void setPp(String pp) {
//        this.pp = pp;
//    }
//
//    public String getPpp() {
//        return ppp;
//    }
//
//    public void setPpp(String ppp) {
//        this.ppp = ppp;
//    }
}

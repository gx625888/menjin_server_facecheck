package com.threey.guard.manage.domain;

import java.io.Serializable;

/**
 * 通卡信息表
 *
 * create table mj_card_common
 * (
 *   id            bigint(30) auto_increment
 *   comment '主键id'
 *     primary key,
 *   CARD_ID       varchar(130)                        null
 *   comment '卡id',
 *
 *   user_name varchar(100) null
 *     comment '持卡人姓名',
 *   user_phone varchar(50) null
 *     comment  '持卡人联系方式',
 *   user_card varchar(100) null
 *     comment '持卡人证件号',
 *
 *   create_date   timestamp default CURRENT_TIMESTAMP null
 *   comment '录入时间',
 *   residentail   varchar(130)                        null
 *   comment '小区id'
 * )
 *   comment '通卡表';
 */
public class CommonCard implements Serializable {

        private long id;
        private String cardId;
        private String userName;
        private String userPhone;
        private String userCard;
        private String residentail;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getResidentail() {
        return residentail;
    }

    public void setResidentail(String residentail) {
        this.residentail = residentail;
    }
}

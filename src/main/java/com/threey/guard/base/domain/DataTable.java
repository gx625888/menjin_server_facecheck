package com.threey.guard.base.domain;

import java.util.List;

/**
 * jquery datatables 对应的实体类
 */
public class DataTable {
    public static class Req{
        private int page;
        private int limit;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }

    public static class Resp<T>{

        private int code;
        private int count;
        private List<T> data;
        private String msg;

        public Resp(){

        }
        public Resp(String msg){
            this.msg = msg;
        }
        public Resp(List<T> list,int total,int code){
            this.code = code;
            this.data = list;
            this.count = total;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}

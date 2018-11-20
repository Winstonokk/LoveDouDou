package com.barnettwong.lovedoudou.bean;

import java.util.List;

public class VideoListBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"id":479,"title":" 电视剧系列","source":4,"anchor":"猫猫猫","avatar":"http//physwhlwdcom/uploads/20180827/6eaa65b91d58fb050304a26626418d84.jpg","live":2,"url":"http://www.yy.com/1351058483/1351058483","hot":0,"is_collect":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 479
         * title :  电视剧系列
         * source : 4
         * anchor : 猫猫猫
         * avatar : http//physwhlwdcom/uploads/20180827/6eaa65b91d58fb050304a26626418d84.jpg
         * live : 2
         * url : http://www.yy.com/1351058483/1351058483
         * hot : 0
         * is_collect : 0
         * is_find :false
         */

        private int id;
        private String title;
        private int source;
        private String anchor;
        private String avatar;
        private int live;
        private String url;
        private String cover;
        private int hot;
        private int is_collect;

        private boolean is_find;

        public boolean isIs_find() {
            return is_find;
        }

        public void setIs_find(boolean is_find) {
            this.is_find = is_find;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getAnchor() {
            return anchor;
        }

        public void setAnchor(String anchor) {
            this.anchor = anchor;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getLive() {
            return live;
        }

        public void setLive(int live) {
            this.live = live;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }
    }
}

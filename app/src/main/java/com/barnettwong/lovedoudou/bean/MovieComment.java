package com.barnettwong.lovedoudou.bean;


public class MovieComment {

    /**
     * id : 7997993
     * nickname : 革角力
     * userImage : http://img32.mtime.cn/up/2013/12/10/195719.16304374_128X128.jpg
     * rating : 0.0
     * title : 6大亮点及3个缺陷
     * summary :


     《乐高蝙蝠侠大电影》本周末即将在国内上映，目前来说这部动画电影在国外的评价相当好。不过说到底，很大程度上它还是一部玩具广告片，虽然远算不上完美，但也基本不会让人失望。而且鉴于其惊人的票房成绩，显然乐高大电影的品牌一时半会不会很快完结掉（北美《乐高蝙蝠侠大电影》开场前还特意播放了为9月《乐高幻影忍者大电影》预热的加映短片）。



     尽管海外评价颇高，国内媒体也一味追捧，但这部动画电...
     * relatedObj : {"type":1,"id":223948,"title":"乐高蝙蝠侠大电影","rating":"7.9","image":"http://img5.mtime.cn/mt/2017/01/25/142342.49345371_1280X720X2.jpg"}
     */

    private int id;
    private String nickname;
    private String userImage;
    private String rating;
    private String title;
    private String summary;
    private RelatedObjBean relatedObj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public RelatedObjBean getRelatedObj() {
        return relatedObj;
    }

    public void setRelatedObj(RelatedObjBean relatedObj) {
        this.relatedObj = relatedObj;
    }

    public static class RelatedObjBean {
        /**
         * type : 1
         * id : 223948
         * title : 乐高蝙蝠侠大电影
         * rating : 7.9
         * image : http://img5.mtime.cn/mt/2017/01/25/142342.49345371_1280X720X2.jpg
         */

        private int type;
        private int id;
        private String title;
        private String rating;
        private String image;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}

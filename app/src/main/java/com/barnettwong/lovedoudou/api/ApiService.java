package com.barnettwong.lovedoudou.api;

import com.barnettwong.lovedoudou.bean.GirlData;
import com.barnettwong.lovedoudou.bean.JuHeNewsData;
import com.barnettwong.lovedoudou.bean.JuheJokeData;
import com.barnettwong.lovedoudou.bean.MovieComment;
import com.barnettwong.lovedoudou.bean.ReviewDetails;
import com.barnettwong.lovedoudou.bean.VideoData;
import com.barnettwong.lovedoudou.bean.VideoListBean;
import com.barnettwong.lovedoudou.bean.VideoTypeBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 */
public interface ApiService {

    @GET("toutiao/index")
    Observable<JuHeNewsData> getJUHENewsList(@QueryMap Map<String, String> map);//获取聚合资讯列表

    @GET("joke/content/list.php")
    Observable<JuheJokeData> getJUHEJOkesList(@QueryMap Map<String, String> map);//获取聚合笑话列表

    @GET("MobileMovie/Review.api?needTop=false")
    Observable<List<MovieComment>> getMovieReview();

    @GET("Review/Detail.api")
    Observable<ReviewDetails> getReviewDetails(@Query("reviewId") int reviewId);

    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoData>>> getVideoList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("startPage") int startPage);

    @GET("v1/index/type")
    Observable<VideoTypeBean> getVideoTypes(@QueryMap Map<String, String> map);//视频分类

    @GET("v1/index")
    Observable<VideoListBean> getVideoLists(@QueryMap Map<String, String> map);//视频列表

}

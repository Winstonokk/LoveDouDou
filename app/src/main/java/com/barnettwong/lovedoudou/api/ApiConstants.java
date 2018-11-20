/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.barnettwong.lovedoudou.api;

public class ApiConstants {
    /**
     * 项目网络请求基本地址
     */
    public static final String NET_BASE_HOST = "http://v.juhe.cn/";

    public static final String YINGPING_HOST = "https://api-m.mtime.cn/";

    public static final String VIDEO_HOST = "http://c.3g.163.com/";

    public static final String HOST_SINA_PHOTO = "http://gank.io/api/";

    public static final String PH_HOST = "http://api.phmovie.net/";

    //图片详情
    public static final String SINA_ID_PHOTO_DETAIL = "hdpic_hdpic_toutiao_4";


    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.NET_HOST:
                host = NET_BASE_HOST;
                break;
            case HostType.YINGPIN_HOST:
                host = YINGPING_HOST;
                break;
            case HostType.VIDEO_HOST:
                host = VIDEO_HOST;
                break;
            case HostType.GIRL_HOST:
                host = HOST_SINA_PHOTO;
                break;
            case HostType.PH_HOST:
                host = PH_HOST;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }

}

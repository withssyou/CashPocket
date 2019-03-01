package com.ylbl.cashpocket.utils;

import com.ylbl.cashpocket.bean.GpsLocation;

import java.util.Random;

public class LocationUtils {
    static final double EARTH_RADIUS = 6372.796924;
    static final double PI = 3.1415926535898;

    /**
     * 根据中心坐标获取指定距离的随机坐标点
     * @param latitude
     * @param longitude
     * @param distance 离中心坐标距离（单位：米）
     * @return 随机坐标
     */
    public static GpsLocation GetRandomLocation(double latitude ,double longitude, double distance) {
        if (distance <= 0) distance = 50;
        double lat, lon, brg;
        double dist = 0;
        double rad360 = 2 * PI;
        distance = distance / 1000;
        GpsLocation location = new GpsLocation();
        double maxdist = distance;
        maxdist = maxdist / EARTH_RADIUS;
        double startlat = rad(latitude);
        double startlon = rad(longitude);
        double cosdif = Math.cos(maxdist) - 1;
        double sinstartlat = Math.sin(startlat);
        double cosstartlat = Math.cos(startlat);
        dist = Math.acos((new Random().nextDouble() * cosdif + 1));
        brg = rad360 * new Random().nextDouble();
        lat = Math.asin(sinstartlat * Math.cos(dist) + cosstartlat * Math.sin(dist) * Math.cos(brg));
        lon = deg(normalizeLongitude(startlon * 1 + Math.atan2(Math.sin(brg) * Math.sin(dist) * cosstartlat, Math.cos(dist) - sinstartlat * Math.sin(lat))));
        lat = deg(lat);
        location.setLat(padZeroRight(lat));
        location.setLon(padZeroRight(lon));
        return location;
    }

    /**
     * 弧度
     *
     * @param d
     * @return
     */
    static double rad(double d) {
        return d * PI / 180.0;
    }
    /**
     * 角度
     *
     * @param rd
     * @return
     */
    static double deg(double rd) {
        return (rd * 180 / Math.PI);
    }

    static double normalizeLongitude(double lon) {
        double n = PI;
        if (lon > n) {
            lon = lon - 2 * n;
        } else if (lon < -n) {
            lon = lon + 2 * n;
        }
        return lon;
    }
    static double padZeroRight(double s) {
        double sigDigits = 8;
        s = Math.round(s * Math.pow(10, sigDigits)) / Math.pow(10, sigDigits);
        return s;
    }
}
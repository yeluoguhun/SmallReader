package com.hanshaoda.smallreader.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午2:57
 * description:
 */

@SuppressLint("ParcelCreator")
public class City implements Parcelable {

    private List<HeWeather5Bean> heWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return heWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> heWeather5) {
        this.heWeather5 = heWeather5;
    }

    public static class HeWeather5Bean implements Parcelable {

        private BasicBean basic;
        private String status;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private static class BasicBean implements Parcelable {
            /**
             * city : 北京
             * cnty : 中国
             * id : CN101010100
             * lat : 39.904000
             * lon : 116.391000
             * prov : 直辖市
             */
            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private String prov;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getProv() {
                return prov;
            }

            public void setProv(String prov) {
                this.prov = prov;
            }

            @Override
            public String toString() {
                return "BasicBean{" +
                        "city='" + city + '\'' +
                        ", cnty='" + cnty + '\'' +
                        ", id='" + id + '\'' +
                        ", lat='" + lat + '\'' +
                        ", lon='" + lon + '\'' +
                        ", prov='" + prov + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

                parcel.writeString(this.city);
                parcel.writeString(this.cnty);
                parcel.writeString(this.id);
                parcel.writeString(this.lat);
                parcel.writeString(this.lon);
                parcel.writeString(this.prov);
            }

            public BasicBean() {
            }

            protected BasicBean(Parcel in) {
                this.city = in.readString();
                this.cnty = in.readString();
                this.id = in.readString();
                this.lat = in.readString();
                this.lon = in.readString();
                this.prov = in.readString();
            }

            public static final Parcelable.Creator<BasicBean> CREATOR = new Parcelable.Creator<BasicBean>() {
                @Override
                public BasicBean createFromParcel(Parcel parcel) {
                    return new BasicBean(parcel);
                }

                @Override
                public BasicBean[] newArray(int i) {
                    return new BasicBean[i];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.basic, i);
            parcel.writeString(this.status);
        }

        public HeWeather5Bean() {
        }

        protected HeWeather5Bean(Parcel parcel) {
            this.basic = parcel.readParcelable(BasicBean.class.getClassLoader());
            this.status = parcel.readString();
        }

        public static final Parcelable.Creator<HeWeather5Bean> CREATOR = new Parcelable.Creator<HeWeather5Bean>() {
            @Override
            public HeWeather5Bean createFromParcel(Parcel parcel) {
                return new HeWeather5Bean(parcel);
            }

            @Override
            public HeWeather5Bean[] newArray(int i) {
                return new HeWeather5Bean[i];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.heWeather5);
    }

    public City() {
    }

    protected City(Parcel p) {
        this.heWeather5 = p.createTypedArrayList(HeWeather5Bean.CREATOR);
    }

    public static final Parcelable.Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel parcel) {
            return new City(parcel);
        }

        @Override
        public City[] newArray(int i) {
            return new City[i];
        }
    };
}

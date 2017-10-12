package com.hanshaoda.smallreader.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午4:47
 * description:
 */
public class WechatItem implements Parcelable {

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean implements Parcelable {

        private int totalPage;
        private int ps;
        private int pno;
        private List<ListBean> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getPno() {
            return pno;
        }

        public void setPno(int pno) {
            this.pno = pno;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable, MultiItemEntity {
            /**
             * id : wechat_20170222006600
             * title : 不死的基因
             * source : 大科技
             * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-13427722.jpg/640
             * mark :
             * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20170222006600
             */

            public static final int STYPE_BIG = 1;
            public static final int STYPE_SMALL = 0;
            public static final int STYLE_SMALL_SPAN_SIZE = 1;
            public static final int STYLE_BIG_SPAN_SIZE = 2;

            private String id;
            private String title;
            private String source;
            private String firstImg;
            private String mark;
            private String url;

            private int itemType = 0;
            private int spansize = 1;

            @Override
            public int getItemType() {
                return itemType;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }

            public int getSpansize() {
                return spansize;
            }

            public void setSpansize(int spansize) {
                this.spansize = spansize;
            }

            public static int getStypeBig() {
                return STYPE_BIG;
            }

            public static int getStypeSmall() {
                return STYPE_SMALL;
            }

            public static int getStyleSmallSpanSize() {
                return STYLE_SMALL_SPAN_SIZE;
            }

            public static int getStyleBigSpanSize() {
                return STYLE_BIG_SPAN_SIZE;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getFirstImg() {
                return firstImg;
            }

            public void setFirstImg(String firstImg) {
                this.firstImg = firstImg;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.title);
                dest.writeString(this.source);
                dest.writeString(this.firstImg);
                dest.writeString(this.mark);
                dest.writeString(this.url);
                dest.writeInt(this.itemType);
                dest.writeInt(this.spansize);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.title = in.readString();
                this.source = in.readString();
                this.firstImg = in.readString();
                this.mark = in.readString();
                this.url = in.readString();
                this.itemType = in.readInt();
                this.spansize = in.readInt();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.totalPage);
            parcel.writeInt(this.ps);
            parcel.writeInt(this.pno);
            parcel.writeList(this.list);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.totalPage = in.readInt();
            this.ps = in.readInt();
            this.pno = in.readInt();
            this.list = new ArrayList<>();
            in.readList(this.list, ListBean.class.getClassLoader());
        }

        private static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel parcel) {
                return new ResultBean(parcel);
            }

            @Override
            public ResultBean[] newArray(int i) {
                return new ResultBean[i];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.reason);
        parcel.writeParcelable(this.result, i);
        parcel.writeInt(this.error_code);
    }

    public WechatItem() {
    }

    protected WechatItem(Parcel parcel) {
        this.reason = parcel.readString();
        this.result = parcel.readParcelable(ResultBean.class.getClassLoader());
        this.error_code = parcel.readInt();
    }

    private static final Creator<WechatItem> CREATOR = new Creator<WechatItem>() {
        @Override
        public WechatItem createFromParcel(Parcel parcel) {
            return new WechatItem(parcel);
        }

        @Override
        public WechatItem[] newArray(int i) {
            return new WechatItem[i];
        }
    };
}

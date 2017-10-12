package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午4:31
 * description:
 */
public class QueryIDCard {
    /**
     * resultcode : 200
     * reason : 成功的返回
     * result : {"area":"河南省驻马店地区汝南县","sex":"男","birthday":"1993年12月20日","verify":""}
     * error_code : 0
     */
    private String resultcode;
    private String reason;
    private int error_code;
    private ResultBean result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * area : 河南省驻马店地区汝南县
         * sex : 男
         * birthday : 1993年12月20日
         * verify :
         */
        private String area;
        private String sex;
        private String birthday;
        private String verify;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getVerify() {
            return verify;
        }

        public void setVerify(String verify) {
            this.verify = verify;
        }

        @Override
        public String toString() {
            return "查询结果{" +
                    "地区='" + area + '\'' +
                    ", 性别='" + sex + '\'' +
                    ", 生日='" + birthday + '\'' +
                    ", 校验='" + verify + '\'' +
                    '}';
        }
    }
}

package com.code.safechain.ui.my.bean;

/**
 * @Auther: hchen
 * @Date: 2020/8/8 0008
 * @Description:
 */
public class UploadIconRsBean {

    /**
     * error : 0
     * message : success
     * result : {"img_url":"http://localhost:51000/upload/200719/xvONcvNGNft6odKVCEHfzUTsNJ3vtLGQZtArYEHr.jpeg"}
     */

    private int error;
    private String message;
    private ResultBean result;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * img_url : http://localhost:51000/upload/200719/xvONcvNGNft6odKVCEHfzUTsNJ3vtLGQZtArYEHr.jpeg
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}

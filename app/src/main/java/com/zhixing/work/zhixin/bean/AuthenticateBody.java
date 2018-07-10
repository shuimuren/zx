package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/9.
 * Description:
 */

public class AuthenticateBody {

    /**
     * AuthenticatesId : 7
     * Info : {"RealName":"wuu","IdCard":"987654321987654321","ImgUrl":"https://www.baidu.com/img/bd_logo1.png?where=super,https://www.baidu.com/img/bd_logo1.png?where=super"}
     */

    private int AuthenticatesId;
    private InfoBean Info;

    public int getAuthenticatesId() {
        return AuthenticatesId;
    }

    public void setAuthenticatesId(int AuthenticatesId) {
        this.AuthenticatesId = AuthenticatesId;
    }

    public InfoBean getInfo() {
        return Info;
    }

    public void setInfo(InfoBean Info) {
        this.Info = Info;
    }

    public static class InfoBean {
        /**
         * RealName : wuu
         * IdCard : 987654321987654321
         * ImgUrl : https://www.baidu.com/img/bd_logo1.png?where=super,https://www.baidu.com/img/bd_logo1.png?where=super
         */

        private String RealName;
        private String IdCard;
        private String ImgUrl;

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getIdCard() {
            return IdCard;
        }

        public void setIdCard(String IdCard) {
            this.IdCard = IdCard;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }
    }
}

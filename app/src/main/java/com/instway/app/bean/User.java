package com.instway.app.bean;

/**
 */
public class User extends Entity {

    private String userId;
    private String userUsername;
    private String userPhone;
    private String userHeadpic;
    private String sessionId;
    private String ts_u_headpic;

    public String getHeadpic() {
        return ts_u_headpic;
    }

    public void setHeadpic(String ts_u_headpic) {
        this.ts_u_headpic = ts_u_headpic;
    }


    public String getUserIid() {
        return userId;
    }

    public void setUserIid(String userIid) {
        this.userId = userIid;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserHeadpic() {
        return userHeadpic;
    }

    public void setUserHeadpic(String userHeadpic) {
        this.userHeadpic = userHeadpic;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}

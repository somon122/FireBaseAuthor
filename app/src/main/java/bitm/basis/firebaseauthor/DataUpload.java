package bitm.basis.firebaseauthor;

public class DataUpload {
    String uId;
    String mName;
    String mTime;

    public DataUpload() {
    }

    public DataUpload(String uId, String mName, String mTime) {
        this.uId = uId;
        this.mName = mName;
        this.mTime = mTime;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}

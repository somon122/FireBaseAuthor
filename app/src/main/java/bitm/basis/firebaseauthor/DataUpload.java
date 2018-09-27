package bitm.basis.firebaseauthor;

public class DataUpload {
    String uId;
    String refName;
    String mTime;

    public DataUpload() {
    }

    public DataUpload(String uId, String mName, String mTime) {
        this.uId = uId;
        this.refName = mName;
        this.mTime = mTime;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}

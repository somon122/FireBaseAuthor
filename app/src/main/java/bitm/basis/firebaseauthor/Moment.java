package bitm.basis.firebaseauthor;

public class Moment {

    String mId;
    String mName;
    String mTime;

    public Moment() {
    }

    public Moment(String mId, String mName, String mTime) {
        this.mId = mId;
        this.mName = mName;
        this.mTime = mTime;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
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

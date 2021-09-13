package techtown.org.materialme;

import android.os.Parcel;
import android.os.Parcelable;

/*
리사이클러뷰에 들어갈 데이터 모델 클래스
데이터를 전달해 detail 부분을 바꿀 예정이므로 넘기고 나타낼 때 아래 정의한 변수를 다 넘기기 위해서 Parcelable 사용함
 */
class Sport implements Parcelable {

    private String title;
    private String info;
    private String details;
    private final int imageResource;

    // 스포츠 데이터 모델을 넣을 생성자
    Sport(String title, String info, int imageResource, String details) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
        this.details = details;
    }


    protected Sport(Parcel in) {
        // 객체를 받고 직렬화를 풀어주는 메소드, 넘겨받은 데이터를 읽어들임
        title = in.readString();
        info = in.readString();
        details = in.readString();
        imageResource = in.readInt();
    }

    // Parcelable을 사용하기 위해 필수적으로 만들어야 하는 static class, Parcel로부터 객체를 만듬
    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    // 각각 getter를 구현함
    public String getInfo() {
        return info;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDetails() {
        return details;
    }

    // Parcelable implements하면 구현해야 하는 메소드들
    @Override
    public int describeContents() {
        return 0;
    }

    // 데이터를 직렬화시켜주는 메소드
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // Parcel에 담겨서 각각 맞는 Data type에 메소드를 통해서 씀
        parcel.writeString(title);
        parcel.writeString(info);
        parcel.writeString(details);
        parcel.writeInt(imageResource);
    }


}

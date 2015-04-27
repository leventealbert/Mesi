package com.leventealbert.mesi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author levencus
 */
public class UserList extends ArrayList<User> implements Parcelable {

    private static final long serialVersionUID = 663585476779879096L;

    public UserList() {
    }

    public UserList(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserList createFromParcel(Parcel in) {
            return new UserList(in);
        }

        public Object[] newArray(int arg0) {
            return null;
        }
    };


    private void readFromParcel(Parcel in) {
        this.clear();

        //First we have to read the list size
        int size = in.readInt();

        //Reading reading data from Parcel
        for (int i = 0; i < size; i++) {
            this.add(new User(
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString()
            ));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int size = this.size();

        //We have to write the list size, we need him recreating the list
        dest.writeInt(size);

        //andding fields to the Parcel.
        for (int i = 0; i < size; i++) {
            User user = this.get(i);

            dest.writeString(user.getId());
            dest.writeString(user.getFirstName());
            dest.writeString(user.getLastName());
            dest.writeString(user.getEmail());
            dest.writeString(user.getLat());
            dest.writeString(user.getLng());
            dest.writeString(user.getAvatar());
            dest.writeString(user.getTeamId());
        }
    }
}

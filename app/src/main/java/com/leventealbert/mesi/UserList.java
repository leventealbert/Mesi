package com.leventealbert.mesi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * class used for the user list json conversions
 *
 * @author Levente Albert
 */
public class UserList extends ArrayList<User> {

    private static final long serialVersionUID = 663585476779879096L;

    /**
     * constructor
     */
    public UserList() {
    }
}

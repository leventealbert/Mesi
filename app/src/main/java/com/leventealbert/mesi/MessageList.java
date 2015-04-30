package com.leventealbert.mesi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * class used for the message list json conversions
 *
 * @author Levente Albert
 */
public class MessageList extends ArrayList<Message> {

    private static final long serialVersionUID = 663525438779879096L;

    /**
     * constructor
     */
    public MessageList() {
    }
}

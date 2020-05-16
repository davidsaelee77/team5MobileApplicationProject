package edu.uw.tcss450.team5tcss450client.ui.contacts;

import java.io.Serializable;

public class Contacts implements Serializable {

    private final String mFirstName;
    private final String mLastName;
    private final String mFullName;

    public Contacts(String mFirstName, String mLastName, String mFullName) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mFullName = mFullName;
    }

    public static class Builder {
        private final String mFirstName;
        private final String mLastName;
        private final String mFullName;

        public Builder(String mFirstName, String mLastName) {
            this.mFirstName = mFirstName;
            this.mLastName = mLastName;
            this.mFullName = mLastName + ", " + mFirstName;
        }

        public Contacts build() {
            return new Contacts( this);
        }

    }

    private Contacts(final Builder builder) {
        this.mFirstName = builder.mFirstName;
        this.mLastName = builder.mLastName;
        this.mFullName = builder.mLastName + ", " + builder.mFirstName;

    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFullName() {
        return mFullName;
    }


}

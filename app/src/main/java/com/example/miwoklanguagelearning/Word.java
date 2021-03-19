package com.example.miwoklanguagelearning;

public class Word {
    //Default translation of the word
    private String mDefaultTranslation;

    //Miwok translation of the word.
    private String mMiwokTranslation;

    // constant value for image resource
    private static final int NO_IMAGE = -1;

    // Image resource id
    private int mImageResource = NO_IMAGE;

    // Audio resource id
    private int mAudioResourceId;


    public Word(String defaultTranslation, String miwokTranslation, int image, int audioResouce) {

        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResource = image;
        mAudioResourceId = audioResouce;

    }

    public Word(String defaultTranslation, String miwokTranslation, int audioResouce) {

        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResouce;

    }


    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean hasImage() {
        return mImageResource != NO_IMAGE;

    }
}

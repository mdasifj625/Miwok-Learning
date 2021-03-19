package com.example.miwoklanguagelearning;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhraseActivity extends AppCompatActivity {



    // Create Instance of Media Player
    private MediaPlayer mMediaPlayer;


    // Create Instance of Audio Focus Request
    private AudioFocusRequest mFocusRequest;


    // Audio manager instance to manage or
    // handle the audio interruptions
    private AudioManager audioManager;


    // Audio attributes instance to set the playback
    // attributes for the media player instance
    // these attributes specify what type of media is
    // to be played and used to callback the audioFocusChangeListener
    AudioAttributes mAudioPlaybackAttribute;


    // media player is handled according to the
    // change in the focus which Android system grants for
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        releaseMediaPlayer();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMediaPlayer.start();
                    }
                }
            };




    // Assign release resource in a global variable
    MediaPlayer.OnCompletionListener mCompleteListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miwok_words);



        // initializing variables for audio focus and playback management (Audio manager system service).
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);


        // TODO Add List of Numbers

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let's go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the current position of the the item clicked and store it
                Word word = words.get(position);
                // Release resource if already another media is playing
                releaseMediaPlayer();


                mAudioPlaybackAttribute = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();

                mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                        .setAudioAttributes(mAudioPlaybackAttribute)
                        .setAcceptsDelayedFocusGain(true)
                        .setOnAudioFocusChangeListener(mAudioFocusChangeListener)
                        .build();

                final Object focusLock = new Object();

                // requesting audio focus and processing the response
                int res = audioManager.requestAudioFocus(mFocusRequest);
                synchronized (focusLock) {
                    if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                        // Audio focus lose
                        // release the media and audio focus if present
                        releaseMediaPlayer();

                    } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // Now focus have been granted
                        // Play the media now.

                        mMediaPlayer = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
                        mMediaPlayer.start();
                        // Release the resource on completion
                        mMediaPlayer.setOnCompletionListener(mCompleteListener);

                    } else if (res == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
                        releaseMediaPlayer();
                    }
                }


            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}
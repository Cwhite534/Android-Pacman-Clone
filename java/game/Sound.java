package com.cwprogramming.pacman.game;

import android.content.Context;
import android.media.MediaPlayer;

import com.cwprogramming.pacman.R;

/**
 * Created by Casey on 3/17/2018.
 */

public class Sound implements MediaPlayer.OnCompletionListener{
    private static Sound thisSound;
    private Context context;
    private MediaPlayer  mp;
    private int clipID;

    public Sound(Context context){
        this.context = context;
        thisSound = this;

    }

    public void playClip(int id) {
        if (mp!=null && id==clipID) {
            mp.pause();
            mp.seekTo(0);
            mp.start();
        }
        else {
            if (mp!=null) mp.release() ;
            clipID = id ;
            mp = MediaPlayer.create(context, id) ;
            mp.setOnCompletionListener(this) ;
            mp.setVolume(0.6f,0.6f) ;
            mp.start() ;
        }
    }

    public void pausePlayer(){
        if(mp != null)
            mp.pause();
    }

    @Override
    public void onCompletion(MediaPlayer amp){
        amp.release() ;
        mp = null ;
    }

    public static Sound getInstance(){
        if(thisSound == null)
            throw new IllegalStateException();
        return thisSound;
    }


}

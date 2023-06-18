package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView titleTv,currentTimeTv,totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay, nextbtn, previousbtn , musicIcon;
    ArrayList<AudioModel> songList;

    AudioModel currentSong;
    MediaPlayer mediaPlayer= MyMediaPlayer.getInstance();
    int x= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv=findViewById(R.id.song_title);
        currentTimeTv=findViewById(R.id.current_time);
        totalTimeTv=findViewById(R.id.total_time);
        seekBar=findViewById(R.id.seek_bar);
        pausePlay=findViewById(R.id.pause_play);
        nextbtn=findViewById(R.id.next);
        previousbtn=findViewById(R.id.previous);
        musicIcon=findViewById(R.id.music_icon_big);

        titleTv.setSelected(true);

        songList= (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");
        setResourcesWithMusıc();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMMS(mediaPlayer.getCurrentPosition()+""));
                    if(mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.baseline_pause_circle_24);
                        musicIcon.setRotation(x++);
                    }else{
                        pausePlay.setImageResource(R.drawable.baseline_play_circle_24);
                        musicIcon.setRotation(0);


                    }
                }
                new Handler().postDelayed(this,100);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer!= null && fromUser){
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    void setResourcesWithMusıc(){
        currentSong=songList.get(MyMediaPlayer.currentIndex);
        titleTv.setText(currentSong.getTitle());
        totalTimeTv.setText(convertToMMMS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v -> pausePlay());
        nextbtn.setOnClickListener(v -> playNextSong());
        previousbtn.setOnClickListener(v -> playPreviousSong());
        playMusic();





    }
    private void playMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.path);

            mediaPlayer.prepare();
            mediaPlayer.start();

            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());



        }catch (Exception e){
            e.printStackTrace();

        }

    }
    private void playNextSong(){
        if(MyMediaPlayer.currentIndex==songList.size()-1)
            return ;
        MyMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithMusıc();

    }
    private void playPreviousSong(){
        if(MyMediaPlayer.currentIndex== 0)
            return ;
        MyMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusıc();

    }
    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();

        else mediaPlayer.start();





    }


    public static String convertToMMMS(String duration){
        Long millis=Long.parseLong(duration);
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));






    }
}
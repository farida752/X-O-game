package com.example.x_o_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
//_____________________________________________________________________________________________________________________________
    String turn= "player1";
    int player1_score=0;
    int player2_score=0;
    int level=0;
    Button [][] buttons=new Button[3][3];
    String [][] buttons_text=new String[3][3];
    Button new_game;
    TextView player1Score;
    TextView player2Score;
    TextView levelCounter;
    TextView turn_shifter;
    TextView player2_text;
    LineView lineView;
    MediaPlayer levelUp;
    MediaPlayer invalid;
    MediaPlayer tie;
    MediaPlayer backgroundSound;
    ImageView settings;
    boolean computer=false;
    boolean two_players=true;
    ImageView sound;
    boolean isUnMute=true;
    Random rd;
    //_____________________________________________________________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundSound=MediaPlayer.create(this,R.raw.background);
        backgroundSound.start();
        backgroundSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                backgroundSound.start();
            }
        });

 //_____________________________________________________________________________________________________________________________
        set_buttons_Array();
        soundController();
//_____________________________________________________________________________________________________________________________
        lineView=findViewById(R.id.lineview);
        lineView.setColor(R.color.background);
        lineView.setViewA(buttons[0][0]);
        lineView.setViewB(buttons[2][2]);
        lineView.draw();
//_____________________________________________________________________________________________________________________________
        set_settings();
        handle_clicks();
    }
 //_____________________________________________________________________________________________________________________________
    public void set_buttons_Array(){
        buttons[0][0]=findViewById(R.id.button00);
        buttons[0][1]=findViewById(R.id.button01);
        buttons[0][2]=findViewById(R.id.button02);
        buttons[1][0]=findViewById(R.id.button10);
        buttons[1][1]=findViewById(R.id.button11);
        buttons[1][2]=findViewById(R.id.button12);
        buttons[2][0]=findViewById(R.id.button20);
        buttons[2][1]=findViewById(R.id.button21);
        buttons[2][2]=findViewById(R.id.button22);

        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons_text[i][j]=" ";}}

        player1Score=findViewById(R.id.player1_score);
        player2Score=findViewById(R.id.player2_score);
        levelCounter=findViewById(R.id.textView_level_counter);
        turn_shifter=findViewById(R.id.turn_shifter);
        settings=findViewById(R.id.imageView_settings);
        player2_text=findViewById(R.id.textView2_player2);
        //lineView=findViewById(R.id.lineview);
        new_game=findViewById(R.id.button_new_game);
        new_game.setVisibility(View.GONE);
        sound=findViewById(R.id.imageView_sound);
        rd=new Random();
    }
 //_____________________________________________________________________________________________________________________________
    public void handle_clicks(){
        for (int i=0;i<3;i++){
            for( int j=0;j<3;j++) {
                if(two_players){
                   on_clicks(i,j);}
                else if(computer){
                    play_with_computer(i,j);
                }
            }}
            }
  //_____________________________________________________________________________________________________________________________
    public void on_clicks(final int i, final int j) {
        buttons[i][j].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shift=0;
                if (turn.equals("player1")){
                   if(! is_valid(i,j)){
                       Toast.makeText(MainActivity.this, "in valid cell try again", Toast.LENGTH_SHORT).show();

                       invalid=MediaPlayer.create(MainActivity.this,R.raw.bomb);
                       if(isUnMute){
                       invalid.start();}

                       shift=1;
                   }
                   else{
                    buttons[i][j].setText("O");
                    buttons[i][j].setTextSize(24);
                    buttons[i][j].setTextColor(getResources().getColor(R.color.red));
                    buttons_text[i][j]="o";

                    if(check_winner(i,j,"o")){

                        levelUp=MediaPlayer.create(MainActivity.this,R.raw.level_up_up);
                        if(isUnMute){
                        levelUp.start();}

                        player1_score++;
                        player1Score.setText(Integer.toString(player1_score));
                        Toast.makeText(MainActivity.this, "Player1 wins ", Toast.LENGTH_SHORT).show();
                        checkNewGame();
                    }
                    if(end_game()){
                        Toast.makeText(MainActivity.this, "this is a tie", Toast.LENGTH_SHORT).show();
                        tie=MediaPlayer.create(MainActivity.this,R.raw.tie);
                        if(isUnMute){
                        tie.start();}
                        checkNewGame();
                    }}}
        //_____________________________________________________________________________________________________________________________
                if (turn.equals("player2")){
                    if(! is_valid(i,j)){
                        Toast.makeText(MainActivity.this, "in valid cell try again", Toast.LENGTH_SHORT).show();

                        invalid=MediaPlayer.create(MainActivity.this,R.raw.bomb);
                        if(isUnMute){
                        invalid.start();}

                        shift=1;}
                    else{
                    buttons[i][j].setText("X");
                    buttons[i][j].setTextSize(24);
                    buttons[i][j].setTextColor(getResources().getColor(R.color.blue));
                    buttons_text[i][j]="x";

                    if(check_winner(i,j,"x")){

                        levelUp=MediaPlayer.create(MainActivity.this,R.raw.level_up_up);
                        if(isUnMute){
                        levelUp.start();}

                        player2_score++;
                        player2Score.setText(Integer.toString(player2_score));
                        Toast.makeText(MainActivity.this, "Player2 wins ", Toast.LENGTH_SHORT).show();
                        checkNewGame();
                    }
                    if(end_game()){
                        Toast.makeText(MainActivity.this, "this is a tie", Toast.LENGTH_SHORT).show();
                        tie=MediaPlayer.create(MainActivity.this,R.raw.tie);
                        if(isUnMute){
                        tie.start();}
                        checkNewGame();
                    }}}

           //_____________________________________________________________________________________________________________________________
            if (shift!=1){
                if(turn.equals("player1")){
                    turn="player2";
                turn_shifter.setText("player2");
                turn_shifter.setTextColor(getResources().getColor(R.color.blue));}
                    else{
                    turn="player1";
                   turn_shifter.setText("player1");
                   turn_shifter.setTextColor(getResources().getColor(R.color.red));}
            }}
        });
    }
 //_____________________________________________________________________________________________________________________________
    public boolean end_game(){
        for (int i=0;i<3;i++){
            for( int j=0;j<3;j++) {
               if( buttons_text[i][j].equals(" ")){
                return false;}}}
            return true;
    }
  //_____________________________________________________________________________________________________________________________
    public void  new_game(){
       for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons_text[i][j]=" ";
                buttons[i][j].setText("");
               buttons[i][j].setBackground(getResources().getDrawable(R.drawable.button_view));
                  }}
       level++;
       levelCounter.setText(Integer.toString(level));
      lineView.setColor(R.color.background);
      lineView.draw();
        new_game.setVisibility(View.GONE);
        activeGrid();

    }
  //_____________________________________________________________________________________________________________________________
    public boolean check_winner(int i,int j,String currrent){
        //int win=0;
       if( buttons_text[0][j].equals(currrent)&&buttons_text[1][j].equals(currrent)&&buttons_text[2][j].equals(currrent)){
          // change_color(currrent,buttons[0][j],buttons[1][j],buttons[2][j]);
           lineView.setDistance_diagonalY(0); lineView.setDistance_diagonalX(0); lineView.setDistanceMiddleRow(0); lineView.setDistance_endRow(0);
           lineView.setDistance__endColoumn(130);
           lineView.setDistance_middle_coloumn(110);
           lineView.setColor(R.color.white);
           lineView.setViewA(buttons[0][j]);
           lineView.setViewB(buttons[2][j]);
           lineView.draw();

          return true;
           }

           //row
          else if( buttons_text[i][0].equals(currrent)&&buttons_text[i][1].equals(currrent)&&buttons_text[i][2].equals(currrent)){
          // change_color(currrent,buttons[i][0],buttons[i][1],buttons[i][2]);
           lineView.setDistance_diagonalY(0); lineView.setDistance_diagonalX(0); lineView.setDistance_middle_coloumn(0); lineView.setDistance__endColoumn(0);
           lineView.setDistance_endRow(230);
           lineView.setDistanceMiddleRow(50);
           lineView.setColor(R.color.white);
           lineView.setViewA(buttons[i][0]);
           lineView.setViewB(buttons[i][2]);
           lineView.draw();
             return true;
          }

          else if(buttons_text[0][0].equals(currrent)&&buttons_text[1][1].equals(currrent)&&buttons_text[2][2].equals(currrent)){
          // change_color(currrent,buttons[0][0],buttons[1][1],buttons[2][2]);
           lineView.setDistanceMiddleRow(0); lineView.setDistance_endRow(0); lineView.setDistance_middle_coloumn(0); lineView.setDistance__endColoumn(0);
           lineView.setDistance_diagonalX(120);
           lineView.setDistance_diagonalY(120);
           lineView.setColor(R.color.white);
           lineView.setViewA(buttons[0][0]);
           lineView.setViewB(buttons[2][2]);
           lineView.draw();
             return true;
          }

          else if(buttons_text[0][2].equals(currrent)&&buttons_text[1][1].equals(currrent)&&buttons_text[2][0].equals(currrent)){
           //change_color(currrent,buttons[0][2],buttons[1][1],buttons[2][0]);
           lineView.setDistanceMiddleRow(0); lineView.setDistance_endRow(0); lineView.setDistance_middle_coloumn(0); lineView.setDistance__endColoumn(0);
           lineView.setDistance_diagonalX(120);
           lineView.setDistance_diagonalY(120);
           lineView.setColor(R.color.white);
           lineView.setViewA(buttons[0][2]);
           lineView.setViewB(buttons[2][0]);
           lineView.draw();
              return true;
          }

          else {
              return false; }
    }
 //_____________________________________________________________________________________________________________________________
    public boolean is_valid(int i,int j){
        return buttons_text[i][j].equals(" ");
    }
 //_____________________________________________________________________________________________________________________________
    public void change_color(String ch,Button button1,Button button2,Button button3){
        if(ch.equals("o")){
            button1.setBackgroundColor(getResources().getColor(R.color.red));
            button2.setBackgroundColor(getResources().getColor(R.color.red));
            button2.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else{
            button1.setBackgroundColor(getResources().getColor(R.color.blue));
            button2.setBackgroundColor(getResources().getColor(R.color.blue));
            button3.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }
 //_____________________________________________________________________________________________________________________________
    public void set_settings (){
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(MainActivity.this,view);
                popupMenu.inflate(R.menu.popup);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id =menuItem.getItemId();
                        switch (id){
                            case R.id.item_play_with_computer:
                                computer=true;
                                two_players=false;
                                handle_clicks();
                                return true;
                            case R.id.item_two_players:
                                two_players=true;
                                computer=false;
                                new_game();
                                level=0;
                                levelCounter.setText(Integer.toString(level));
                                player1_score=0;
                                player1Score.setText(Integer.toString(player1_score));
                                player2_text.setText("player2");
                                player2_score=0;
                                player2Score.setText(Integer.toString(player2_score));
                                handle_clicks();
                            default:
                                return false;

                        }
                    }
                });
                popupMenu.show();
            }

        });
    }
 //_____________________________________________________________________________________________________________________________
        public void play_with_computer(final int i,final int j){
            new_game();
            level=0;
            levelCounter.setText(Integer.toString(level));
            player1_score=0;
            player1Score.setText(Integer.toString(player1_score));
            player2_text.setText("Computer");
            player2_score=0;
            player2Score.setText(Integer.toString(player2_score));
            turn="player1";
            turn_shifter.setText("player1");
            turn_shifter.setTextColor(getResources().getColor(R.color.red));

            buttons[i][j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int shift=0;
                    if (turn.equals("player1")){
                        if(! is_valid(i,j)){
                            Toast.makeText(MainActivity.this, "in valid cell try again", Toast.LENGTH_SHORT).show();

                            invalid=MediaPlayer.create(MainActivity.this,R.raw.bomb);
                            if(isUnMute){
                            invalid.start();}

                            shift=1;
                        }
                        else{
                            buttons[i][j].setText("O");
                            buttons[i][j].setTextSize(24);
                            buttons[i][j].setTextColor(getResources().getColor(R.color.red));
                            buttons_text[i][j]="o";

                            if(check_winner(i,j,"o")){

                                levelUp=MediaPlayer.create(MainActivity.this,R.raw.level_up_up);
                                if(isUnMute){
                                levelUp.start();}

                                player1_score++;
                                player1Score.setText(Integer.toString(player1_score));
                                Toast.makeText(MainActivity.this, "Player1 wins ", Toast.LENGTH_SHORT).show();
                                checkNewGame();
                                 }
                            if(end_game()){
                                Toast.makeText(MainActivity.this, "this is a tie", Toast.LENGTH_SHORT).show();
                                tie=MediaPlayer.create(MainActivity.this,R.raw.tie);
                                if(isUnMute){
                                tie.start();}
                                checkNewGame();
                                }}

                        if (shift!=1){
                                turn="computer";
                                turn_shifter.setText("computer");
                                turn_shifter.setTextColor(getResources().getColor(R.color.blue));}

                        if (turn.equals("computer")&&check_winner(i,j,"o")==false){
                            int row=rd.nextInt(3);
                            int coloumn=rd.nextInt(3);

                            while (! is_valid(row,coloumn)){
                                row=rd.nextInt(3);
                                coloumn=rd.nextInt(3);}
                          //  else{
                                buttons[row][coloumn].setText("X");
                                buttons[row][coloumn].setTextSize(24);
                                buttons[row][coloumn].setTextColor(getResources().getColor(R.color.blue));
                                buttons_text[row][coloumn]="x";

                                if(check_winner(row,coloumn,"x")){

                                    levelUp=MediaPlayer.create(MainActivity.this,R.raw.level_up_up);
                                    if(isUnMute){
                                    levelUp.start();}

                                    player2_score++;
                                    player2Score.setText(Integer.toString(player2_score));
                                    Toast.makeText(MainActivity.this, "Computer wins ", Toast.LENGTH_SHORT).show();
                                    checkNewGame();
                                }
                                if(end_game()){
                                    Toast.makeText(MainActivity.this, "this is a tie", Toast.LENGTH_SHORT).show();
                                    tie=MediaPlayer.create(MainActivity.this,R.raw.tie);
                                    if(isUnMute){
                                      tie.start();}
                                    checkNewGame();
                                }}

                                turn="player1";
                                turn_shifter.setText("player1");
                                turn_shifter.setTextColor(getResources().getColor(R.color.red));

                        }
                        }
                 //   }
            });
        }
 //_____________________________________________________________________________________________________________________________

 public void checkNewGame(){
     enableGrid();
          new_game.setVisibility(View.VISIBLE);
          new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_game();
            }
        });
}
//____________________________________________________________________________________________________________________________________________________
public void enableGrid(){
    for (int i=0;i<3;i++){
        for( int j=0;j<3;j++) {
            buttons[i][j].setClickable(false);
        }}
}
 //____________________________________________________________________________________________________________________________________________________
    public void activeGrid(){
        for (int i=0;i<3;i++){
            for( int j=0;j<3;j++) {
                buttons[i][j].setClickable(true);
            }}
    }
//____________________________________________________________________________________________________________________________________________________
    public void soundController(){
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUnMute==true){
                    isUnMute=false;
                    sound.setImageResource(R.drawable.ic_volume_off);
                     backgroundSound.pause();

                }
                else{
                    isUnMute=true;
                    sound.setImageResource(R.drawable.ic_volume_up);
                    backgroundSound.start();
                    backgroundSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            backgroundSound.start();
                        }
                    });
                }
            }
        });

    }
    //____________________________________________________________________________________________________________________________________________________
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backgroundSound.pause();
    }
//____________________________________________________________________________________________________________________________________________________

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        backgroundSound.start();
        backgroundSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                backgroundSound.start();
            }
        });
    }
    //____________________________________________________________________________________________________________________________________________________
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        backgroundSound.pause();
    }
}





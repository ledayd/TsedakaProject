package com.example.apple.tsedaka;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.paypal.android.MEP.PayPalPayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.floor;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_FILE_NAME = "myPrefs";
    protected static final String PREFS_TOTAL_AMOUNT = "total_amount";

    private SharedPreferences settings;                         // the object that related to the file.
    private SharedPreferences.Editor editor;                    // the object that can insert data to the file.

    private ImageView tsedaka;
    boolean dragging;
    float fixX;
    float fixY;
    static Piece unEuro = new Piece();
    static double total;
    static TextView totalTV;
    View view;
    View view2;
    RelativeLayout mainLayout;
    RelativeLayout.LayoutParams paramView;
    RelativeLayout.LayoutParams paramView34;
    RelativeLayout.LayoutParams paramButtons;
    static RelativeLayout.LayoutParams params;
    View view3;
    View view4;
    Handler handler;
    ArrayList<Piece> pieces = new ArrayList<>();
    LinearLayout halfdown;
    LinearLayout llfulsscreen;
    LinearLayout thirdupofhalfup;
    //LinearLayout mainLayout;
    int pieceWidth;
    int pieceHeight;
    int table[] = new int[3];
    Piece tableau[] = new Piece[4];
    Button autresMontantsBtn;
    EditText autresMontantsET;
    Button valideAutresMontantsBtn;
    Button resetButton;
    Button paypal;
    Boolean pieceIsAlreadyPointed = false;

    //essai pour git

    int piecemoved;
    MediaPlayer mPlayer;

    // modification pour tester ajout de commit

    // encore une modif juste pour formation git

    // test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.activity_main);
        llfulsscreen = (LinearLayout) findViewById(R.id.llfullscreen);

        halfdown = (LinearLayout) findViewById(R.id.halfdown);

        autresMontantsBtn = (Button) findViewById(R.id.autresMontantsBtn);
        autresMontantsET = (EditText) findViewById(R.id.autresMontantsET);
        valideAutresMontantsBtn = (Button) findViewById(R.id.validationMontantBtn);
        resetButton = (Button) findViewById(R.id.resetButton);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);




        autresMontantsET.setVisibility(View.INVISIBLE);
        valideAutresMontantsBtn.setVisibility(View.INVISIBLE);

        autresMontantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autresMontantsBtn.setVisibility(View.INVISIBLE);
                autresMontantsET.setVisibility(View.VISIBLE);
                valideAutresMontantsBtn.setVisibility(View.VISIBLE);
            }
        });

        valideAutresMontantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String montant = autresMontantsET.getText().toString();
                Double f = Double.valueOf(montant);
                if (f > 0) {
                    mPlayer.start();
                    Toast toast4 = Toast.makeText(MainActivity.this, "Shkoyeh'!", Toast.LENGTH_LONG);
                    toast4.show();
                    total = total + f;
                    total = (double) Math.round(total * 10) / 10;
                    totalTV.setText("| " + total + " € |");
                    saveData(total);

                }

                autresMontantsET.setVisibility(View.INVISIBLE);
                autresMontantsET.setText("");
                valideAutresMontantsBtn.setVisibility(View.INVISIBLE);
                autresMontantsBtn.setVisibility(View.VISIBLE);

            }
        });


        settings = getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);  // read data from file.
        editor = settings.edit();

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });


        int margin = 10;


        System.out.println("" + tableau.length);

        for (int i = 0; i <= 3; i++) {
            Piece piece = new Piece();
            piece.tag = i;

            ImageView imageView = new ImageView(this);
            piece.setImageView(imageView);
            pieces.add(piece);
        }

        pieces.get(0).setValeurNDrawable(0.2f, R.drawable.vingtctse);
        pieces.get(1).setValeurNDrawable(0.5f, R.drawable.piececinquantectse);
        pieces.get(2).setValeurNDrawable(1, R.drawable.uneuro);
        pieces.get(3).setValeurNDrawable(2, R.drawable.deuxeuros);


        total = (double) settings.getFloat(PREFS_TOTAL_AMOUNT, 0);
        total = (double) Math.round(total * 10) / 10;


        totalTV = (TextView) findViewById(R.id.totalTextView);
        totalTV.setText("| " + total + " € |");


        tsedaka = (ImageView) findViewById(R.id.imageViewTsedaka);

        view3 = new View(this);
        view4 = new View(this);
        paramView = new RelativeLayout.LayoutParams(10, 280);

        view = new View(this);
        view2 = new View(this);

        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        view2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        view3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        view4.setBackgroundColor(getResources().getColor(R.color.colorAccent));


        mPlayer = MediaPlayer.create(this, R.raw.bruitpiece_01);


        mainLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                pieceWidth = mainLayout.getWidth() / 4 - 50;
                                pieceHeight = mainLayout.getHeight() / 6 - 15;
                                params = new RelativeLayout.LayoutParams(pieceWidth, pieceWidth);
                                params.setMargins(25, 25, 25, 25);
                                System.out.println("ouou" + pieceWidth);

                                //unEuro.getImageView().setLayoutParams(params);
                                for (int i = 0; i < 4; i++) {

                                    params = new RelativeLayout.LayoutParams((i + 6) * pieceWidth / 9, (i + 6) * pieceWidth / 9);
                                    pieces.get(i).getImageView().setLayoutParams(params);

                                    float localX = i * (pieceWidth) + (i) * 25 + 40;
                                    pieces.get(i).setXinit(localX);
                                    pieces.get(i).setYinit(50f);
                                    pieces.get(i).getImageView().setX(localX);
                                    pieces.get(i).getImageView().setY(50f);
                                    mainLayout.addView(pieces.get(i).getImageView());
                                }

                                //paramButtons = new RelativeLayout.LayoutParams(2*pieceWidth/3,pieceWidth/4);
                                resetButton.setWidth(pieceWidth);
                                resetButton.setHeight(4 * pieceWidth / 9);

                                valideAutresMontantsBtn.setWidth(pieceWidth);
                                valideAutresMontantsBtn.setHeight(4 * pieceWidth / 9);

                                autresMontantsBtn.setWidth(pieceWidth);
                                autresMontantsBtn.setHeight(4 * pieceWidth / 9);

                                autresMontantsET.setWidth(pieceWidth);
                                autresMontantsET.setHeight(4 * pieceWidth / 9);


                                int local = totalTV.getHeight();


//                                valideAutresMontantsBtn.setLayoutParams(paramButtons);
//                                autresMontantsBtn.setLayoutParams(paramButtons);
//                                autresMontantsET.setLayoutParams(paramButtons);


                                System.out.println("halfown.y" + halfdown.getY());

                                System.out.println("ouou" + pieceWidth);
                            }
                        }

        );


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("ouou" + pieceWidth);
                System.out.println("halfown.y" + halfdown.getY());
                float t = halfdown.getY() + tsedaka.getY();
                System.out.println("ouou   " + t);
                System.out.println("tesdaka.y seul " + tsedaka.getY());
                System.out.println("pice width " + pieceWidth);


                for (int i = 0; i < 4; i++) {
                    if (event.getX() >= pieces.get(i).getImageView().getX()
                            && event.getX() <= pieces.get(i).getImageView().getX() + pieces.get(i).getImageView().getWidth()
                            && event.getY() >= pieces.get(i).getImageView().getY()
                            && event.getY() <= pieces.get(i).getImageView().getY() + pieces.get(i).getImageView().getHeight()
                            && !pieceIsAlreadyPointed) {
                        Log.d("TAG", "you have touch the picture");
                        fixX = event.getX() - pieces.get(i).getImageView().getX();
                        fixY = event.getY() - pieces.get(i).getImageView().getY();
                        dragging = true;

                        piecemoved = i;
                        pieceIsAlreadyPointed = true;
                    }
                }

//
                break;

            case MotionEvent.ACTION_MOVE:
                // move my finger
                if (dragging) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            view.setX(tsedaka.getX() + tsedaka.getWidth() / 3);
                            view2.setX(tsedaka.getX() + 2 * tsedaka.getWidth() / 3 - 10);
                            view.setY(halfdown.getY() - 180);
                            view2.setY(halfdown.getY() - 180);

                            paramView34 = new RelativeLayout.LayoutParams(tsedaka.getWidth() / 3, 10);


                            view3.setX(view.getX());
                            view4.setX(view.getX());
                            view3.setY(view.getY());
                            view4.setY(view.getY() + 280);

                            mainLayout.post(new Runnable() {
                                @Override
                                public void run() {


                                    mainLayout.removeView(view);
                                    mainLayout.removeView(view2);
                                    mainLayout.removeView(view3);
                                    mainLayout.removeView(view4);
                                    mainLayout.addView(view, paramView);
                                    mainLayout.addView(view2, paramView);
                                    mainLayout.addView(view3, paramView34);
                                    mainLayout.addView(view4, paramView34);


                                }
                            });





                        }
                    }).start();


                    pieces.get(piecemoved).getImageView().setX(-fixX + event.getX());
                    pieces.get(piecemoved).getImageView().setY(-fixY + event.getY());

                }
                break;


            case MotionEvent.ACTION_UP:
                // stop touching the screen

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (dragging) {
                            dragging = false;


                            mainLayout.post(new Runnable() {
                                @Override
                                public void run() {


                                    mainLayout.removeView(view);
                                    mainLayout.removeView(view2);
                                    mainLayout.removeView(view3);
                                    mainLayout.removeView(view4);

                                    // System.out.println(" hello " + view.getY());
//
                                }
                            });


//                    if ((unEuro.getImageView().getX() + unEuro.getImageView().getWidth() / 2 >= view.getX())
//                            && (unEuro.getImageView().getX() + unEuro.getImageView().getWidth() / 2 <= view2.getX())
//                            && (unEuro.getImageView().getY() + unEuro.getImageView().getHeight() / 2 <= view4.getY())
//                            && (unEuro.getImageView().getY() + unEuro.getImageView().getHeight() / 2 >= view3.getY())
//                            ) {
                            if ((pieces.get(piecemoved).getImageView().getX() + pieces.get(piecemoved).getImageView().getWidth() / 2 >= view.getX())
                                    && (pieces.get(piecemoved).getImageView().getX() + pieces.get(piecemoved).getImageView().getWidth() / 2 <= view2.getX())
                                    && (pieces.get(piecemoved).getImageView().getY() + pieces.get(piecemoved).getImageView().getHeight() / 2 <= view4.getY())
                                    && (pieces.get(piecemoved).getImageView().getY() + pieces.get(piecemoved).getImageView().getHeight() / 2 >= view3.getY())
                                    ) {


                                total = total + pieces.get(piecemoved).getValeur();
                                total = (double) Math.round(total * 100) / 100;

                                mainLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toast3 = Toast.makeText(MainActivity.this, "Shkoyeh'!", Toast.LENGTH_LONG);
                                        toast3.show();
                                        pieces.get(piecemoved).getImageView().setVisibility(View.INVISIBLE);
                                        mPlayer.start();
                                        totalTV.setText("| " + total + " € |");

//                                        pieces.get(piecemoved).getImageView().setVisibility(View.VISIBLE);
                                    }
                                });


//                                String s = df.format(total);
//                                total = Double.parseDouble(s);

                                saveData(total);
                                goHome(pieces.get(piecemoved));

//
//


                            }else{pieceIsAlreadyPointed=false; goHome(pieces.get(piecemoved));}



                        }
                    }
                }).start();

        }
        return true;
    }
    //


    private void goHome(final Piece piece) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                float xUp = piece.getImageView().getX();
                float yUp = piece.getImageView().getY();

                float pente = (float) (((float) yUp - piece.getYinit()) / ((float) xUp - piece.getXinit()));
                int signeMvtX;
                int signeMvtY;

                if (xUp > piece.getXinit()) {
                    signeMvtX = 1;
                } else signeMvtX = -1;
                if (yUp > piece.getYinit()) {
                    signeMvtY = 1;
                } else signeMvtY = -1;

                while (abs(piece.getImageView().getX() - piece.getXinit()) > 0.0011f
                        && abs(piece.getImageView().getY() - piece.getYinit()) > 0.0011f) {
                    piece.getImageView().setX(piece.getImageView().getX() - signeMvtX * 0.001f);
                    piece.getImageView().setY(piece.getImageView().getY() - signeMvtY * abs(pente) * 0.001f);
                }

                pieces.get(piecemoved).getImageView().setVisibility(View.VISIBLE);
                pieceIsAlreadyPointed = false;


            }

        });
    }

    private void saveData(double totalAmount) {
        editor.putFloat(PREFS_TOTAL_AMOUNT, (float) totalAmount);
        editor.commit();    // execute the data changing... DON'T FORGET!!!!
    }

    private void resetData() {
        mainLayout.post(new Runnable() {
            @Override
            public void run() {

                total = 0;
                totalTV.setText("| " + total + " € |");


            }
        });// execute the data changing... DON'T FORGET!!!!

        saveData(total);
    }

//    @Override
//    protected void onDestroy() {
//        if(mPlayer != null) {
//            mPlayer.release();
//            mPlayer = null;
//        }
//    }


}


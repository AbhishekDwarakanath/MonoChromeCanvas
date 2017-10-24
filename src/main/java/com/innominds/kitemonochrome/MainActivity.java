package com.innominds.kitemonochrome;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.innominds.kitemonochrome.Contacts.Contact;
import com.innominds.kitemonochrome.Contacts.ContactFetcher;
import com.innominds.kitemonochrome.Contacts.ContactHelper;
import com.innominds.kitemonochrome.Contacts.ContactPhone;
import com.innominds.kitemonochrome.network.CustomPhoneStateListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ImageView AltimageView;
    ImageView ChoosenImageView;
    int width = 84;
    int height = 48;
    boolean scale = true;

    public static int HomeScreen = 0;
    public static int ContactScreenMenu = 1;

    public static int Messages = 2;
    public static int dialerScreen = 3;
    public static int ContactsList = 4;
    public static int CallingScreen = 5;
    public static int ContactDetailScreen = 6;
    public static int NewContactScreenName = 7;
    public static int NewContactScreenNumber = 8;
    public static int ContactsOptions = 9;
    public static int DeleteContactListScreen = 10;
    public static int DeleteContactConfirmScreen = 11;
    //int nexWidth = 1260;
    //int nexHeight = 720;

  /* int nexWidth = 840;
    int nexHeight = 480; */

    int nexWidth = 84;
    int nexHeight = 48;

    Button mSelectBtn;
    Button mLeftBtn;
    Button mRightBtn;
    Button mHomeBtn;
    int screen = 0;

    Button mScaletoTen;
    Button mScaletosmall;

    Button mBtn1;
    Button mBtn2;
    Button mBtn3;
    Button mBtn4;
    Button mBtn5;
    Button mBtn6;
    Button mBtn7;
    Button mBtn8;
    Button mBtn9;
    Button mBtn0;
    Button mBtnstar;
    Button mBtnhash;
    Button mCall;
    Button cancelBtn;

    List<String> dialer = new ArrayList<String>();

    String[] Contactsptr;
    String[] Contacts = new String[6];
    private int sizeofContact = 0;
    private Contact[] Contacts1 ;
    boolean LoadedContacts = false;
    int fromContact = 0;
    Map<Integer, Contact> map = new LinkedHashMap<Integer, Contact>();
    Boolean traverseMap = false;
    Boolean contactsLoaded = false;
    Boolean contactsLoaded1 = false;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    TelephonyManager telephonyManager;
    PhoneStateListener MyListener;
    private static final int REQUEST_READ_PHONE_STATE = 0;
    public static String LOG_TAG = "MainActivity";

    NetorkSignalBroadcastReceiver mNetorkSignalBroadcastReceiver;
    private IntentFilter intentFilter;
    int mBatteryLevel = 0;
    private int onSignalStrength = 0;

    private final List<Contact> list = new LinkedList<Contact>();
    int frmContact = 0;
    int toContact = 5;

    List<String> contactname = new ArrayList<String>();
    int contactnameindex = 0;
    int count = 0;
    int samekey = 0;

    List<String> contactnumber = new ArrayList<String>();
    private String[] contactsOptionsPtr;
    private String[] contactsOptions = new String[]{"Search","Add","Delete"};
    private Contact DeleteContact ;

    private int dialerindex = 0;
    private boolean capsLetters = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1340);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(new CustomPhoneStateListener(this),
                    PhoneStateListener.LISTEN_CALL_STATE
                            | PhoneStateListener.LISTEN_CELL_INFO // Requires API 17
                            | PhoneStateListener.LISTEN_CELL_LOCATION
                            | PhoneStateListener.LISTEN_DATA_ACTIVITY
                            | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                            | PhoneStateListener.LISTEN_SERVICE_STATE
                            | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                            | PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
                            | PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        } else {

        }*/

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //MonoChromeView monoChromeView = new MonoChromeView(this);


        AltimageView = (ImageView) findViewById(R.id.mAltered);
        AltimageView.setImageBitmap(drawbitmapfornetworksignal(onSignalStrength, "Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
        //AltimageView.setImageBitmap(drawBitmapHomeScreen("Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
        //AltimageView.setImageBitmap(drawbitmap("Menu", 2, (nexHeight / 1.02)));

        mSelectBtn = (Button) findViewById(R.id.selectBtn);
        mRightBtn = (Button) findViewById(R.id.rightBtn);
        mLeftBtn = (Button) findViewById(R.id.leftBtn);
        mHomeBtn = (Button) findViewById(R.id.homeBtn);
        mScaletosmall = (Button) findViewById(R.id.scale);
        mScaletoTen = (Button) findViewById(R.id.scale10);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn6 = (Button) findViewById(R.id.btn6);
        mBtn7 = (Button) findViewById(R.id.btn7);
        mBtn8 = (Button) findViewById(R.id.btn8);
        mBtn9 = (Button) findViewById(R.id.btn9);
        mBtn0 = (Button) findViewById(R.id.btn0);
        mBtnstar = (Button) findViewById(R.id.btnstar);
        mBtnhash = (Button) findViewById(R.id.btnhash);
        mCall = (Button) findViewById(R.id.btncall);
        cancelBtn = (Button) findViewById(R.id.cancelbtn);
        mSelectBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
        mLeftBtn.setOnClickListener(this);
        mHomeBtn.setOnClickListener(this);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
        mBtn8.setOnClickListener(this);
        mBtn9.setOnClickListener(this);
        mBtn0.setOnClickListener(this);
        mBtnstar.setOnClickListener(this);
        mBtnhash.setOnClickListener(this);
        mCall.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        mBtnhash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                capsLetters = true?false:true;
                return true;
            }
        });
        mScaletoTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 nexWidth = 840;
//                 nexHeight = 480;
                //getScaledBitmap(drawbitmap("Menu",(nexWidth/2.52),(nexHeight/1.02)));
                scale = true;
                //AltimageView.setImageBitmap(drawbitmap("Drawing multiline text is actually pretty simple and this is what you need to do", 5, (nexHeight / 1.02)));
                AltimageView.setImageBitmap(drawBitmapHomeScreen("Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
            }
        });

        mScaletosmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scale = false;
                AltimageView.setImageBitmap(drawBitmapHomeScreen("Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
            }
        });

        //ChoosenImageView = (ImageView) findViewById(R.id.mChoosen);
        /*Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logoinno_1);
        //ChoosenImageView.setImageBitmap(largeIcon);

        Bitmap bmp = Bitmap.createBitmap(largeIcon.getWidth(),largeIcon.getHeight(), largeIcon.getConfig());
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        canvas.drawBitmap(largeIcon, 0, 0, paint);

        Canvas canvas1 = new Canvas(bmp);
          float scale = getResources().getDisplayMetrics().density;
           Paint p = new Paint();
            p.setColor(Color.BLUE);
          p.setTextSize(24*scale);
           canvas.drawText("Hello", AltimageView.getWidth(),AltimageView.getHeight(),
                p);
        AltimageView.setImageBitmap(bmp);
*/
        //MonoChromeView monoChromeView = new MonoChromeView(this);
        //monoChromeView.draw(canvas2);
        //setContentView(monoChromeView);

        float scale = getResources().getDisplayMetrics().density;
//        MonoChromeView monoChromeView1 = new MonoChromeView(this);
//        Bitmap bitmap1 = Bitmap.createBitmap(84, 48, Bitmap.Config.ARGB_8888);
//        Canvas canvas3 = new Canvas(bitmap1);
//        monoChromeView.draw(canvas3);
//        ChoosenImageView.setImageBitmap(bitmap1);

        mNetorkSignalBroadcastReceiver = new NetorkSignalBroadcastReceiver();
        intentFilter = new IntentFilter("com.innominds.Monochrome.NETWORKSIGNALINFO");
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i(LOG_TAG, "onSignalStrengthsChanged: " + grantResults);
                }
                break;
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            default:
                break;
        }
    }

    public Bitmap drawbitmap(String text, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 5.5);
        //return getScaledBitmap(bitmap);

       /* Paint.FontMetrics fm = new Paint.FontMetrics();
        Paint mTxtPaint = new Paint();
        mTxtPaint.setColor(Color.GREEN);
        mTxtPaint.setTextSize(nexHeight / (float) 5.5);
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        mTxtPaint.setTypeface(tf);

        mTxtPaint.getFontMetrics(fm);

        canvas.drawRect((float) x - 2,(float) y - mTxtPaint.getTextSize(), (float) x + mTxtPaint.measureText(text), (float) y, mTxtPaint);

        mTxtPaint.setColor(Color.BLACK);*/

        canvas.drawText(text, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    private Bitmap drawbitmapforCalling(Contact contact, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 6);
        canvas.drawText("Calling ....", (int) (nexWidth / 7), (int) (nexHeight / 1.2), pText);
        canvas.drawText(contact.name, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    private Bitmap drawbitmapforDialing(List<String> numbers, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 6);
        if(screen == NewContactScreenNumber){
            canvas.drawText("Number", (int) x/5, (int) y/ 2, pText);
        }
        for (int i = 0; i < dialer.size(); i++) {
            canvas.drawText(dialer.get(i), ((int) x * i + 1) / 3, (int) y, pText);
        }
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    private Bitmap drawbitmapforNewContact(List<String> name, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 6);
        canvas.drawText("Name", (int) x/5, (int) y/ 2, pText);
        if(contactname!=null){
            for (int i = 0; i < contactname.size(); i++) {
                canvas.drawText(contactname.get(i), ((int) x * i + 1) / 3, (int) y, pText);
            }
        }
        //Log.d("MainActivity",""+convertToString(contactname));
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    private Bitmap drawbitmapforContactDetail(Contact contact, double x, double y) {
        if (contact.name.charAt(0) == '>') {
            contact.name = contact.name.replace(">", "");
        }
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 6);
        canvas.drawText(contact.numbers.get(0).number, (int) (nexWidth / 7), (int) (nexHeight / 1.2), pText);
        canvas.drawText(contact.name, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    private Bitmap drawbitmapforContactDelete(Contact contact, double x, double y) {
        if (contact.name.charAt(0) == '>') {
            contact.name = contact.name.replace(">", "");
        }
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 6);
        canvas.drawText("Delete ?", (int) x/5, (int) y/ 2, pText);
        canvas.drawText(contact.numbers.get(0).number, (int) (nexWidth / 7), (int) (nexHeight / 1.2), pText);
        canvas.drawText(contact.name, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    public Bitmap drawbitmapforContacts(String[] contacts, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        //pText.setTextSize(nexHeight / (float) 6.6);
        pText.setTextSize(nexHeight / (float) 6);
        //canvas.drawText(text, (int) x, (int) y, pText);
        //drawString(canvas,pText,text,5,10);
        drawMultilineText(contacts, 5, 8, pText, canvas);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
        //return getScaledBitmap(bitmap);
    }

    public Bitmap drawbitmapforContactsscrollable(String[] contacts, String[] pointerto, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        //pText.setTextSize(nexHeight / (float) 6.6);
        pText.setTextSize(nexHeight / (float) 6);
        //canvas.drawText(text, (int) x, (int) y, pText);
        //drawString(canvas,pText,text,5,10);
        drawMultilineText(drawpointertoContacts(contacts, pointerto), 5, 8, pText, canvas);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
        //return getScaledBitmap(bitmap);
    }

    public Bitmap drawbitmapforContactsscrollabl1e1(Contact[] contacts, String[] pointerto, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        //pText.setTextSize(nexHeight / (float) 6.6);
        pText.setTextSize(nexHeight / (float) 6);
        //canvas.drawText(text, (int) x, (int) y, pText);
        //drawString(canvas,pText,text,5,10);
        drawMultilineText1(drawpointertoContacts1(contacts, pointerto), 5, 8, pText, canvas);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
        //return getScaledBitmap(bitmap);
    }

    public Bitmap drawbitmapforContactsOptions(String [] contactsOptionsPtr ,String [] pointerto ,double x,double y){
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        //pText.setTextSize(nexHeight / (float) 6.6);
        pText.setTextSize(nexHeight / (float) 6.6);
        drawMultilineTextContactOptions(drawpointertoContactsoptions(contactsOptionsPtr, pointerto), 5, 8, pText, canvas);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    private String[] drawpointertoContacts(String[] contacts, String[] pointerto) {
        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                contacts[j] = ">" + contacts[j];
                return contacts;
            }

        }
        return contacts;
    }

    private Contact[] drawpointertoContacts1(Contact[] contacts, String[] pointerto) {
        for (int j = 0; j < sizeofContact; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                contacts[j].name = ">" + contacts[j].name;
                return contacts;
            }

        }
        return contacts;
    }

    private String[] drawpointertoContactsoptions(String[] contacts, String[] pointerto) {
        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                contacts[j] = ">" + contacts[j];
                return contacts;
            }

        }
        return contacts;
    }

    private String[] pointToNextContact(String[] pointerto) {
        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                pointerto[j] = "";
                if (j < pointerto.length - 1) {
                    pointerto[j + 1] = ">" + pointerto[j];
                    return pointerto;
                } else {

                    Contactsptr = new String[]{">", "", "", "", "", ""};
                    for (int k = 0; k < Contacts.length; k++) {

                        Contacts[k] = Contacts[k].replace(Contacts[k], "");

                    }
                    contactsLoaded = false;
                    //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                }
            }

        }
        return pointerto;
    }

    private String[] pointToNextContact1(String[] pointerto) {

        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                pointerto[j] = "";
                if (j < pointerto.length - 1) {
                    pointerto[j + 1] = ">" + pointerto[j];
                    return pointerto;
                } else {

                        frmContact = frmContact + 1;
                        toContact = toContact + 1;
                        Contactsptr[Contactsptr.length - 1] = ">" + Contactsptr[Contactsptr.length - 1];
                        contactsLoaded1 = false;

//                    for (int k = 0; k < Contacts.length; k++) {
//
//                        Contacts[k] = Contacts[k].replace(Contacts[k], "");
//
//                    }
                    //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                }
            }

        }
        return Contactsptr;
    }

    private String[] pointToNextOption(String[] pointerto){
        for(int j = 0;j< pointerto.length;++j){
            if(!pointerto[j].equalsIgnoreCase("")){
                pointerto[j] = "";
                if(j<pointerto.length-1) {
                    pointerto[j + 1] = ">" + pointerto[j];
                    return pointerto;
                }else{
                    pointerto[0] = ">";
                    pointerto[2] = pointerto[2].replace(">","");

                }
            }

        }
        return pointerto;
    }

    private String[] getContactOptions() {
        for (int k = 0; k < contactsOptions.length; k++) {
            if (contactsOptions[k].charAt(0) == '>') {
                contactsOptions[k] = contactsOptions[k].replace(">", "");
            }
        }

        return contactsOptions;
    }

    private String[] pointToPrevContact(String[] pointerto) {
        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                pointerto[j] = "";
                if (j > 0) {
                    pointerto[j - 1] = ">" + pointerto[j];
                    return pointerto;
                } else {
                    pointerto[0] = pointerto[0].replace(">", "");
                    pointerto[5] = pointerto[5].replace("", ">");
                    for (int k = 0; k < Contacts.length; k++) {

                        Contacts[k] = Contacts[k].replace(Contacts[k], "");

                    }
                    contactsLoaded = false;
                }

            }

        }
        return pointerto;
    }

    private String[] pointToPrevContact1(String[] pointerto) {

        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                pointerto[j] = "";
                if (j > 0) {
                    pointerto[j - 1] = ">" + pointerto[j];
                    return pointerto;
                } else {
                    frmContact = frmContact - 1;
                    //toContact = toContact+1;
                    Contactsptr[0] = ">" + Contactsptr[0];
                    contactsLoaded1 = false;
                }

            }

        }
        return pointerto;
    }


    void drawMultilineText(String[] str, int x, int y, Paint paint, Canvas canvas) {
        if (str != null) {
            Rect mBounds = canvas.getClipBounds();
            int lineHeight = 0;
            int yoffset = 0;
            //String[] lines      = str.split("\\s+");

            // set height of each line (height of text + 20%)
            paint.getTextBounds("Ig", 0, 2, mBounds);
            lineHeight = (int) ((float) mBounds.height() * 1.15);
            // draw each line
            for (int i = 0; i < str.length; ++i) {
                if (str[i] != null) {
                    canvas.drawText(str[i], x, y + yoffset, paint);
                    yoffset = yoffset + lineHeight;
                }

            }
        }
    }

    void drawMultilineText1(Contact[] str, int x, int y, Paint paint, Canvas canvas) {
        if (str != null) {
            Rect mBounds = canvas.getClipBounds();
            int lineHeight = 0;
            int yoffset = 0;
            //String[] lines      = str.split("\\s+");

            // set height of each line (height of text + 20%)
            paint.getTextBounds("Ig", 0, 2, mBounds);
            lineHeight = (int) ((float) mBounds.height() * 1.15);
            // draw each line
            for (int i = 0; i < str.length; ++i) {
                if (str[i] != null) {

                    if(str[i].name.charAt(0) == '>') {
                        Paint.FontMetrics fm = new Paint.FontMetrics();
                        Paint mTxtPaint = new Paint();
                        mTxtPaint.setColor(Color.GREEN);
                        mTxtPaint.setTextSize(nexHeight / (float) 5.5);
                        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
                        mTxtPaint.setTypeface(tf);
                        mTxtPaint.getFontMetrics(fm);
                        canvas.drawRect(0, (float) yoffset , 84, (float) yoffset + mTxtPaint.getTextSize(), mTxtPaint);
                        paint.setColor(Color.BLACK);
                        canvas.drawText(str[i].name.replace(">",""), x, y + yoffset, paint);
                        yoffset = yoffset + lineHeight;
                    }else{
                        paint.setColor(Color.GREEN);
                        canvas.drawText(str[i].name, x, y + yoffset, paint);
                        yoffset = yoffset + lineHeight;
                    }

                }

            }
        }
    }

    void drawMultilineTextContactOptions(String[] str, int x, int y, Paint paint, Canvas canvas) {
        if (str != null) {
            Rect mBounds = canvas.getClipBounds();
            int lineHeight = 0;
            int yoffset = 0;
            //String[] lines      = str.split("\\s+");

            // set height of each line (height of text + 20%)
            paint.getTextBounds("Ig", 0, 2, mBounds);
            lineHeight = (int) ((float) mBounds.height() * 2.3);
            // draw each line
            for (int i = 0; i < str.length; ++i) {
                if (str[i] != null) {

                    if(str[i].charAt(0) == '>') {
                        Paint.FontMetrics fm = new Paint.FontMetrics();
                        Paint mTxtPaint = new Paint();
                        mTxtPaint.setColor(Color.GREEN);
                        mTxtPaint.setTextSize(nexHeight / (float) 5.5);
                        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
                        mTxtPaint.setTypeface(tf);
                        mTxtPaint.getFontMetrics(fm);
                        canvas.drawRect(0, (float) yoffset , 84, (float) yoffset + mTxtPaint.getTextSize(), mTxtPaint);
                        paint.setColor(Color.BLACK);
                        canvas.drawText(str[i].replace(">",""), x, y + yoffset, paint);
                        yoffset = yoffset + lineHeight;
                    }else{
                        paint.setColor(Color.GREEN);
                        canvas.drawText(str[i], x, y + yoffset, paint);
                        yoffset = yoffset + lineHeight;
                    }
                }

            }
        }
    }

    public Bitmap drawBitmapHomeScreen(String text, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);

        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 5.5);
        canvas.drawText(text, (int) x, (int) y, pText);
        canvas.drawText("0", 5, 12, pText);
        canvas.drawText("0", 5, 20, pText);
        canvas.drawText("0", 5, 28, pText);
        canvas.drawText("0", 5, 36, pText);
        canvas.drawText("V", 5, 44, pText);

        canvas.drawText("0", 73, 12, pText);
        canvas.drawText("0", 73, 20, pText);
        canvas.drawText("0", 73, 28, pText);
        canvas.drawText("0", 73, 36, pText);
        canvas.drawText("V", 73, 44, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    private Bitmap drawbitmapfornetworksignal(int SignalStregth, String text, double x, double y) {
        int Networklevelindicator = 44;
        int Batterylevelindicator = 44;
        Bitmap bitmap = Bitmap.createBitmap(nexWidth, nexHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, nexWidth, nexHeight, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(nexHeight / (float) 5.5);
        canvas.drawText(text, (int) x, (int) y, pText);
        canvas.drawText("V", 5, 44, pText);
        canvas.drawText("V", 73, 44, pText);
        for (int i = 0; i < SignalStregth; i++) {
            Networklevelindicator = Networklevelindicator - 8;
            canvas.drawText("0", 5, Networklevelindicator, pText);
        }
        for (int i = 0; i < mBatteryLevel; i++) {
            Batterylevelindicator = Batterylevelindicator - 8;
            canvas.drawText("0", 73, Batterylevelindicator, pText);
        }
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }


    private void loadContactsMap(String[] contacts) {
        ArrayList<Contact> listContacts = new ContactFetcher(this).fetchAll();
        for (int i = 0; i < listContacts.size(); i++) {
            Contact c = listContacts.get(i);
            //map.put(i,new ContactsDetails(c.name,c.numbers.get(i).number));
        }
        /*for (int i = 0; i < contacts.length; i++) {
            map.put(i, new ContactsDetails(contacts[i], "9845098450"));
        }*/
    }

    private void loadContactsMap() {
        //ArrayList<Contact> list = ContactHelper.getContactCursor(getContentResolver(), "");
        ArrayList<Contact> listContacts = new ContactFetcher(this).fetchAll();

        Collections.sort(listContacts,Contact.ContactNameComparator);

        //ArrayList<Contact> SortedArrayList = sortContacts(listContacts);
        //ArrayList<Contact> listContacts = ContactHelper.getContactCursor(getContentResolver(),"");
       /*for (int i = 0; i < listContacts.size(); i++) {
            Contact c = listContacts.get(i);
            map.put(i, c);
        }*/

        if (!LoadedContacts)
            for (int i = 0; i < listContacts.size(); i++) {
                Contact c = listContacts.get(i);
                list.add(i, c);
                LoadedContacts = true;
            }

        /*for (int i = 0; i < contacts.length; i++) {
            map.put(i, new ContactsDetails(contacts[i], "9845098450"));
        }*/
    }

    private ArrayList<Contact> sortContacts(ArrayList<Contact> contacts){

        Contact temp ;
        for (int i = 0; i < contacts.size(); i++)
        {
            for (int j = i + 1; j < contacts.size(); j++)
            {

                if (contacts.get(i).name.compareTo(contacts.get(j).name)>0)
                {
                    temp = contacts.get(i);
                    contacts.set(i, contacts.get(j));
                    contacts.set(j,temp );
                }
            }
        }
        return contacts;
    }
    private String convertToString(List<String> letters) {

        StringBuilder sb = new StringBuilder();
        for(String str : letters){
            sb.append(str).append("");
        }

        String strfromArrayList = sb.toString();

        return  strfromArrayList;

    }


   /* private String[] getContacts() {
        int i = 0;
        if (!contactsLoaded) {
            for (Map.Entry<Integer, Contact> entry : map.entrySet()) {
                int key = entry.getKey();
                if (key == fromContact) {
                    traverseMap = true;
                }

                if (traverseMap) {
                    if (i < 6) {
                        Contacts[i] = entry.getValue().name;
                        i++;
                        if (i == 5) {
                            contactsLoaded = true;
                        }
                        Contactsptr = new String[]{">", "", "", "", "", ""};
                    } else {
                        fromContact = key;
                        contactsLoaded = true;
                        traverseMap = false;
                        i = 0;
                        return Contacts;
                    }

                }
            }
        } else {
            for (int k = 0; k < Contacts.length; k++) {
                if (Contacts[k].charAt(0) == '>') {
                    Contacts[k] = Contacts[k].replace(">", "");
                }
            }
        }
        return Contacts;
    }*/

    private Contact[] getContacts1() {

        if (!contactsLoaded1) {

            if (!list.isEmpty()) {

                    if(list.size() < 6){
                        sizeofContact = list.size();

                    }else{
                        sizeofContact = 6;
                    }
                    Contacts1 = new Contact[sizeofContact];
                    if (frmContact + 6 > list.size()) {
                        frmContact = 0;
                        Contactsptr[0] = ">" + Contactsptr[0];
                        Contactsptr[5] = Contactsptr[5].replace(">", "");
                    }
                    for (int i = 0; i < sizeofContact; ++i) {
                        Contacts1[i] = list.get(frmContact);
                        frmContact++;
                    }
                    frmContact = frmContact - 6;

                if (Contacts1[0] != null) {
                    for (int k = 0; k < Contacts1.length; k++) {
                        if (Contacts1[k].name.charAt(0) == '>') {
                            Contacts1[k].name = Contacts1[k].name.replace(">", "");
                        }
                    }
                }
                contactsLoaded1 = true;
                Contact c = list.get(1);
                Log.d(LOG_TAG, c.name);
            }
        } else {
            for (int k = 0; k < Contacts1.length; k++) {
                if (Contacts1[k].name.charAt(0) == '>') {
                    Contacts1[k].name = Contacts1[k].name.replace(">", "");
                }
            }
        }

        return Contacts1;
    }

    private Bitmap getScaledBitmap(Bitmap bitmapOrg) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        float scaleWidth = metrics.scaledDensity;
        float scaleHeight = metrics.scaledDensity;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(20, 20);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
        return resizedBitmap;
        //return bitmapOrg;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetorkSignalBroadcastReceiver, intentFilter);
        registerReceiver(this.mBatInfoReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetorkSignalBroadcastReceiver);
        unregisterReceiver(mBatInfoReceiver);
    }

    @Override
    public void onClick(View view) {
        if (screen == NewContactScreenName) {
            if (contactname != null) {
                for (int i = 0; i < contactname.size(); i++) {
                    Log.d("name", "" + contactname.get(i));
                }
            }
            int id = view.getId();
            if (samekey == 0) {
                samekey = id;
                contactname.add(contactnameindex, "");
            }
            if (samekey != id) {
                count = 0;
                samekey = id;
            }
        }
        switch (view.getId()) {
            case R.id.homeBtn:
                //dialer.clear();
                if (screen != HomeScreen) {
                    //nexWidth = 1260;
                    //nexHeight = 720;
                    //AltimageView.setImageBitmap(drawbitmap("Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
                    AltimageView.setImageBitmap(drawbitmapfornetworksignal(onSignalStrength, "Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
                    //AltimageView.setImageBitmap(drawBitmapHomeScreen("Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
                    screen = HomeScreen;
                }
                break;
            case R.id.selectBtn:
                //dialer.clear();
                if (screen == ContactScreenMenu) {
                    screen = ContactsOptions;
                    if(contactsOptionsPtr== null)
                    contactsOptionsPtr = new String[]{">","",""};
                    AltimageView.setImageBitmap(drawbitmapforContactsOptions(getContactOptions(), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));
                } else if (screen == ContactsOptions) {

                    for (int i = 0; i < contactsOptions.length; i++) {
                        if (!contactsOptions[i].equalsIgnoreCase("")) {
                            if (contactsOptions[i].charAt(0) == '>') {
                                if(contactsOptions[i].equalsIgnoreCase(">Search")){
                                    screen = ContactsList;
                                    loadContactsMap();
                                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                                    Contactsptr = new String[]{">", "", "", "", "", ""};

                                    //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                    AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));

                                }else if(contactsOptions[i].equalsIgnoreCase(">Add")){
                                    screen = NewContactScreenName;
                                    mSelectBtn.setText("Next");
                                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                                }else if(contactsOptions[i].equalsIgnoreCase(">Delete")){
                                    mSelectBtn.setText("Delete");
                                    screen = DeleteContactListScreen;
                                    loadContactsMap();
                                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                                    Contactsptr = new String[]{">", "", "", "", "", ""};
                                    //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                    AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                }
                            }
                        }
                    }

                    //AltimageView.setImageBitmap(drawbitmapforContacts(contacts, (nexWidth / 3.15), (nexHeight / 1.67)));
                }else if (screen == HomeScreen) {
                    AltimageView.setImageBitmap(drawbitmap("CONTACTS", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = ContactScreenMenu;
                    mLeftBtn.setVisibility(View.VISIBLE);
                    mRightBtn.setVisibility(View.VISIBLE);
                } else if (screen == ContactsList) {
                    for (int i = 0; i < Contacts1.length; i++) {
                        if (!Contacts1[i].name.equalsIgnoreCase("")) {
                            if (Contacts1[i].name.charAt(0) == '>') {
                                AltimageView.setImageBitmap(drawbitmapforContactDetail(Contacts1[i], (nexWidth / 2.5), (nexHeight / 1.67)));
                                screen = ContactDetailScreen;
                                mSelectBtn.setText("Back");
                            }
                        }
                    }
                } else if (screen == ContactDetailScreen) {
                    screen = ContactsList;
                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                    //Contactsptr = new String[]{">", "", "", "", "", ""};
                    AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    mSelectBtn.setText("Select");
                }else if (screen == NewContactScreenName){
                    screen = NewContactScreenNumber;
                    mSelectBtn.setText("Save");
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                }else if (screen == NewContactScreenNumber){
                    new ContactFetcher(this).insertContact(getContentResolver(),
                            convertToString(contactname), convertToString(dialer));
                    dialer.clear();
                    contactname.clear();
                    mSelectBtn.setText("Select");
                    screen = ContactsList;
                    LoadedContacts = false;
                    loadContactsMap();
                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                    Contactsptr = new String[]{">", "", "", "", "", ""};
                    //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    mSelectBtn.setText("Select");
                }else if (screen == DeleteContactListScreen){
                    for (int i = 0; i < Contacts1.length; i++) {
                        if (!Contacts1[i].name.equalsIgnoreCase("")) {
                            if (Contacts1[i].name.charAt(0) == '>') {
                                /* String Number = Contacts1[i].numbers.get(0).number.replace(" ", "");

                                new ContactFetcher(this).deleteContact(getContentResolver(),Number);
                                list.clear();
                                LoadedContacts = false;
                                contactsLoaded1 = false;
                                loadContactsMap();

                                Contactsptr = new String[]{">", "", "", "", "", ""};
                                //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                */
                                screen = DeleteContactConfirmScreen;
                                DeleteContact = Contacts1[i];
                                AltimageView.setImageBitmap(drawbitmapforContactDelete(Contacts1[i], (nexWidth / 3.15), (nexHeight / 1.67)));
                            }
                        }
                    }
                }else if(screen == DeleteContactConfirmScreen){
                    if(DeleteContact != null) {
                        mSelectBtn.setText("Delete");
                        screen = DeleteContactListScreen;
                        String Number = DeleteContact.numbers.get(0).number.replace(" ", "");

                        new ContactFetcher(this).deleteContact(getContentResolver(), Number);
                        list.clear();
                        LoadedContacts = false;
                        contactsLoaded1 = false;
                        loadContactsMap();

                        Contactsptr = new String[]{">", "", "", "", "", ""};
                        //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                        AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    }
                }
                break;
            case R.id.cancelbtn:
                if (screen == ContactsList || screen == DeleteContactListScreen) {
                    screen = ContactsOptions;
                    mSelectBtn.setText("Select");
                    contactsOptionsPtr = new String[]{">", "", ""};
                    AltimageView.setImageBitmap(drawbitmapforContactsOptions(getContactOptions(), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));

                } else if (screen == DeleteContactConfirmScreen) {
                    screen = DeleteContactListScreen;
                    /*list.clear();
                    LoadedContacts = false;
                    contactsLoaded1 = false;
                    loadContactsMap();

                    Contactsptr = new String[]{">", "", "", "", "", ""};*/
                    //AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));

                } else if(screen == ContactsOptions){
                    screen = ContactScreenMenu;
                    AltimageView.setImageBitmap(drawbitmap("CONTACTS", (nexWidth / 4.5), (nexHeight / 1.67)));
                    mLeftBtn.setVisibility(View.VISIBLE);
                    mRightBtn.setVisibility(View.VISIBLE);

                }else if(screen == ContactScreenMenu || screen == Messages){
                    screen = HomeScreen;
                    AltimageView.setImageBitmap(drawBitmapHomeScreen("Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
                }else if(screen == NewContactScreenName){
                    if(contactnameindex >= 0){
                        //contactname.get(contactnameindex).replace(contactname.get(contactnameindex),"");
                        contactname.set(contactnameindex,"");
                        contactnameindex = contactnameindex - 1;
                        AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight/1.67 )));
                    }else{
                        contactname.clear();
                        contactnameindex = 0;
                        samekey = 0;
                        screen = ContactsOptions;
                        mSelectBtn.setText("Select");
                        contactsOptionsPtr = new String[]{">", "", ""};
                        AltimageView.setImageBitmap(drawbitmapforContactsOptions(getContactOptions(), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    }
                }else if(screen == NewContactScreenNumber){
                    if(dialerindex > 0) {
                        dialer.set(dialerindex-1, "");
                        AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                        dialerindex--;
                    }else{
                        dialer.clear();
                        dialerindex = 0;
                        screen = NewContactScreenName;
                        mSelectBtn.setText("Next");
                        AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight/1.67 )));
                    }
                }
                break;
            case R.id.leftBtn:
                //dialer.clear();
                if (screen == Messages) {
                    AltimageView.setImageBitmap(drawbitmap("CONTACTS", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = ContactScreenMenu;
                } else if (screen == ContactsList ||screen == DeleteContactListScreen) {
                    Contactsptr = pointToPrevContact1(Contactsptr);
                    if (frmContact >= 0) {
                        AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    } else {
                        frmContact = 0;
                    }
                }else if (screen == ContactsOptions) {
                   /* Contactsptr = pointToPrevContact1(Contactsptr);
                    if (frmContact >= 0) {
                        AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    } else {
                        frmContact = 0;
                    }*/
                }
                break;
            case R.id.rightBtn:
                if (screen == NewContactScreenName) {
                    contactnameindex++;
                    contactname.add(contactnameindex, "");
                    count = 0;
                } else if (screen == HomeScreen) {
                    dialer.clear();

                }else if (screen == ContactScreenMenu) {
                    AltimageView.setImageBitmap(drawbitmap("MESSAGES", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = Messages;
                }else if (screen == ContactsOptions) {
                    contactsOptionsPtr= pointToNextOption(contactsOptionsPtr);
                    AltimageView.setImageBitmap(drawbitmapforContactsOptions(getContactOptions(), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));
                }else if (screen == ContactsList || screen == DeleteContactListScreen) {
                    /*Contactsptr = pointToNextContact(Contactsptr);
                    AltimageView.setImageBitmap(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));*/
                    Contactsptr = pointToNextContact1(Contactsptr);
                    AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                }
                break;
            case R.id.btncall:
                loadContactsMap();
                if (screen == ContactsList) {
                    for (int i = 0; i < Contacts1.length; i++) {
                        if (!Contacts1[i].name.equalsIgnoreCase("")) {
                            if (Contacts1[i].name.charAt(0) == '>') {
                                Contact contactcheck = Contacts1[i];
                                String contactcheck1 = contactcheck.name.replace(">", "");
                                AltimageView.setImageBitmap(drawbitmapforCalling(Contacts1[i], (nexWidth / 2.5), (nexHeight / 1.67)));
                                screen = CallingScreen;
                                mCall.setText("End Call");
                            }
                        }
                    }
                }
                /*{
                    for (int i = 0; i < Contacts.length; i++) {
                        if (!Contacts[i].equalsIgnoreCase("")) {
                            if (Contacts[i].charAt(0) == '>') {
                                for (Map.Entry<Integer, Contact> entry : map.entrySet()) {
                                    int key = entry.getKey();
                                    Contact contact = entry.getValue();
                                    String contactcheck = Contacts[i];
                                    String contactcheck1 = contactcheck.replace(">", "");
                                    if (contact.name.equalsIgnoreCase(contactcheck1)) {
                                        AltimageView.setImageBitmap(drawbitmapforCalling(contact, (nexWidth / 2.5), (nexHeight / 1.67)));
                                        screen = CallingScreen;
                                        mCall.setText("End Call");
                                    }
                                }

                            }
                        }
                    }
                }*/
                else if (screen == CallingScreen) {
                    screen = ContactsList;
                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                    //Contactsptr = new String[]{">", "", "", "", "", ""};
                    AltimageView.setImageBitmap(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    mCall.setText("Call");
                }
                break;
            case R.id.btn1:
                dialer.add(dialerindex,"1");
                dialerindex++;
                AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                //screen = 3;
                break;
            case R.id.btn2:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "a");
                            count = 1;
                            break;
                            //break;
                        case 1:
                            contactname.set(contactnameindex, "b");
                            count = 2;
                            break;
                            //return;
                        case 2:
                            contactname.set(contactnameindex, "c");
                            count = 3;
                            break;
                            //return;
                        case 3:
                            contactname.set(contactnameindex, "2");
                            count = 0;
                            break;
                            //return;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight/1.67 )));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"2");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }
                break;
            case R.id.btn3:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "c");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, "d");
                            count = 2;
                            break;
                        case 2:
                            contactname.set(contactnameindex, "f");
                            count = 3;
                            break;
                        case 3:
                            contactname.set(contactnameindex, "3");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"3");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }
                break;
            case R.id.btn4:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "g");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, "h");
                            count = 2;
                            break;
                        case 2:
                            contactname.set(contactnameindex, "i");
                            count = 3;
                            break;
                        case 3:
                            contactname.set(contactnameindex, "4");
                            count = 0;
                            break;

                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"4");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn5:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "j");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, "k");
                            count = 2;
                            break;
                        case 2:
                            contactname.set(contactnameindex, "l");
                            count = 3;
                            break;
                        case 3:
                            contactname.set(contactnameindex, "5");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"5");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn6:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "m");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, "n");
                            count = 2;
                            break;
                        case 2:
                            contactname.set(contactnameindex, "o");
                            count = 3;
                            break;
                        case 3:
                            contactname.set(contactnameindex, "6");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"6");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn7:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "p");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, "q");
                            count = 2;
                            break;
                        case 2:
                            contactname.set(contactnameindex, "r");
                            count = 3;
                            break;
                        case 3:
                            contactname.set(contactnameindex, "s");
                            count = 4;
                            break;
                        case 4:
                            contactname.set(contactnameindex, "7");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"7");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn8:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "t");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, "u");
                            count = 2;
                            break;
                        case 2:
                            contactname.set(contactnameindex, "v");
                            count = 3;
                            break;
                        case 3:
                            contactname.set(contactnameindex, "8");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"8");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn9:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "w");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, "x");
                            count = 2;
                            break;
                        case 2:
                            contactname.set(contactnameindex, "y");
                            count = 3;
                            break;
                        case 3:
                            contactname.set(contactnameindex, "z");
                            count = 4;
                            break;
                        case 4:
                            contactname.set(contactnameindex, "9");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"9");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn0:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "0");
                            count = 1;
                            break;
                        case 1:
                            contactname.set(contactnameindex, " ");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"0");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btnstar:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "*");
                            count = 0;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"*");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btnhash:
                if (screen == NewContactScreenName) {
                    switch (count) {
                        case 0:
                            contactname.set(contactnameindex, "#");
                            count = 0;
                            break;
                        case 1:
                            capsLetters = true;
                            break;
                    }
                    AltimageView.setImageBitmap(drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"#");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;


        }
    }

    public class NetorkSignalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();
            int SignalStrength = extras.getInt("mSignalStrength");

            if (SignalStrength < 28) {
                onSignalStrength = 4;
            } else if (SignalStrength > 28 && SignalStrength < 50) {
                onSignalStrength = 3;
            } else if (SignalStrength > 50 && SignalStrength < 28) {
                onSignalStrength = 2;
            }

            Log.i(LOG_TAG,
                    "onSignalStrengthsChanged: " + onSignalStrength);
            if (screen == HomeScreen)
                AltimageView.setImageBitmap(drawbitmapfornetworksignal(onSignalStrength, "Menu", (nexWidth / 2.52), (nexHeight / 1.02)));

        }
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            if (!list.isEmpty()) {
                Contact c = list.get(1);
                Log.d(LOG_TAG, c.name);
            }


            // TODO Auto-generated method stub
            int level = intent.getIntExtra("level", 0);
            Log.i(LOG_TAG, String.valueOf(level) + "%");
            int BatteryLevel = level;

            if (BatteryLevel > 25 && BatteryLevel < 50) {
                mBatteryLevel = 2;
            } else if (BatteryLevel > 50 && BatteryLevel < 75) {
                mBatteryLevel = 3;
            } else if (BatteryLevel > 75) {
                mBatteryLevel = 4;
            } else {
                mBatteryLevel = 1;
            }
            if (screen == HomeScreen)
                AltimageView.setImageBitmap(drawbitmapfornetworksignal(onSignalStrength, "Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
        }
    };
}

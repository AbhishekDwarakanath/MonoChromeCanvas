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
import com.innominds.kitemonochrome.bitmaps.ContactBitmap;
import com.innominds.kitemonochrome.network.CustomPhoneStateListener;
import com.innominds.kitemonochrome.onClickListner.ContactsonClickListner;

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
    public ImageView AltimageView;
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

    //private Contact[] Contacts1 ;
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
    //int frmContact = 0;

    List<String> contactname = new ArrayList<String>();
    int contactnameindex = 0;
    int count = 0;
    int samekey = 0;

    List<String> contactnumber = new ArrayList<String>();
    private String[] contactsOptionsPtr;
    //private String[] contactsOptions = new String[]{"Search","Add","Delete"};
    private Contact DeleteContact ;

    private int dialerindex = 0;
    private boolean capsLetters = false;
    ContactBitmap contactBitmap;
    private ContactsonClickListner contactsonClickListner;

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
        contactBitmap = new ContactBitmap(this);
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
        setOnClickListnerClass(this);
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
        canvas.drawText(text, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }
    }

    public Bitmap drawbitmapforDialing(List<String> numbers, double x, double y) {
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
    public String convertToString(List<String> letters) {
        StringBuilder sb = new StringBuilder();
        for(String str : letters){
            sb.append(str).append("");
        }
        String strfromArrayList = sb.toString();
        return  strfromArrayList;
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
                    setOnClickListnerClass(contactsonClickListner);
                    screen = ContactsOptions;
                    if(contactsOptionsPtr== null)
                    contactsOptionsPtr = new String[]{">","",""};
                    AltimageView.setImageBitmap(contactBitmap.drawbitmapforContactsOptions(contactBitmap.getContactOptions(contactBitmap.getContactsOptions()), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    contactsonClickListner = new ContactsonClickListner(MainActivity.this,contactBitmap,contactsOptionsPtr,screen);
                    setOnClickListnerClass(contactsonClickListner);
                }
                else if (screen == HomeScreen) {
                    AltimageView.setImageBitmap(drawbitmap("CONTACTS", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = ContactScreenMenu;
                    mLeftBtn.setVisibility(View.VISIBLE);
                    mRightBtn.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.cancelbtn:
                if(screen == ContactScreenMenu || screen == Messages){
                    screen = HomeScreen;
                    AltimageView.setImageBitmap(drawBitmapHomeScreen("Menu", (nexWidth / 2.52), (nexHeight / 1.02)));
                }
                break;
            case R.id.leftBtn:
                //dialer.clear();
                if (screen == Messages) {
                    AltimageView.setImageBitmap(drawbitmap("CONTACTS", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = ContactScreenMenu;
                }
                break;
            case R.id.rightBtn:
                if (screen == HomeScreen) {
                    dialer.clear();
                }else if (screen == ContactScreenMenu) {
                    AltimageView.setImageBitmap(drawbitmap("MESSAGES", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = Messages;
                }
                break;
            case R.id.btncall:
                /*contactBitmap.loadContactsMap();
                if (screen == ContactsList) {
                    for (int i = 0; i < contactBitmap.getContacts1().length; i++) {
                        if (!contactBitmap.getContacts1()[i].name.equalsIgnoreCase("")) {
                            if (contactBitmap.getContacts1()[i].name.charAt(0) == '>') {
                                Contact contactcheck = contactBitmap.getContacts1()[i];
                                String contactcheck1 = contactcheck.name.replace(">", "");
                                AltimageView.setImageBitmap(contactBitmap.drawbitmapforCalling(contactBitmap.getContacts1()[i], (nexWidth / 2.5), (nexHeight / 1.67)));
                                screen = CallingScreen;
                                mCall.setText("End Call");
                            }
                        }
                    }
                }
                else if (screen == CallingScreen) {
                    screen = ContactsList;
                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                    //Contactsptr = new String[]{">", "", "", "", "", ""};
                    AltimageView.setImageBitmap(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    mCall.setText("Call");
                }*/
                break;
            case R.id.btn1:
                dialer.add(dialerindex,"1");
                dialerindex++;
                AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                //screen = 3;
                break;
            case R.id.btn2:
               if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"2");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }
                break;
            case R.id.btn3:
                 if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"3");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }
                break;
            case R.id.btn4:
                if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"4");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn5:
                if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"5");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn6:
                if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"6");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn7:
               if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"7");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn8:
                if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"8");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn9:
               if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"9");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btn0:
                if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"0");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btnstar:
                if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"*");
                    dialerindex++;
                    AltimageView.setImageBitmap(drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }

                break;
            case R.id.btnhash:
                if (screen == HomeScreen || screen == NewContactScreenNumber) {
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
           /* if (!list.isEmpty()) {
                Contact c = list.get(1);
                Log.d(LOG_TAG, c.name);
            }*/


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

    public void setOnClickListnerClass(View.OnClickListener listner){
        mSelectBtn.setOnClickListener(listner);
        mRightBtn.setOnClickListener(listner);
        mLeftBtn.setOnClickListener(listner);
        mHomeBtn.setOnClickListener(listner);
        mBtn1.setOnClickListener(listner);
        mBtn2.setOnClickListener(listner);
        mBtn3.setOnClickListener(listner);
        mBtn4.setOnClickListener(listner);
        mBtn5.setOnClickListener(listner);
        mBtn6.setOnClickListener(listner);
        mBtn7.setOnClickListener(listner);
        mBtn8.setOnClickListener(listner);
        mBtn9.setOnClickListener(listner);
        mBtn0.setOnClickListener(listner);
        mBtnstar.setOnClickListener(listner);
        mBtnhash.setOnClickListener(listner);
        mCall.setOnClickListener(listner);
        cancelBtn.setOnClickListener(listner);
    }

    public void setScreen(int screen){
        this.screen = screen;
    }
    public void setImageView(Bitmap bmp){
        AltimageView.setImageBitmap(bmp);
    }
    public void setSelectButton(String text){
        mSelectBtn.setText(text);
    }
    public void setCallButton(String text){
        mCall.setText(text);
    }
    public void setButtonVisibility(String button,boolean b){
        if(button.equalsIgnoreCase("left")){
            if(b) {
                mLeftBtn.setVisibility(View.VISIBLE);
            }else{
                mLeftBtn.setVisibility(View.INVISIBLE);
            }
        }
        if(button.equalsIgnoreCase("right")){
            if(b) {
                mRightBtn.setVisibility(View.VISIBLE);
            }else{
                mRightBtn.setVisibility(View.INVISIBLE);
            }
        }
    }
}

package com.innominds.kitemonochrome.bitmaps;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;

import com.innominds.kitemonochrome.Contacts.Contact;
import com.innominds.kitemonochrome.Contacts.ContactFetcher;
import com.innominds.kitemonochrome.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by adwarakanath on 24/10/17.
 */
public class ContactBitmap {

    Context context;
    int width = 84;
    int height = 48;
    Boolean contactsLoaded1 = false;
    int frmContact = 0;
    private int sizeofContact = 0;

    private String[] contactsOptionsPtr;

    public String[] getContactsOptions() {
        return contactsOptions;
    }

    public void setContactsOptions(String[] contactsOptions) {
        this.contactsOptions = contactsOptions;
    }

    private String[] contactsOptions = new String[]{"Search","Add","Delete"};

    public void setContacts1(Contact[] contacts1) {
        Contacts1 = contacts1;
    }

    public Contact[] getContacts1() {
        return Contacts1;
    }

    private Contact[] Contacts1 ;

    public boolean isLoadedContacts() {
        return LoadedContacts;
    }

    public void setLoadedContacts(boolean loadedContacts) {
        LoadedContacts = loadedContacts;
    }

    boolean LoadedContacts = false;

    public List<Contact> getList() {
        return list;
    }

    public void clearList(){
        list.clear();
    }
    private final List<Contact> list = new LinkedList<Contact>();

    public Boolean getContactsLoaded1() {
        return contactsLoaded1;
    }

    public void setContactsLoaded1(Boolean contactsLoaded1) {
        this.contactsLoaded1 = contactsLoaded1;
    }



    public int getFromContact() {
        return frmContact;
    }

    public void setFromContact(int frmContact) {
        this.frmContact = frmContact;
    }


    public ContactBitmap(Context c) {
        context = c;
    }

    public void loadContactsMap() {
        //ArrayList<Contact> list = ContactHelper.getContactCursor(getContentResolver(), "");
        ArrayList<Contact> listContacts = new ContactFetcher(context).fetchAll();

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

    public String[] pointToPrevContact1(String[] pointerto) {

        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                pointerto[j] = "";
                if (j > 0) {
                    pointerto[j - 1] = ">" + pointerto[j];
                    return pointerto;
                } else {
                    frmContact = frmContact - 1;
                    pointerto[0] = ">" + pointerto[0];
                    contactsLoaded1 = false;
                }

            }

        }
        return pointerto;
    }

    public String[] pointToNextContact1(String[] pointerto) {

        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                pointerto[j] = "";
                if (j < pointerto.length - 1) {
                    pointerto[j + 1] = ">" + pointerto[j];
                    return pointerto;
                } else {

                    frmContact = frmContact + 1;
                    pointerto[pointerto.length - 1] = ">" + pointerto[pointerto.length - 1];
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
        return pointerto;
    }

    public Contact[] getContacts(String[] pointerto) {

        if (!getContactsLoaded1()) {

            if (!list.isEmpty()) {

                if(list.size() < 6){
                    sizeofContact = list.size();

                }else{
                    sizeofContact = 6;
                }
                Contacts1 = new Contact[sizeofContact];
                if (frmContact + 6 > list.size()) {
                    frmContact = 0;
                    pointerto[0] = ">" + pointerto[0];
                    pointerto[5] = pointerto[5].replace(">", "");
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
                //contactBitmap.setContactsLoaded1(true);
                contactsLoaded1 = true;
                Contact c = list.get(1);
                //Log.d(LOG_TAG, c.name);
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

    public Bitmap drawbitmapforNewContact(List<String> name, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, height, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(height / (float) 6);
        canvas.drawText("Name", (int) x/5, (int) y/ 2, pText);
        if(name!=null){
            for (int i = 0; i < name.size(); i++) {
                canvas.drawText(name.get(i), ((int) x * i + 1) / 3, (int) y, pText);
            }
        }

            return getScaledBitmap(bitmap);
    }

    public Bitmap drawbitmapforContactDetail(Contact contact, double x, double y) {
        if (contact.name.charAt(0) == '>') {
            contact.name = contact.name.replace(">", "");
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, height, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(height / (float) 6);
        canvas.drawText(contact.numbers.get(0).number, (int) (width / 7), (int) (height / 1.2), pText);
        canvas.drawText(contact.name, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        /*if (scale) {

        } else {
            return bitmap;
        }*/

        return getScaledBitmap(bitmap);
    }

    public Bitmap drawbitmapforContactDelete(Contact contact, double x, double y) {
        if (contact.name.charAt(0) == '>') {
            contact.name = contact.name.replace(">", "");
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, height, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(height / (float) 6);
        canvas.drawText("Delete ?", (int) x/5, (int) y/ 2, pText);
        canvas.drawText(contact.numbers.get(0).number, (int) (width / 7), (int) (height / 1.2), pText);
        canvas.drawText(contact.name, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        /*if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }*/
        return getScaledBitmap(bitmap);
    }



   public Bitmap drawbitmapforContactsscrollabl1e1(Contact[] contacts, String[] pointerto, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, height, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        //pText.setTextSize(nexHeight / (float) 6.6);
        pText.setTextSize(height / (float) 6);
        //canvas.drawText(text, (int) x, (int) y, pText);
        //drawString(canvas,pText,text,5,10);
        drawMultilineText1(drawpointertoContacts1(contacts, pointerto), 5, 8, pText, canvas);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        /*if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }*/
        //return getScaledBitmap(bitmap);

       return getScaledBitmap(bitmap);
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
                        mTxtPaint.setTextSize(height / (float) 5.5);
                        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
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
    public Bitmap drawbitmapforDialing(List<String> dialer, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, height, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(height / (float) 6);
       // if(screen == NewContactScreenNumber){
            canvas.drawText("Number", (int) x/5, (int) y/ 2, pText);
       // }
        for (int i = 0; i < dialer.size(); i++) {
            canvas.drawText(dialer.get(i), ((int) x * i + 1) / 3, (int) y, pText);
        }
            return getScaledBitmap(bitmap);
    }

    public Bitmap drawbitmapforCalling(Contact contact, double x, double y) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, height, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        pText.setTextSize(height / (float) 6);
        canvas.drawText("Calling ....", (int) (width / 7), (int) (height / 1.2), pText);
        canvas.drawText(contact.name, (int) x, (int) y, pText);
        canvas.drawBitmap(bitmap, 0, 0, pText);
       /* if (scale) {
            return getScaledBitmap(bitmap);
        } else {
            return bitmap;
        }*/

        return getScaledBitmap(bitmap);
    }

     public Bitmap drawbitmapforContactsOptions(String [] contactsOptionsPtr ,String [] pointerto ,double x,double y){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, height, pBackground);
        Paint pText = new Paint();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
        pText.setTypeface(tf);
        pText.setColor(Color.GREEN);
        //pText.setTextSize(nexHeight / (float) 6.6);
        pText.setTextSize(height / (float) 6.6);
        drawMultilineTextContactOptions(drawpointertoContactsoptions(contactsOptionsPtr, pointerto), 5, 8, pText, canvas);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        /*if (scale) {

        } else {
            return bitmap;
        }*/
         return getScaledBitmap(bitmap);
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
                        mTxtPaint.setTextSize(height / (float) 5.5);
                        Typeface tf = Typeface.createFromAsset(context.getAssets(), "5x8_lcd_hd44780u_a02.ttf");
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



    private Contact[] drawpointertoContacts1(Contact[] contacts, String[] pointerto) {
        for (int j = 0; j < contacts.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                contacts[j].name = ">" + contacts[j].name;
                return contacts;
            }

        }
        return contacts;
    }


    public String[] pointToNextOption(String[] pointerto){
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

    public String[] getContactOptions(String[] contactsOptions) {
        for (int k = 0; k < contactsOptions.length; k++) {
            if (contactsOptions[k].charAt(0) == '>') {
                contactsOptions[k] = contactsOptions[k].replace(">", "");
            }
        }

        return contactsOptions;
    }


    private Bitmap getScaledBitmap(Bitmap bitmapOrg) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
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


}

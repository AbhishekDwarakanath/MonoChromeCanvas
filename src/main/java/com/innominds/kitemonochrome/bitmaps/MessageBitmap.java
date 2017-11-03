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

import com.innominds.kitemonochrome.Contacts.Contact;
import com.innominds.kitemonochrome.MainActivity;

/**
 * Created by adwarakanath on 27/10/17.
 */
public class MessageBitmap {
    Context context;
    int width = 84;
    int height = 48;
    private String[] MessageOptions = new String[]{"New Message","Inbox","Outbox"};


    public String[] getMessageOptions() {
        return MessageOptions;
    }

    public void setMessageOptions(String[] messageOptions) {
        MessageOptions = messageOptions;
    }



    public MessageBitmap(Context context) {
        this.context = context;

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

    public String[] getMessageOptions(String[] messageOptions) {
        for (int k = 0; k < messageOptions.length; k++) {
            if (messageOptions[k].charAt(0) == '>') {
                messageOptions[k] = messageOptions[k].replace(">", "");
            }
        }

        return messageOptions;
    }

    public Bitmap drawbitmapforMessagesOptions(String [] contactsOptionsPtr , String [] pointerto , double x, double y){
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
        drawMultilineTextMessageOptions(drawpointertoMessageoptions(contactsOptionsPtr, pointerto), 5, 8, pText, canvas);
        canvas.drawBitmap(bitmap, 0, 0, pText);
        /*if (scale) {

        } else {
            return bitmap;
        }*/
        return getScaledBitmap(bitmap);
    }

    private String[] drawpointertoMessageoptions(String[] contacts, String[] pointerto) {
        for (int j = 0; j < pointerto.length; ++j) {
            if (!pointerto[j].equalsIgnoreCase("")) {
                contacts[j] = ">" + contacts[j];
                return contacts;
            }

        }
        return contacts;
    }

    public void drawMultilineTextMessageOptions(String[] str, int x, int y, Paint paint, Canvas canvas) {
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

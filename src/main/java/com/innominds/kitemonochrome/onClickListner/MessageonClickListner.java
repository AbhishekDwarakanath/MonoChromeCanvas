package com.innominds.kitemonochrome.onClickListner;

import android.content.Context;
import android.view.View;

import com.innominds.kitemonochrome.MainActivity;
import com.innominds.kitemonochrome.R;
import com.innominds.kitemonochrome.Utils.Utils;
import com.innominds.kitemonochrome.bitmaps.MessageBitmap;

/**
 * Created by adwarakanath on 2/11/17.
 */
public class MessageonClickListner implements View.OnClickListener {
    private int screen = 0;
    private MessageBitmap messageBitmap;
    private String[] messageOptionsPtr;
    private Context context;
    private MainActivity act;
    int nexWidth = 84;
    int nexHeight = 48;

    public MessageonClickListner(MainActivity act, MessageBitmap messageBitmap, String[] messageOptionsPtr, int screen) {
        this.screen = screen;
        this.messageOptionsPtr = messageOptionsPtr;
        this.messageBitmap = messageBitmap;
        this.context = act;
        this.act = act;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeBtn:
                break;
            case R.id.selectBtn:
                if(screen == Utils.MessageOptionScreen){
                    for (int i = 0; i < messageBitmap.getMessageOptions().length; i++) {

                    }
                }

                break;
            case R.id.cancelbtn:
                if(screen == Utils.MessageOptionScreen){
                act.setOnClickListnerClass(act);
                screen = Utils.MessageSceenMenu;
                act.setScreen(screen);
                act.setImageView(act.drawbitmap("MESSAGES", (nexWidth / 4.5), (nexHeight / 1.67)));
                act.setButtonVisibility("left",true);
                act.setButtonVisibility("right",true);
            }
                break;
            case R.id.rightBtn:
                if (screen == Utils.MessageOptionScreen) {
                messageOptionsPtr= messageBitmap.pointToNextOption(messageOptionsPtr);
                act.setImageView(messageBitmap.drawbitmapforMessagesOptions(messageBitmap.getMessageOptions(messageBitmap.getMessageOptions()), messageOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));
            }
                break;
            case R.id.leftBtn:
                break;
            case R.id.btn1 :
                break;
            case R.id.btn2 :
                break;
            case R.id.btn3 :
                break;
            case R.id.btn4 :
                break;
            case R.id.btn5 :
                break;
            case R.id.btn6 :
                break;
            case R.id.btn7 :
                break;
            case R.id.btn8 :
                break;
            case R.id.btn9 :
                break;
            case R.id.btn0 :
                break;
            case R.id.btnhash :
                break;
            case R.id.btnstar :
                break;
        }
    }
}

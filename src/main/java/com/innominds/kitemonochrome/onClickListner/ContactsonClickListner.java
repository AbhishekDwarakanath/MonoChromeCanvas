package com.innominds.kitemonochrome.onClickListner;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.innominds.kitemonochrome.Contacts.Contact;
import com.innominds.kitemonochrome.Contacts.ContactFetcher;
import com.innominds.kitemonochrome.MainActivity;
import com.innominds.kitemonochrome.R;
import com.innominds.kitemonochrome.bitmaps.ContactBitmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by adwarakanath on 27/10/17.
 */
public class ContactsonClickListner implements View.OnClickListener {

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
    int screen = 0;
    Context context;
    public static int DeleteContactConfirmScreen = 11;
    ContactBitmap contactBitmap;
    String[] Contactsptr;
    String[] Contacts = new String[6];
    //private Contact[] Contacts1 ;
    boolean LoadedContacts = false;
    int fromContact = 0;
    Map<Integer, Contact> map = new LinkedHashMap<Integer, Contact>();
    Boolean traverseMap = false;
    Boolean contactsLoaded = false;
    Boolean contactsLoaded1 = false;
    private final List<Contact> list = new LinkedList<Contact>();
    //int frmContact = 0;

    List<String> dialer = new ArrayList<String>();
    List<String> contactname = new ArrayList<String>();
    int contactnameindex = 0;
    int count = 0;
    int samekey = 0;

    List<String> contactnumber = new ArrayList<String>();
    private String[] contactsOptionsPtr;
    //private String[] contactsOptions = new String[]{"Search","Add","Delete"};
    private Contact DeleteContact ;

    private int dialerindex = 0;


    int nexWidth = 84;
    int nexHeight = 48;
    MainActivity act;

    public ContactsonClickListner(MainActivity act,ContactBitmap contactBitmap,String [] contactsOptionsptrs,int screen) {
        this.context = act;
        this.act = act;
        this.contactBitmap = contactBitmap;
        this.contactsOptionsPtr = contactsOptionsptrs;
        this.screen = screen;

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

                break;
            case R.id.selectBtn:
                if (screen == ContactsOptions) {
                    for (int i = 0; i < contactBitmap.getContactsOptions().length; i++) {
                        if (!contactBitmap.getContactsOptions()[i].equalsIgnoreCase("")) {
                            if (contactBitmap.getContactsOptions()[i].charAt(0) == '>') {
                                if(contactBitmap.getContactsOptions()[i].equalsIgnoreCase(">Search")){
                                    screen = ContactsList;
                                    contactBitmap.loadContactsMap();
                                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                                    Contactsptr = new String[]{">", "", "", "", "", ""};
                                    //act.setImageView(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                    act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                }else if(contactBitmap.getContactsOptions()[i].equalsIgnoreCase(">Add")){
                                    contactname.clear();
                                    samekey =0;
                                    contactnameindex =0;
                                    screen = NewContactScreenName;
                                    act.setSelectButton("Next");
                                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                                }else if(contactBitmap.getContactsOptions()[i].equalsIgnoreCase(">Delete")){
                                    act.setSelectButton("Delete");
                                    screen = DeleteContactListScreen;
                                    contactBitmap.loadContactsMap();
                                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                                    Contactsptr = new String[]{">", "", "", "", "", ""};
                                    //act.setImageView(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                    act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                }
                            }
                        }
                    }
                    //act.setImageView(drawbitmapforContacts(contacts, (nexWidth / 3.15), (nexHeight / 1.67)));
                } else if (screen == ContactsList) {
                    for (int i = 0; i < contactBitmap.getContacts1().length; i++) {
                        String name = contactBitmap.getContacts1()[i].name;
                        if (!contactBitmap.getContacts1()[i].name.equalsIgnoreCase("")) {
                            if (contactBitmap.getContacts1()[i].name.charAt(0) == '>') {
                                act.setImageView(contactBitmap.drawbitmapforContactDetail(contactBitmap.getContacts1()[i], (nexWidth / 2.5), (nexHeight / 1.67)));
                                screen = ContactDetailScreen;
                                act.setSelectButton("Back");
                            }
                        }
                    }
                } else if (screen == ContactDetailScreen) {
                    screen = ContactsList;
                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                    //Contactsptr = new String[]{">", "", "", "", "", ""};
                    act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    act.setSelectButton("Select");
                }else if (screen == NewContactScreenName){
                    screen = NewContactScreenNumber;
                    act.setSelectButton("Save");
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                }else if (screen == NewContactScreenNumber){
                    new ContactFetcher(act).insertContact(act.getContentResolver(),
                            act.convertToString(contactname), act.convertToString(dialer));
                    dialer.clear();
                    contactname.clear();
                    contactnameindex =0;
                    act.setSelectButton("Select");
                    samekey = 0;
                    screen = ContactsList;
                    contactBitmap.setLoadedContacts(false);
                    //LoadedContacts = false;
                    contactBitmap.loadContactsMap();
                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                    Contactsptr = new String[]{">", "", "", "", "", ""};
                    //act.setImageView(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    act.setSelectButton("Select");
                }else if (screen == DeleteContactListScreen){
                    for (int i = 0; i < contactBitmap.getContacts1().length; i++) {
                        if (!contactBitmap.getContacts1()[i].name.equalsIgnoreCase("")) {
                            if (contactBitmap.getContacts1()[i].name.charAt(0) == '>') {
                                /* String Number = Contacts1[i].numbers.get(0).number.replace(" ", "");

                                new ContactFetcher(this).deleteContact(getContentResolver(),Number);
                                list.clear();
                                LoadedContacts = false;
                                contactsLoaded1 = false;
                                loadContactsMap();

                                Contactsptr = new String[]{">", "", "", "", "", ""};
                                //act.setImageView(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                act.setImageView(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                                */
                                screen = DeleteContactConfirmScreen;
                                DeleteContact = contactBitmap.getContacts1()[i];
                                act.setImageView(contactBitmap.drawbitmapforContactDelete(contactBitmap.getContacts1()[i], (nexWidth / 3.15), (nexHeight / 1.67)));
                            }
                        }
                    }
                }else if(screen == DeleteContactConfirmScreen){
                    if(DeleteContact != null) {
                        act.setSelectButton("Delete");
                        screen = DeleteContactListScreen;
                        String Number = DeleteContact.numbers.get(0).number.replace(" ", "");

                        new ContactFetcher(act).deleteContact(act.getContentResolver(), Number);
                        //list.clear();
                        contactBitmap.clearList();
                        //contactBitmap.setLoadedContacts(false);
                        //LoadedContacts = false;
                        //contactsLoaded1 = false;
                        contactBitmap.setLoadedContacts(false);
                        contactBitmap.setContactsLoaded1(false);
                        contactBitmap.loadContactsMap();

                        Contactsptr = new String[]{">", "", "", "", "", ""};
                        //act.setImageView(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                        act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    }
                }
                break;
            case R.id.cancelbtn:
                if (screen == ContactsList || screen == DeleteContactListScreen) {
                    screen = ContactsOptions;
                    act.setSelectButton("Select");
                    contactsOptionsPtr = new String[]{">", "", ""};
                    act.setImageView(contactBitmap.drawbitmapforContactsOptions(contactBitmap.getContactOptions(contactBitmap.getContactsOptions()), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));

                } else if (screen == DeleteContactConfirmScreen) {
                    screen = DeleteContactListScreen;
                    /*list.clear();
                    LoadedContacts = false;
                    contactsLoaded1 = false;
                    loadContactsMap();

                    Contactsptr = new String[]{">", "", "", "", "", ""};*/
                    //act.setImageView(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));

                } else if(screen == ContactsOptions){
                    act.setOnClickListnerClass(act);
                    screen = ContactScreenMenu;
                    act.setScreen(screen);
                    act.setImageView(act.drawbitmap("CONTACTS", (nexWidth / 4.5), (nexHeight / 1.67)));
                    act.setButtonVisibility("left",true);
                    act.setButtonVisibility("right",true);
                    /*mLeftBtn.setVisibility(View.VISIBLE);
                    mRightBtn.setVisibility(View.VISIBLE);
*/
                }else if(screen == NewContactScreenName){
                    if(contactnameindex > 0){
                        //contactname.get(contactnameindex).replace(contactname.get(contactnameindex),"");
                        contactname.set(contactnameindex,"");
                        contactnameindex = contactnameindex - 1;
                        act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight/1.67 )));
                    }else{
                        contactname.clear();
                        contactnameindex = 0;
                        samekey = 0;
                        screen = ContactsOptions;
                        act.setSelectButton("Select");
                        contactsOptionsPtr = new String[]{">", "", ""};
                        act.setImageView(contactBitmap.drawbitmapforContactsOptions(contactBitmap.getContactOptions(contactBitmap.getContactsOptions()), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    }
                }else if(screen == NewContactScreenNumber){
                    if(dialerindex > 0) {
                        dialer.set(dialerindex-1, "");
                        act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                        dialerindex--;
                    }else{
                        dialer.clear();
                        dialerindex = 0;
                        //samekey = 0;
                        screen = NewContactScreenName;
                        act.setSelectButton("Next");
                        act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight/1.67 )));
                    }
                }
                break;
            case R.id.leftBtn:
                //dialer.clear();
                if (screen == Messages) {
                    act.setImageView(act.drawbitmap("CONTACTS", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = ContactScreenMenu;
                } else if (screen == ContactsList ||screen == DeleteContactListScreen) {
                    Contactsptr = contactBitmap.pointToPrevContact1(Contactsptr);
                    if (contactBitmap.getFromContact() >= 0) {
                        act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    } else {
                        contactBitmap.setFromContact(0);
                    }
                }else if (screen == ContactsOptions) {
                   /* Contactsptr = pointToPrevContact1(Contactsptr);
                    if (frmContact >= 0) {
                        act.setImageView(drawbitmapforContactsscrollabl1e1(getContacts1(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
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
                    act.setImageView(act.drawbitmap("MESSAGES", (nexWidth / 4.5), (nexHeight / 1.67)));
                    screen = Messages;
                }else if (screen == ContactsOptions) {
                    contactsOptionsPtr= contactBitmap.pointToNextOption(contactsOptionsPtr);
                    act.setImageView(contactBitmap.drawbitmapforContactsOptions(contactBitmap.getContactOptions(contactBitmap.getContactsOptions()), contactsOptionsPtr, (nexWidth / 3.15), (nexHeight / 1.67)));
                }else if (screen == ContactsList || screen == DeleteContactListScreen) {
                    /*Contactsptr = pointToNextContact(Contactsptr);
                    act.setImageView(drawbitmapforContactsscrollable(getContacts(), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));*/
                    Contactsptr = contactBitmap.pointToNextContact1(Contactsptr);
                    act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                }
                break;
            case R.id.btncall:
                contactBitmap.loadContactsMap();
                if (screen == ContactsList) {
                    for (int i = 0; i < contactBitmap.getContacts1().length; i++) {
                        if (!contactBitmap.getContacts1()[i].name.equalsIgnoreCase("")) {
                            if (contactBitmap.getContacts1()[i].name.charAt(0) == '>') {
                                Contact contactcheck = contactBitmap.getContacts1()[i];
                                String contactcheck1 = contactcheck.name.replace(">", "");
                                act.setImageView(contactBitmap.drawbitmapforCalling(contactBitmap.getContacts1()[i], (nexWidth / 2.5), (nexHeight / 1.67)));
                                screen = CallingScreen;
                               act.setCallButton("End Call");
                            }
                        }
                    }
                }
                else if (screen == CallingScreen) {
                    screen = ContactsList;
                    //loadContactsMap(new String[]{"Abhi", "Innominds", "Innominds Office", "Ambulance", "Care", "Police", "Hospital", "Hostel", "Hotel", "Shop", "House", "Office"});
                    //Contactsptr = new String[]{">", "", "", "", "", ""};
                    act.setImageView(contactBitmap.drawbitmapforContactsscrollabl1e1(contactBitmap.getContacts(Contactsptr), Contactsptr, (nexWidth / 3.15), (nexHeight / 1.67)));
                    act.setCallButton("Call");
                }
                break;
            case R.id.btn1:
                dialer.add(dialerindex,"1");
                dialerindex++;
                act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight/1.67 )));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"2");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"3");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"4");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"5");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"6");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"7");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"8");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"9");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"0");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight / 1.67)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"*");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
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

                            break;
                    }
                    act.setImageView(contactBitmap.drawbitmapforNewContact(contactname, (nexWidth / 4.5), (nexHeight)));
                } else if (screen == HomeScreen || screen == NewContactScreenNumber) {
                    dialer.add(dialerindex,"#");
                    dialerindex++;
                    act.setImageView(contactBitmap.drawbitmapforDialing(dialer, (nexWidth / 4.5), (nexHeight / 1.67)));
                    //screen = 3;
                }
                break;
            
        }

    }
}

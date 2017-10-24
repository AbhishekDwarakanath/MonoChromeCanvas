package com.innominds.kitemonochrome.Contacts;

/**
 * Created by adwarakanath on 11/8/17.
 */
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.content.CursorLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// new ContactFetcher(this).fetchAll();
public class ContactFetcher {

    private final Context context;

    public ContactFetcher(Context c) {
        this.context = c;
    }

    public ArrayList<Contact> fetchAll() {
        String[] projectionFields = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };
        ArrayList<Contact> listContacts = new ArrayList<>();
        ArrayList<Contact> listContacts1 = new ArrayList<>();
        CursorLoader cursorLoader = new CursorLoader(context,
                ContactsContract.Contacts.CONTENT_URI,
                projectionFields, // the columns to retrieve
                null, // the selection criteria (none)
                null, // the selection args (none)
                null // the sort order (default)
        );

        Cursor c = cursorLoader.loadInBackground();

        final Map<String, Contact> contactsMap = new HashMap<>(c.getCount());

        if (c.moveToFirst()) {

            int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                String contactId = c.getString(idIndex);
                String contactDisplayName = c.getString(nameIndex);
                Contact contact = new Contact(contactId, contactDisplayName);
                contactsMap.put(contactId, contact);
                listContacts.add(contact);
            } while (c.moveToNext());
        }

        c.close();


        matchContactNumbers(contactsMap);

        return listContacts;
    }

    public boolean insertContact(ContentResolver contactAdder,
                                        String firstName, String mobileNumber) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                        firstName).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER,
                        mobileNumber)
                .withValue(Phone.TYPE,
                        Phone.TYPE_MOBILE).build());

        try {
            contactAdder.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void deleteContact(ContentResolver contactHelper,
                                     String number) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String[] args = new String[]{String.valueOf(getContactID(
                contactHelper, number))};

        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=?", args).build());
        try {
            contactHelper.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private static long getContactID(ContentResolver contactHelper,
                                     String number) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));

        String[] projection = {ContactsContract.PhoneLookup._ID};
        Cursor cursor = null;

        try {
            cursor = contactHelper.query(contactUri, projection, null, null,
                    null);

            if (cursor.moveToFirst()) {
                int personID = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID);
                return cursor.getLong(personID);
            }

            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return -1;
    }
    public void matchContactNumbers(Map<String, Contact> contactsMap) {
        // Get numbers
        final String[] numberProjection = new String[]{
                Phone.NUMBER,
                Phone.TYPE,
                Phone.CONTACT_ID,
        };

        Cursor phone = new CursorLoader(context,
                Phone.CONTENT_URI,
                numberProjection,
                null,
                null,
                null).loadInBackground();

        if (phone.moveToFirst()) {
            final int contactNumberColumnIndex = phone.getColumnIndex(Phone.NUMBER);
            final int contactTypeColumnIndex = phone.getColumnIndex(Phone.TYPE);
            final int contactIdColumnIndex = phone.getColumnIndex(Phone.CONTACT_ID);

            while (!phone.isAfterLast()) {
                final String number = phone.getString(contactNumberColumnIndex);
                final String contactId = phone.getString(contactIdColumnIndex);
                Contact contact = contactsMap.get(contactId);
                if (contact == null) {
                    continue;
                }
                final int type = phone.getInt(contactTypeColumnIndex);
                String customLabel = "Custom";
                CharSequence phoneType = Phone.getTypeLabel(context.getResources(), type, customLabel);
                contact.addNumber(number, phoneType.toString());
                phone.moveToNext();
            }
        }

        phone.close();
    }
/*
    public void matchContactEmails(Map<String, Contact> contactsMap) {
        // Get email
        final String[] emailProjection = new String[]{
                Email.DATA,
                Email.TYPE,
                Email.CONTACT_ID,
        };

        Cursor email = new CursorLoader(context,
                Email.CONTENT_URI,
                emailProjection,
                null,
                null,
                null).loadInBackground();

        if (email.moveToFirst()) {
            final int contactEmailColumnIndex = email.getColumnIndex(Email.DATA);
            final int contactTypeColumnIndex = email.getColumnIndex(Email.TYPE);
            final int contactIdColumnsIndex = email.getColumnIndex(Email.CONTACT_ID);

            while (!email.isAfterLast()) {
                final String address = email.getString(contactEmailColumnIndex);
                final String contactId = email.getString(contactIdColumnsIndex);
                final int type = email.getInt(contactTypeColumnIndex);
                String customLabel = "Custom";
                Contact contact = contactsMap.get(contactId);
                if (contact == null) {
                    continue;
                }
                CharSequence emailType = Email.getTypeLabel(context.getResources(), type, customLabel);
                contact.addEmail(address, emailType.toString());
                email.moveToNext();
            }
        }

        email.close();
    }*/
}
package my.contact.list;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ContactReader {
    private static final String CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private static final String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    private static final String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHOTO_ID = ContactsContract.CommonDataKinds.Phone.PHOTO_ID;

    public static ArrayList<Contact> getAllContacts(Context context) {
        ContentResolver cr = context.getContentResolver();

        Cursor cursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{CONTACT_ID, NAME, PHOTO_ID, NUMBER},
                null,
                null,
                NAME + " ASC"
        );

        HashMap<Long,Contact> contacts = new HashMap<>();


        if(cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(CONTACT_ID));
                String number = cursor.getString(cursor.getColumnIndex(NUMBER));

                if (contacts.containsKey(id)){
                    contacts.get(id).setNumber(contacts.get(id).getNumber() + ", " + number);
                }
                else {

                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    long photo = cursor.getLong(cursor.getColumnIndex(PHOTO_ID));
                    Contact contact = new Contact(id, name, photo, number);
                    contacts.put(id, contact);
                }
            }
            while (cursor.moveToNext());

            if (cursor!=null){
                cursor.close();
            }


            ArrayList<Contact> contactList = new ArrayList<>(contacts.values());

            Collections.sort(contactList, new Comparator<Contact>() {
                public int compare(Contact o1, Contact o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });


            return contactList;

        }

        if (cursor!=null){
            cursor.close();
        }

        return null;

    }

}

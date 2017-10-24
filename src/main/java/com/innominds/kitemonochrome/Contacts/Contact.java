package com.innominds.kitemonochrome.Contacts;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by adwarakanath on 11/8/17.
 */
public class Contact {
    public String id;
    public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ContactPhone> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<ContactPhone> numbers) {
        this.numbers = numbers;
    }

    public ArrayList<ContactPhone> numbers;


    public Contact(String id, String name) {
        this.id = id;
        this.name = name;
        this.numbers = new ArrayList<ContactPhone>();
    }

    @Override
    public String toString() {
        String result = name;
        if (numbers.size() > 0) {
            ContactPhone number = numbers.get(0);
            result += " (" + number.number + " - " + number.type + ")";
        }
        return result;
    }


    public void addNumber(String number, String type) {
        numbers.add(new ContactPhone(number, type));
    }

    public static Comparator<Contact> ContactNameComparator = new Comparator<Contact>() {

        @Override
        public int compare(Contact c1, Contact c2) {
            String ContactName1 = c1.getName().toUpperCase();
            String ContactName2 = c2.getName().toUpperCase();

            //ascending order
            return ContactName1.compareTo(ContactName2);
        }

        };
}
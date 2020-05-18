//package edu.uw.tcss450.team5tcss450client.ui.contacts;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//
//public class ContactsGenerator {
//
//    private static final Contacts[] contacts;
//    public static final int COUNT = 20;
//
//    private static final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
//
//    static {
//        contacts = new Contacts[COUNT];
//        for (int i = 0; i < contacts.length; i++) {
//            contacts[i] = new Contacts
//                    .Builder(randomNameGenerator(), randomNameGenerator(), randomNameGenerator(), generateAlphabet().get(i))
//                    .build();
//        }
//    }
//
//    public static String randomNameGenerator() {
//        final Set<String> identifiers = new HashSet<String>();
//        Random rand = new Random();
//        StringBuilder builder = new StringBuilder();
//        while (builder.toString().length() == 0) {
//            int length = rand.nextInt(5) + 5;
//            for (int i = 0; i < length; i++) {
//                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
//            }
//            if (identifiers.contains(builder.toString())) {
//                builder = new StringBuilder();
//            }
//        }
//        return builder.toString();
//    }
//
//    public static List<String> generateAlphabet() {
//
//        List<String> alphabet = new ArrayList<>();
//
//        for (int i = 0; i < 26; i++) {
//
//            alphabet.add(Character.toString((char) (65 + i)));
//        }
//
//        return alphabet;
//    }
//
//    public static List<Contacts> getContactList() {
//        return Arrays.asList(contacts);
//    }
//
//    public static Contacts[] getContacts() {
//        return Arrays.copyOf(contacts, contacts.length);
//    }
//
//    private ContactsGenerator() {
//    }
//
//}

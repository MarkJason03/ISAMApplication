package com.example.fyp_application.Utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordHashHandler {

    private PasswordHashHandler(){}
    public static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        try {
            // Use argon2id for a balance between Argon2i and Argon2d
            return argon2.hash(15, 65536, 1, password.toCharArray());
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }


    public static boolean verifyPassword(String hash, String password) {
        Argon2 argon2 = Argon2Factory.create();
        try {
            return argon2.verify(hash, password.toCharArray());
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }

}

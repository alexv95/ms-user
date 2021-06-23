package com.bci.msuser.util;

import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class RandomUUIDGeneration {
    public String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


}

package com.prodyna.pac.ars.backend.user.service;

import java.security.MessageDigest;

import com.prodyna.pac.ars.backend.common.client.exception.ArsRuntimeException;
import com.sun.mail.util.BASE64EncoderStream;

public class PasswordEncrypter {

	private static final String HASHING_ALGORITHM = "SHA";

	public String encryptPassword(String password) {
		try {
			MessageDigest digester = MessageDigest.getInstance(HASHING_ALGORITHM);
			byte[] passwordBytes = password.getBytes("UTF-8");
			byte[] digestedPasswordBytes = digester.digest(passwordBytes);
			byte[] encodedPassword = BASE64EncoderStream.encode(digestedPasswordBytes);
			return new String(encodedPassword);
		} catch (Exception e) {
			throw new ArsRuntimeException("Password Encryption failed", e);
		}
	}

}

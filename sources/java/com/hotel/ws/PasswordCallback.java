package com.hotel.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class PasswordCallback implements CallbackHandler {
	
	private Map<String, String> passwords = 
		new HashMap<String, String>();
	
	public PasswordCallback() {
		// passwords per accedere alle chiavi private
		passwords.put("hotel", "hotelpassw");
	}

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i=0; i<callbacks.length; i++) {
			WSPasswordCallback wsCallback = (WSPasswordCallback) callbacks[i];
			String user = wsCallback.getIdentifier();
			if (null == user)
				throw new UnsupportedCallbackException(callbacks[i], 
						"Nessun utente specificato");
			String password = passwords.get(user);
			if (null != password) {
				wsCallback.setPassword(password);
			}
		}
	}
}

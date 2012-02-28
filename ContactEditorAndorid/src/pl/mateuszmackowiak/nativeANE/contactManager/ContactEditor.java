package pl.mateuszmackowiak.nativeANE.contactManager;


import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
*
* @author Mateusz Maçkowiak
*/
public class ContactEditor implements FREExtension {

	public static final String ERROR_EVENT = "nativeError";
	
	@Override
	public FREContext createContext(String arg0) {
		return new ContactEditorContext();
	}

	@Override
	public void dispose() {
		Log.i("NativeAlert", "dispose");
	}

	@Override
	public void initialize() {
		Log.i("NativeAlert", "initialize");
	}
}

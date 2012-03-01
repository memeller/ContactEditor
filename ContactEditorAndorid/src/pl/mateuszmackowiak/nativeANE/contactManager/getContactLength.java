package pl.mateuszmackowiak.nativeANE.contactManager;

import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class getContactLength implements FREFunction {

	public static final String KEY = "getContactCount";
	
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		try{
			Cursor contactCursor =  context.getActivity().managedQuery(Phone.CONTENT_URI, new String[] { Phone.CONTACT_ID, Phone.DISPLAY_NAME },null, null, null);
			
			int count = contactCursor.getCount();
			FREObject frecount = FREObject.newObject(count);
			return frecount;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

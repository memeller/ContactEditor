package pl.mateuszmackowiak.nativeANE.contactManager;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class getContactLength implements FREFunction {

	public static final String KEY = "getContactCount";
	
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		try{
			Cursor cursor =  context.getActivity().managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
			int count = cursor.getCount();
			FREObject frecount = FREObject.newObject(count);
			return frecount;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

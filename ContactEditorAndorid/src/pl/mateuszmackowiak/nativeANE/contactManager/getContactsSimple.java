package pl.mateuszmackowiak.nativeANE.contactManager;

import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;


import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class getContactsSimple implements FREFunction {
	public static final String KEY= "getContactsSimple";
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		try {
			
			Cursor contactCursor =  context.getActivity().getContentResolver().query(Phone.CONTENT_URI, 
					new String[] { Phone.CONTACT_ID, Phone.DISPLAY_NAME },null, null
					,Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
			
			int count = contactCursor.getCount();
			
			FREArray contacts = FREArray.newArray(count);
				
			FREObject contact = null;
			int countNum = 0;
			String compositename;
			Integer id;
			
			if (count> 0) {
				while (contactCursor.moveToNext())
				{
					try {
						contact = FREObject.newObject("Object", null);
						compositename =contactCursor.getString(1);

						id = contactCursor.getInt(0);

						if(compositename!=null)
						  contact.setProperty(Details.TYPE_COMPOSITENAME, FREObject.newObject(compositename));
						contact.setProperty(Details.TYPE_RECORD_ID, FREObject.newObject(id));

			 			contacts.setObjectAt(countNum, contact);
					  	countNum++;
				  	
					} catch (Exception e) {
						context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,KEY+e.toString());
					}
				}
			}
			
			contactCursor.close();

			return contacts;
		} catch (Exception e) {
			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,KEY+e.toString());
			e.printStackTrace();
			return null;
		}
	}

}

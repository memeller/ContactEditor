package pl.mateuszmackowiak.nativeANE.contactManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class getContactsSimple implements FREFunction {
	public static final String KEY= "getContactsSimple";
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		try {
			ContentResolver resolver = context.getActivity().getContentResolver();

			
			Cursor phones = resolver.query(Phone.CONTENT_URI, null,null,null, null);
			
			int count = phones.getCount();
			
			FREArray contacts = FREArray.newArray(count);
				
			FREObject contact = null;
			int countNum = 0;
			String compositename,id;

			if (count> 0) {
				while (phones.moveToNext())
				{
					try {
					contact = FREObject.newObject("Object", null);
					compositename =phones.getString(phones.getColumnIndex(Phone.DISPLAY_NAME));
					id = phones.getString(phones.getColumnIndex(Phone.CONTACT_ID));
					
					
					if(compositename!=null)
					  contact.setProperty("compositename", FREObject.newObject(compositename));
				 	if(id!=null)
					  contact.setProperty("recordId", FREObject.newObject(id));
				 	
					//context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"phones.getString(phones.getColumnIndex(Phone.DELETED))   "+phones.getString(phones.getColumnIndex(Phone.DELETED)));
				 	//if (Integer.parseInt(phones.getString(phones.getColumnIndex(Phone.DELETED))) > 0){
				 		contacts.setObjectAt(countNum, contact);
					  	countNum++;
				 	//}
					} catch (Exception e) {
						context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"getContatcts "+e.toString());
					}
				}
			}
			phones.close();
			return contacts;
		} catch (Exception e) {
			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"getContatcts "+e.toString());
			e.printStackTrace();
			return null;
		}
	}

}

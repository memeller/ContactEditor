package pl.mateuszmackowiak.nativeANE.contactManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;


public class getAllContacts implements FREFunction {

	public static final String KEY = "getContacts";
	
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		try {
			ContentResolver resolver = context.getActivity().getContentResolver();
			
			Cursor contactCursor =  context.getActivity().managedQuery(Phone.CONTENT_URI, new String[] { Phone.CONTACT_ID, Phone.DISPLAY_NAME
					},null, null
					, Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
			
			int count = contactCursor.getCount();
			
			FREArray paramArr,contacts = FREArray.newArray(count);
			FREObject contact = null,paramObj;
			String compositename,recordId;
			Integer id;
			
			int countNum = 0;
			
			if (count> 0) {
				while (contactCursor.moveToNext())
				{
					try {
						contact = FREObject.newObject("Object", null);
						
						id = contactCursor.getInt(contactCursor.getColumnIndex(Phone.CONTACT_ID));
						contact.setProperty(Details.TYPE_RECORD_ID, FREObject.newObject(id));
						if(id!=null){
							recordId = id.toString();
							compositename =contactCursor.getString(contactCursor.getColumnIndex(Phone.DISPLAY_NAME));
							if(compositename!=null)
								  contact.setProperty(Details.TYPE_COMPOSITENAME, FREObject.newObject(compositename));

					 		paramArr = Details.getPhoneNumbers(context, resolver, recordId);
					 		if(paramArr!=null && paramArr.getLength()>0)
					 			contact.setProperty(Details.TYPE_PHONES, paramArr);
					 		
					 		paramArr = Details.getEmailAddresses(context, resolver, recordId);
					 		if(paramArr!=null && paramArr.getLength()>0)
					 			contact.setProperty(Details.TYPE_EMAILS, paramArr);
					 		
					 		paramArr = Details.getContactNotes(context, resolver, recordId);
					 		if(paramArr!=null && paramArr.getLength()>0)
					 			contact.setProperty(Details.TYPE_NOTES, paramArr);
					 		
					 		paramArr = Details.getContactAddresses(context, resolver, recordId);
					 		if(paramArr!=null && paramArr.getLength()>0)
					 			contact.setProperty(Details.TYPE_ADRESS, paramArr);
					 		
					 		paramObj = Details.getContactOrg(context, resolver, recordId);
					 		if(paramObj!=null)
					 			contact.setProperty(Details.TYPE_ORGANIZAIOTN, paramObj);
					 		
					 		paramObj = Details.getCotactParam(context, resolver, recordId, CommonDataKinds.Phone.DISPLAY_NAME);
					 		if(paramObj!=null)
					 			contact.setProperty(Details.TYPE_COMPOSITENAME, paramObj);
							
							
				 			contacts.setObjectAt(countNum, contact);
						  	countNum++;
						}
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

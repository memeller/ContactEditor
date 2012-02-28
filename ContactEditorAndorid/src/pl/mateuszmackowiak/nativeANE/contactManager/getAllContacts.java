package pl.mateuszmackowiak.nativeANE.contactManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
//import android.provider.ContactsContract.CommonDataKinds.StructuredName;

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

			
			Cursor phones = resolver.query(Phone.CONTENT_URI, null,null,null, null);
			
			int count = phones.getCount();
			
			FREArray contacts = FREArray.newArray(count);
				
			FREObject contact = null;
			int countNum = 0;
			String compositename;//,lastname,name;
			Integer id;
			FREArray notes,emails,numbers;
			FREObject org;
			if (count> 0) {
				while (phones.moveToNext())
				{
					try {
						contact = FREObject.newObject("Object", null);
						compositename =phones.getString(phones.getColumnIndex(Phone.DISPLAY_NAME));
						//name =phones.getString(phones.getColumnIndex(StructuredName.GIVEN_NAME));
						//lastname =phones.getString(phones.getColumnIndex(StructuredName.FAMILY_NAME));
						id = phones.getInt(phones.getColumnIndex(Phone.CONTACT_ID));
						
						notes = Details.getContactNotes(context, resolver, id.toString());
						emails = Details.getEmailAddresses(context, resolver, id.toString());
						numbers = Details.getPhoneNumbers(context, resolver, id.toString());
						
						/*if(lastname!=null)
							  contact.setProperty("lastname", FREObject.newObject(lastname));
						if(name!=null)
							  contact.setProperty("name", FREObject.newObject(name));
						*/
						if(compositename!=null)
						  contact.setProperty("compositename", FREObject.newObject(compositename));
						contact.setProperty("recordId", FREObject.newObject(id));
					 	if(notes!=null)
					 		contact.setProperty("notes", notes);
						if(numbers!=null)
					 		contact.setProperty("phones", numbers);
						if(emails!=null)
					 		contact.setProperty("emails", emails);
						
						
						String orgName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
			 			String title = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
			 			if (orgName.length() > 0) {
			 				org =FREObject.newObject("Object", null);
			 				if(orgName!=null)
			 					org.setProperty("orgName", FREObject.newObject(orgName));
			 				if(title!=null)
			 					org.setProperty("title", FREObject.newObject(title));
			 				contact.setProperty("company", org);
			 			}
			 			contacts.setObjectAt(countNum, contact);
					  	countNum++;
				  	
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

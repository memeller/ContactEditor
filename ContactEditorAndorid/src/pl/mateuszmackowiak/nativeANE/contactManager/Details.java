package pl.mateuszmackowiak.nativeANE.contactManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREReadOnlyException;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class Details  {

	public static FREArray getPhoneNumbers(FREContext context, ContentResolver resolver ,String id) {
		try {
	 		FREArray phones = FREArray.newArray(0);
	 		int count = 0;
 		Cursor pCur = context.getActivity().managedQuery(
 				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
 				null, 
 				ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
 				new String[]{id}, null);
	 		while (pCur.moveToNext()) {
	 			String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	 			if (phone.length() > 0) {
	 				phones.setLength(count+1);
	 				phones.setObjectAt(count, FREObject.newObject(phone));
	 				count++;
	 			}
	 		} 
	 		pCur.close();
	 		return(phones);
		} catch (Exception e){
 			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"getPhoneNumbers "+e.toString());
 			return null;
 		}
 	}
 	
 	public static FREArray getEmailAddresses(FREContext context, ContentResolver resolver ,String id) {
 		try {
	 		FREArray emails = FREArray.newArray(0);
	 		int count = 0;
	 		
	 		Cursor emailCur = context.getActivity().managedQuery( 
	 				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
	 				null,
	 				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
	 				new String[]{id}, null); 
	 		while (emailCur.moveToNext()) { 
	 			String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
	 			if (email.length() > 0) {
	 				emails.setLength(count+1);
	 				emails.setObjectAt(count, FREObject.newObject(email));
	 				count++;
	 			}
	 		} 
	 		emailCur.close();
	 		return(emails);
 		} catch (Exception e){
 			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"getEmailAddresses "+e.toString());
 			return null;
 		}
 	}
 	
 	public static FREArray getContactNotes(FREContext context, ContentResolver resolver ,String id){
 		try {
	 		FREArray notes = FREArray.newArray(0);
	 		int count = 0;
	 		String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
	 		String[] whereParameters = new String[]{id, 
	 			ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE}; 
	 		Cursor noteCur = resolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null); 
	 		if (noteCur.moveToFirst()) { 
	 			String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
	 			if (note.length() > 0) {
	 				notes.setLength(count+1);
	 				notes.setObjectAt(count, FREObject.newObject(note));
	 				count++;
	 			}
	 		} 
	 		noteCur.close();
	 		return(notes);
 		} catch (Exception e){
 			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"getContatctNotes "+e.toString());
 			return null;
 		}
 	}
 	
 	public static FREArray getContactAddresses(FREContext context, ContentResolver resolver ,String id) {
 		try {
	 		FREArray adresses = FREArray.newArray(0);
	 		FREObject adress =null;
	 		int count = 0;
	 		String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
	 		String[] whereParameters = new String[]{id, 
	 				ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}; 
	 		
	 		Cursor addrCur = context.getActivity().managedQuery(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);
	 		while(addrCur.moveToNext()) {
	 			String poBox = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
	 			String street = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
	 			String city = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
	 			String state = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
	 			String postalCode = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
	 			String country = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
	 			String type = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
	 			adress = FREObject.newObject("Object", null);
	 			if(poBox!=null)
	 				adress.setProperty("poBox", FREObject.newObject(poBox));
				if(street!=null)
					adress.setProperty("title", FREObject.newObject(street));
				
				if(city!=null)
					adress.setProperty("city", FREObject.newObject(city));
				if(state!=null)
					adress.setProperty("state", FREObject.newObject(state));
				if(postalCode!=null)
					adress.setProperty("postalCode", FREObject.newObject(postalCode));
				if(country!=null)
					adress.setProperty("country", FREObject.newObject(country));
				if(type!=null)
					adress.setProperty("type", FREObject.newObject(type));
				
 				adresses.setLength(count+1);
 				adresses.setObjectAt(count, adress);
 				count++;

	 		} 
	 		addrCur.close();
	 		return(adresses);
 		} catch (Exception e){
 			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"getContactAddresses "+e.toString());
 			return null;
 		}
 	}
 	

 	public static FREObject getContactOrg(FREContext context, ContentResolver resolver ,String id) throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, FREReadOnlyException {
 		FREObject org = null;
 		String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
 		String[] whereParameters = new String[]{id, 
 				ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE}; 
 		
 		Cursor orgCur = context.getActivity().managedQuery(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);
 
 		if (orgCur.moveToFirst()) { 
 			String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
 			String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
 			if (orgName.length() > 0) {
 				org =FREObject.newObject("Object", null);
 				if(orgName!=null)
 					org.setProperty("orgName", FREObject.newObject(orgName));
 				if(title!=null)
 					org.setProperty("title", FREObject.newObject(title));
 			}
 		} 
 		orgCur.close();
 		return(org);
 	}
 	
 	
 	
 	
 	public static FREObject getCotactParam(FREContext context, ContentResolver resolver ,String id,String columnName) throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, FREReadOnlyException {
 		FREObject name = null;

 		Cursor pCur = context.getActivity().managedQuery( 
 				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
 				null,
 				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", 
 				new String[]{id}, null); 
 		

 		if (pCur.moveToFirst()) { 
 			String nameS = pCur.getString(pCur.getColumnIndex(columnName));
 			if (nameS!=null && nameS.length() > 0) {
 				name= FREObject.newObject(nameS);
 			}
 		} 
 		pCur.close();
 		return(name);
 	}

}

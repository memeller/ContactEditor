package pl.mateuszmackowiak.nativeANE.contactManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Data;

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
	
	public final static String TYPE_ORGANIZAIOTN ="organization";
	public final static String TYPE_RECORD_ID ="recordId";
	public final static String TYPE_NOTES ="notes";
	public final static String TYPE_EMAILS ="emails";
	public final static String TYPE_PHONES ="phones";
	public final static String TYPE_ADRESS ="adress";
	public final static String TYPE_COMPOSITENAME ="compositename";
	
	public final static String TYPE_NAME ="name";
	public final static String TYPE_LASTNAME ="lastname";
	
	

	public static FREArray getPhoneNumbers(FREContext context, ContentResolver resolver ,String id) throws IllegalStateException, FREASErrorException, FREWrongThreadException, IllegalArgumentException, FREInvalidObjectException, FREReadOnlyException, FRETypeMismatchException {
	 		FREArray phones = FREArray.newArray(0);
	 		int count = 0;
	 		Cursor pCur = context.getActivity().managedQuery( Phone.CONTENT_URI, 
	 				new String[] { Phone.NUMBER},Phone.CONTACT_ID +" = ?",new String[]{id}, null);
	 		
	 		while (pCur.moveToNext()) {
	 			String phone = pCur.getString(pCur.getColumnIndex(Phone.NUMBER));
	 			if (phone.length() > 0) {
	 				phones.setLength(count+1);
	 				phones.setObjectAt(count, FREObject.newObject(phone));
	 				count++;
	 			}
	 		} 
	 		pCur.close();
	 		return(phones);
 	}
 	
 	public static FREArray getEmailAddresses(FREContext context, ContentResolver resolver ,String id) throws IllegalStateException, FREASErrorException, FREWrongThreadException, IllegalArgumentException, FREInvalidObjectException, FREReadOnlyException, FRETypeMismatchException {
	 		FREArray emails = FREArray.newArray(0);
	 		int count = 0;
	 		
	 		Cursor emailCur = context.getActivity().managedQuery( 
	 				Email.CONTENT_URI, 
	 				new String[] { Email.DATA},Email.CONTACT_ID + " = ?", 
	 				new String[]{id}, null); 
	 		
	 		while (emailCur.moveToNext()) { 
	 			String email = emailCur.getString(emailCur.getColumnIndex(Email.DATA));
	 			if (email.length() > 0) {
	 				emails.setLength(count+1);
	 				emails.setObjectAt(count, FREObject.newObject(email));
	 				count++;
	 			}
	 		} 
	 		emailCur.close();
	 		return(emails);
 	}
 	
 	public static FREArray getContactNotes(FREContext context, ContentResolver resolver ,String id)throws IllegalStateException, FREASErrorException, FREWrongThreadException, IllegalArgumentException, FREInvalidObjectException, FREReadOnlyException, FRETypeMismatchException {
	 		FREArray notes = FREArray.newArray(0);
	 		int count = 0;
	 		
	 		String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
	 		String[] whereParameters = new String[]{id, 
	 			Note.CONTENT_ITEM_TYPE}; 
	 		Cursor noteCur = resolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null); 
	 		if (noteCur.moveToFirst()) { 
	 			String note = noteCur.getString(noteCur.getColumnIndex(Note.NOTE));
	 			if (note.length() > 0) {
	 				notes.setLength(count+1);
	 				notes.setObjectAt(count, FREObject.newObject(note));
	 				count++;
	 			}
	 		} 
	 		noteCur.close();
	 		return(notes);
 	}
 	
 	public static FREArray getContactAddresses(FREContext context, ContentResolver resolver ,String id) throws IllegalStateException, FREASErrorException, FREWrongThreadException, IllegalArgumentException, FREInvalidObjectException, FREReadOnlyException, FRETypeMismatchException, FRENoSuchNameException {
	 		FREArray adresses = FREArray.newArray(0);
	 		FREObject adress =null;
	 		boolean add=false;
	 		int count = 0;
	 		String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
	 		String[] whereParameters = new String[]{id, 
	 				StructuredPostal.CONTENT_ITEM_TYPE}; 
	 		
	 		Cursor addrCur = context.getActivity().managedQuery(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);
	 		while(addrCur.moveToNext()) {
	 			String poBox = addrCur.getString(addrCur.getColumnIndex(StructuredPostal.POBOX));
	 			String street = addrCur.getString(addrCur.getColumnIndex(StructuredPostal.STREET));
	 			String city = addrCur.getString(addrCur.getColumnIndex(StructuredPostal.CITY));
	 			String state = addrCur.getString(addrCur.getColumnIndex(StructuredPostal.REGION));
	 			String postalCode = addrCur.getString(addrCur.getColumnIndex(StructuredPostal.POSTCODE));
	 			String country = addrCur.getString(addrCur.getColumnIndex(StructuredPostal.COUNTRY));
	 			String type = addrCur.getString(addrCur.getColumnIndex(StructuredPostal.TYPE));
	 			adress = FREObject.newObject("Object", null);
	 			if(poBox!=null){
	 				adress.setProperty("poBox", FREObject.newObject(poBox));
	 			}
	 			if(street!=null){
					adress.setProperty("title", FREObject.newObject(street));
					add=true;
	 			}
				if(city!=null){
					adress.setProperty("city", FREObject.newObject(city));
					add=true;
	 			}
				if(state!=null){
					adress.setProperty("state", FREObject.newObject(state));
						add=true;
				}
				if(postalCode!=null){
					adress.setProperty("postalCode", FREObject.newObject(postalCode));
					add=true;
				}
				if(country!=null){
					adress.setProperty("country", FREObject.newObject(country));
					add=true;
				}
				if(type!=null){
					adress.setProperty("type", FREObject.newObject(type));
					add=true;
				}
				if(add){
	 				adresses.setLength(count+1);
	 				adresses.setObjectAt(count, adress);
	 				count++;
	 				add=false;
				}
	 		} 
	 		addrCur.close();
	 		return(adresses);
 	}
 	

 	public static FREObject getContactOrg(FREContext context, ContentResolver resolver ,String id) throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, FREReadOnlyException {
 		FREObject org = null;
 		String where = Data.CONTACT_ID + " = ? AND " + Data.MIMETYPE + " = ?"; 
 		String[] whereParameters = new String[]{id, 
 				Organization.CONTENT_ITEM_TYPE}; 
 		
 		Cursor orgCur = context.getActivity().managedQuery(Data.CONTENT_URI, null, where, whereParameters, null);
 
 		if (orgCur.moveToFirst()) { 
 			String orgName = orgCur.getString(orgCur.getColumnIndex(Organization.DATA));
 			String title = orgCur.getString(orgCur.getColumnIndex(Organization.TITLE));
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
 				Phone.CONTENT_URI, 
 				null,
 				Phone.CONTACT_ID + " = ?", 
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

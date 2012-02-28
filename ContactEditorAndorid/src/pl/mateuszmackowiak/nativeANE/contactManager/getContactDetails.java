package pl.mateuszmackowiak.nativeANE.contactManager;


import android.content.ContentResolver;
import android.provider.ContactsContract.CommonDataKinds;


import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class getContactDetails implements FREFunction {
	public static final String KEY ="getContactDetails";
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		try {
			Integer id = arg1[0].getAsInt();
			if(id ==null)
				return null;
			FREObject details= FREObject.newObject("Object", null);
			
			FREArray phones = null;
			FREArray emails = null;
			FREArray notes = null;
			FREArray adress = null;
			
	 		FREObject org = null;
	 		//FREObject name =null;
	 		//FREObject lastname =null;
	 		FREObject compositename =null;
	 		
	 		ContentResolver resolver = context.getActivity().getContentResolver();
	 		
	 		phones = Details.getPhoneNumbers(context, resolver, id.toString());
	 		emails = Details.getEmailAddresses(context, resolver, id.toString());
	 		notes = Details.getContactNotes(context, resolver, id.toString());
	 		org = Details.getContactOrg(context, resolver, id.toString());
	 		adress = Details.getContactAddresses(context, resolver, id.toString());
	 		
	 		//name = Details.getCotactParam(context, resolver, id.toString(), CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME);
	 		//lastname = Details.getCotactParam(context, resolver, id.toString(), CommonDataKinds.StructuredName.DISPLAY_NAME_PRIMARY);
	 		compositename= Details.getCotactParam(context, resolver, id.toString(), CommonDataKinds.Phone.DISPLAY_NAME);
	 		
	 		//if(name!=null)
	 			//details.setProperty("name", name);
	 		
	 		//if(lastname!=null)
	 			//details.setProperty("lastname", lastname);
	 		if(compositename!=null)
	 			details.setProperty("compositename", compositename);
	 		
	 		if(adress!=null && adress.getLength()>0)
	 			details.setProperty("adress", adress);
	 		
	 		if(phones!=null && phones.getLength()>0)
	 			details.setProperty("phones", phones);
	 		
	 		if(emails!=null && emails.getLength()>0)
	 			details.setProperty("emails", emails);
	 		
	 		if(notes!=null && notes.getLength()>0)
	 			details.setProperty("notes", notes);
	 		
	 		details.setProperty("recordId", FREObject.newObject(id));
	 		
	 		
	 		if(org!=null)
	 			details.setProperty("organization", org);
	 		
	 		//pCur.close();
	 		
	 		return(details);
		} catch (Exception e){
 			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,"getDetails "+e.toString());
 			return null;
 		}

	}

}

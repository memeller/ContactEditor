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
			Integer id = null;
			if(arg1[0] !=null)
				id = arg1[0].getAsInt();
			if(id ==null)
				return null;
			String recordId = id.toString();
			
			FREObject details= FREObject.newObject("Object", null);
			
			FREArray paramArr = null;
	 		FREObject paramObj = null;

	 		
	 		ContentResolver resolver = context.getActivity().getContentResolver();
	 		
	 		paramArr = Details.getPhoneNumbers(context, resolver, recordId);
	 		if(paramArr!=null && paramArr.getLength()>0)
	 			details.setProperty(Details.TYPE_PHONES, paramArr);
	 		
	 		paramArr = Details.getEmailAddresses(context, resolver, recordId);
	 		if(paramArr!=null && paramArr.getLength()>0)
	 			details.setProperty(Details.TYPE_EMAILS, paramArr);
	 		
	 		paramArr = Details.getContactNotes(context, resolver, recordId);
	 		if(paramArr!=null && paramArr.getLength()>0)
	 			details.setProperty(Details.TYPE_NOTES, paramArr);
	 		
	 		paramArr = Details.getContactAddresses(context, resolver, recordId);
	 		if(paramArr!=null && paramArr.getLength()>0)
	 			details.setProperty(Details.TYPE_ADRESS, paramArr);
	 		
	 		paramObj = Details.getContactOrg(context, resolver, recordId);
	 		if(paramObj!=null)
	 			details.setProperty(Details.TYPE_ORGANIZAIOTN, paramObj);
	 		
	 		paramObj = Details.getCotactParam(context, resolver, recordId, CommonDataKinds.Phone.DISPLAY_NAME);
	 		if(paramObj!=null)
	 			details.setProperty(Details.TYPE_COMPOSITENAME, paramObj);
	 		
	 		//name = Details.getCotactParam(context, resolver, recordId, CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME);
	 		//lastname = Details.getCotactParam(context, resolver, recordId, CommonDataKinds.StructuredName.DISPLAY_NAME_PRIMARY);
	 		
	 		details.setProperty(Details.TYPE_RECORD_ID, FREObject.newObject(id));

	 		return(details);
		} catch (Exception e){
 			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,KEY+e.toString());
 			return null;
 		}

	}

}

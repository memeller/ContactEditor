package pl.mateuszmackowiak.nativeANE.contactManager;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;


import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class removeContact implements FREFunction {

	public static final String KEY = "removeContact";
	
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		try{
			
			Integer id = arg1[0].getAsInt();
			if(id==null)
				return FREObject.newObject(false);

			 ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
			 ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.
					CONTENT_URI).withSelection(ContactsContract.Data.CONTACT_ID+"=?",
					new String[]{id.toString()}).build());
			 context.getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			
			return FREObject.newObject(true);
		}catch(Exception e){
			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,KEY+"   "+e.toString());
			try {
				return FREObject.newObject(false);
			} catch (FREWrongThreadException e1) {
				return null;
			}
		}
	}

		
}

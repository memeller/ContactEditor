package pl.mateuszmackowiak.nativeANE.contactManager;

import java.util.ArrayList;



import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.accounts.OnAccountsUpdateListener;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;

import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.Website;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;



public class newContact implements FREFunction, OnAccountsUpdateListener {

	public static final String KEY = "addContact";
	
	private FREContext context = null;
	private ArrayList<AccountData> acounts=null;
	
	String name,lastname,phone,company,email,website;
	
	@Override
	public FREObject call(FREContext context, FREObject[] arg) {
		this.context = context;
		try{
			if(arg[0]!=null)
				name = arg[0].getAsString();
			if(arg.length>1 && arg[1]!=null)
				lastname = arg[1].getAsString();
			if(arg.length>2 && arg[2]!=null)
				phone = arg[2].getAsString();
			if(arg.length>3 && arg[3]!=null)
				company = arg[3].getAsString();
			if(arg.length>4 && arg[4]!=null)
				email = arg[4].getAsString();
			if(arg.length>5 && arg[5]!=null)
				website = arg[5].getAsString();

			AccountManager.get(context.getActivity()).addOnAccountsUpdatedListener(this, null, true);

		}catch(Exception e){
			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT,KEY+"   "+e.toString());
		}
		return null;
	}
	
	
	/**
     * Updates account list spinner when the list of Accounts on the system changes. Satisfies
     * OnAccountsUpdateListener implementation.
     */
    public void onAccountsUpdated(Account[] a) {

        // Get account data from system
        AuthenticatorDescription[] accountTypes = AccountManager.get(context.getActivity()).getAuthenticatorTypes();
        acounts = new ArrayList<AccountData>();
        // Populate tables
        for (int i = 0; i < a.length; i++) {
            // The user may have multiple accounts with the same name, so we need to construct a
            // meaningful display name for each.
            String systemAccountType = a[i].type;
            AuthenticatorDescription ad = getAuthenticatorDescription(systemAccountType, accountTypes);
            AccountData data = new AccountData(a[i].name, ad);
            acounts.add(data);
        }
        if(acounts.size()==1)
        	addContactToAccount(acounts.get(0));
        else if(acounts.size()>1)
        	createPopup();
    }
    /**
     * Obtain the AuthenticatorDescription for a given account type.
     * @param type The account type to locate.
     * @param dictionary An array of AuthenticatorDescriptions, as returned by AccountManager.
     * @return The description for the specified account type.
     */
    private AuthenticatorDescription getAuthenticatorDescription(String type,
            AuthenticatorDescription[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i].type.equals(type)) {
                return dictionary[i];
            }
        }
        // No match found
        context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT, KEY+"   Unable to find matching authenticator");
        return null;
    }
    
    
    
    /**
     * creates a popup with single list choice for all accounts
     */
	public void createPopup() {
		CharSequence choices[] = new CharSequence[acounts.size()];
		for (int i = 0; i < acounts.size(); i++) {
			AccountData acount = acounts.get(i);
			choices[i] = acount.getName()+"    \n"+acount.getTypeLabel();
		}
		AlertDialog.Builder builder = (Integer.parseInt(android.os.Build.VERSION.SDK)<11)?new AlertDialog.Builder(context.getActivity()):new AlertDialog.Builder(context.getActivity(),AlertDialog.THEME_HOLO_DARK);
		builder.setCancelable(true);
		builder.setOnCancelListener(new CancelListener(context));
		builder.setSingleChoiceItems(choices, -1, new SingleChoiceClickListener(context));
		builder.create().show();
	}
	
	private class CancelListener implements DialogInterface.OnCancelListener{
    	private FREContext context; 
    	CancelListener(FREContext context)
    	{
    		this.context=context;
    	}
 
        public void onCancel(DialogInterface dialog) 
        {
     	    context.dispatchStatusEventAsync("addingCancled",String.valueOf(-1));        
            dialog.cancel();
        }
    }
	private class SingleChoiceClickListener implements DialogInterface.OnClickListener{
    	private FREContext context; 
    	SingleChoiceClickListener(FREContext context)
    	{
    		super();
    		this.context=context;
    	}
 
        public void onClick(DialogInterface dialog,int id) 
        {
        	AccountData selectedAccount = acounts.get(id);
        	addContactToAccount(selectedAccount);
     	    context.dispatchStatusEventAsync("acountSelected",String.valueOf(id)); 
     	    dialog.dismiss();
        }
    }
	
	
	public void addContactToAccount(AccountData selectedAccount){
		try{
			String displayName="";
			if(name!=null)
				displayName = name;
			if(lastname!=null)
				displayName = displayName+"  "+name;
		 	ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		 	
	        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
	                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, selectedAccount.getType())
	                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, selectedAccount.getName())
	                .build());
	        
	        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                .withValue(ContactsContract.Data.MIMETYPE,
	                        StructuredName.CONTENT_ITEM_TYPE)
	                 .withValue(StructuredName.GIVEN_NAME,name)
	                 .withValue(StructuredName.FAMILY_NAME,lastname)
	                .build());
	        if(phone!=null && phone.isEmpty()==false){
		        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		                .withValue(ContactsContract.Data.MIMETYPE,
		                        Phone.CONTENT_ITEM_TYPE)
		                .withValue(Phone.NUMBER, phone)
		                .withValue(Phone.TYPE, Phone.TYPE_HOME)
		                .build());
	        }
	        if(email!=null && email.isEmpty()==false){
		        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		                .withValue(ContactsContract.Data.MIMETYPE,
		                        Email.CONTENT_ITEM_TYPE)
		                .withValue(Email.DATA, email)
		                .withValue(Email.TYPE, Email.TYPE_HOME)
		                .build());
	        }
	        if(company!=null && company.isEmpty()==false){
		        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		                .withValue(ContactsContract.Data.MIMETYPE,Organization.CONTENT_ITEM_TYPE)
		                .withValue(Organization.DATA, company)
		                .withValue(Organization.TYPE, Organization.TYPE_WORK)
		                .build());
	        }
	        if(website!=null && website.isEmpty()==false){
	        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                .withValue(ContactsContract.Data.MIMETYPE,
	                        Website.CONTENT_ITEM_TYPE)
	                .withValue(Website.DATA, website)
	                .withValue(Website.TYPE, Website.TYPE_HOME)
	                .build());
	        }
	        context.getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	         	
			name=null;
			lastname=null;
			phone=null;
			company=null;
			email=null;
			website=null;
			acounts = null;
			context.dispatchStatusEventAsync("contactAdded", "contact "+displayName+" added");
		     
		}catch(Exception e){
			context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT, KEY+"   "+e.toString());
		}
	}
	
	
	
	/**
     * A container class used to repreresent all known information about an account.
     */
    private class AccountData {
        private String mName;
        private String mType;
        private CharSequence mTypeLabel;

        /**
         * @param name The name of the account. This is usually the user's email address or
         *        username.
         * @param description The description for this account. This will be dictated by the
         *        type of account returned, and can be obtained from the system AccountManager.
         */
        public AccountData(String name, AuthenticatorDescription description) {
            mName = name;
            if (description != null) {
                mType = description.type;

                // The type string is stored in a resource, so we need to convert it into something
                // human readable.
                String packageName = description.packageName;
                PackageManager pm = context.getActivity().getPackageManager();

                if (description.labelId != 0) {
                    mTypeLabel = pm.getText(packageName, description.labelId, null);
                    if (mTypeLabel == null) {
                        context.dispatchStatusEventAsync(ContactEditor.ERROR_EVENT, KEY+"   IllegalArgumentException ==> LabelID provided, but label not found");
                    }
                } else {
                    mTypeLabel = "";
                }

            }
        }

        public String getName() {
            return mName;
        }

        public String getType() {
            return mType;
        }

        public CharSequence getTypeLabel() {
            return mTypeLabel;
        }


        public String toString() {
            return mName;
        }
    }
    
    
    
}

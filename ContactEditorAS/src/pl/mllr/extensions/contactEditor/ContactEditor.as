
/*

ADOBE SYSTEMS INCORPORATED
Copyright 2011 Adobe Systems Incorporated
All Rights Reserved.

NOTICE:  Adobe permits you to use, modify, and distribute this file in accordance with the
terms of the Adobe license agreement accompanying it.  If you have received this file from a
source other than Adobe, then your use, modification, or distribution of it requires the prior
written permission of Adobe.

*/


package pl.mllr.extensions.contactEditor
{
	import flash.external.ExtensionContext;
	
	import mx.core.mx_internal;
	
	public class ContactEditor
	{
		public static const EXTENSION_ID : String = "pl.mllr.extensions.contactEditor";
		private static var _instance:ContactEditor = null;
		private static var _shouldCreateInstance:Boolean = false;
		private static var _set:Boolean = false;
		private static var _isSupp:Boolean = false;
		private static var context:ExtensionContext;
		/**
		 * initializes contacteditor
		 */
		public function ContactEditor()
		{
			if(context==null){
				try{
					context = ExtensionContext.createExtensionContext(EXTENSION_ID, null);
				}catch(e:Error){
					trace(e.message,e.errorID);
				}
			}
					
		}
		/**
		 *Adds contact to AddressBook 
		 * @param name first name
		 * @param lastname last name
		 * @param phone phone number (currently only home supported)
		 * @param company company name
		 * @param email email address (currently only home supported)
		 * @param website website (currently only home supported)
		 * 
		 */		
		public function addContact(name:String,lastname:String="",phone:String="",company:String="",email:String="",website:String=""):void
		{  
			
			context.call("addContact",name,lastname,phone,company,email,website) ;
		}
		/**
		 * gets all contacts from AddressBook 
		 * @return an array of contacts with following: name, lastname, compositename, birthdate, recordid, phones (array), emails (array);
		 * 
		 */		
		public function getContacts():Array
		{  
			
			return context.call("getContacts") as Array;
		}
		/**
		 *returns number of contacts in AddressBook 
		 * @return num of contacts
		 * 
		 */		
		public function getContactCount():int
		{
			return context.call("getContactCount") as int;
		}
		/**
		 *removes contact with specified recordId 
		 * @param recordId record identifier, as set in AddressBook on the device
		 * @return true if contact was removed, false if not found
		 * 
		 */		
		public function removeContact(recordId:int):Boolean
		{
			return context.call("removeContact",recordId) as Boolean;
		}
		/**
		 * Whether a Notification system is available on the device (true);<br>otherwise false
		 */
		public static function get isSupported():Boolean{
			if(!_set){// checks if a value was set before
				try{
					_set = true;
					if(context==null)
						context = ExtensionContext.createExtensionContext(EXTENSION_ID, null);
					_isSupp = context.call("isSupported");
				}catch(e:Error){
					trace(e.message,e.errorID);
					return _isSupp;
				}
			}	
			return _isSupp;
		}
	}
}






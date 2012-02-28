package pl.mllr.extensions.contactEditor
{

	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;

	public class ContactEditor extends EventDispatcher
	{
<<<<<<< HEAD
		public static const EXTENSION_ID : String = "pl.mllr.extensions.contactEditor";
=======
		private static var context:ExtensionContext = null;
		
>>>>>>> Android Support
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
<<<<<<< HEAD
			if(context==null){
				try{
					context = ExtensionContext.createExtensionContext(EXTENSION_ID, null);
				}catch(e:Error){
					trace(e.message,e.errorID);
				}
			}
					
=======
			context = ExtensionContext.createExtensionContext("pl.mllr.extensions.contactEditor",null);
			context.addEventListener(StatusEvent.STATUS, onStatus);
		}
		
		protected function onStatus(event:Event):void
		{
			this.dispatchEvent(event.clone());
>>>>>>> Android Support
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
<<<<<<< HEAD
=======
		
>>>>>>> Android Support
		/**
		 * gets all contacts from AddressBook 
		 * @return an array of contacts with following: compositename, recordid;
		 * 
		 */	
		public function getContactsSimple():Array
		{  
			
			return context.call("getContactsSimple") as Array;
		}
		/**
		 * gets details of specified contact from AddressBook 
		 * @return an object with following: name, lastname, compositename, birthdate, recordid, phones (array), emails (array);
		 * 
		 */	
		public function getContactDetails(recordId:int):Object
		{  
			
			return context.call("getContactDetails",recordId) as Object;
		}
<<<<<<< HEAD
=======
		
		
>>>>>>> Android Support
		public function dispose():void{
			if(context)
				context.dispose();
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
<<<<<<< HEAD
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
					_isSupp = context.call("contactEditorIsSupported");
				}catch(e:Error){
					trace(e.message,e.errorID);
					return _isSupp;
				}
			}	
			return _isSupp;
=======
>>>>>>> Android Support
		}
	}
}






# Contact Editor - AddressBook Native Extension for iOS and Android#

authors: Paweł Meller & Mateusz Maćkowiak (https://github.com/mateuszmackowiak)

ARC support for iOS: Julian Xhokaxhiu <xhokaxhiujulian@gmail.com>

### iOS6 support!###
(scroll down to iOS section for info)

###What is available?:###

* iOS6 support
* arc support by Julian Xhokaxhiu
* setContactImage - sets new image used as contact photo
* getContactBitmapData - returns bitmap data of image used as contact photo
* addContactInWindow - shows native window for adding new contact
* showContactDetailsInWindow - shows native window for viewing contact details. If 'isEditable' parameter is set to true, enables editing of contact details.
* pickContact - shows system contact picker, that enables user to select desired contact from list. After selecting the picker is dismissed, and the selected recordId is sent using ContactEditorEvent.CONTACT_SELECTED 
* getContactCount - gets the number of contacts in AddressBook
* addcontact - adds contact to AddressBook
* removeContact - removes contact with specified recordId
* isSupported - returns if ane is supported on current platform, added after feedback from Mateusz
* getContactsSimple - gets contacts from AddressBook(compositename, recordid), shoud be faster than getContacts, because it requires less processing.
* <del>getContacts</del> function was removed as it was very slow.
* getContactDetails - gets details for specified contact

#iOS#
Finally the extension works under iOS 6 using the new permission system for apps :) Thanks to Julian, the extension now works with ARC enabled. New function is introduced:

* hasPermission - checks if the user has allowed/blocked access to address book

The permissions are also checked when accessing address book data, so that if the user blocks the access while the application is running or by using settings -> privacy, the application won't crash.
To sum up: always call hasPermission when trying to acces address book. If it is the first time you are trying to access the address book, then a system dialog is displayed asking for permission. It the user accepts the request, the function will return true and you can use address book normally. If you get false, then the user has blocked access to his address book either by clicking 'deny' in dialog box or by using settings -> privacy.
After getting one 'deny' the setting can be only changed in privacy settings by the user (it is not possible to display the permission request dialog again).

Current version should also work with earlier versions of iOS.

# Android #
Mateusz added implementation for Android platform, so big thanks to him for his work! :)

As always android requires manifests. Just for accessing contacts informations:

    < uses-permission android:name="android.permission.READ_CONTACTS" />
  
But more if Your app is going to add or remove any info:

    < uses-permission android:name="android.permission.WRITE_CONTACTS" />
    #only if adding:
    < uses-permission android:name="android.permission.GET_ACCOUNTS" />
    
To pick a contact it is necessary to ad also : 
			
			<application>
				<activity android:name="pl.mateuszmackowiak.nativeANE.contactManager.PickContactHandler" android:theme="@android:style/Theme.Translucent.NoTitleBar"></activity>
			</application>
			
## ANE compilation ##
To compile the ane, osx widh iOS SDK is needed. This ane uses AddressBook framework, which has to be linked during the compilation (sample ane build.sh is provided in build directory, it already includes the link to AddressBook framework).

Tested on iPad, iOS 5.01, 6.01 / Android 3.1,4.03
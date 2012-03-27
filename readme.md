# Contact Editor - AddressBook Native Extension #
### now for iOS and Android ###
author: memeller@gmail.com & Mateusz Maćkowiak (https://github.com/mateuszmackowiak)
Currently supported:

* (new, iOS only) getContactBitmapData - returns bitmap data of image used as contact photo
* (new, iOS only) addContactInWindow - shows native window for adding new contact
* (new, iOS only) showContactDetailsInWindow - shows native window for viewing contact details. If 'isEditable' parameter is set to true, enables editing of contact details.
* pickContact - shows system contact picker, that enables user to select desired contact from list. After selecting the picker is dismissed, and the selected recordId is sent using ContactEditorEvent.CONTACT_SELECTED 
* getContactCount - gets the number of contacts in AddressBook
* getContacts - gets contacts from AddressBook (name, lastname, compositename, phones, emails, recordid, birthdate, (NEW) addresses)
* addcontact - adds contact to AddressBook
* removeContact - removes contact with specified recordId
* isSupported - returns if ane is supported on current platform, added on request from Mateusz  
added after feedback from Mateusz(https://github.com/mateuszmackowiak)
* getContactsSimple - gets contacts from AddressBook(compositename, recordid), shoud be faster than getContacts, because it requires less processing.
* getContactDetails - gets details for specified contact


Mateusz added implementation for Android platform and cleaned up some mess i left in files, so big thanks to him for his work! :)

As always android requires manifests. Just for accessing contacts informations:

    < uses-permission android:name="android.permission.READ_CONTACTS" />
  
But more if Your app is going to add or remove any info:

    < uses-permission android:name="android.permission.WRITE_CONTACTS" />
    #only if adding:
    < uses-permission android:name="android.permission.GET_ACCOUNTS" />

### ANE compilation ###
To compile the ane, osx widh iOS SDK is needed. This ane uses AddressBook framework, which has to be linked during the compilation (sample ane build.sh is provided in build directory, it already includes the link to AddressBook framework).

Not much, but still better than accesing the sql database directly ;)
Tested on iPad, iOS 5.01 / Andorid 3.1
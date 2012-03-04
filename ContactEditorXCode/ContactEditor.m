/*
 
 Contact editor by memeller@gmail.com
 isSupported function by https://github.com/mateuszmackowiak
 
 */
#import "ContactEditor.h"
#import "ContactEditorHelper.h"
@implementation ContactEditor

ContactEditorHelper *contactEditorHelper;
ABAddressBookRef addressBook;
FREObject showContactPicker(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    if (!contactEditorHelper) {
        contactEditorHelper = [[ContactEditorHelper alloc] init];
    }
    
    [contactEditorHelper setContext:ctx];
    [contactEditorHelper showContactPicker];
    
    
    return NULL;    
}

FREObject removeContact(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[])
{
    FREObject retBool = nil;
    uint32_t boolean=0;
    uint32_t recordId;
    if(FRE_OK==FREGetObjectAsUint32(argv[0], &recordId))
    {
        addressBook=ABAddressBookCreate();
        ABRecordID abrecordId=recordId;
        ABRecordRef aRecord = ABAddressBookGetPersonWithRecordID(addressBook, abrecordId);
        if(aRecord)
        {
            DLog(@"record found, trying to remove %i",abrecordId);
            ABAddressBookRemoveRecord(addressBook, aRecord, NULL);
            // CFRelease(aRecord);
            boolean=1;
            DLog(@"ContactRemoved");
        }
        if(ABAddressBookHasUnsavedChanges)
            ABAddressBookSave(addressBook, NULL);
        DLog(@"Release");
        CFRelease(addressBook);
        DLog(@"Return data");
    }
    else
        DLog(@"something wrong with value");
    DLog(@"setting returned value");
    FRENewObjectFromBool(boolean, &retBool);
    return retBool;
    
}
FREObject contactEditorIsSupported(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] ){
    FREObject retVal;
    if(FRENewObjectFromBool(YES, &retVal) == FRE_OK){
        return retVal;
    }else{
        return nil;
    }
}
FREObject addContact(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[])
{
    addressBook=ABAddressBookCreate();
	uint32_t usernameLength;
    const uint8_t *name;
    uint32_t surnameLength;
    const uint8_t *surname;
    uint32_t usercontactLength;
    const uint8_t *contact;
    uint32_t usercompanyLength;
    const uint8_t *company;
    uint32_t useremailLength;
    const uint8_t *email;
	uint32_t websiteLength;
    const uint8_t *website;
	DLog(@"Parsing data...");
    //Turn our actionscrpt code into native code.
    FREGetObjectAsUTF8(argv[0], &usernameLength, &name);
    FREGetObjectAsUTF8(argv[1], &surnameLength, &surname);
    FREGetObjectAsUTF8(argv[2], &usercontactLength, &contact);
    FREGetObjectAsUTF8(argv[3], &usercompanyLength, &company);
    FREGetObjectAsUTF8(argv[4], &useremailLength, &email);
	FREGetObjectAsUTF8(argv[5], &websiteLength, &website);
    NSString *username = nil;
    NSString *usersurname=nil;
    NSString *usercontact = nil;
    NSString *usercompany = nil;
    NSString *useremail = nil;
    NSString *userwebsite = nil;
    DLog(@"Creating strings");
    //Create NSStrings from CStrings
    if (FRE_OK == FREGetObjectAsUTF8(argv[0], &usernameLength, &name)) {
        username = [NSString stringWithUTF8String:(char*)name];
    }
    if (FRE_OK == FREGetObjectAsUTF8(argv[1], &surnameLength, &name)) {
        usersurname = [NSString stringWithUTF8String:(char*)surname];
    }
    if (FRE_OK == FREGetObjectAsUTF8(argv[2], &usercontactLength, &contact)) {
        usercontact = [NSString stringWithUTF8String:(char*)contact];
    }
    
    if (FRE_OK == FREGetObjectAsUTF8(argv[3], &usercompanyLength, &company)) {
        usercompany = [NSString stringWithUTF8String:(char*)company];
    }
    
    if (argc >= 4 && (FRE_OK == FREGetObjectAsUTF8(argv[4], &useremailLength, &email))) {
        useremail = [NSString stringWithUTF8String:(char*)email];
    }
    
    if (argc >= 5 && (FRE_OK == FREGetObjectAsUTF8(argv[5], &websiteLength, &website))) {
        userwebsite = [NSString stringWithUTF8String:(char*)website];
    }
    
    ABRecordRef aRecord = ABPersonCreate(); 
    CFErrorRef  anError = NULL;
    
    DLog(@"Adding name");
    // Username
    ABRecordSetValue(aRecord, kABPersonFirstNameProperty, username, &anError);
    ABRecordSetValue(aRecord, kABPersonLastNameProperty, usersurname, &anError);
    // Phone Number.
    if(usercontact)
    {
        DLog(@"Adding phone number");
        ABMutableMultiValueRef multi = ABMultiValueCreateMutable(kABMultiStringPropertyType);
        ABMultiValueAddValueAndLabel(multi, (CFStringRef)usercontact, kABWorkLabel, NULL);
        ABRecordSetValue(aRecord, kABPersonPhoneProperty, multi, &anError);
        //    CFRelease(multi);
    }
    // Company
    DLog(@"Adding company");
    if(usercompany)
    {
        ABRecordSetValue(aRecord, kABPersonOrganizationProperty, usercompany, &anError);
    }
    //// email
    DLog(@"Adding email");
    if(usercompany)
    {
        ABMutableMultiValueRef multiemail = ABMultiValueCreateMutable(kABMultiStringPropertyType);
        ABMultiValueAddValueAndLabel(multiemail, (CFStringRef)useremail, kABWorkLabel, NULL);
        ABRecordSetValue(aRecord, kABPersonEmailProperty, multiemail, &anError);
        //  CFRelease(multiemail);
    }
    // website
    DLog(@"Adding website");
    //DLog(userwebsite);
    if(userwebsite)
    {
        ABMutableMultiValueRef multiweb = ABMultiValueCreateMutable(kABMultiStringPropertyType);
        ABMultiValueAddValueAndLabel(multiweb, (CFStringRef)userwebsite, kABHomeLabel, NULL);
        ABRecordSetValue(aRecord, kABPersonURLProperty, multiweb, &anError);
        //  CFRelease(multiweb);
    }
    // Function
    //ABRecordSetValue(aRecord, kABPersonJobTitleProperty, userrole, &anError);
    CFErrorRef error =nil;
    DLog(@"Writing values");
    
    
    DLog(@"Saving to contacts");
    ABAddressBookAddRecord(addressBook, aRecord, &error);
    if (error != NULL) { 
		
		DLog(@"error while creating..");
	} 
    if(ABAddressBookHasUnsavedChanges)
        ABAddressBookSave(addressBook, &error);
    
    DLog(@"Releasing data");
    //CFRelease(aRecord);
    //[username release];
    //[usersurname release];
    //[usercontact release];
    //[usercompany release];
    //[useremail release];
    //[userwebsite release];
    CFRelease(addressBook);
    return NULL;
}
FREObject getContacts(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[])
{
    DLog(@"Getting contact data");
    addressBook=ABAddressBookCreate();
    CFArrayRef people = ABAddressBookCopyArrayOfAllPeople(addressBook);
    DLog(@"Parsing data");
    FREObject returnedArray = NULL;
    FRENewObject((const uint8_t*)"Array", 0, NULL, &returnedArray, nil);
    FRESetArrayLength(returnedArray, CFArrayGetCount(people));
    int32_t j=0;
    FREObject retStr=NULL;
    for (CFIndex i = 0; i < CFArrayGetCount(people); i++) {
        FREObject contact;
        FRENewObject((const uint8_t*)"Object", 0, NULL, &contact,NULL);
        
        ABRecordRef person = CFArrayGetValueAtIndex(people, i);
        
        //person id
        int personId = (int)ABRecordGetRecordID(person);
        DLog(@"Adding person with id: %i",personId);
        FREObject recordId;
        FRENewObjectFromInt32(personId, &recordId);
        FRESetObjectProperty(contact, (const uint8_t*)"recordId", recordId, NULL);
        
        //composite name
        CFStringRef personCompositeName = ABRecordCopyCompositeName(person);
        retStr=NULL;
        if(personCompositeName)
        {
            NSString *personCompositeString = [NSString stringWithString:(NSString *)personCompositeName];
            DLog(@"Adding composite name: %@",personCompositeString);
            FRENewObjectFromUTF8(strlen([personCompositeString UTF8String])+1, (const uint8_t*)[personCompositeString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"compositename", retStr, NULL);
            //[personCompositeString release];
            CFRelease(personCompositeName);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"compositename", retStr, NULL);
        
        retStr=NULL;
        
        
        
        //person first name
        CFStringRef personName = ABRecordCopyValue(person, kABPersonFirstNameProperty);
        if(personName)
        {
            NSString *personNameString = [NSString stringWithString:(NSString *)personName];
            DLog(@"Adding first name: %@",personNameString);
            FRENewObjectFromUTF8(strlen([personNameString UTF8String])+1, (const uint8_t*)[personNameString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"name", retStr, NULL);
            //[personNameString release];
            CFRelease(personName);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"name", retStr, NULL);
        retStr=NULL;
        //surname
        CFStringRef personSurName = ABRecordCopyValue(person, kABPersonLastNameProperty);
        if(personSurName)
        {
            NSString *personSurNameString = [NSString stringWithString:(NSString *)personSurName];
            DLog(@"Adding last name: %@",personSurNameString);
            FRENewObjectFromUTF8(strlen([personSurNameString UTF8String])+1, (const uint8_t*)[personSurNameString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"lastname", retStr, NULL);
            //[personSurNameString release];
            CFRelease(personSurName);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"lastname", retStr, NULL);
        retStr=NULL;
        
        //birthdate
        NSDate *personBirthdate = (NSDate*)ABRecordCopyValue(person, kABPersonBirthdayProperty);
        if(personBirthdate)
        {
            NSDateFormatter *dateFormatter=[[NSDateFormatter alloc] init];
            [dateFormatter setDateStyle:NSDateFormatterShortStyle];
            
            NSString *personBirthdateString = [dateFormatter stringFromDate:personBirthdate];
            DLog(@"Adding birthdate: %@",personBirthdateString);
            FRENewObjectFromUTF8(strlen([personBirthdateString UTF8String])+1, (const uint8_t*)[personBirthdateString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"birthdate", retStr, NULL);
            //[personBirthdateString release];
            [dateFormatter release];
            //CFRelease(personBirthdate);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"birthdate", retStr, NULL);
        
        //emails
        retStr=NULL;
        FREObject emailsArray = NULL;
        FRENewObject((const uint8_t*)"Array", 0, NULL, &emailsArray, nil);
        
        ABMultiValueRef emails = ABRecordCopyValue(person, kABPersonEmailProperty);
        if(emails)
        {
            for (CFIndex k=0; k < ABMultiValueGetCount(emails); k++) {
                NSString* email = (NSString*)ABMultiValueCopyValueAtIndex(emails, k);
                DLog(@"Adding email: %@",email);
                FRENewObjectFromUTF8(strlen([email UTF8String])+1, (const uint8_t*)[email UTF8String], &retStr);
                FRESetArrayElementAt(emailsArray, k, retStr);
                [email release];
            }
            CFRelease(emails);
            FRESetObjectProperty(contact, (const uint8_t*)"emails", emailsArray, NULL);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"emails", NULL, NULL);
        retStr=NULL;
        //phones
        FREObject phonesArray = NULL;
        FRENewObject((const uint8_t*)"Array", 0, NULL, &phonesArray, nil);
        ABMultiValueRef phones = ABRecordCopyValue(person, kABPersonPhoneProperty);
        if(phones)
        {
            for (CFIndex k=0; k < ABMultiValueGetCount(phones); k++) {
                NSString* phone = (NSString*)ABMultiValueCopyValueAtIndex(phones, k);
                DLog(@"Adding phone: %@",phone);
                FRENewObjectFromUTF8(strlen([phone UTF8String])+1, (const uint8_t*)[phone UTF8String], &retStr);
                FRESetArrayElementAt(phonesArray, k, retStr);
                [phone release];
                
            }
            CFRelease(phones);
            FRESetObjectProperty(contact, (const uint8_t*)"phones", phonesArray, NULL);            
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"phones", NULL, NULL);
        
        //addContact to array*/
        DLog(@"Adding element to array %ld",i);
        FRESetArrayElementAt(returnedArray, j, contact);
        j++;
        CFRelease(person);
    }
    DLog(@"Release");
    CFRelease(addressBook);
    DLog(@"Return data");
    return returnedArray;
}
FREObject getContactDetails(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[])
{
    
    uint32_t argrecordId;
    FREObject contact=NULL;
    FRENewObject((const uint8_t*)"Object", 0, NULL, &contact,NULL);
    if(FRE_OK==FREGetObjectAsUint32(argv[0], &argrecordId))
    {
        addressBook=ABAddressBookCreate();
        ABRecordID abrecordId=argrecordId;
        ABRecordRef person = ABAddressBookGetPersonWithRecordID(addressBook, abrecordId);
        FREObject retStr=NULL;
        int personId = (int)ABRecordGetRecordID(person);
        DLog(@"Adding person with id: %i",personId);
        FREObject recordId;
        FRENewObjectFromInt32(personId, &recordId);
        FRESetObjectProperty(contact, (const uint8_t*)"recordId", recordId, NULL);
        
        //composite name
        CFStringRef personCompositeName = ABRecordCopyCompositeName(person);
        retStr=NULL;
        if(personCompositeName)
        {
            NSString *personCompositeString = [NSString stringWithString:(NSString *)personCompositeName];
            DLog(@"Adding composite name: %@",personCompositeString);
            FRENewObjectFromUTF8(strlen([personCompositeString UTF8String])+1, (const uint8_t*)[personCompositeString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"compositename", retStr, NULL);
            //[personCompositeString release];
            CFRelease(personCompositeName);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"compositename", retStr, NULL);
        
        retStr=NULL;
        
        
        
        //person first name
        CFStringRef personName = ABRecordCopyValue(person, kABPersonFirstNameProperty);
        if(personName)
        {
            NSString *personNameString = [NSString stringWithString:(NSString *)personName];
            DLog(@"Adding first name: %@",personNameString);
            FRENewObjectFromUTF8(strlen([personNameString UTF8String])+1, (const uint8_t*)[personNameString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"name", retStr, NULL);
            //[personNameString release];
            CFRelease(personName);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"name", retStr, NULL);
        retStr=NULL;
        //surname
        CFStringRef personSurName = ABRecordCopyValue(person, kABPersonLastNameProperty);
        if(personSurName)
        {
            NSString *personSurNameString = [NSString stringWithString:(NSString *)personSurName];
            DLog(@"Adding last name: %@",personSurNameString);
            FRENewObjectFromUTF8(strlen([personSurNameString UTF8String])+1, (const uint8_t*)[personSurNameString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"lastname", retStr, NULL);
            //[personSurNameString release];
            CFRelease(personSurName);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"lastname", retStr, NULL);
        retStr=NULL;
        
        //birthdate
        NSDate *personBirthdate = (NSDate*)ABRecordCopyValue(person, kABPersonBirthdayProperty);
        if(personBirthdate)
        {
            NSDateFormatter *dateFormatter=[[NSDateFormatter alloc] init];
            [dateFormatter setDateStyle:NSDateFormatterShortStyle];
            
            NSString *personBirthdateString = [dateFormatter stringFromDate:personBirthdate];
            DLog(@"Adding birthdate: %@",personBirthdateString);
            FRENewObjectFromUTF8(strlen([personBirthdateString UTF8String])+1, (const uint8_t*)[personBirthdateString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"birthdate", retStr, NULL);
            //[personBirthdateString release];
            [dateFormatter release];
            //CFRelease(personBirthdate);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"birthdate", retStr, NULL);
        
        //emails
        retStr=NULL;
        FREObject emailsArray = NULL;
        FRENewObject((const uint8_t*)"Array", 0, NULL, &emailsArray, nil);
        
        ABMultiValueRef emails = ABRecordCopyValue(person, kABPersonEmailProperty);
        if(emails)
        {
            for (CFIndex k=0; k < ABMultiValueGetCount(emails); k++) {
                NSString* email = (NSString*)ABMultiValueCopyValueAtIndex(emails, k);
                DLog(@"Adding email: %@",email);
                FRENewObjectFromUTF8(strlen([email UTF8String])+1, (const uint8_t*)[email UTF8String], &retStr);
                FRESetArrayElementAt(emailsArray, k, retStr);
                [email release];
            }
            CFRelease(emails);
            FRESetObjectProperty(contact, (const uint8_t*)"emails", emailsArray, NULL);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"emails", NULL, NULL);
        retStr=NULL;
        //phones
        FREObject phonesArray = NULL;
        FRENewObject((const uint8_t*)"Array", 0, NULL, &phonesArray, nil);
        ABMultiValueRef phones = ABRecordCopyValue(person, kABPersonPhoneProperty);
        if(phones)
        {
            for (CFIndex k=0; k < ABMultiValueGetCount(phones); k++) {
                NSString* phone = (NSString*)ABMultiValueCopyValueAtIndex(phones, k);
                DLog(@"Adding phone: %@",phone);
                FRENewObjectFromUTF8(strlen([phone UTF8String])+1, (const uint8_t*)[phone UTF8String], &retStr);
                FRESetArrayElementAt(phonesArray, k, retStr);
                [phone release];
                
            }
            CFRelease(phones);
            FRESetObjectProperty(contact, (const uint8_t*)"phones", phonesArray, NULL);            
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"phones", NULL, NULL);
        
        //CFRelease(person);
        
        
        CFRelease(addressBook);
    }
    return contact;
}

FREObject getContactsSimple(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[])
{
    DLog(@"Getting contact data");
    addressBook=ABAddressBookCreate();
    CFArrayRef people = ABAddressBookCopyArrayOfAllPeople(addressBook);
    DLog(@"Parsing data");
    FREObject returnedArray = NULL;
    FRENewObject((const uint8_t*)"Array", 0, NULL, &returnedArray, nil);
    FRESetArrayLength(returnedArray, CFArrayGetCount(people));
    int32_t j=0;
    FREObject retStr=NULL;
    for (CFIndex i = 0; i < CFArrayGetCount(people); i++) {
        FREObject contact;
        FRENewObject((const uint8_t*)"Object", 0, NULL, &contact,NULL);
        
        ABRecordRef person = CFArrayGetValueAtIndex(people, i);
        
        //person id
        int personId = (int)ABRecordGetRecordID(person);
        DLog(@"Adding person with id: %i",personId);
        FREObject recordId;
        FRENewObjectFromInt32(personId, &recordId);
        FRESetObjectProperty(contact, (const uint8_t*)"recordId", recordId, NULL);
        
        //composite name
        CFStringRef personCompositeName = ABRecordCopyCompositeName(person);
        retStr=NULL;
        if(personCompositeName)
        {
            NSString *personCompositeString = [NSString stringWithString:(NSString *)personCompositeName];
            DLog(@"Adding composite name: %@",personCompositeString);
            FRENewObjectFromUTF8(strlen([personCompositeString UTF8String])+1, (const uint8_t*)[personCompositeString UTF8String], &retStr);
            FRESetObjectProperty(contact, (const uint8_t*)"compositename", retStr, NULL);
            //[personCompositeString release];
            CFRelease(personCompositeName);
        }
        else
            FRESetObjectProperty(contact, (const uint8_t*)"compositename", retStr, NULL);
        
        DLog(@"Adding element to array %ld",i);
        FRESetArrayElementAt(returnedArray, j, contact);
        j++;
        CFRelease(person);
    }
    DLog(@"Release");
    CFRelease(addressBook);
    DLog(@"Return data");
    return returnedArray;
}

FREObject getContactCount(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[])
{
    addressBook=ABAddressBookCreate();
    DLog(@"Getting emails");
    CFArrayRef people = ABAddressBookCopyArrayOfAllPeople(addressBook);
    
    FREObject contactCount;
    FRENewObjectFromInt32(CFArrayGetCount(people), &contactCount);
    // create an instance of Object and save it to FREObject position
    DLog(@"Release");
    CFRelease(addressBook);
    DLog(@"Return data");
    return contactCount;
}

// ContextInitializer()
//
// The context initializer is called when the runtime creates the extension context instance.

void ContactEditorContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, 
                                     uint32_t* numFunctionsToTest, const FRENamedFunction** functionsToSet) {
	
    
	*numFunctionsToTest = 8;
	FRENamedFunction* func = (FRENamedFunction*)malloc(sizeof(FRENamedFunction) * 8);
    
	func[0].name = (const uint8_t*)"addContact";
	func[0].functionData = NULL;
	func[0].function = &addContact;
    func[1].name = (const uint8_t*)"getContacts";
	func[1].functionData = NULL;
	func[1].function = &getContacts;
    func[2].name = (const uint8_t*)"getContactCount";
	func[2].functionData = NULL;
	func[2].function = &getContactCount;
    func[3].name = (const uint8_t*)"removeContact";
	func[3].functionData = NULL;
	func[3].function = &removeContact;
    func[4].name = (const uint8_t*)"contactEditorIsSupported";
	func[4].functionData = NULL;
	func[4].function = &contactEditorIsSupported;
    func[5].name = (const uint8_t*)"getContactsSimple";
	func[5].functionData = NULL;
	func[5].function = &getContactsSimple;
    func[6].name = (const uint8_t*)"getContactDetails";
	func[6].functionData = NULL;
	func[6].function = &getContactDetails;
    func[7].name = (const uint8_t*)"showContactPicker";
	func[7].functionData = NULL;
	func[7].function = &showContactPicker;
    
	*functionsToSet = func;
    DLog(@"Exiting ContactEditorContextInitializer()");
}



// ContextFinalizer()
//
// The context finalizer is called when the extension's ActionScript code
// calls the ExtensionContext instance's dispose() method.
// If the AIR runtime garbage collector disposes of the ExtensionContext instance, the runtime also calls
// ContextFinalizer().

void ContactEditorContextFinalizer(FREContext ctx) {
	
    [contactEditorHelper setContext:NULL];
	[contactEditorHelper release];
	contactEditorHelper = nil;
    // Nothing to clean up.
    
	return;
}



// ExtInitializer()
//
// The extension initializer is called the first time the ActionScript side of the extension
// calls ExtensionContext.createExtensionContext() for any context.

void ContactEditorExtInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, 
                                 FREContextFinalizer* ctxFinalizerToSet) {
	
  	*extDataToSet = NULL;
	*ctxInitializerToSet = &ContactEditorContextInitializer;
	*ctxFinalizerToSet = &ContactEditorContextFinalizer;
} 



// ExtFinalizer()
//
// The extension finalizer is called when the runtime unloads the extension. However, it is not always called.

void ContactEditorExtFinalizer(void* extData) {
	
    
	// Nothing to clean up.
	
    
    
	return;
}

@end
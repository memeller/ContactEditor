//
//  ContactEditorHelper.h
//
//  Created by Paweł Meller, using NativeMail from P.Kościerzyński as help :)
//

#import	 "FlashRuntimeExtensions.h"


@interface ContactEditorHelper : NSObject<ABPeoplePickerNavigationControllerDelegate> {
	FREContext context;
}

-(void) showContactPicker;

-(void)setContext:(FREContext)ctx;

@end

//
//  ContactEditorHelper.h
//
//  Created by Paweł Meller, using NativeMail from P.Kościerzyński as help :)
//

#import	 "FlashRuntimeExtensions.h"


@interface ContactEditorViewDetailsHelper : NSObject<ABPersonViewControllerDelegate> {
	FREContext context;
}

-(void) showContactDetailsInWindow:(ABRecordRef)person
isEditable:(BOOL)isEditable;

-(void)setContext:(FREContext)ctx;

@end

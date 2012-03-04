//
//  ContactEditorHelper.m
//
//  Created by Paweł Meller, using NativeMail from P.Kościerzyński as help :)
//

#import "ContactEditorHelper.h"


@implementation ContactEditorHelper

static  NSString *event_name = @"contactSelected";


-(void) showContactPicker
{
    
    ABPeoplePickerNavigationController *picker = [[ABPeoplePickerNavigationController alloc] init];
	picker.peoplePickerDelegate = self;
        //show mail composer
    [[[[UIApplication sharedApplication] keyWindow] rootViewController] presentModalViewController:picker animated:YES];
   
	[picker release];

}
- (void)peoplePickerNavigationControllerDidCancel:(ABPeoplePickerNavigationController *)peoplePicker {
    // assigning control back to the main controller
    [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissModalViewControllerAnimated:YES];
}

- (BOOL)peoplePickerNavigationController:(ABPeoplePickerNavigationController *)peoplePicker shouldContinueAfterSelectingPerson:(ABRecordRef)person {
    int personId = (int)ABRecordGetRecordID(person);
    DLog(@"Got person with id: %i",personId);
    NSString * s = [NSString stringWithFormat:@"%i", personId];
    FREDispatchStatusEventAsync(context, (uint8_t*)[event_name UTF8String], (uint8_t*) (uint8_t*)[s UTF8String]);
    [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissModalViewControllerAnimated:YES];
    [[NSNotificationCenter defaultCenter] postNotificationName:UIDeviceOrientationDidChangeNotification object:nil];
	return NO;
}

- (BOOL)peoplePickerNavigationController:(ABPeoplePickerNavigationController *)peoplePicker shouldContinueAfterSelectingPerson:(ABRecordRef)person property:(ABPropertyID)property identifier:(ABMultiValueIdentifier)identifier {
	return NO;
}
- (BOOL)personViewController:(ABPersonViewController *)personViewController shouldPerformDefaultActionForPerson:(ABRecordRef)person property:(ABPropertyID)property identifier:(ABMultiValueIdentifier)identifier
{
    return NO;
}
-(void)setContext:(FREContext)ctx {
    context = ctx;
}


@end

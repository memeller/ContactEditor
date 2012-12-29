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
    /* includes ios6 function and older, depreciated for ios5 */
    if([[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] respondsToSelector:@selector(presentViewController:animated:completion:)])
        [[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] presentViewController:picker animated:YES completion:^{/* done */}];
    else
        [[[[[UIApplication sharedApplication] windows] objectAtIndex:0] rootViewController] presentModalViewController:picker animated:YES];
    
    //[[[[UIApplication sharedApplication] keyWindow] rootViewController] presentModalViewController:picker animated:YES];

}
- (void)peoplePickerNavigationControllerDidCancel:(ABPeoplePickerNavigationController *)peoplePicker {
    // assigning control back to the main controller
    /* includes ios6 function and older for ios5 */
    if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissViewControllerAnimated:completion:)])
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissViewControllerAnimated:(YES) completion:nil];
    else if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissModalViewControllerAnimated:)])
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissModalViewControllerAnimated:YES];
}

- (BOOL)peoplePickerNavigationController:(ABPeoplePickerNavigationController *)peoplePicker shouldContinueAfterSelectingPerson:(ABRecordRef)person {
    int personId = (int)ABRecordGetRecordID(person);
    DLog(@"Got person with id: %i",personId);
    NSString * s = [NSString stringWithFormat:@"%i", personId];
    FREDispatchStatusEventAsync(context, (uint8_t*)[event_name UTF8String], (uint8_t*) (uint8_t*)[s UTF8String]);
    /* includes ios6 function and older for ios5 */
    if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissViewControllerAnimated:completion:)])
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissViewControllerAnimated:(YES) completion:nil];
    else if([[[[UIApplication sharedApplication] keyWindow] rootViewController] respondsToSelector:@selector(dismissModalViewControllerAnimated:)])
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

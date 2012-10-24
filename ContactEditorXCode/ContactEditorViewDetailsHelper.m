//
//  ContactEditorViewDetailsHelper.m
//
//  Created by Paweł Meller, using NativeMail from P.Kościerzyński as help :)
//

#import "ContactEditorViewDetailsHelper.h"

@implementation ContactEditorViewDetailsHelper

static  NSString *event_name = @"contactNotFound";


-(void) showContactDetailsInWindow:(ABRecordRef)person
                      isEditable:(BOOL)isEditable 
{

    DLog(@"contact details native window begin");
    ABPersonViewController *picker = [[ABPersonViewController alloc] init];
    if(person)
    {
        picker.allowsEditing = isEditable;
        picker.personViewDelegate = self;
        picker.displayedPerson = person;
        UINavigationController *navController = [[[UINavigationController alloc] initWithRootViewController:picker] autorelease];
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] presentViewController:navController animated:YES  completion:nil];
        picker.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"Back",nil) style:UIBarButtonItemStylePlain target:self action:@selector(ReturnFromPersonView)];
    }
    [picker release];

}
- (void)ReturnFromPersonView{
    [[[[UIApplication sharedApplication] keyWindow] rootViewController] dismissViewControllerAnimated:YES  completion:nil];
    [[NSNotificationCenter defaultCenter] postNotificationName:UIDeviceOrientationDidChangeNotification object:nil];
}
- (BOOL)personViewController:(ABPersonViewController *)personViewController shouldPerformDefaultActionForPerson:(ABRecordRef)person property:(ABPropertyID)property identifier:(ABMultiValueIdentifier)identifier
{
    return YES;
}
-(void)setContext:(FREContext)ctx {
    context = ctx;
}


@end

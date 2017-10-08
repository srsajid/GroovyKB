package mailchimp.method.v2_0.lists

import mailchimp.MailChimpAPIVersion
import mailchimp.MailChimpMethod
import mailchimp.anotation.Field
import mailchimp.anotation.Method

@Method(name = "lists/merge-vars", version = MailChimpAPIVersion.v2_0)
class MergeVarListMethod extends MailChimpMethod {

    @Field
    public String[] id;

}

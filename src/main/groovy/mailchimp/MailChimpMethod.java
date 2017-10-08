/*
 * Copyright 2012 Ecwid, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mailchimp;


import mailchimp.anotation.Field;
import mailchimp.anotation.Method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public abstract class MailChimpMethod<R>  implements Payload{

	@Field
	public String apikey;
	
	/**
	 * Get the MailChimp API method meta-info.
	 *
	 * @throws IllegalArgumentException if neither this class nor any of its superclasses
	 * are annotated with {@link Method}.
	 */
	public final Method getMetaInfo() {
		for(Class<?> c = getClass(); c != null; c = c.getSuperclass()) {
			Method a = c.getAnnotation(Method.class);
			if(a != null) {
				return a;
			}
		}
		
		throw new IllegalArgumentException("Neither "+getClass()+" nor its superclasses are annotated with "+Method.class);
	}
	
	/**
	 * Get the method result type.
	 */
}

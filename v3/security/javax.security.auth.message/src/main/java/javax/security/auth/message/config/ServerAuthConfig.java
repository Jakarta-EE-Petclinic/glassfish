/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.security.auth.message.config;

import java.util.Map;
import javax.security.auth.Subject;

import javax.security.auth.message.*;

/**
 * This interface describes a configuration of ServerAuthConfiguration objects
 * for a message layer and application context (for example, the messaging context of 
 * a specific application, or set of applications). 
 *
 * <p> Implementations of this interface are returned by an AnthConfigProvider.
 *
 * <p> Callers interact with a ServerAuthConfig to obtain ServerAuthContext
 * objects suitable for processing a given message exchange at the layer and
 * within the application context of the ServerAuthConfig.
 *
 * Each ServerAuthContext object is responsible for instantiating, 
 * initializing, and invoking the one or more ServerAuthModules 
 * encapsulated in the ServerAuthContext.
 *
 * <p> After having acquired a ServerAuthContext, a caller operates on the
 * context to cause it to invoke the encapsulated ServerAuthModules to
 * validate service requests and to secure service responses.
 *
 * @version %I%, %G%
 * @see AuthConfigProvider
 */
public interface ServerAuthConfig extends AuthConfig {

    /**
     * Get a ServerAuthContext instance from this ServerAuthConfig.
     *
     * <p> The implementation of this method returns a ServerAuthContext
     * instance that encapsulates the ServerAuthModules used to
     * validate requests and secure responses associated
     * with the given <i>authContextID</i>.
     *
     * <p> Specifically, this method accesses this ServerAuthConfig
     * object with the argument <i>authContextID</i> to determine the
     * ServerAuthModules that are to be encapsulated in the returned
     * ServerAuthContext instance.
     * 
     * <P> The ServerAuthConfig object establishes the request 
     * and response MessagePolicy objects that are passed to the encapsulated 
     * modules when they are initialized by the returned ServerAuthContext 
     * instance. It is the modules' responsibility to enforce these policies 
     * when invoked.
     * 
     * @param authContextID An identifier used to index
     *		the provided <i>config</i>, or null.
     *		This value must be identical to the value returned by
     *		the <code>getAuthContextID</code> method for all
     *		<code>MessageInfo</code> objects passed to the
     *		<code>validateRequest</code>
     *		method of the returned ServerAuthContext.
     *
     * @param serviceSubject A Subject that represents the source of the 
     *          service response to be secured by the acquired authentication
     *          context. The principal and credentials of
     *          the Subject may be used to select or acquire the 
     *          authentication context. If the Subject is not null, 
     *          additional Principals or credentials (pertaining to the source 
     *          of the response) may be added to the Subject. A null value may
     *          be passed for this parameter.
     *
     * @param properties A Map object that may be used by
     *          the caller to augment the properties that will be passed 
     *          to the encapsulated modules at module initialization.
     *          The null value may be passed for this parameter.
     *
     * @return A ServerAuthContext instance that encapsulates the
     *		ServerAuthModules used to secure and validate
     *		requests/responses associated with the given 
     *          <i>authContextID</i>,
     *		or null (indicating that no modules are configured).
     *
     * @exception AuthException If this method fails.
     */
    public abstract ServerAuthContext 
    getAuthContext(String authContextID, Subject serviceSubject, Map properties) 
	throws AuthException;


}


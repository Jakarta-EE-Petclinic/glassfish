/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2010 Sun Microsystems, Inc. All rights reserved.
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

package com.sun.enterprise.tools.verifier.tests.ejb.runtime.resource;

import com.sun.enterprise.deployment.EjbDescriptor;
import com.sun.enterprise.tools.verifier.tests.ComponentNameConstructor;
import com.sun.enterprise.tools.verifier.Result;

/** ejb [0,n]
 *   resource-env-ref [0,n]
 *       resource-env-ref-name [String]
 *       jndi-name [String]
 *
 * The jndi-name tag specifies the jndi name to which the resource environment reference
 * name is binded.
 *
 * The value of this elemnet should not be null 
 *
 * @author Irfan Ahmed
 */

public class ASEjbREnvRefJndiName extends ASEjbResEnvRef {
    public Result check(EjbDescriptor descriptor)
    {
        result = getInitializedResult();
        compName = getVerifierContext().getComponentNameConstructor();
        try
        {
            int ejbResEnvReference = getCountNodeSet("/sun-ejb-jar/enterprise-beans/ejb[ejb-name=\""+descriptor.getName()+"\"]/resource-env-ref");
            if (ejbResEnvReference>0)
            {
                for (int i=1;i<=ejbResEnvReference;i++)
                {
                    String refName = getXPathValue("/sun-ejb-jar/enterprise-beans/ejb[ejb-name=\""+descriptor.getName()+"\"]/resource-env-ref["+i+"]/resource-env-ref-name");
                    String refJndiName = getXPathValue("/sun-ejb-jar/enterprise-beans/ejb[ejb-name=\""+descriptor.getName()+"\"]/resource-env-ref["+i+"]/jndi-name");
                    /* Bug: 4954967. JNDI name does not have any restriction of starting with "jms/" 
                       only checking for null is sufficient for this test. */    
                    if(refJndiName == null || refJndiName.equals("")) {
                        result.failed(smh.getLocalString
                                ("tests.componentNameConstructor",
                                        "For [ {0} ]",
                                        new Object[] {compName.toString()}));
                        result.failed(smh.getLocalString(getClass().getName()+".failed",
                            "FAILED [AS-EJB res-env-ref] : res-ref with res-ref-name {0} is not defined in the ejb-jar.xml",
                            new Object[]{refName}));
                    } else {
                        result.passed(smh.getLocalString
                                ("tests.componentNameConstructor",
                                        "For [ {0} ]",
                                        new Object[] {compName.toString()}));
                        result.passed(smh.getLocalString(getClass().getName()+".passed",
                            "PASSED [AS-EJB res-env-ref] : jndi-name {0} is valid", new Object[]{refJndiName}));
                    }
                }
            }else
            {
                addNaDetails(result, compName);
                result.notApplicable(smh.getLocalString
                         (getClass().getName() + ".notApplicable",
                                 "NOT-APPLICABLE: {0} Does not define any resource-env-ref Elements",
                                 new Object[] {descriptor.getName()}));
            }
        }catch(Exception ex)
        {
            result.addErrorDetails(smh.getLocalString
                    ("tests.componentNameConstructor",
                            "For [ {0} ]",
                            new Object[] {compName.toString()}));
            result.failed(smh.getLocalString(getClass().getName()+".notRun",
                    "NOT RUN [AS-EJB] Could not create descriptor Object."));

        }
        return result;
    }
}

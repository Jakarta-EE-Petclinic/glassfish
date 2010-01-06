/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
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

package org.glassfish.web.valve;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;

/**
 * Adapter valve for wrapping a GlassFish-style valve that was compiled
 * against the "old" org.apache.catalina.Valve interface from GlassFish
 * releases prior to V3 (which has been renamed to
 * org.glassfish.web.valve.GlassFishValve in GlassFish V3).
 *
 * @author jluehe
 */
public class GlassFishValveAdapter implements GlassFishValve {

    // The wrapped GlassFish-style valve to which to delegate
    private Valve gfValve;

    private Method invokeMethod;
    private Method postInvokeMethod;

    /**
     * Constructor.
     *
     * @param gfValve The GlassFish valve to which to delegate
     */
    public GlassFishValveAdapter(Valve gfValve) throws Exception {
        this.gfValve = gfValve;
        invokeMethod = gfValve.getClass().getMethod("invoke", Request.class,
                                                    Response.class);
        postInvokeMethod = gfValve.getClass().getMethod("postInvoke",
                                                        Request.class,
                                                        Response.class);
    }

    public String getInfo() {
        return gfValve.getInfo();
    }

    /**
     * Delegates to the invoke() of the wrapped GlassFish-style valve.
     */
    public int invoke(Request request,
                      Response response)
                throws IOException, ServletException {
        try {
            return ((Integer) invokeMethod.invoke(gfValve, request, response)).intValue();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Delegates to the postInvoke() of the wrapped GlassFish-style valve.
     */
    public void postInvoke(Request request, Response response)
                throws IOException, ServletException {
        try {
            postInvokeMethod.invoke(gfValve, request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

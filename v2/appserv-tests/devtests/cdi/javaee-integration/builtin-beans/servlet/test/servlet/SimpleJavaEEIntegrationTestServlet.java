/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Sun Microsystems, Inc. All rights reserved.
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
package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import test.beans.TestBeanInterface;
import test.beans.artifacts.Preferred;
import test.beans.artifacts.TestDatabase;

@WebServlet(name = "mytest", urlPatterns = { "/myurl" })
public class SimpleJavaEEIntegrationTestServlet extends HttpServlet {
    @Inject @Preferred
    TestBeanInterface tb;

    @Inject @TestDatabase
    DataSource ds;

    // Inject the built-in beans
    @Inject UserTransaction ut;
    @Inject Principal principal;
    @Inject Validator validator;
    @Inject ValidatorFactory vf;

    public void service(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        PrintWriter writer = res.getWriter();
        writer.write("Hello from Servlet 3.0.");
        String msg = "";

        if (ds == null)
            msg += "typesafe Injection of datasource into a servlet failed";

        if (tb == null)
            msg += "Injection of request scoped bean failed";

        if (tb.testDatasourceInjection().trim().length() != 0)
            msg += tb.testDatasourceInjection();

        System.out.println("UserTransaction: " + ut);
        System.out.println("Principal: " + principal);
        System.out.println("Default Validator: " + validator);
        System.out.println("Default ValidatorFactory: " + vf);
        if (ut == null)
            msg += "UserTransaction not available for injection "
                    + "with the default qualifier in Servlet";

        if (principal == null)
            msg += "Caller Principal not available for injection "
                    + "with the default qualifier in Servlet";

        if (validator == null)
            msg += "Default Validator not available for injection "
                    + "with the default qualifier in Servlet";

        if (vf == null)
            msg += "ValidationFactory not available for injection "
                    + "with the default qualifier in Servlet";

        writer.write(msg + "\n");
    }

}

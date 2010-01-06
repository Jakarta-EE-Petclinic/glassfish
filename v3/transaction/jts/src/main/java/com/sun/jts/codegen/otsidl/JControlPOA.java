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

/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package com.sun.jts.codegen.otsidl;


/**
* com/sun/jts/codegen/otsidl/JControlPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/sun/jts/ots.idl
* Tuesday, February 5, 2002 12:57:23 PM PST
*/


//#-----------------------------------------------------------------------------
public abstract class JControlPOA extends org.omg.PortableServer.Servant
 implements com.sun.jts.codegen.otsidl.JControlOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getGlobalTID", new java.lang.Integer (0));
    _methods.put ("getLocalTID", new java.lang.Integer (1));
    _methods.put ("getTranState", new java.lang.Integer (2));
    _methods.put ("setTranState", new java.lang.Integer (3));
    _methods.put ("get_terminator", new java.lang.Integer (4));
    _methods.put ("get_coordinator", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // otsidl/JControl/getGlobalTID
       {
         org.omg.CosTransactions.StatusHolder status = new org.omg.CosTransactions.StatusHolder ();
         org.omg.CosTransactions.otid_t $result = null;
         $result = this.getGlobalTID (status);
         out = $rh.createReply();
         org.omg.CosTransactions.otid_tHelper.write (out, $result);
         org.omg.CosTransactions.StatusHelper.write (out, status.value);
         break;
       }


  // transaction, and a value that indicates the state of the transaction.
       case 1:  // otsidl/JControl/getLocalTID
       {
         org.omg.CosTransactions.StatusHolder status = new org.omg.CosTransactions.StatusHolder ();
         long $result = (long)0;
         $result = this.getLocalTID (status);
         out = $rh.createReply();
         out.write_longlong ($result);
         org.omg.CosTransactions.StatusHelper.write (out, status.value);
         break;
       }


  // value that indicates the state of the transaction.
       case 2:  // otsidl/JControl/getTranState
       {
         org.omg.CosTransactions.Status $result = null;
         $result = this.getTranState ();
         out = $rh.createReply();
         org.omg.CosTransactions.StatusHelper.write (out, $result);
         break;
       }


  // Returns the state of the transaction as the Control object knows it.
       case 3:  // otsidl/JControl/setTranState
       {
         org.omg.CosTransactions.Status state = org.omg.CosTransactions.StatusHelper.read (in);
         this.setTranState (state);
         out = $rh.createReply();
         break;
       }

       case 4:  // CosTransactions/Control/get_terminator
       {
         try {
           org.omg.CosTransactions.Terminator $result = null;
           $result = this.get_terminator ();
           out = $rh.createReply();
           org.omg.CosTransactions.TerminatorHelper.write (out, $result);
         } catch (org.omg.CosTransactions.Unavailable $ex) {
           out = $rh.createExceptionReply ();
           org.omg.CosTransactions.UnavailableHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // CosTransactions/Control/get_coordinator
       {
         try {
           org.omg.CosTransactions.Coordinator $result = null;
           $result = this.get_coordinator ();
           out = $rh.createReply();
           org.omg.CosTransactions.CoordinatorHelper.write (out, $result);
         } catch (org.omg.CosTransactions.Unavailable $ex) {
           out = $rh.createExceptionReply ();
           org.omg.CosTransactions.UnavailableHelper.write (out, $ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:otsidl/JControl:1.0", 
    "IDL:omg.org/CosTransactions/Control:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public JControl _this() 
  {
    return JControlHelper.narrow(
    super._this_object());
  }

  public JControl _this(org.omg.CORBA.ORB orb) 
  {
    return JControlHelper.narrow(
    super._this_object(orb));
  }


} // class JControlPOA

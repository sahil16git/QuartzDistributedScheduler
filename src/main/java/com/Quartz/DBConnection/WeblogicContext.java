package com.Quartz.DBConnection;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WeblogicContext {

	public static InitialContext getInitialContext() throws NamingException {
		// Get an InitialContext
		Hashtable h = new Hashtable();
		h.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		h.put(Context.PROVIDER_URL, "t3://localhost:7001");
		h.put(Context.SECURITY_PRINCIPAL, "weblogic");
		h.put(Context.SECURITY_CREDENTIALS, "weblogic123");
		return new InitialContext(h);
	}
}

package org.books.presentation.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(CatalogResource.class);
		classes.add(CustomersResource.class);
		classes.add(OrdersResource.class);
		return classes;
	}
}

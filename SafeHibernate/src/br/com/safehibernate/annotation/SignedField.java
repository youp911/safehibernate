package br.com.safehibernate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.safehibernate.provider.SignatureProvider;

//Define a pol�tica de renten��o desta anota��o.
//Definindo como Runtime esta anota��o ser� mantida pela JVM
//durante a execu��o do programa, permitindo que seja obtida
//via reflex�o.
@Retention (RetentionPolicy.RUNTIME)

//Define que esta anota��o s� pode ser utilizada em campos de classes
@Target (ElementType.FIELD)

@Documented
public @interface SignedField {

	Class<? extends SignatureProvider> provider() default SignatureProvider.class;
	
	String storeOnField() default "this";
}

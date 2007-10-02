package br.com.safehibernate.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Define a política de rentenção desta anotação.
//Definindo como Runtime esta anotação será mantida pela JVM
//durante a execução do programa, permitindo que seja obtida
//via reflexão.
@Retention (RetentionPolicy.RUNTIME)

//Define que esta anotação só pode ser utilizada em campos de classes
@Target (ElementType.FIELD)

@Documented
public @interface EncryptedField {

}

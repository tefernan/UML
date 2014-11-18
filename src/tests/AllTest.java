package tests;

import tests.TestClase;
import tests.TestConnectionClase;
import tests.TestUmlClase;

import tests.TestActorCaso;
import tests.TestConnectionCaso;
import tests.TestUmlCaso;
import tests.TestUsecaseCaso;

import tests.TestComentario;

import org.junit.runner.RunWith;  
import org.junit.runners.Suite.SuiteClasses;  
import org.junit.runners.Suite;  
  
@RunWith
(Suite.class) 

@SuiteClasses
({TestClase.class, TestConnectionClase.class, TestUmlClase.class, TestActorCaso.class, TestConnectionCaso.class, TestUmlCaso.class, TestUsecaseCaso.class, TestComentario.class})  

//clase para ejecutar todos los test de forma conjunta.
public class AllTest {
	
}

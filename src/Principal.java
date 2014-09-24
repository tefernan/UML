import java.io.IOException;
import java.util.*;

public class Principal {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		UserCaseCreator uc = new UserCaseCreator("usercase.xml");
		UmlUso UML = uc.parseXML();
		
		System.out.println("Nombre diagrama: "+  UML.getNombreDiagrama());
		
		for(UsecaseUso a: UML.getListaCasos()){
			System.out.println(a.getName());
		}
		
		UML.ordenarUserCases();
		//UML.OrdenarActores();
		
		CrearUC a= new CrearUC(UML.getNombreDiagrama(),UML);
		a.CrearUsers();
		a.CrearCasos();
		a.dibujarFlecha();
		a.Finalizar();

	}
	

}

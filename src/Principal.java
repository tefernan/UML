import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;

public class Principal {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Elija el archivo");
		
		//Elegir el archivo
		JFileChooser fc = new JFileChooser();
		File archivo = null;
        if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)){
            archivo= fc.getSelectedFile();
        }
        else {
            System.out.println("No selecciono archivo");
            System.exit(1);
        }

  
		UserCaseCreator uc = new UserCaseCreator(archivo);
		UmlUso UML = uc.parseXML();
		
		UML.ordenarUserCases();
		UML.OrdenarActores();
		
		
		CrearUC diag= new CrearUC(UML.getNombreDiagrama(),UML);
	
		
		diag.CrearUsers();
		diag.CrearCasos();
		diag.CrearConexiones();
		diag.Finalizar();
		

	}
	

}

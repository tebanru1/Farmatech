
package modelo;

import org.junit.Test;
import Controlador.Usuario;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class usuarioTest {
    @Test 
  public void ValidarUsuario_CredencialesCorrectas(){
      Usuario usuario=new Usuario();
      boolean reasultado=usuario.validarUsuario("juanp", "clave123");
        assertTrue(reasultado);
    }  
      @Test 
  public void ValidarUsuario_CredencialesIncorrectas(){
      Usuario usuario=new Usuario();
      boolean reasultado=usuario.validarUsuario("juanp", "clave124");
        assertFalse(reasultado);
    }  
    
}

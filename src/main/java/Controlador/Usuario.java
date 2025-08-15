
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario {
    conexion con=new conexion();
        
    public boolean validarUsuario(String usuario, String contraseña){
        String sql="SELECT * FROM usuarios WHERE usuario= ? AND contraseña =?";
        try (Connection cn = con.conectar();
             PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setString(1, usuario);
            ps.setString(2,contraseña);
            ResultSet rs=ps.executeQuery();
            return rs.next();
        }catch (Exception e){
            System.out.print("Error"+ e);
            return false;
        }
         
    }
}

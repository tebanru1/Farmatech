package Controlador;
import Modelo.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
  
public login log(String usuario,String contraseña){
    login l=new login();
    String sql="SELECT * FROM usuarios WHERE usuario=? AND contraseña=?";
    try {
        con=cn.conectar();
        ps=con.prepareStatement(sql);
        ps.setString(1,usuario);
        ps.setString(2,contraseña);
        rs=ps.executeQuery();
        if(rs.next()){
            l.setId(rs.getInt("id"));
            l.setNombre(rs.getString("nombre"));
            l.setUsuario(rs.getString("usuario"));
            l.setContraseña(rs.getString("contraseña"));
            l.setRol(rs.getString("rol"));
        }

    } catch (SQLException e) {
        System.out.println(e.toString());
    }  return l;
}
public boolean RegistrarUsuario(login reg){
    String sql="INSERT INTO usuarios (nombre,usuario,contraseña,rol) VALUES (?,?,?,?)";
    try {
        con=cn.conectar();
        ps=con.prepareStatement(sql);
        ps.setString(1,reg.getNombre());
        ps.setString(2,reg.getUsuario());
        ps.setString(3,reg.getContraseña());
        ps.setString(4,reg.getRol());
        ps.execute();
        return true; 
    } catch (SQLException e) {
        System.out.println(e.toString());
        return false;
    }
}
}

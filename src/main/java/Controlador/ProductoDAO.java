package Controlador;
import Modelo.Config;
import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class ProductoDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
 public int RegistrarProducto(Producto prod) {
    int idgenerado = -1; // valor por defecto si falla
    String sql = "INSERT INTO productos(nombre, descripcion, precio, stock, id_proveedor, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1, prod.getNombre());
        ps.setString(2, prod.getDescripcion());
        ps.setFloat(3, prod.getPrecio());
        ps.setInt(4, prod.getStock());
        ps.setInt(5, prod.getId_proveedor());
        ps.setInt(6, prod.getId_Categoria());
        ps.executeUpdate(); 
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            idgenerado = rs.getInt(1);
        }
    } catch (SQLException e) {
        System.out.println("Error al registrar producto: " + e.toString());
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            System.out.println("Error al cerrar recursos: " + ex.toString());
        }
    }
    return idgenerado;
}

    public void NombreProveedor(JComboBox proveedor){
        String sql="SELECT id,nombre FROM proveedores";
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            proveedor.removeAllItems(); 
            proveedor.addItem(new Proveedor(0,"Seleccione un proveedor"));
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                proveedor.addItem(new Proveedor(id, nombre));
                }
            }catch(SQLException e){
                    System.out.println(e.toString());
                    }finally{
                            try{
                            ps.close();
                            rs.close();
                            }catch(SQLException ex){
                            System.out.println(ex.toString());
                            }
                            }
        }
    public void Categoria(JComboBox cate){
        String sql="SELECT id,nombre FROM categorias";
        
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            cate.removeAllItems(); 
            cate.addItem(new categoria(0,"Seleccione una categoria"));
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                cate.addItem(new categoria(id, nombre));
                }
            }catch(SQLException e){
                    System.out.println(e.toString());
                    }finally{
                            try{
                            ps.close();
                            rs.close();
                            }catch(SQLException ex){
                            System.out.println(ex.toString());
                            }
                            }
        }
    public List ListarProductos(){
        List<Producto> ListaPro =new ArrayList();
        String sql="SELECT * FROM productos";
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                  Producto prod=new Producto();
                  prod.setId(rs.getInt("id"));
                  prod.setNombre(rs.getString("nombre"));
                  prod.setDescripcion(rs.getString("descripcion"));
                  prod.setPrecio(rs.getFloat("precio"));
                  prod.setStock(rs.getInt("stock"));
                  prod.setId_proveedor(rs.getShort("id_proveedor"));
                  prod.setId_Categoria(rs.getShort("id_categoria"));
                  ListaPro.add(prod);
          
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListaPro;
    }
    public boolean ActualizarProducto(Producto prod){
        String sql="UPDATE productos SET nombre=?, descripcion=?, precio=?, stock=?, id_categoria=?, id_proveedor=? WHERE ID=?";
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setString(1, prod.getNombre());
            ps.setString(2, prod.getDescripcion());
            ps.setFloat(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getId_Categoria());
            ps.setInt(6, prod.getId_proveedor());
            ps.setInt(7, prod.getId());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
            }finally{
            try{
                ps.close();
            }catch(SQLException ex){
                System.out.println(ex.toString());   
            }
        }
    }
    public boolean EliminarProducto(Producto prod){
        String sqlLotes = "DELETE FROM lotes WHERE id_producto = ?";
        String sqlProd = "DELETE FROM productos WHERE id = ?";
        try{
            con = cn.conectar();

        
            ps = con.prepareStatement(sqlLotes);
            ps.setInt(1, prod.getId());
            ps.executeUpdate();


            ps = con.prepareStatement(sqlProd);
            ps.setInt(1, prod.getId());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try{
                ps.close();
            }catch(SQLException ex){
                System.out.println(ex.toString());
            }
        }
    }
    public Producto BuscarPro(int id){
        Producto product=new Producto();
        String sql="SELECT * FROM productos WHERE id=?";
        try {
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            if (rs.next()){
                product.setNombre(rs.getString("nombre"));
                product.setPrecio(rs.getFloat("precio"));
                product.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }return product;
   }
    public Config BuscarDatos(){
        Config conf=new Config();
        String sql="SELECT * FROM empresa";
        try {
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if (rs.next()){
                conf.setId(rs.getInt("id"));
                conf.setNit(rs.getInt("nit"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTel(rs.getInt("tel"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setCorreo(rs.getString("correo"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }return conf;
   }
    public boolean ActualizarConfig(Config conf){
        String sql="UPDATE empresa SET nit=?,nombre=?, tel=?, direccion=?, correo=? WHERE id=?";
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setInt(1, conf.getNit());
            ps.setString(2, conf.getNombre());
            ps.setInt(3, conf.getTel());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getCorreo());
            ps.setInt(6, conf.getId());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
            }finally{
            try{
                ps.close();
            }catch(SQLException ex){
                System.out.println(ex.toString());   
            }
        }
    }
    public List<Producto> buscarPorNombre(String nombre) {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT * FROM productos WHERE nombre LIKE ?";

    try  {
        con=cn.conectar();
        ps=con.prepareStatement(sql);
        ps.setString(1, nombre + "%"); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setPrecio(rs.getFloat("precio"));
            p.setStock(rs.getInt("stock"));
            p.setId_proveedor(rs.getInt("id_proveedor"));
            p.setId_Categoria(rs.getInt("id_categoria"));
            lista.add(p);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    }
    return lista;
}

}




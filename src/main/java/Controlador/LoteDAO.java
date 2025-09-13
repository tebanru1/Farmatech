
package Controlador;
import Modelo.Lote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoteDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarLote(Lote lote) {
        String sql = "INSERT INTO lotes (id_producto, codigo_lote, fecha_fabricacion, fecha_vencimiento,cantidad,id_proveedor) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, lote.getIdProducto());
            ps.setString(2, lote.getCodigoLote());
            ps.setDate(3, new java.sql.Date(lote.getFechaFabricacion().getTime()));
            ps.setDate(4, new java.sql.Date(lote.getFechaVencimiento().getTime()));
            ps.setInt(5, lote.getCantidad());
            ps.setInt(6, lote.getIdProveedor());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    public Lote obtenerLotePorProducto(int idProducto) {
    String sql = "SELECT * FROM lotes WHERE id_producto = ?";
    Lote lote = null;
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idProducto);
        rs = ps.executeQuery();
        if (rs.next()) {
            lote = new Lote();
            lote.setIdProducto(rs.getInt("id_producto"));
            lote.setCodigoLote(rs.getString("codigo_lote"));
            lote.setFechaFabricacion(rs.getDate("fecha_fabricacion"));
            lote.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
            lote.setCantidad(rs.getInt("cantidad"));
            lote.setIdProveedor(rs.getInt("id_proveedor"));
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener lote: " + e.toString());
    }
    return lote;
}

    
}

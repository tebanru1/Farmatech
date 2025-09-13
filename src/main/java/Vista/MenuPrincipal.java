
package Vista;

import Modelo.Cliente;
import Controlador.ClienteDAO;
import Modelo.Config;
import Controlador.Excel;
import Controlador.LoteDAO;
import Modelo.Producto;
import Controlador.ProductoDAO;
import Modelo.Proveedor;
import Controlador.ProveedorDAO;
import Modelo.Ventas;
import Controlador.VentasDAO;
import Modelo.Lote;
import Modelo.categoria;
import Modelo.detalleVenta;
import Modelo.login;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.general.DefaultPieDataset;

public class MenuPrincipal extends javax.swing.JFrame {
    Cliente cl=new Cliente();
    ClienteDAO client=new ClienteDAO();
    Proveedor pro=new Proveedor();
    ProveedorDAO prove=new ProveedorDAO();
    Producto prod=new Producto();
    ProductoDAO PRO=new ProductoDAO();
    Lote l=new Lote();
    LoteDAO LD=new LoteDAO();
    Config config=new Config();
    Ventas v=new Ventas();
    VentasDAO Vdao=new VentasDAO();
    detalleVenta Dv=new detalleVenta();
    DefaultTableModel modelo=new DefaultTableModel();
    DefaultTableModel mdl=new DefaultTableModel();
    private Lote lote;
    private JPopupMenu popupSugerencias = new JPopupMenu();
     private JPopupMenu listarclientes = new JPopupMenu();
    float TotalPagar = 0;
     int item;
     
    public MenuPrincipal() {
        FlatMaterialLighterIJTheme.setup();
        initComponents();
        setResizable(false);
        calcularTotalPagar();
        PRO.NombreProveedor(cbxId_Proveedor);
        PRO.Categoria(Id_Caracteristicas);
        Estilos();
        Fecha();
        this.setLocationRelativeTo(null);
        ocultarCamposInternos();
        ListarConfig();   
    }
   public MenuPrincipal(login priv){
    initComponents();
    setResizable(false);
    calcularTotalPagar();
    PRO.NombreProveedor(cbxId_Proveedor);
    PRO.Categoria(Id_Caracteristicas);
    Estilos();
    Fecha();
    this.setLocationRelativeTo(null);
    ocultarCamposInternos();
    txtUsuario.setText(priv.getNombre());
    txtRol.setText(priv.getRol());
    aplicarPrivilegios(priv.getRol());
}

   private void ocultarCamposInternos() {
    txtcorreo.setVisible(false);
    txtdireccion.setVisible(false);
    txttelefono.setVisible(false);
    txtIdCliente.setVisible(false);
    txtIdPro.setVisible(false);
    txtIdVenta.setVisible(false);
    txtIdProducto.setVisible(false);
    txtIdProveedor.setVisible(false);
    txtidempresa.setVisible(false);
}
   
    private void aplicarPrivilegios(String rol) {
    switch (rol) {
        case "Administrador":
            break;
        case "Auxiliar de Farmacia":
            btnRegistrar.setEnabled(false);
            btnProveedor.setEnabled(false);
            btnConfiguracion.setEnabled(false);
            btnEditarPro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            break;
        case "Cajero":
            btnRegistrar.setEnabled(false);
            btnProveedor.setEnabled(false);
            btnConfiguracion.setEnabled(false);
            btnEditarPro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            break;
        case "Administrador de Inventarios":
            btnNuevaVenta.setEnabled(false);
            btnClientes.setEnabled(false);
            btnRegistrar.setEnabled(false);
            btnConfiguracion.setEnabled(false);
            break;
        case "Auditor / Inspector":
            btnNuevaVenta.setEnabled(false);
            btnClientes.setEnabled(false);
            btnRegistrar.setEnabled(false);
            btnProveedor.setEnabled(false);
            btnConfiguracion.setEnabled(false);
            btnEditarPro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            break;
        default:
            JOptionPane.showMessageDialog(this, "Rol no reconocido: " + rol);
            dispose();
    }
}

    
    public void ListarCliente(){
        List<Cliente> ListarCl=client.ListarCliente();
        modelo = (DefaultTableModel) tableCliente.getModel();
        Object[] ob=new Object[6];
        for(int i=0;i< ListarCl.size();i++){
            ob[0]=ListarCl.get(i).getId();
            ob[1]=ListarCl.get(i).getCedula();
            ob[2]=ListarCl.get(i).getNombre();
            ob[3]=ListarCl.get(i).getTelefono();
            ob[4]=ListarCl.get(i).getCorreo();
            ob[5]=ListarCl.get(i).getDireccion();  
            modelo.addRow(ob);
        }
        tableCliente.setModel(modelo);
    }
    public void ListarProducto(){
        List<Producto> ListarProd=PRO.ListarProductos();
        modelo = (DefaultTableModel) tableProducto.getModel();
        Object[] ob=new Object[7];
        for(int i=0;i< ListarProd.size();i++){
            ob[0]=ListarProd.get(i).getId();
            ob[1]=ListarProd.get(i).getNombre();
            ob[2]=ListarProd.get(i).getDescripcion();
            ob[3]=ListarProd.get(i).getPrecio();
            ob[4]=ListarProd.get(i).getStock();
            ob[5]=ListarProd.get(i).getId_Categoria();
            ob[6]=ListarProd.get(i).getId_proveedor();
            modelo.addRow(ob);
        }
        tableProducto.setModel(modelo);
    }
    public void ListarVentas(){
        List<Ventas> ListarVent=Vdao.ListarVentas();
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob=new Object[5];
        for(int i=0;i< ListarVent.size();i++){
            ob[0]=ListarVent.get(i).getId();
            ob[1]=ListarVent.get(i).getCliente();
            ob[2]=ListarVent.get(i).getVendedor();
            ob[3]=ListarVent.get(i).getTotal();
            ob[4]=ListarVent.get(i).getFecha();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);
    }
    public void ListarConfig(){
        config=PRO.BuscarDatos();
        txtidempresa.setText(""+config.getId());
        txtNitConfig.setText(""+config.getNit());
        txtNombreConfig.setText(""+config.getNombre());
        txtTelefonoConfig.setText(""+config.getTel());
        txtDireccionConfig.setText(""+config.getDireccion());
        txtCorreoConfig.setText(""+config.getCorreo());
        

    }
    public void ListarProveedores(){
        List<Proveedor> ListarPro=prove.ListarProveedores();
        modelo = (DefaultTableModel) tableProveedor.getModel();
        Object[] ob=new Object[6];
        for(int i=0;i< ListarPro.size();i++){
            ob[0]=ListarPro.get(i).getId();
            ob[1]=ListarPro.get(i).getNit();
            ob[2]=ListarPro.get(i).getNombre();
            ob[3]=ListarPro.get(i).getTelefono();
            ob[4]=ListarPro.get(i).getCorreo();
            ob[5]=ListarPro.get(i).getDireccion();  
            modelo.addRow(ob);
        }
        tableProveedor.setModel(modelo);
    }
    public void LimpiarTable(){
        for(int i = modelo.getRowCount()-1;i>=0;i--){
            modelo.removeRow(i);
            
        }
    }
    private void Estilos(){
        dateText.putClientProperty( "FlatLaf.style", "font: bold $h2.regular.font"); 
        dateText.setForeground(Color.white);
    }
    private void Fecha(){
        LocalDate now=LocalDate.now();
        int year =now.getYear();
        int dia= now.getDayOfMonth();
        int month = now.getMonthValue();
        int diaSemanaIndex = now.getDayOfWeek().getValue() - 1;
        String[] dias={"Lunes","Martes","Miércoles","Jueves","Viernes","Sabadado","Domingo"};
        String[] meses={"Enero","Febrero","Marzo","Abril","Mayo",
        "Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        String fechaFormateada = dias[diaSemanaIndex] + " " + dia + " de " + meses[month - 1] + " del " + year;
        dateText.setText(fechaFormateada);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfiguracion = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        txtUsuario = new javax.swing.JLabel();
        txtRol = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        dateText = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVenta = new javax.swing.JTable();
        btnEliminarVenta = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCedulaVenta = new javax.swing.JTextField();
        txtNombreClienteVenta = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        labelTotal = new javax.swing.JLabel();
        txtTotalPagar = new javax.swing.JLabel();
        txtIdProducto = new javax.swing.JTextField();
        txttelefono = new javax.swing.JLabel();
        txtcorreo = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtCedulaCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtCorreoCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCliente = new javax.swing.JTable();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtNitProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtCorreoProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableProveedor = new javax.swing.JTable();
        btnGuardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtNombrePro = new javax.swing.JTextField();
        txtStockPro = new javax.swing.JTextField();
        txtDescripcionPro = new javax.swing.JTextField();
        btnGuardarPro = new javax.swing.JButton();
        btnEditarPro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtPrecioPro = new javax.swing.JTextField();
        txtIdPro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Id_Caracteristicas = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbxId_Proveedor = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableProducto = new javax.swing.JTable();
        btnExcelPro = new javax.swing.JButton();
        btnFechaVencimiento = new javax.swing.JButton();
        txtLote = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        JDFechaFabricacion = new com.toedter.calendar.JDateChooser();
        JDFechaVencimiento = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        btnGenerarGrafica = new javax.swing.JButton();
        fechainicio = new com.toedter.calendar.JDateChooser();
        fechafinal = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtNitConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtCorreoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        btnActualizarEmpresa = new javax.swing.JButton();
        txtidempresa = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 204));

        btnNuevaVenta.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevaVenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNuevaVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/001-ventas.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnNuevaVenta.setBorderPainted(false);
        btnNuevaVenta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevaVenta.setIconTextGap(10);
        btnNuevaVenta.setMaximumSize(new java.awt.Dimension(151, 44));
        btnNuevaVenta.setMinimumSize(new java.awt.Dimension(151, 44));
        btnNuevaVenta.setPreferredSize(new java.awt.Dimension(80, 30));
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(51, 51, 255));
        btnClientes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnClientes.setBorderPainted(false);
        btnClientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnClientes.setIconTextGap(10);
        btnClientes.setMaximumSize(new java.awt.Dimension(104, 44));
        btnClientes.setMinimumSize(new java.awt.Dimension(104, 44));
        btnClientes.setPreferredSize(new java.awt.Dimension(80, 30));
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedor.setBackground(new java.awt.Color(51, 51, 255));
        btnProveedor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedores");
        btnProveedor.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnProveedor.setBorderPainted(false);
        btnProveedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProveedor.setIconTextGap(10);
        btnProveedor.setMaximumSize(new java.awt.Dimension(130, 44));
        btnProveedor.setMinimumSize(new java.awt.Dimension(130, 44));
        btnProveedor.setPreferredSize(new java.awt.Dimension(80, 30));
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(51, 51, 255));
        btnProductos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/entrega.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnProductos.setBorderPainted(false);
        btnProductos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProductos.setIconTextGap(10);
        btnProductos.setMaximumSize(new java.awt.Dimension(117, 44));
        btnProductos.setMinimumSize(new java.awt.Dimension(117, 44));
        btnProductos.setPreferredSize(new java.awt.Dimension(80, 30));
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
        });
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(51, 51, 255));
        btnVentas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compras.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnVentas.setBorderPainted(false);
        btnVentas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVentas.setIconTextGap(10);
        btnVentas.setMaximumSize(new java.awt.Dimension(97, 44));
        btnVentas.setMinimumSize(new java.awt.Dimension(97, 44));
        btnVentas.setPreferredSize(new java.awt.Dimension(80, 30));
        btnVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVentasMouseClicked(evt);
            }
        });
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfiguracion.setBackground(new java.awt.Color(51, 51, 255));
        btnConfiguracion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnConfiguracion.setForeground(new java.awt.Color(255, 255, 255));
        btnConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/config.png"))); // NOI18N
        btnConfiguracion.setText("Configuración");
        btnConfiguracion.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnConfiguracion.setBorderPainted(false);
        btnConfiguracion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnConfiguracion.setIconTextGap(10);
        btnConfiguracion.setMaximumSize(new java.awt.Dimension(99, 44));
        btnConfiguracion.setMinimumSize(new java.awt.Dimension(99, 44));
        btnConfiguracion.setPreferredSize(new java.awt.Dimension(80, 30));
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LogoFarmaTech.png"))); // NOI18N

        btnRegistrar.setBackground(new java.awt.Color(51, 51, 255));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Clientes.png"))); // NOI18N
        btnRegistrar.setText("Registrar Usuarios");
        btnRegistrar.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRegistrar.setIconTextGap(10);
        btnRegistrar.setMaximumSize(new java.awt.Dimension(104, 44));
        btnRegistrar.setMinimumSize(new java.awt.Dimension(104, 44));
        btnRegistrar.setPreferredSize(new java.awt.Dimension(80, 30));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuario.setText("USUARIO");

        txtRol.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtRol.setForeground(new java.awt.Color(255, 255, 255));
        txtRol.setText("ROL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(txtUsuario)
                .addGap(10, 10, 10)
                .addComponent(txtRol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, -1));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        Titulo.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("PUNTO DE VENTA");

        dateText.setText("---------------------------");

        btnCerrarSesion.setBackground(new java.awt.Color(255, 204, 204));
        btnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesion.png"))); // NOI18N
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                        .addGap(346, 346, 346)
                        .addComponent(btnCerrarSesion)
                        .addGap(48, 48, 48))
                    .addComponent(dateText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCerrarSesion)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateText, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 0, 1036, 150));

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Código");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Descripción");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Cantidad");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Precio");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setText("Stock Disponible");

        txtCodigoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoVentaActionPerformed(evt);
            }
        });
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyReleased(evt);
            }
        });

        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
            }
        });
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
        });

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setBackground(new java.awt.Color(204, 204, 204));

        txtStockDisponible.setEditable(false);
        txtStockDisponible.setBackground(new java.awt.Color(204, 204, 204));

        tableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableVenta.setGridColor(new java.awt.Color(0, 0, 0));
        tableVenta.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        tableVenta.setMinimumSize(new java.awt.Dimension(60, 50));
        tableVenta.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tableVenta);
        if (tableVenta.getColumnModel().getColumnCount() > 0) {
            tableVenta.getColumnModel().getColumn(0).setMinWidth(20);
            tableVenta.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(0).setMaxWidth(100);
            tableVenta.getColumnModel().getColumn(1).setMinWidth(100);
            tableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(2).setMinWidth(10);
            tableVenta.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(2).setMaxWidth(100);
            tableVenta.getColumnModel().getColumn(3).setMinWidth(10);
            tableVenta.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(3).setMaxWidth(100);
            tableVenta.getColumnModel().getColumn(4).setMinWidth(20);
            tableVenta.getColumnModel().getColumn(4).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/basura (1).png"))); // NOI18N
        btnEliminarVenta.setIconTextGap(0);
        btnEliminarVenta.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("CÉDULA:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("NOMBRE:");

        txtCedulaVenta.setMinimumSize(new java.awt.Dimension(64, 50));
        txtCedulaVenta.setPreferredSize(new java.awt.Dimension(64, 30));
        txtCedulaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaVentaActionPerformed(evt);
            }
        });
        txtCedulaVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaVentaKeyPressed(evt);
            }
        });

        txtNombreClienteVenta.setMinimumSize(new java.awt.Dimension(64, 50));
        txtNombreClienteVenta.setPreferredSize(new java.awt.Dimension(64, 30));
        txtNombreClienteVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreClienteVentaKeyReleased(evt);
            }
        });

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/impresora.png"))); // NOI18N
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        labelTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/peso.png"))); // NOI18N
        labelTotal.setText("TOTAL A PAGAR");

        txtTotalPagar.setText("jLabel11");

        txtIdProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductoActionPerformed(evt);
            }
        });

        txttelefono.setText("jLabel11");

        txtcorreo.setText("jLabel22");

        txtdireccion.setText("jLabel26");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDescripcionVenta))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtStockDisponible))
                        .addGap(121, 121, 121)
                        .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarVenta)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                .addGap(101, 101, 101))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(txtCedulaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addGap(237, 237, 237))
                            .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnGenerarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                        .addGap(148, 148, 148)
                        .addComponent(labelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                        .addGap(42, 42, 42)
                        .addComponent(txtTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEliminarVenta, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTotal)
                            .addComponent(txtTotalPagar))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCedulaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelefono)
                            .addComponent(txtcorreo)
                            .addComponent(txtdireccion)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))))
        );

        jTabbedPane2.addTab("tab1", jPanel4);

        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel12.setText("Cédula:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel13.setText("Nombre:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel14.setText("Teléfono:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel15.setText("Correo:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel16.setText("Dirección:");

        txtCedulaCliente.setPreferredSize(new java.awt.Dimension(64, 30));

        txtNombreCliente.setPreferredSize(new java.awt.Dimension(64, 30));

        txtTelefonoCliente.setPreferredSize(new java.awt.Dimension(64, 30));

        txtCorreoCliente.setPreferredSize(new java.awt.Dimension(64, 30));

        txtDireccionCliente.setPreferredSize(new java.awt.Dimension(64, 30));

        tableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÉDULA", "NOMBRE", "TELÉFONO", "CORREO", "DIRECCION"
            }
        ));
        tableCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableCliente);
        if (tableCliente.getColumnModel().getColumnCount() > 0) {
            tableCliente.getColumnModel().getColumn(0).setMinWidth(1);
            tableCliente.getColumnModel().getColumn(0).setPreferredWidth(1);
            tableCliente.getColumnModel().getColumn(1).setPreferredWidth(30);
            tableCliente.getColumnModel().getColumn(2).setPreferredWidth(50);
            tableCliente.getColumnModel().getColumn(3).setPreferredWidth(30);
            tableCliente.getColumnModel().getColumn(4).setPreferredWidth(120);
            tableCliente.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        btnGuardarCliente.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.setPreferredSize(new java.awt.Dimension(40, 40));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.setPreferredSize(new java.awt.Dimension(40, 40));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.setPreferredSize(new java.awt.Dimension(40, 40));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.setPreferredSize(new java.awt.Dimension(40, 40));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnGuardarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(btnEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(btnEliminarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(btnNuevoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccionCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCorreoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtCedulaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                                .addGap(20, 20, 20)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab2", jPanel5);

        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel17.setText("Nit:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel18.setText("Nombre:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel19.setText("Teléfono:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel20.setText("Correo:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel21.setText("Dirección:");

        txtNitProveedor.setPreferredSize(null);

        txtNombreProveedor.setOpaque(true);
        txtNombreProveedor.setPreferredSize(null);

        txtTelefonoProveedor.setOpaque(true);
        txtTelefonoProveedor.setPreferredSize(null);

        txtCorreoProveedor.setOpaque(true);
        txtCorreoProveedor.setPreferredSize(null);

        txtDireccionProveedor.setOpaque(true);
        txtDireccionProveedor.setPreferredSize(null);

        tableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NIT", "NOMBRE", "TELÉFONO", "CORREO", "DIRECCION"
            }
        ));
        tableProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableProveedor);
        if (tableProveedor.getColumnModel().getColumnCount() > 0) {
            tableProveedor.getColumnModel().getColumn(0).setMinWidth(2);
            tableProveedor.getColumnModel().getColumn(0).setPreferredWidth(2);
            tableProveedor.getColumnModel().getColumn(1).setMinWidth(10);
            tableProveedor.getColumnModel().getColumn(1).setPreferredWidth(10);
            tableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableProveedor.getColumnModel().getColumn(3).setPreferredWidth(30);
            tableProveedor.getColumnModel().getColumn(4).setPreferredWidth(60);
            tableProveedor.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        btnGuardarProveedor.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProveedor.setPreferredSize(new java.awt.Dimension(40, 40));
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarProveedor.setPreferredSize(new java.awt.Dimension(40, 40));
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.setPreferredSize(new java.awt.Dimension(40, 40));
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        btnNuevoProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProveedor.setPreferredSize(new java.awt.Dimension(40, 40));
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCorreoProveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addComponent(txtNitProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(109, 109, 109))
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnGuardarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(btnEditarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNitProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab3", jPanel6);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel23.setText("Nombre:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel24.setText("Stock:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel25.setText("Descripción:");

        txtStockPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockProActionPerformed(evt);
            }
        });

        btnGuardarPro.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnGuardarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarPro.setPreferredSize(new java.awt.Dimension(40, 40));
        btnGuardarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProActionPerformed(evt);
            }
        });

        btnEditarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarPro.setPreferredSize(new java.awt.Dimension(40, 40));
        btnEditarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProActionPerformed(evt);
            }
        });

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarPro.setPreferredSize(new java.awt.Dimension(40, 40));
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });

        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        btnNuevoPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoPro.setPreferredSize(new java.awt.Dimension(40, 40));
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel27.setText("Precio:");

        txtIdPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setText("Proveedor:");

        Id_Caracteristicas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Id_Caracteristicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Id_CaracteristicasActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setText("Categoria:");

        cbxId_Proveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxId_Proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxId_ProveedorActionPerformed(evt);
            }
        });

        tableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "DESCRIPCIÓN", "PRECIO", "STOCK", "CATEGORIA", "PROVEEDOR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductoMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableProducto);
        if (tableProducto.getColumnModel().getColumnCount() > 0) {
            tableProducto.getColumnModel().getColumn(0).setMinWidth(1);
            tableProducto.getColumnModel().getColumn(0).setPreferredWidth(2);
            tableProducto.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableProducto.getColumnModel().getColumn(2).setMinWidth(1);
            tableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableProducto.getColumnModel().getColumn(3).setMinWidth(1);
            tableProducto.getColumnModel().getColumn(3).setPreferredWidth(10);
            tableProducto.getColumnModel().getColumn(4).setMinWidth(1);
            tableProducto.getColumnModel().getColumn(4).setPreferredWidth(2);
            tableProducto.getColumnModel().getColumn(5).setMinWidth(1);
            tableProducto.getColumnModel().getColumn(5).setPreferredWidth(2);
            tableProducto.getColumnModel().getColumn(6).setMinWidth(1);
            tableProducto.getColumnModel().getColumn(6).setPreferredWidth(2);
        }

        btnExcelPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/excel.png"))); // NOI18N
        btnExcelPro.setPreferredSize(new java.awt.Dimension(40, 40));
        btnExcelPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProActionPerformed(evt);
            }
        });

        btnFechaVencimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fecha-de-vencimiento.png"))); // NOI18N
        btnFechaVencimiento.setPreferredSize(new java.awt.Dimension(40, 40));
        btnFechaVencimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFechaVencimientoActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel34.setText("Lote:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel35.setText("Fecha F:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel36.setText("Fecha V:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnGuardarPro, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                .addGap(40, 40, 40)
                                .addComponent(btnEditarPro, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                .addGap(40, 40, 40)
                                .addComponent(btnEliminarPro, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                .addGap(40, 40, 40)
                                .addComponent(btnNuevoPro, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombrePro)
                                    .addComponent(txtDescripcionPro, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Id_Caracteristicas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxId_Proveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtLote)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtIdPro, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtStockPro, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(JDFechaVencimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                                .addComponent(JDFechaFabricacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnExcelPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(79, 79, 79)
                        .addComponent(btnFechaVencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(62, 62, 62)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(txtIdPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtNombrePro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescripcionPro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStockPro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addGap(10, 10, 10)
                        .addComponent(JDFechaFabricacion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel35))
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36)
                    .addComponent(JDFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxId_Proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Id_Caracteristicas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarPro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoPro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExcelPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 565, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab4", jPanel7);

        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL", "FECHA"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setMinWidth(5);
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(5);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVentas.getColumnModel().getColumn(2).setMinWidth(10);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(10);
            TableVentas.getColumnModel().getColumn(3).setMinWidth(10);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        btnPdfVentas.setMaximumSize(new java.awt.Dimension(60, 60));
        btnPdfVentas.setPreferredSize(new java.awt.Dimension(40, 40));
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        btnGenerarGrafica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grafico.png"))); // NOI18N
        btnGenerarGrafica.setMaximumSize(new java.awt.Dimension(60, 60));
        btnGenerarGrafica.setPreferredSize(new java.awt.Dimension(40, 40));
        btnGenerarGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarGraficaActionPerformed(evt);
            }
        });

        jLabel11.setText("Fecha inicio:");

        jLabel22.setText("fecha final:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnPdfVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(294, 294, 294)
                        .addComponent(btnGenerarGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel11)
                        .addGap(7, 7, 7)
                        .addComponent(fechainicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel22)
                        .addGap(7, 7, 7)
                        .addComponent(fechafinal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPdfVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnGenerarGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(fechainicio, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                .addComponent(fechafinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jTabbedPane2.addTab("tab5", jPanel8);

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("NIT");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setText("NOMBRE");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setText("TELÉFONO");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("DIRECCIÓN");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("CORREO");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel33.setText("DATOS DE LA EMPRESA");

        btnActualizarEmpresa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnActualizarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btnActualizarEmpresa.setText("ACTUALIZAR");
        btnActualizarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(txtidempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(225, 225, 225))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDireccionConfig)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(162, 162, 162))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(127, 127, 127)))
                                        .addGap(135, 135, 135)))
                                .addGap(59, 59, 59)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(282, 282, 282))
                                    .addComponent(txtCorreoConfig)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtNitConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(235, 235, 235))
                                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(70, 70, 70))
                                    .addComponent(txtTelefonoConfig))
                                .addGap(78, 78, 78))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(btnActualizarEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(288, 288, 288)))
                .addGap(128, 128, 128))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtidempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNitConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoConfig))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDireccionConfig, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCorreoConfig)
                        .addGap(1, 1, 1)))
                .addGap(123, 123, 123)
                .addComponent(btnActualizarEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );

        jTabbedPane2.addTab("tab6", jPanel9);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 113, 1030, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        calcularTotalPagar();
        limpiarCamposVenta();
        limpiarCampoCliente();
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed

        jTabbedPane2.setSelectedIndex(1);
        LimpiarTable();
        LimpiarCliente();
        ListarCliente();
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        jTabbedPane2.setSelectedIndex(2);
        LimpiarTable();
        LimpiarProveedor();
        ListarProveedores();
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        jTabbedPane2.setSelectedIndex(3);
        LimpiarTable();
        LimpiarProducto();
        PRO.NombreProveedor(cbxId_Proveedor);
        PRO.Categoria(Id_Caracteristicas);
        ListarProducto();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedPane2.setSelectedIndex(4);
        LimpiarTable();
        ListarVentas();
        
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
        jTabbedPane2.setSelectedIndex(5);
        ListarConfig();
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked

    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void txtIdProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductoActionPerformed

    private void btnExcelProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProActionPerformed
        Excel.reporte();
    }//GEN-LAST:event_btnExcelProActionPerformed

    private void tableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductoMouseClicked
    int fila = tableProducto.rowAtPoint(evt.getPoint());
    int idProducto = Integer.parseInt(tableProducto.getValueAt(fila, 0).toString());

    txtIdPro.setText(String.valueOf(idProducto));
    txtNombrePro.setText(tableProducto.getValueAt(fila, 1).toString());
    txtDescripcionPro.setText(tableProducto.getValueAt(fila, 2).toString());
    txtPrecioPro.setText(tableProducto.getValueAt(fila, 3).toString());
    txtStockPro.setText(tableProducto.getValueAt(fila, 4).toString());
    
    int idCategoria = Integer.parseInt(tableProducto.getValueAt(fila, 5).toString());
    int idProveedor = Integer.parseInt(tableProducto.getValueAt(fila, 6).toString());

    for (int i = 0; i < Id_Caracteristicas.getItemCount(); i++) {
        Object item = Id_Caracteristicas.getItemAt(i);
        if (item instanceof categoria) {
            categoria cat = (categoria) item;
            if (cat.getId() == idCategoria) {
                Id_Caracteristicas.setSelectedIndex(i);
                break;
            }
        }
    }
    for (int i = 0; i < cbxId_Proveedor.getItemCount(); i++) {
        Object item = cbxId_Proveedor.getItemAt(i);
        if (item instanceof Proveedor) {
            Proveedor prov = (Proveedor) item;
            if (prov.getId() == idProveedor) {
                cbxId_Proveedor.setSelectedIndex(i);
                break;
            }
        }
    }
ObtenerLote(idProducto);
    }//GEN-LAST:event_tableProductoMouseClicked

    private void cbxId_ProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxId_ProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxId_ProveedorActionPerformed

    private void Id_CaracteristicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Id_CaracteristicasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Id_CaracteristicasActionPerformed

    private void txtIdProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        LimpiarProducto();
        PRO.NombreProveedor(cbxId_Proveedor);
        PRO.Categoria(Id_Caracteristicas);

    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        if(!"".equals(txtIdPro.getText())
            &&!"".equals(txtNombrePro.getText())
            &&!"".equals(txtDescripcionPro.getText())
            &&!"".equals(txtPrecioPro.getText())
            &&!"".equals(txtStockPro.getText())){
            prod.setId(Integer.parseInt(txtIdPro.getText()));
            prod.setNombre(txtNombrePro.getText());
            prod.setDescripcion(txtDescripcionPro.getText());
            prod.setPrecio(Float.parseFloat(txtPrecioPro.getText()));
            prod.setStock(Integer.parseInt(txtStockPro.getText()));
            PRO.EliminarProducto(prod);
            LimpiarTable();
            LimpiarProducto();
            ListarProducto();
            PRO.NombreProveedor(cbxId_Proveedor);
            PRO.Categoria(Id_Caracteristicas);
            JOptionPane.showMessageDialog(null, "Eliminado con exito");
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione fila");
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnEditarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProActionPerformed
        if(!"".equals(txtIdPro.getText())
            &&!"".equals(txtNombrePro.getText())
            &&!"".equals(txtDescripcionPro.getText())
            &&!"".equals(txtPrecioPro.getText())
            &&!"".equals(txtStockPro.getText())
            &&cbxId_Proveedor.getSelectedIndex()!=0
            &&Id_Caracteristicas.getSelectedIndex()!=0){
            prod.setId(Integer.parseInt(txtIdPro.getText()));
            prod.setNombre(txtNombrePro.getText());
            prod.setDescripcion(txtDescripcionPro.getText());
            prod.setPrecio(Float.parseFloat(txtPrecioPro.getText()));
            prod.setStock(Integer.parseInt(txtStockPro.getText()));
            prod.setId_Categoria(Id_Caracteristicas.getSelectedIndex());
            prod.setId_proveedor(cbxId_Proveedor.getSelectedIndex());
            PRO.ActualizarProducto(prod);
            LimpiarTable();
            LimpiarProducto();
            ListarProducto();
            PRO.NombreProveedor(cbxId_Proveedor);
            PRO.Categoria(Id_Caracteristicas);
            JOptionPane.showMessageDialog(null, "Actualizado con exito");
        }else{
            JOptionPane.showMessageDialog(null, "Hay campos vacios, favor verifique");
        }
    }//GEN-LAST:event_btnEditarProActionPerformed

    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
        if(!"".equals(txtDescripcionPro.getText())
            &&!"".equals(txtNombrePro.getText())
            &&!"".equals(txtPrecioPro.getText())
            &&!"".equals(txtStockPro.getText())
            &&cbxId_Proveedor.getSelectedIndex()!=0
            &&Id_Caracteristicas.getSelectedIndex()!=0){

            prod.setNombre(txtNombrePro.getText());
            prod.setDescripcion(txtDescripcionPro.getText());
            prod.setPrecio(Float.parseFloat(txtPrecioPro.getText()));
            prod.setStock(Integer.parseInt(txtStockPro.getText()));
            prod.setId_proveedor(cbxId_Proveedor.getSelectedIndex());
            prod.setId_Categoria(Id_Caracteristicas.getSelectedIndex());
            int idGenerado = PRO.RegistrarProducto(prod);
            if (idGenerado != -1) { 
            l.setIdProducto(idGenerado);
            l.setCodigoLote(txtLote.getText());
            l.setFechaFabricacion(JDFechaFabricacion.getDate());
            l.setFechaVencimiento(JDFechaVencimiento.getDate());
            l.setCantidad(Integer.parseInt(txtStockPro.getText()));
            l.setIdProveedor(cbxId_Proveedor.getSelectedIndex());
            LD.registrarLote(l);
            }
            LimpiarTable();
            LimpiarProducto();
            ListarProducto();
            PRO.NombreProveedor(cbxId_Proveedor);
            PRO.Categoria(Id_Caracteristicas);

            JOptionPane.showMessageDialog(null, "Producto Registrado");
        }
        else{
            JOptionPane.showMessageDialog(null, "No se ha podido guardar el producto, favor llenar todos los campos");
        }
    }//GEN-LAST:event_btnGuardarProActionPerformed

    private void txtStockProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockProActionPerformed

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked

    }//GEN-LAST:event_jPanel6MouseClicked

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        LimpiarProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if(!"".equals(txtIdProveedor.getText())){
            int Mensaje=JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if(Mensaje==0){
                int id=Integer.parseInt(txtIdProveedor.getText());
                prove.EliminarProveedor(id);
                LimpiarTable();
                LimpiarProveedor();
                ListarProveedores();
            }
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }else{
            pro.setId(Integer.parseInt(txtIdProveedor.getText()));
            pro.setNit(Integer.parseInt(txtNitProveedor.getText()));
            pro.setNombre(txtNombreProveedor.getText());
            pro.setTelefono(Long.parseLong(txtTelefonoProveedor.getText()));
            pro.setCorreo(txtCorreoProveedor.getText());
            pro.setDireccion(txtDireccionProveedor.getText());

            if (!"".equals(txtIdProveedor.getText()) &&
                !"".equals(txtNombreProveedor.getText()) &&
                !"".equals(txtTelefonoProveedor.getText()) &&
                !"".equals(txtCorreoProveedor.getText()) &&
                !"".equals(txtDireccionProveedor.getText())) {

                prove.ActualizarProveedor(pro);
                LimpiarTable();
                LimpiarProveedor();
                ListarProveedores();
            }else{
                JOptionPane.showMessageDialog(null, "Hay Campos Vacios,favor Verificar");
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
        if(!"".equals(txtNitProveedor.getText())
            &&!"".equals(txtNombreProveedor.getText())
            &&!"".equals(txtTelefonoProveedor.getText())
            &&!"".equals(txtCorreoProveedor.getText())
            &&!"".equals(txtDireccionProveedor.getText())){
            pro.setNit(Integer.parseInt(txtNitProveedor.getText()));
            pro.setNombre(txtNombreProveedor.getText());
            pro.setTelefono(Long.parseLong(txtTelefonoProveedor.getText()));
            pro.setCorreo(txtCorreoProveedor.getText());
            pro.setDireccion(txtDireccionProveedor.getText());
            prove.RegistrarProveedor(pro);
            LimpiarTable();
            LimpiarProveedor();
            ListarProveedores();
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
        }else{
            JOptionPane.showMessageDialog(null, "Hay campos vacios,por favor verifique");
        }
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void tableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProveedorMouseClicked
        int fila=tableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(tableProveedor.getValueAt(fila,0).toString());
        txtNitProveedor.setText(tableProveedor.getValueAt(fila,1).toString());
        txtNombreProveedor.setText(tableProveedor.getValueAt(fila,2).toString());
        txtTelefonoProveedor.setText(tableProveedor.getValueAt(fila,3).toString());
        txtCorreoProveedor.setText(tableProveedor.getValueAt(fila,4).toString());
        txtDireccionProveedor.setText(tableProveedor.getValueAt(fila,5).toString());
    }//GEN-LAST:event_tableProveedorMouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked

    }//GEN-LAST:event_jPanel5MouseClicked

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        LimpiarCliente();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        if(!"".equals(txtIdCliente.getText())){
            int pregunta=JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if(pregunta ==0){
                int id=Integer.parseInt(txtIdCliente.getText());
                client.EliminarCliente(id);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
        else {
          
            cl.setId(Integer.parseInt(txtIdCliente.getText())); 
            cl.setCedula(Integer.parseInt(txtCedulaCliente.getText()));
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(Long.parseLong(txtTelefonoCliente.getText()));
            cl.setCorreo(txtCorreoCliente.getText());
            cl.setDireccion(txtDireccionCliente.getText());

            if (!"".equals(txtCedulaCliente.getText()) &&
                !"".equals(txtNombreCliente.getText()) &&
                !"".equals(txtTelefonoCliente.getText()) &&
                !"".equals(txtCorreoCliente.getText()) &&
                !"".equals(txtDireccionCliente.getText())) {

                client.ModificarCliente(cl);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            }else{
                JOptionPane.showMessageDialog(null, "Hay Campos Vacios,favor Verificar");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if(!"".equals(txtCedulaCliente.getText())||!"".equals(txtNombreCliente.getText())
            ||!"".equals(txtTelefonoCliente.getText())||!"".equals(txtCorreoCliente.getText())
            ||!"".equals(txtDireccionCliente.getText())){
            cl.setCedula(Integer.parseInt(txtCedulaCliente.getText()));
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(Long.parseLong(txtTelefonoCliente.getText()));
            cl.setCorreo(txtCorreoCliente.getText());
            cl.setDireccion(txtDireccionCliente.getText());
            client.RegistrarCliente(cl);
            LimpiarTable();
            LimpiarCliente();
            ListarCliente();
            JOptionPane.showMessageDialog(null, "Cliente Registrado");

        }else{
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void tableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClienteMouseClicked
        int fila=tableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(tableCliente.getValueAt(fila,0).toString());
        txtCedulaCliente.setText(tableCliente.getValueAt(fila,1).toString());
        txtNombreCliente.setText(tableCliente.getValueAt(fila,2).toString());
        txtTelefonoCliente.setText(tableCliente.getValueAt(fila,3).toString());
        txtCorreoCliente.setText(tableCliente.getValueAt(fila,4).toString());
        txtDireccionCliente.setText(tableCliente.getValueAt(fila,5).toString());
    }//GEN-LAST:event_tableClienteMouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked

    private void txtCedulaVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            String cedula=txtCedulaVenta.getText().trim();
            if(!"".equals(txtCedulaVenta.getText())){
                int numcedula=Integer.parseInt(cedula);
                cl =client.BuscarCliente(numcedula);
                if(cl.getNombre()!=null){
                    txtNombreClienteVenta.setText(""+cl.getNombre());
                    txttelefono.setText(""+cl.getTelefono());
                    txtcorreo.setText(""+cl.getCorreo());
                    txtdireccion.setText(""+cl.getDireccion());
                }else{
                    txtCedulaVenta.setText("");
                    txtNombreClienteVenta.setText("");
                    txttelefono.setText("");
                    txtcorreo.setText("");
                    txtdireccion.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtCedulaVentaKeyPressed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        int filaSeleccionada = tableVenta.getSelectedRow();

        if (filaSeleccionada != -1) {
            modelo = (DefaultTableModel) tableVenta.getModel();
            modelo.removeRow(filaSeleccionada);
            calcularTotalPagar();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
        }
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText()) && !"".equals(txtCantidadVenta.getText())) {
                try {
                    String codigo = txtCodigoVenta.getText().trim();
                    String descripcion = txtDescripcionVenta.getText().trim();
                    int cant = Integer.parseInt(txtCantidadVenta.getText().trim());
                    float precio = Float.parseFloat(txtPrecioVenta.getText().trim());
                    int stock = Integer.parseInt(txtStockDisponible.getText().trim());

                    mdl = (DefaultTableModel) tableVenta.getModel();
                    boolean encontrado = false;

                    for (int i = 0; i < mdl.getRowCount(); i++) {
                        if (mdl.getValueAt(i, 0).equals(codigo)) {

                            int cantidadExistente = (int) mdl.getValueAt(i, 2);
                            int nuevaCantidad = cantidadExistente + cant;

                            if (nuevaCantidad > stock) {
                                JOptionPane.showMessageDialog(null, "Stock insuficiente para aumentar la cantidad.");
                                return;
                            }

                            mdl.setValueAt(nuevaCantidad, i, 2);
                            mdl.setValueAt(nuevaCantidad * precio, i, 4);
                            limpiarCamposVenta();
                            calcularTotalPagar();
                            encontrado = true;

                            break;
                        }
                    }

                    if (!encontrado) {
                        if (stock >= cant) {
                            float total = cant * precio;
                            item++;
                            Object[] fila = new Object[5];
                            fila[0] = codigo;
                            fila[1] = descripcion;
                            fila[2] = cant;
                            fila[3] = precio;
                            fila[4] = total;
                            mdl.addRow(fila);
                            tableVenta.setModel(mdl);
                            limpiarCamposVenta();
                            calcularTotalPagar();
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Stock no disponible");
                        }
                    }

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese la cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String codigoTexto = txtCodigoVenta.getText().trim();

        if (!codigoTexto.equals("")) {
            try {
                int id = Integer.parseInt(codigoTexto);
                prod = PRO.BuscarPro(id);

                if (prod.getNombre() != null) {
                    txtDescripcionVenta.setText(prod.getNombre());
                    txtPrecioVenta.setText(String.valueOf(prod.getPrecio()));
                    txtStockDisponible.setText(String.valueOf(prod.getStock()));
                    txtCantidadVenta.requestFocus();
                } else {
                    limpiarCamposVenta();
                    JOptionPane.showMessageDialog(null, "El producto no existe");
                    txtCodigoVenta.requestFocus();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico");
                txtCodigoVenta.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese el código del producto");
            txtCodigoVenta.requestFocus();
        }
    }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCedulaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaVentaActionPerformed

    }//GEN-LAST:event_txtCedulaVentaActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed

        modelo=(DefaultTableModel) tableVenta.getModel();
        if (modelo.getRowCount()==0) {
          JOptionPane.showMessageDialog(null,"No hay productos en la venta");
        }else{
                RegistrarVenta();
                RegistrarDetalle();
                ActualizaStock();
                pdf();
                limpiarCamposVenta();
                limpiarDatosCliente();
                LimpiarTable();
                calcularTotalPagar();
                JOptionPane.showMessageDialog(null, "¡Venta generada con exito!");
                txtCodigoVenta.requestFocus();
                
        }
   
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped

    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void btnActualizarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarEmpresaActionPerformed
               if(!"".equals(txtidempresa.getText())
            &&!"".equals(txtNitConfig.getText())
            &&!"".equals(txtNombreConfig.getText())
            &&!"".equals(txtTelefonoConfig.getText())
            &&!"".equals(txtCorreoConfig.getText())
            &&!"".equals(txtDireccionConfig.getText()))
            {
            config.setNit(Integer.parseInt(txtNitConfig.getText()));
            config.setNombre(txtNombreConfig.getText());
            config.setTel(Integer.parseInt(txtTelefonoConfig.getText()));
            config.setDireccion(txtDireccionConfig.getText());
            config.setCorreo(txtCorreoConfig.getText()); 
            config.setId(Integer.parseInt(txtidempresa.getText()));
            PRO.ActualizarConfig(config);
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificado");
            ListarConfig();
        }else{
            JOptionPane.showMessageDialog(null, "Hay campos vacios, favor verifique");
        }
    }//GEN-LAST:event_btnActualizarEmpresaActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
       int fila=TableVentas.rowAtPoint(evt.getPoint());
       txtIdVenta.setText(TableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
    try {
    int id = Integer.parseInt(txtIdVenta.getText());
    String userHome = System.getProperty("user.home");
    File carpetaDocs = new File(userHome, "Documents");
    if (!carpetaDocs.exists()) {
        carpetaDocs = new File(userHome, "Documentos");
    }
    File file = new File(carpetaDocs, "venta" + id + ".pdf");
    if (file.exists()) {
        Desktop.getDesktop().open(file);
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró la factura PDF para la venta " + id);
    }
} catch (IOException ex) {
    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
}
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
       Registrar reg=new Registrar();
       reg.setVisible(true);
       
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        Login R=new Login();
        R.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void txtCodigoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaActionPerformed

    private void txtDescripcionVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyReleased
    String texto = txtDescripcionVenta.getText().trim();
    popupSugerencias.setVisible(false); 

    if (!texto.isEmpty()) {
        List<Producto> lista = PRO.buscarPorNombre(texto);

        if (!lista.isEmpty()) {
            popupSugerencias.removeAll(); 

            for (Producto p : lista) {
                JMenuItem item = new JMenuItem(p.getNombre());
                item.addActionListener(e -> {
                    txtDescripcionVenta.setText(p.getNombre());
                    txtCodigoVenta.setText(String.valueOf(p.getId()));
                    txtStockDisponible.setText(String.valueOf(p.getStock()));
                    txtPrecioVenta.setText(String.valueOf(p.getPrecio()));
                    popupSugerencias.setVisible(false);
                    txtCantidadVenta.requestFocus();
                });
                popupSugerencias.add(item);
            }
            popupSugerencias.show(txtDescripcionVenta, 0, txtDescripcionVenta.getHeight());
        }
    }
    }//GEN-LAST:event_txtDescripcionVentaKeyReleased

    private void btnFechaVencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFechaVencimientoActionPerformed
        String diasStr = JOptionPane.showInputDialog("Ingrese los días para próximos vencimientos:");
    if (diasStr != null && !diasStr.isEmpty()) {
        int dias = Integer.parseInt(diasStr);
        Excel.reporteLotesPorVencer(dias);
    }
    }//GEN-LAST:event_btnFechaVencimientoActionPerformed

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        LimpiarTable();
        ListarProducto();
    }//GEN-LAST:event_btnProductosMouseClicked

    private void txtNombreClienteVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteVentaKeyReleased
  String texto = txtNombreClienteVenta.getText().trim();
    listarclientes.setVisible(false); 

    if (!texto.isEmpty()) {
        List<Cliente> lista = client.buscarPorNombre(texto);

        if (!lista.isEmpty()) {
            listarclientes.removeAll(); 

            for (Cliente clien : lista) {
                JMenuItem cliente = new JMenuItem(clien.getNombre());
                cliente.addActionListener(e -> {
                    txtCedulaVenta.setText(String.valueOf(clien.getCedula()));
                    txtNombreClienteVenta.setText(String.valueOf(clien.getNombre()));
                    txttelefono.setText(String.valueOf(clien.getTelefono()));
                    txtcorreo.setText(String.valueOf(clien.getCorreo()));
                    txtdireccion.setText(String.valueOf(clien.getDireccion()));
                    listarclientes.setVisible(false);
                    btnGenerarVenta.requestFocus();
                });
                listarclientes.add(cliente);
            }
            listarclientes.show(txtNombreClienteVenta, 0, txtNombreClienteVenta.getHeight());
        }
    }
    }//GEN-LAST:event_txtNombreClienteVentaKeyReleased

    private void btnGenerarGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarGraficaActionPerformed
    try {
    java.util.Date inicioUtil = fechainicio.getDate();
    java.util.Date finUtil = fechafinal.getDate();

    if (inicioUtil == null || finUtil == null) {
        JOptionPane.showMessageDialog(this, "Selecciona ambas fechas.");
        return;
    }

    java.sql.Date inicio = new java.sql.Date(inicioUtil.getTime());
    java.sql.Date fin = new java.sql.Date(finUtil.getTime());

    String[] opciones = {"Productos", "Ventas ($)"};
    int eleccion = JOptionPane.showOptionDialog(
            this,
            "Seleccione el tipo de reporte",
            "Opciones de Reporte",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
    );

    if (eleccion == -1) return; 

    JFreeChart chart = null;

    if (eleccion == 0) {
        Map<String, Integer> ventas = Vdao.obtenerVentasPorProducto(inicio, fin);

        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ventas en este rango de fechas.");
            return;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : ventas.entrySet()) {
            dataset.addValue(entry.getValue(), "Productos", entry.getKey());
        }

        chart = ChartFactory.createBarChart(
                "Cantidad Vendida por Producto",
                "Producto",
                "Cantidad",
                dataset
        );

        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    } else if (eleccion == 1) {
        
        Map<String, Double> ventasTotales = Vdao.obtenerVentasPorFecha(inicio, fin);

        if (ventasTotales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ventas en este rango de fechas.");
            return;
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : ventasTotales.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        chart = ChartFactory.createPieChart(
                "Ventas Totales por Día ($)",
                dataset,
                true,
                true,
                false
        );
    }

    
    ChartPanel panel = new ChartPanel(chart);
    JFrame ventana = new JFrame("Reporte de Ventas");
    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    ventana.add(panel);
    ventana.setSize(800, 600);
    ventana.setLocationRelativeTo(null);
    ventana.setVisible(true);

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    e.printStackTrace();
}
limpiarFecha();
    }//GEN-LAST:event_btnGenerarGraficaActionPerformed

    private void btnVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseClicked
     LimpiarTable();
     ListarVentas();
     limpiarFecha();
    }//GEN-LAST:event_btnVentasMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMaterialLighterIJTheme.setup();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Id_Caracteristicas;
    private com.toedter.calendar.JDateChooser JDFechaFabricacion;
    private com.toedter.calendar.JDateChooser JDFechaVencimiento;
    private javax.swing.JTable TableVentas;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnActualizarEmpresa;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfiguracion;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarPro;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnExcelPro;
    private javax.swing.JButton btnFechaVencimiento;
    private javax.swing.JButton btnGenerarGrafica;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarPro;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVentas;
    private javax.swing.JComboBox<String> cbxId_Proveedor;
    private javax.swing.JLabel dateText;
    private com.toedter.calendar.JDateChooser fechafinal;
    private com.toedter.calendar.JDateChooser fechainicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JTable tableCliente;
    private javax.swing.JTable tableProducto;
    private javax.swing.JTable tableProveedor;
    private javax.swing.JTable tableVenta;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCedulaCliente;
    private javax.swing.JTextField txtCedulaVenta;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtCorreoCliente;
    private javax.swing.JTextField txtCorreoConfig;
    private javax.swing.JTextField txtCorreoProveedor;
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtLote;
    private javax.swing.JTextField txtNitConfig;
    private javax.swing.JTextField txtNitProveedor;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteVenta;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombrePro;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JLabel txtRol;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtStockPro;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JLabel txtTotalPagar;
    private javax.swing.JLabel txtUsuario;
    private javax.swing.JLabel txtcorreo;
    private javax.swing.JLabel txtdireccion;
    private javax.swing.JLabel txtidempresa;
    private javax.swing.JLabel txttelefono;
    // End of variables declaration//GEN-END:variables
    private void LimpiarCliente(){
        txtIdCliente.setText("");
        txtCedulaCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtCorreoCliente.setText("");
        txtDireccionCliente.setText("");
    }
     private void LimpiarProveedor(){
        txtIdProveedor.setText("");
        txtNitProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtCorreoProveedor.setText("");
        txtDireccionProveedor.setText("");
    }
     private void LimpiarProducto(){
         txtIdPro.setText("");
         txtNombrePro.setText("");
         txtPrecioPro.setText("");
         txtDescripcionPro.setText("");
         txtStockPro.setText("");
         cbxId_Proveedor.removeAllItems();
         Id_Caracteristicas.removeAllItems();
         txtLote.setText("");
         JDFechaFabricacion.setDate(null);
         JDFechaVencimiento.setDate(null);
     }
     private void calcularTotalPagar() {
    TotalPagar = 0; 
    modelo = (DefaultTableModel) tableVenta.getModel();

    for (int i = 0; i < modelo.getRowCount(); i++) {
        float totalFila = Float.parseFloat(modelo.getValueAt(i, 4).toString()); 
        TotalPagar += totalFila;
    }
    txtTotalPagar.setText(String.format("%.2f", TotalPagar));
    }
    
    private void limpiarFecha(){
        fechainicio.setDate(null);
        fechafinal.setDate(null);
    }
    private void limpiarCamposVenta() {
    txtCodigoVenta.setText("");
    txtDescripcionVenta.setText("");
    txtCantidadVenta.setText("");
    txtPrecioVenta.setText("");
    txtStockDisponible.setText("");
}
    private void limpiarDatosCliente(){
     txtCedulaVenta.setText("");
    txtNombreClienteVenta.setText("");
    txtcorreo.setText("");
    txtdireccion.setText("");
    txttelefono.setText("");
    }
    
     private void limpiarCampoCliente(){
    txtCedulaVenta.setText("");
    txtNombreClienteVenta.setText("");
    txttelefono.setText("");
    txtcorreo.setText("");
    txtdireccion.setText("");
    txtCodigoVenta.requestFocus();
     }
     private void RegistrarVenta() {
        String Cliente = txtNombreClienteVenta.getText();
        String Vendedor = txtUsuario.getText();
        Float total = TotalPagar;
        v.setCliente(Cliente);
        v.setVendedor(Vendedor);
        v.setTotal(total);
        java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
        v.setFecha(fechaActual);
        Vdao.RegistrarVenta(v);
}

     private void RegistrarDetalle(){
         for (int i = 0; i < tableVenta.getRowCount(); i++) {
             int cod=Integer.parseInt(tableVenta.getValueAt(i, 0).toString());
             String nombre=tableVenta.getValueAt(i, 1).toString();
             int cant=Integer.parseInt(tableVenta.getValueAt(i, 2).toString());
             float precio=Float.parseFloat(tableVenta.getValueAt(i, 3).toString());
             float preciototal=Float.parseFloat(tableVenta.getValueAt(i, 4).toString());
            int id =Vdao.idventa();
            Dv.setCod_producto(cod);
            Dv.setNombre(nombre);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setPrecioTotal(preciototal);
            Dv.setId_venta(id);
            Vdao.RegistrarDetalle(Dv);
         }
     }
   private void ObtenerLote(int idProducto) {
       lote = LD.obtenerLotePorProducto(idProducto);
    if (lote != null) {
        txtLote.setText(lote.getCodigoLote());
        JDFechaFabricacion.setDate(lote.getFechaFabricacion());
        JDFechaVencimiento.setDate(lote.getFechaVencimiento());   
        
    } else {
        txtLote.setText("Sin lote");
        JDFechaFabricacion.setDate(null);
        JDFechaVencimiento.setDate(null);
        
    }

     }
     private void ActualizaStock(){
         for (int i = 0; i < tableVenta.getRowCount(); i++){
             int cod=Integer.parseInt(tableVenta.getValueAt(i, 0).toString());
             int cant=Integer.parseInt(tableVenta.getValueAt(i, 2).toString());
             prod=PRO.BuscarPro(cod);
             int StockActual=prod.getStock()-cant;
             Vdao.ActualizarStock(StockActual, cod);
         }
     }
    private void pdf() {
    try {
        int id = Vdao.idventa();
        FileOutputStream archivo;

       
        String userHome = System.getProperty("user.home");
        File carpetaDocs = new File(userHome, "Documents");
        if (!carpetaDocs.exists()) {
            carpetaDocs = new File(userHome, "Documentos");
        }

        File file = new File(carpetaDocs, "venta" + id + ".pdf");
        archivo = new FileOutputStream(file);

        Document doc = new Document();
        PdfWriter.getInstance(doc, archivo);
        doc.open();

       
        InputStream is = getClass().getResourceAsStream("/imagenes/Logo2.png");
        if (is != null) {
            byte[] bytes = is.readAllBytes();
            Image img = Image.getInstance(bytes);
            img.scaleToFit(80, 80);
            img.setAlignment(Image.ALIGN_LEFT);
            
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.ORANGE);
            Font titulo = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura: " + id + "\n" + "Fecha: " +
                    new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);
            
            config = PRO.BuscarDatos();
            String Nit = String.valueOf(config.getNit());
            String Nom = config.getNombre();
            String tel = String.valueOf(config.getTel());
            String dir = config.getDireccion();
            String cor = config.getCorreo();

            Encabezado.addCell("");
            Encabezado.addCell("NIT: " + Nit + "\nNOMBRE: " + Nom + "\nTELEFONO: " + tel +
                    "\nCORREO: " + cor + "\nDIRECCIÓN: " + dir);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
        } else {
            System.out.println("⚠ No se encontró la imagen Logo2.png");
        }


        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        Font fontDato = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

    
        Paragraph cli = new Paragraph();
        cli.add(Chunk.NEWLINE);
        cli.add(new Chunk("Cliente\n\n", fontTitulo));
        doc.add(cli);

        Paragraph dato = new Paragraph();
        dato.add(new Chunk("NIT/CC: ", fontTitulo));
        dato.add(new Chunk(txtCedulaVenta.getText() + "\n", fontDato));
        dato.add(new Chunk("NOMBRE: ", fontTitulo));
        dato.add(new Chunk(txtNombreClienteVenta.getText() + "\n", fontDato));
        dato.add(new Chunk("TELÉFONO: ", fontTitulo));
        dato.add(new Chunk(txttelefono.getText() + "\n", fontDato));
        dato.add(new Chunk("CORREO: ", fontTitulo));
        dato.add(new Chunk(txtcorreo.getText() + "\n", fontDato));
        dato.add(new Chunk("DIRECCIÓN: ", fontTitulo));
        dato.add(new Chunk(txtdireccion.getText() + "\n\n", fontDato));
        doc.add(dato);

   
        PdfPTable tablapro = new PdfPTable(4);
        tablapro.setWidthPercentage(100);
        tablapro.getDefaultCell().setBorder(0);
        float[] Columnapro = new float[]{15f, 55f, 15f, 15f};
        tablapro.setWidths(Columnapro);
        tablapro.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

        PdfPCell pro1 = new PdfPCell(new Phrase("CANTIDAD", fontTitulo));
        PdfPCell pro2 = new PdfPCell(new Phrase("DESCRIPCION", fontTitulo));
        PdfPCell pro3 = new PdfPCell(new Phrase("PRECIO U", fontTitulo));
        PdfPCell pro4 = new PdfPCell(new Phrase("TOTAL", fontTitulo));

        pro1.setHorizontalAlignment(Element.ALIGN_LEFT);
        pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
        pro3.setHorizontalAlignment(Element.ALIGN_LEFT);
        pro4.setHorizontalAlignment(Element.ALIGN_LEFT);

        pro1.setBorder(0);
        pro2.setBorder(0);
        pro3.setBorder(0);
        pro4.setBorder(0);

        pro1.setBackgroundColor(BaseColor.ORANGE);
        pro2.setBackgroundColor(BaseColor.ORANGE);
        pro3.setBackgroundColor(BaseColor.ORANGE);
        pro4.setBackgroundColor(BaseColor.ORANGE);

        tablapro.addCell(pro1);
        tablapro.addCell(pro2);
        tablapro.addCell(pro3);
        tablapro.addCell(pro4);

        for (int i = 0; i < tableVenta.getRowCount(); i++) {
            String producto = tableVenta.getValueAt(i, 1).toString();
            String cantidad = tableVenta.getValueAt(i, 2).toString();
            String precio = tableVenta.getValueAt(i, 3).toString();
            String total = tableVenta.getValueAt(i, 4).toString();
            tablapro.addCell(cantidad);
            tablapro.addCell(producto);
            tablapro.addCell(precio);
            tablapro.addCell(total);
        }
        doc.add(tablapro);

   
        Paragraph info = new Paragraph();
        info.add(Chunk.NEWLINE);
        info.add("Total a Pagar: " + TotalPagar);
        info.setAlignment(Element.ALIGN_RIGHT);
        doc.add(info);

        doc.close();
        archivo.close();
        Desktop.getDesktop().open(file);

    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    }
}

}

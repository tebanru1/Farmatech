package Vista;

import Modelo.login;
import Controlador.loginDAO;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Registrar extends javax.swing.JFrame{
    login lg=new login();
    loginDAO login=new loginDAO();
public Registrar() {
        FlatMaterialLighterIJTheme.setup();
        initComponents();
        configurarPlaceholders();
        setResizable(false);
        estilos();
        this.setLocationRelativeTo(null);
        cargarDatosLogin();
        configurarPlaceholders();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         
    }

    // MÉTODOS PERSONALIZADOS
private void estilos(){
    btnIngresar.putClientProperty( "JButton.buttonType" , "roundRect" );
}
    private void guardarDatosLogin(String usuario, String contraseña, boolean recordar) {
        try {
            Properties props = new Properties();
            props.setProperty("usuario", usuario);
            props.setProperty("contraseña", contraseña);
            props.setProperty("recordar", String.valueOf(recordar));
            FileOutputStream out = new FileOutputStream("login.properties");
            props.store(out, null);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosLogin() {
        try {
            Properties props = new Properties();
            File file = new File("login.properties");
            if (file.exists()) {
                FileInputStream in = new FileInputStream(file);
                props.load(in);
                in.close();

                String usuario = props.getProperty("usuario");
                String contraseña = props.getProperty("contraseña");
                boolean recordar = Boolean.parseBoolean(props.getProperty("recordar"));

                if (recordar) {
                    txtUsuario.setText(usuario);
                    txtUsuario.setForeground(new java.awt.Color(0, 0, 0));
                    txtContraseña.setText(contraseña);
                    txtContraseña.setForeground(new java.awt.Color(0, 0, 0));
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configurarPlaceholders() {
         txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (txtNombre.getText().equals("Ingrese Nombre")) {
                txtNombre.setText("");
                txtNombre.setForeground(java.awt.Color.BLACK);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            if (txtNombre.getText().isEmpty()) {
                txtNombre.setForeground(new java.awt.Color(204, 204, 204));
                txtNombre.setText("Ingrese Nombre");
            }
        }
    });
     
    txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (txtUsuario.getText().equals("Ingrese Usuario")) {
                txtUsuario.setText("");
                txtUsuario.setForeground(java.awt.Color.BLACK);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            if (txtUsuario.getText().isEmpty()) {
                txtUsuario.setForeground(new java.awt.Color(204, 204, 204));
                txtUsuario.setText("Ingrese Usuario");
            }
        }
    });

    // Evento para limpiar el campo de contraseña al hacer clic
    txtContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (String.valueOf(txtContraseña.getPassword()).equals("****************")) {
                txtContraseña.setText("");
                txtContraseña.setEchoChar('●'); // Activa puntos
                txtContraseña.setForeground(java.awt.Color.BLACK);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            if (String.valueOf(txtContraseña.getPassword()).isEmpty()) {
                txtContraseña.setForeground(new java.awt.Color(204, 204, 204));
                txtContraseña.setText("****************");
                txtContraseña.setEchoChar((char) 0); // Desactiva puntos
            }
        }
    });
}
  
    public void Registar(){
        String nombre=txtNombre.getText();
        String usuario=txtUsuario.getText();
        String contraseña=String.valueOf(txtContraseña.getPassword());
        String rol=cbRol.getSelectedItem().toString();
        if (!"".equals(usuario)
                &&!"".equals(nombre)
                &&!"".equals(rol)
                &&!"".equals(contraseña)){
            lg.setNombre(nombre);
            lg.setUsuario(usuario);
            lg.setContraseña(contraseña);
            lg.setRol(rol);
            login.RegistrarUsuario(lg);
            JOptionPane.showMessageDialog(null, "Registro Exitoso");
           
            dispose();
        }
    }

    public static void main(String[] args) {
       Registrar vistaLogin = new Registrar();
        vistaLogin.setVisible(true);
       
        
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        txtNombre = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbRol = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Nota 5_ contenido1.jpg"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REGISTRO");

        btnIngresar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnIngresar.setText("REGISTRAR");
        btnIngresar.setAlignmentY(0.0F);
        btnIngresar.setMaximumSize(new java.awt.Dimension(702, 218));
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("NOMBRE:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("USUARIO:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("CONTRASEÑA:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("ROL:");

        cbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Administrador de Inventarios", "Auxiliar de Farmacia", "Auditor / Inspector", "Cajero" }));
        cbRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombre)
                    .addComponent(txtUsuario)
                    .addComponent(txtContraseña)
                    .addComponent(cbRol, 0, 206, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRolActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
       Registar();
    }//GEN-LAST:event_btnIngresarActionPerformed


   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JComboBox<String> cbRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
public String getUsuario() {
    return txtUsuario.getText();
}

public String getContraseña() {
    return new String(txtContraseña.getPassword());
}
public javax.swing.JButton getBtnIngresar() {
    return btnIngresar;
}

}

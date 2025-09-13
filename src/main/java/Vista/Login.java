package Vista;

import Modelo.login;
import Controlador.loginDAO;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame{
    login lg=new login();
    loginDAO login=new loginDAO();
public Login() {
        FlatMaterialLighterIJTheme.setup();
        initComponents();
        setResizable(false);
        estilos();
        this.setLocationRelativeTo(null);
        cargarDatosLogin();
        configurarPlaceholders();
         
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
                    recordarme.setState(true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configurarPlaceholders() {
    // Evento para limpiar el campo de usuario al hacer clic
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

 public void Validar() {
    String usuario = txtUsuario.getText();
    String contraseña = String.valueOf(txtContraseña.getPassword());

    if (!usuario.equals("") && !contraseña.equals("")) {
        lg = login.log(usuario, contraseña); 

        if (lg.getUsuario() != null) {
           guardarDatosLogin(usuario, contraseña, recordarme.getState());
            MenuPrincipal mp = new MenuPrincipal(lg);
            mp.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña");
    }
}

    
    public static void main(String[] args) {
       Login vistaLogin = new Login();
        vistaLogin.setVisible(true);
        
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtContraseña = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        recordarme = new java.awt.Checkbox();
        contraOlvidada = new java.awt.Label();
        btnIngresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Nota 5_ contenido1.jpg"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("INGRESO");

        txtContraseña.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtContraseña.setForeground(new java.awt.Color(204, 204, 204));
        txtContraseña.setBorder(null);
        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyPressed(evt);
            }
        });

        txtUsuario.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtUsuario.setText("Ingrese Usuario");
        txtUsuario.setBorder(null);
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        recordarme.setLabel("Recordarme");

        contraOlvidada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contraOlvidada.setText("Contraseña Olvidada?");
        contraOlvidada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contraOlvidadaMouseClicked(evt);
            }
        });

        btnIngresar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnIngresar.setText("INGRESAR");
        btnIngresar.setAlignmentY(0.0F);
        btnIngresar.setMaximumSize(new java.awt.Dimension(702, 218));
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator2)
                            .addComponent(txtContraseña)
                            .addComponent(txtUsuario)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(recordarme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(contraOlvidada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(recordarme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contraOlvidada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        configurarPlaceholders();
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
       Validar();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void txtContraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyPressed
         configurarPlaceholders();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){Validar();}
    }//GEN-LAST:event_txtContraseñaKeyPressed

    private void contraOlvidadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contraOlvidadaMouseClicked
       JOptionPane.showMessageDialog(null, "Favor comunicarse con el area de soporte: \nsoporte@farmatech.com\n+57 300 123 4567");
    }//GEN-LAST:event_contraOlvidadaMouseClicked


   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private java.awt.Label contraOlvidada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private java.awt.Checkbox recordarme;
    private javax.swing.JPasswordField txtContraseña;
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

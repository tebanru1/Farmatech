
package Controlador;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class Excel {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
public static void reporte() {
    Workbook book = new XSSFWorkbook();
    Sheet sheet = book.createSheet("Productos");

    try {
        
        InputStream is = Excel.class.getResourceAsStream("/imagenes/Logo2.png");
        if (is != null) {
            byte[] bytes = IOUtils.toByteArray(is);
            int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();

            sheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 0));
            CreationHelper help = book.getCreationHelper();
            Drawing<?> draw = sheet.createDrawingPatriarch();

            ClientAnchor anchor = help.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(0);
            anchor.setCol2(1);
            anchor.setRow2(4);
            draw.createPicture(anchor, imgIndex);
        } else {
            System.out.println("⚠ No se encontró la imagen Logo2.png");
        }

        
        CellStyle tituloEstilo = book.createCellStyle();
        tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
        tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
        Font fuenteTitulo = book.createFont();
        fuenteTitulo.setFontName("Arial");
        fuenteTitulo.setBold(true);
        fuenteTitulo.setFontHeightInPoints((short) 14);
        tituloEstilo.setFont(fuenteTitulo);

        Row filaTitulo = sheet.createRow(1);
        Cell celdaTitulo = filaTitulo.createCell(1);
        celdaTitulo.setCellStyle(tituloEstilo);
        celdaTitulo.setCellValue("Reporte de Productos");
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));

        
        String[] cabecera = {"Nombre", "Descripción", "Precio", "Existencia"};
        CellStyle headerStyle = book.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        Font font = book.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        Row filaEncabezados = sheet.createRow(4);
        for (int i = 0; i < cabecera.length; i++) {
            Cell celdaEnzabezado = filaEncabezados.createCell(i);
            celdaEnzabezado.setCellStyle(headerStyle);
            celdaEnzabezado.setCellValue(cabecera[i]);
        }

       
        conexion cn = new conexion();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = cn.conectar();

        int numFilaDatos = 5;
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);

        ps = con.prepareStatement("SELECT nombre, descripcion, precio, stock FROM productos");
        rs = ps.executeQuery();
        int numCol = rs.getMetaData().getColumnCount();

        while (rs.next()) {
            Row filaDatos = sheet.createRow(numFilaDatos);
            for (int a = 0; a < numCol; a++) {
                Cell CeldaDatos = filaDatos.createCell(a);
                CeldaDatos.setCellStyle(datosEstilo);
                CeldaDatos.setCellValue(rs.getString(a + 1));
            }
            numFilaDatos++;
        }

       
        for (int i = 0; i < cabecera.length; i++) {
            sheet.autoSizeColumn(i);
        }

        sheet.setZoom(150);

       
        String userHome = System.getProperty("user.home");
        File carpetaDocs = new File(userHome, "Documents");
        if (!carpetaDocs.exists()) {
            carpetaDocs = new File(userHome, "Documentos");
        }

        File file = new File(carpetaDocs, "productos.xlsx");
        FileOutputStream fileOut = new FileOutputStream(file);
        book.write(fileOut);
        fileOut.close();

        Desktop.getDesktop().open(file);
        JOptionPane.showMessageDialog(null, "Reporte Generado");

    } catch (IOException | SQLException ex) {
        Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public static void reporteLotesPorVencer(int dias) {
    Workbook book = new XSSFWorkbook();
    Sheet sheet = book.createSheet("Lotes por Vencer");

    try {
        CellStyle tituloEstilo = book.createCellStyle();
        tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
        tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
        Font fuenteTitulo = book.createFont();
        fuenteTitulo.setFontName("Arial");
        fuenteTitulo.setBold(true);
        fuenteTitulo.setFontHeightInPoints((short) 14);
        tituloEstilo.setFont(fuenteTitulo);

        Row filaTitulo = sheet.createRow(0);
        Cell celdaTitulo = filaTitulo.createCell(0);
        celdaTitulo.setCellStyle(tituloEstilo);
        celdaTitulo.setCellValue("Reporte de Lotes Próximos a Vencer");
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 4));

        
        Row filaFecha = sheet.createRow(3);
        Cell celdaFechaReporte = filaFecha.createCell(2);
        celdaFechaReporte.setCellValue("Generado el: " + java.time.LocalDate.now());

      
        String[] cabecera = {"ID Lote", "Producto", "Código Lote", "Cantidad", "Fecha Vencimiento"};
        CellStyle headerStyle = book.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        Font font = book.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        Row filaEncabezados = sheet.createRow(4);
        for (int i = 0; i < cabecera.length; i++) {
            Cell celdaEncabezado = filaEncabezados.createCell(i);
            celdaEncabezado.setCellStyle(headerStyle);
            celdaEncabezado.setCellValue(cabecera[i]);
        }

       
        conexion cn = new conexion();
        Connection con = cn.conectar();

        String sql = """
            SELECT l.id, p.nombre, l.codigo_lote, l.cantidad, l.fecha_vencimiento
            FROM lotes l
            INNER JOIN productos p ON l.id_producto = p.id
            WHERE l.fecha_vencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY)
            ORDER BY l.fecha_vencimiento ASC
        """;

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, dias);
        ResultSet rs = ps.executeQuery();

 
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);

  
        CreationHelper createHelper = book.getCreationHelper();
        CellStyle dateStyle = book.createCellStyle();
        dateStyle.cloneStyleFrom(datosEstilo);
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        int numFilaDatos = 5;
        while (rs.next()) {
            Row filaDatos = sheet.createRow(numFilaDatos);

     
            Cell celdaId = filaDatos.createCell(0);
            celdaId.setCellStyle(datosEstilo);
            celdaId.setCellValue(rs.getInt("id"));

      
            Cell celdaProd = filaDatos.createCell(1);
            celdaProd.setCellStyle(datosEstilo);
            celdaProd.setCellValue(rs.getString("nombre"));

  
            Cell celdaCod = filaDatos.createCell(2);
            celdaCod.setCellStyle(datosEstilo);
            celdaCod.setCellValue(rs.getString("codigo_lote"));

            Cell celdaCant = filaDatos.createCell(3);
            celdaCant.setCellStyle(datosEstilo);
            celdaCant.setCellValue(rs.getInt("cantidad"));

            Cell celdaFec = filaDatos.createCell(4);
            celdaFec.setCellStyle(dateStyle);
            celdaFec.setCellValue(rs.getDate("fecha_vencimiento"));

            numFilaDatos++;
        }

       
        for (int i = 0; i < cabecera.length; i++) {
            sheet.autoSizeColumn(i);
        }

        sheet.setZoom(140);

       
        String userHome = System.getProperty("user.home");
        File carpetaDocs = new File(userHome, "Documents");
        if (!carpetaDocs.exists()) {
            carpetaDocs = new File(userHome, "Documentos");
        }

        File file = new File(carpetaDocs, "Lotes_Por_Vencer.xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            book.write(fileOut);
        }

        rs.close();
        ps.close();
        con.close();

      
        Desktop.getDesktop().open(file);
        JOptionPane.showMessageDialog(null, "Reporte de Lotes por Vencer generado con éxito");

    } catch (IOException | SQLException ex) {
        Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte: " + ex.getMessage());
    }
}

}
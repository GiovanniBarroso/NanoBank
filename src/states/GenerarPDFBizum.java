package states;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import sqlconnect.Conexion;

public class GenerarPDFBizum {

	private static final String OUTPUT_PDF_FILE = "Registro_Bizum";

	public static void generarPDFBizum(int id_usuario, String nombreUsuario) {
		String outputFileName = OUTPUT_PDF_FILE + " " + nombreUsuario + ".pdf";
		Document document = new Document(PageSize.A4);

		try {
			FileOutputStream outputStream = new FileOutputStream(outputFileName);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();

			// Encabezado del documento
			Paragraph header = new Paragraph("NANOBANK\nBIZUM", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 40));
			header.setAlignment(Element.ALIGN_LEFT);

			// Añadir logo
			Image logo = Image.getInstance("src/img/foto_7.png");
			logo.scaleToFit(100, 100);
			logo.setAlignment(Element.ALIGN_RIGHT);
			document.add(logo);
			

			// Crear una tabla para alinear el título y el logo
			PdfPTable titleTable = new PdfPTable(2);
			titleTable.setWidthPercentage(100);
			PdfPCell titleCell = new PdfPCell(header);
			PdfPCell logoCell = new PdfPCell(logo);

			// Ajustar las celdas para que no tengan bordes
			titleCell.setBorder(PdfPCell.NO_BORDER);
			logoCell.setBorder(PdfPCell.NO_BORDER);

			// Alinear el contenido de las celdas al centro verticalmente
			titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			// Añadir las celdas a la tabla
			titleTable.addCell(titleCell);
			titleTable.addCell(logoCell);

			// Añadir la tabla al documento
			document.add(titleTable);

			// Información del usuario
			Paragraph userInfo = new Paragraph("\nID_USUARIO: " + id_usuario + "\nUSUARIO: " + nombreUsuario, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 15));
			document.add(userInfo);

			// Espacio en blanco
			document.add(new Paragraph("\n\n"));

			PdfPTable table = createBizumTable();
			fillBizumTable(table, id_usuario);

			document.add(table);

			System.out.println("\nRegistro_Bizum.pdf generado correctamente.");
		} catch (Exception e) {
			System.err.println("Error al generar el PDF de Bizum: " + e.getMessage());
		} finally {
			if (document != null && document.isOpen()) {
				document.close();
			}
		}
	}

	private static PdfPTable createBizumTable() {
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);

		String[] headers = {"ID Transacción", "Cuenta Origen", "Teléfono", "Nombre Destino", "Cantidad", "Concepto"};
		for (String header : headers) {
			PdfPCell cell = new PdfPCell();
			cell.addElement(new Paragraph(header, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
			table.addCell(cell);
		}

		return table;
	}



	private static void fillBizumTable(PdfPTable table, int id_usuario) throws SQLException {
		Conexion conexion = new Conexion();

		try (Connection con = conexion.conectar();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM Bizum WHERE id_usuario = ?");
				) {

			ps.setInt(1, id_usuario); 
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PdfPCell cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("id_transaciones"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("cuentaOrigen"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("telefono"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("nombreDestino"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("cantidad"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("concepto"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

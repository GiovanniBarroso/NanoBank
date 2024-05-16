package states;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import sqlconnect.Conexion;

public class GenerarPDFCarteras {

	private static final String OUTPUT_PDF_FILE = "Registro_Cartera ";

	public static void generarPDFCartera(int id_usuario, String nombreUsuario) {
		String outputFileName = OUTPUT_PDF_FILE + nombreUsuario + ".pdf";
		Document document = new Document(PageSize.A4);

		try {

			FileOutputStream outputStream = new FileOutputStream(outputFileName);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();

			// Encabezado del documento
			Paragraph header = new Paragraph("NANOBANK\nCARTERA", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 40));
			header.setAlignment(Element.ALIGN_CENTER);
			document.add(header);

			// Información del usuario
			Paragraph userInfo = new Paragraph("\nID_USUARIO: " + id_usuario + "\nUSUARIO: " + nombreUsuario, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 15));
			document.add(userInfo);


			document.add(new Paragraph("\n\n"));

			PdfPTable table = createCarterasTable();
			fillCarterasTable(table, id_usuario);

			document.add(table);

			System.out.println("\nRegistro_Cartera.pdf generado correctamente.");
		} catch (Exception e) {
			System.err.println("Error al generar el PDF de CARTERAS: " + e.getMessage());
		} finally {
			if (document != null && document.isOpen()) {
				document.close();
			}
		}
	}

	private static PdfPTable createCarterasTable() {
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);

		String[] headers = {"ID Cartera", "porcentajeRF", "porcentajeRV", "cantidadInvertida"};
		for (String header : headers) {
			PdfPCell cell = new PdfPCell();
			cell.addElement(new Paragraph(header, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
			table.addCell(cell);
		}

		return table;
	}




	private static void fillCarterasTable(PdfPTable table, int id_usuario) throws SQLException {
		Conexion conexion = new Conexion();

		try (Connection con = conexion.conectar();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM FormularioCarteras WHERE id_usuario = ?");
				) {

			ps.setInt(1, id_usuario); 
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PdfPCell cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("id_cartera"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("porcentajeRF"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("porcentajeRV"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Paragraph(rs.getString("cantidadInvertida"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
				table.addCell(cell);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
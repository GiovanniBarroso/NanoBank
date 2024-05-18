package states;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;


import sqlconnect.Conexion;

public class GenerarPDFTransferencias {

	private static final String OUTPUT_PDF_FILE = "Registro_Transferencias ";

	public static void generarPDFTransferencias(int id_usuario, String nombreUsuario) {

		// Obtiene la ruta del directorio de trabajo actual
		String currentDir = System.getProperty("user.dir");

		// Define la carpeta donde se guardarán los PDF dentro del directorio de trabajo actual
		String folderPath = currentDir + "/PDF Consultas";

		// Crea el directorio si no existe
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		// Concatena la ruta de la carpeta con el nombre de archivo
		String outputFileName = folderPath + "/" + OUTPUT_PDF_FILE + nombreUsuario + ".pdf";
		Document document = new Document(PageSize.A4);

		try {

			FileOutputStream outputStream = new FileOutputStream(outputFileName);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();


			Paragraph header = new Paragraph("NANOBANK\nTRANSFERENCIA", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 40));
			header.setAlignment(Element.ALIGN_LEFT);


			Image logo = Image.getInstance("src/img/foto_7.png");
			logo.scaleToFit(100, 100);

			PdfPTable titleTable = new PdfPTable(2);
			titleTable.setWidthPercentage(100);

			// Ancho de las celdas (ajustar según sea necesario)
			float[] columnWidths = {80f, 20f}; 
			titleTable.setWidths(columnWidths);

			PdfPCell titleCell = new PdfPCell(header);
			PdfPCell logoCell = new PdfPCell(logo);

			titleCell.setBorder(PdfPCell.NO_BORDER);
			logoCell.setBorder(PdfPCell.NO_BORDER);

			titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			titleTable.addCell(titleCell);
			titleTable.addCell(logoCell);

			document.add(titleTable);


			// Información del usuario
			Paragraph userInfo = new Paragraph("\nID_USUARIO: " + id_usuario + "\nUSUARIO: " + nombreUsuario, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 15));
			document.add(userInfo);


			Paragraph dateInfo = new Paragraph("FECHA: " + new Date().toString(), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 15));
			document.add(dateInfo);


			document.add(new Paragraph("\n\n"));


			PdfPTable table = createTransferenciasTable();
			fillTransferenciasTable(table, id_usuario);
			document.add(table);

			System.out.println("\nRegistro_Transferencias.pdf generado correctamente.");


		} catch (Exception e) {
			System.err.println("Error al generar el PDF de transferencias: " + e.getMessage());
		} finally {
			if (document != null && document.isOpen()) {
				document.close();
			}
		}
	}



	private static PdfPTable createTransferenciasTable() {
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);

		String[] headers = {"ID Transacción", "Cuenta Origen", "Cuenta Destino", "Nombre Destino", "Cantidad", "Concepto"};
		for (String header : headers) {
			PdfPCell cell = new PdfPCell();
			cell.addElement(new Paragraph(header, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
			table.addCell(cell);
		}

		return table;

	}



	private static void fillTransferenciasTable(PdfPTable table, int id_usuario) throws SQLException {
		Conexion conexion = new Conexion();

		try (Connection con = conexion.conectar();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM Transferencia WHERE id_usuario = ?");
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
				cell.addElement(new Paragraph(rs.getString("cuentaDestino"), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
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
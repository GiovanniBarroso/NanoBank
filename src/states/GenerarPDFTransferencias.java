package states;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import sqlconnect.Conexion;

public class GenerarPDFTransferencias {

	private static final String OUTPUT_PDF_FILE = "transferencias_registros.pdf";

	public static void generarPDFTransferencias() {
		Document document = new Document(PageSize.A4);
		try {
			FileOutputStream outputStream = new FileOutputStream(OUTPUT_PDF_FILE);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();

			PdfPTable table = createTransferenciasTable();
			fillTransferenciasTable(table);

			document.add(table);

			System.out.println("PDF de transferencias generado correctamente.");
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
			table.addCell(header);
		}

		return table;
	}



	private static void fillTransferenciasTable(PdfPTable table) throws SQLException {
		Conexion conexion = new Conexion();
		try (Connection con = conexion.conectar();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM Transferencia");
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				table.addCell(rs.getString("id_transaciones"));
				table.addCell(rs.getString("cuentaOrigen"));
				table.addCell(rs.getString("cuentaDestino"));
				table.addCell(rs.getString("nombreDestino"));
				table.addCell(rs.getString("cantidad"));
				table.addCell(rs.getString("concepto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
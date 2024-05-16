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

public class GenerarPDFCarteras {

	private static final String OUTPUT_PDF_FILE = "Registro_Carteras";

	public static void generarPDFCarteras(int id_usuario, String nombreUsuario) {
		String outputFileName = OUTPUT_PDF_FILE + " " + nombreUsuario + ".pdf";
		Document document = new Document(PageSize.A4);
		try {
			FileOutputStream outputStream = new FileOutputStream(outputFileName);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();

			PdfPTable table = createCarterasTable();
			fillCarterasTable(table, id_usuario);

			document.add(table);

			System.out.println("PDF de carteras generado correctamente.");
		} catch (Exception e) {
			System.err.println("Error al generar el PDF de carteras: " + e.getMessage());
		} finally {
			if (document != null && document.isOpen()) {
				document.close();
			}
		}
	}



	private static PdfPTable createCarterasTable() {
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);

		String[] headers = {"ID Cartera", "Porcentaje RF", "Porcentaje RV", "Cantidad Invertida"};
		for (String header : headers) {
			table.addCell(header);
		}

		return table;
	}



	private static void fillCarterasTable(PdfPTable table, int id_usuario) throws SQLException {
	    Conexion conexion = new Conexion();
	    try (Connection con = conexion.conectar();
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM FormularioCarteras WHERE id_usuario = ?");
	            ) {
	        ps.setInt(1, id_usuario); // Establecer el parámetro id_usuario en la consulta
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            table.addCell(rs.getString("id_cartera"));
	            table.addCell(rs.getString("porcentajeRF"));
	            table.addCell(rs.getString("porcentajeRV"));
	            table.addCell(rs.getString("cantidadInvertida"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
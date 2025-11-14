package controllers;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import models.DetalleCarro;
import models.ItemCarro;
import services.LoginService;
import services.LoginServiceImplement;

@WebServlet({"/descargar-factura"})
public class GenerarFacturaPdfServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DetalleCarro detalleCarro = (DetalleCarro)session.getAttribute("carro");
        LoginService auth = new LoginServiceImplement();
        Optional<String> usernameOptional = auth.getUsername(req);
        if (detalleCarro != null && !detalleCarro.getItem().isEmpty()) {
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=factura_" + System.currentTimeMillis() + ".pdf");

            try {
                Document document = new Document(PageSize.A4);
                OutputStream out = resp.getOutputStream();
                PdfWriter.getInstance(document, out);
                document.open();
                Font titleFont = new Font(FontFamily.HELVETICA, 20.0F, 1, new BaseColor(0, 255, 0));
                new Font(FontFamily.HELVETICA, 12.0F, 1, BaseColor.BLACK);
                Font normalFont = new Font(FontFamily.HELVETICA, 10.0F, 0, BaseColor.BLACK);
                Font boldFont = new Font(FontFamily.HELVETICA, 10.0F, 1, BaseColor.BLACK);
                Paragraph title = new Paragraph("FACTURA DE COMPRA", titleFont);
                title.setAlignment(1);
                title.setSpacingAfter(20.0F);
                document.add(title);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String fecha = sdf.format(new Date());
                String numeroFactura = "F-" + System.currentTimeMillis();
                Paragraph infoFactura = new Paragraph();
                infoFactura.add(new Chunk("Número de Factura: ", boldFont));
                infoFactura.add(new Chunk(numeroFactura + "\n", normalFont));
                infoFactura.add(new Chunk("Fecha: ", boldFont));
                infoFactura.add(new Chunk(fecha + "\n", normalFont));
                if (usernameOptional.isPresent()) {
                    infoFactura.add(new Chunk("Cliente: ", boldFont));
                    infoFactura.add(new Chunk((String)usernameOptional.get() + "\n", normalFont));
                }

                infoFactura.setSpacingAfter(20.0F);
                document.add(infoFactura);
                document.add(new Paragraph("_____________________________________________________________"));
                document.add(Chunk.NEWLINE);
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100.0F);
                table.setSpacingBefore(10.0F);
                table.setSpacingAfter(20.0F);
                float[] columnWidths = new float[]{1.5F, 3.0F, 2.0F, 2.0F, 2.0F};
                table.setWidths(columnWidths);
                BaseColor headerColor = new BaseColor(0, 200, 0);
                Font tableHeaderFont = new Font(FontFamily.HELVETICA, 10.0F, 1, BaseColor.WHITE);
                String[] headers = new String[]{"ID", "Nombre", "Precio", "Cantidad", "Subtotal"};

                for(String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
                    cell.setBackgroundColor(headerColor);
                    cell.setHorizontalAlignment(1);
                    cell.setPadding(8.0F);
                    table.addCell(cell);
                }

                for(ItemCarro item : detalleCarro.getItem()) {
                    PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(item.getProducto().getIdProducto()), normalFont));
                    cellId.setHorizontalAlignment(1);
                    cellId.setPadding(5.0F);
                    table.addCell(cellId);
                    PdfPCell cellNombre = new PdfPCell(new Phrase(item.getProducto().getNombre(), normalFont));
                    cellNombre.setPadding(5.0F);
                    table.addCell(cellNombre);
                    PdfPCell cellPrecio = new PdfPCell(new Phrase("$" + String.format("%.2f", item.getProducto().getPrecio()), normalFont));
                    cellPrecio.setHorizontalAlignment(2);
                    cellPrecio.setPadding(5.0F);
                    table.addCell(cellPrecio);
                    PdfPCell cellCantidad = new PdfPCell(new Phrase(String.valueOf(item.getCantidad()), normalFont));
                    cellCantidad.setHorizontalAlignment(1);
                    cellCantidad.setPadding(5.0F);
                    table.addCell(cellCantidad);
                    PdfPCell cellSubtotal = new PdfPCell(new Phrase("$" + String.format("%.2f", item.getSubtotal()), normalFont));
                    cellSubtotal.setHorizontalAlignment(2);
                    cellSubtotal.setPadding(5.0F);
                    table.addCell(cellSubtotal);
                }

                document.add(table);
                PdfPTable tableTotales = new PdfPTable(2);
                tableTotales.setWidthPercentage(50.0F);
                tableTotales.setHorizontalAlignment(2);
                tableTotales.setWidths(new float[]{3.0F, 2.0F});
                PdfPCell cellSubtotalLabel = new PdfPCell(new Phrase("Subtotal:", boldFont));
                cellSubtotalLabel.setBorder(0);
                cellSubtotalLabel.setHorizontalAlignment(2);
                cellSubtotalLabel.setPadding(5.0F);
                tableTotales.addCell(cellSubtotalLabel);
                PdfPCell cellSubtotalValue = new PdfPCell(new Phrase("$" + String.format("%.2f", detalleCarro.getSubtotal()), normalFont));
                cellSubtotalValue.setBorder(0);
                cellSubtotalValue.setHorizontalAlignment(2);
                cellSubtotalValue.setPadding(5.0F);
                tableTotales.addCell(cellSubtotalValue);
                PdfPCell cellIvaLabel = new PdfPCell(new Phrase("IVA (15%):", boldFont));
                cellIvaLabel.setBorder(0);
                cellIvaLabel.setHorizontalAlignment(2);
                cellIvaLabel.setPadding(5.0F);
                tableTotales.addCell(cellIvaLabel);
                PdfPCell cellIvaValue = new PdfPCell(new Phrase("$" + String.format("%.2f", detalleCarro.getIva()), normalFont));
                cellIvaValue.setBorder(0);
                cellIvaValue.setHorizontalAlignment(2);
                cellIvaValue.setPadding(5.0F);
                tableTotales.addCell(cellIvaValue);
                Font totalFont = new Font(FontFamily.HELVETICA, 12.0F, 1, new BaseColor(0, 150, 0));
                PdfPCell cellTotalLabel = new PdfPCell(new Phrase("TOTAL:", totalFont));
                cellTotalLabel.setBorder(1);
                cellTotalLabel.setHorizontalAlignment(2);
                cellTotalLabel.setPadding(8.0F);
                tableTotales.addCell(cellTotalLabel);
                PdfPCell cellTotalValue = new PdfPCell(new Phrase("$" + String.format("%.2f", detalleCarro.getTotal()), totalFont));
                cellTotalValue.setBorder(1);
                cellTotalValue.setHorizontalAlignment(2);
                cellTotalValue.setPadding(8.0F);
                tableTotales.addCell(cellTotalValue);
                document.add(tableTotales);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                Paragraph footer = new Paragraph("Gracias por su compra", new Font(FontFamily.HELVETICA, 10.0F, 2, BaseColor.GRAY));
                footer.setAlignment(1);
                document.add(footer);
                document.close();
                out.close();
            } catch (DocumentException e) {
                throw new IOException("Error al generar el PDF", e);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/ver-carro");
        }
    }
}


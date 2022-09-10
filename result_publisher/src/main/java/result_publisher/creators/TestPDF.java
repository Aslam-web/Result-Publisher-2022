package result_publisher.creators;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import result_publisher.App;
import result_publisher.models.Certificate_One;

public class TestPDF {

	private Certificate_One certificate;
	private String SRC;
	private PdfCanvas pdfCanvas;
	private Canvas canvas;

	public static final String PDF_DATA_STORE = App.DATA_STORE + "certificates/";
//	private String description = "Successfully completing the academcic year 2020-2021 with excellent performance";
//	private String signature = "data/others/sign_1.png";
//	private String date = new SimpleDateFormat("dd MMMM yyyy").format(new Date());
//	private String stamp = "data/others/stamp_1.png";

	public TestPDF(Certificate_One certificate) {
		this.certificate = certificate;
		this.SRC = certificate.getCertificateAsPdf();
	}

	public static void main(String[] args) throws IOException {
		new TestPDF(new Certificate_One()).writeName("M.N Aslam");
	}

	private void writeName(String studentName) throws IOException {

//		String SRC = PDF_DATA_STORE + "empty pdf.pdf";
		String destination = PDF_DATA_STORE + "Test PDF1.pdf";
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(destination));
//		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destination));
		
		PdfPage page1 = pdfDoc.getFirstPage();
		pdfCanvas = new PdfCanvas(page1);

		// name
		PdfFont nameFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLDITALIC);

		canvas = new Canvas(pdfCanvas, this.certificate.getNamePosition());
		canvas.setHorizontalAlignment(HorizontalAlignment.CENTER);
		Text name = new Text(studentName)
				.setFont(nameFont)
				.setUnderline()
				.setFontColor(new DeviceRgb(0, 255, 255))
				.setFontSize(42)
				.setTextAlignment(TextAlignment.CENTER);
		Paragraph paragraph = new Paragraph().add(name);
		paragraph.setTextAlignment(TextAlignment.CENTER);
		canvas.add(paragraph);

		// desc
		PdfFont descFont = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);

		canvas = new Canvas(pdfCanvas, this.certificate.getDescriptionPosition());
		canvas.setHorizontalAlignment(HorizontalAlignment.CENTER);
		Text disc = new Text("Some description to display for the reason of award")
				.setFont(descFont)
				.setFontColor(new DeviceRgb(140, 140, 140))
				.setFontSize(24)
				.setTextAlignment(TextAlignment.CENTER);
		paragraph = new Paragraph().add(disc);
		paragraph.setTextAlignment(TextAlignment.CENTER);
		canvas.add(paragraph);
		
		// signature
		Image signature = new Image(ImageDataFactory.create("data/others/sign_1.png"));
		Rectangle rect = this.certificate.getSignaturePosition();

		canvas = new Canvas(pdfCanvas, rect);
		signature.scaleAbsolute(rect.getWidth(), rect.getHeight());
		canvas.add(signature);
		
		// date
		PdfFont dateFont = PdfFontFactory.createFont(FontProgramFactory.createFont("fonts/Fondamento-Regular.ttf"));

		canvas = new Canvas(pdfCanvas, this.certificate.getDatePosition());
		Text date = new Text(new SimpleDateFormat("dd MMMM yyyy").format(new Date()))
				.setFont(dateFont)
				.setFontColor(new DeviceRgb(255, 0, 0))
				.setFontSize(25)
				.setTextAlignment(TextAlignment.CENTER);
		paragraph = new Paragraph().add(date);
		paragraph.setTextAlignment(TextAlignment.CENTER);
		canvas.add(paragraph);
		
		// end
		canvas.close();
		pdfDoc.close();

		System.out.println("\t\tSuccessfully created !");
		System.out.println("-----------------------------------------------------");
	}

}

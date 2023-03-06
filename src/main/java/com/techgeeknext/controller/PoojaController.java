package com.techgeeknext.controller;

import com.techgeeknext.model.Pooja;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PoojaController {

	@GetMapping("/pooja/records/report")
	public ResponseEntity<byte[]> getEmployeeRecordReport() {

		try {
			// create pooja data

			List<Pooja> poojaLst = new ArrayList<Pooja>(){{
				add(new Pooja(1,"INV1","നെയ്യഭിഷേകം (1 മുദ്ര )",2,10.0,20.0,"സന്നിദാനം അഞ്ചൽ "));
				add(new Pooja(2,"INV2","നിറയും പുത്തരിയും.",2,10.0,20.0,"സന്നിദാനം അഞ്ചൽ "));
			}};


			//dynamic parameters required for report
			Map<String, Object> poojaParams = new HashMap<String, Object>();
			poojaParams.put("fromInvoiceNo", 1000l);
			poojaParams.put("toInvoiceNo", 20000l);
			poojaParams.put("temple", "ശബരിമല മകരവിളക്ക് ആജ്ഞാ ");
			poojaParams.put("userName", "User name");
			poojaParams.put("ds", new JRBeanCollectionDataSource(poojaLst));

			JasperPrint poojaReport =
					JasperFillManager.fillReport
				   (
							JasperCompileManager.compileReport(
							ResourceUtils.getFile("classpath:bill.jrxml")
									.getAbsolutePath()) // path of the jasper report
							, poojaParams // dynamic parameters
							, new JREmptyDataSource()
					);

			HttpHeaders headers = new HttpHeaders();
			//set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "poojas-details.pdf");
			//create the report in PDF format
			return new ResponseEntity<byte[]>
					(JasperExportManager.exportReportToPdf(poojaReport), headers, HttpStatus.OK);
			
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

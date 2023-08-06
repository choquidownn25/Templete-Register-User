/**
 * 
 */
package org.exemple.data;

import lombok.*;

import java.util.ArrayList;

/**
 * @author Madre Hermosa
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CertificadoBeneficios {
	 	private String code;
	    private String caseid;
	    private String msg;
	    private ArrayList<Periodo> data;
	    private String pdf_boleta;

	    
}

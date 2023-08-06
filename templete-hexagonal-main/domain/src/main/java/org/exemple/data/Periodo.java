/**
 * 
 */
package org.exemple.data;

import lombok.*;

/**
 * @author Madre Hermosa
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Periodo {

	 	private String fecha_sol;
	    private String n_sol;
	    private String nombre_emp;
	    private String tipo_sol;
	    private String fecha_finiquito;
	    private String fecha_ult_giro;
	    private String giros_pagados;
	    //@JsonProperty("CIC") 
	    private String cIC;
	    //@JsonProperty("FCS") 
	    private String fCS;
	    private String totales_pagados;

	    
}

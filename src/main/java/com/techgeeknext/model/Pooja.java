package com.techgeeknext.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pooja {

	private int id;
	private String invoiceNo;
	private String poojaName;
	private Integer quantity;
	private Double singleAmount;
	private Double amount;
	private String address;


}

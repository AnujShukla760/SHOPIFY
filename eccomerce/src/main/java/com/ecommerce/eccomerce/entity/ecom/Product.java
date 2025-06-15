package com.ecommerce.eccomerce.entity.ecom;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String productname;
	private String hsnCode;
	private String barCode;
	private String modelNo;
	private String serialNo;

	@Lob
	private String description;

	private Integer quantity;
	private Integer minimumStock;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	List<ProductImages> productImages = new ArrayList<ProductImages>();

	@ManyToOne
	ProductSubCategory productSubCategory;

	private Double costPrice;

	private Double mrp;

	@ManyToOne
	private TaxPercentage taxPercentage;

	@ManyToOne
	private Brand brand;

}

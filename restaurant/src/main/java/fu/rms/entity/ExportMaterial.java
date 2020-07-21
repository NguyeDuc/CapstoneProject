package fu.rms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "export_material")
@Getter
@Setter
public class ExportMaterial {

	@Id
	@Column(name="export_material_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long exportMaterialId;
	
	@Column(name="quantity")	// số lượng theo đơn vị xuất
	private Double quantity;
	
	@Column(name="price")		// giá xuất
	private Double price;
	
	@Column(name="sum_price")
	private Double sumPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="export_id")
	private Export export;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="material_id")
	private Material material;
}
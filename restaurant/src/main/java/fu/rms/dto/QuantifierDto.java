package fu.rms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class QuantifierDto {

	private Long quantifierId;
	
	private Double quantity;
	
	private String unit;
	
	private Double cost;
	
	private String description;
	
	private MaterialQuantifier material;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MaterialQuantifier{
		private Long materialId;
		private String materialName;
		private String unit;		// theo đơn vị xuất
		private Double unitPrice;	// theo đơn vị xuất
	}
	
	
	
}
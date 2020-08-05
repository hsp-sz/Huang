package cn.keyi.bye.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="artifact")
public class Artifact  implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "artifactId")
	private Long artifactId;
	// 工件（产品）名称
	@Column(name = "artifactName")
	private String artifactName;
	// 产品（工件）代号
	@Column(name = "artifactCode")
	private String artifactCode;
	// 重量
	@Column(name = "weight")
	private Float weight;
	// 材料代码
	@Column(name = "materialCode")
	private String materialCode;
	// 材料名称
	@Column(name = "materialName")
	private String materialName;
	// 用于标记此工件是否是顶层工件，即产品。0-产品，非0-工件
	@Column(name = "productFlag")
	private Short productFlag = 1;
	// 标记此工件是否可以继续分解（拥有下一级明细），目前代码以8ZT开头的工件为不可分解，其它工件为可分解
	@Column(name = "canBeSplit")
	private Boolean canBeSplit = Boolean.TRUE;
	// 备注
	@Column(name = "artifactMemo")
	private String artifactMemo;
	// 明细中的下级零件
	@OneToMany(mappedBy = "master")
	@JsonIgnore
	private List<ArtifactDetail> subDetail;
	// 明细中的上级零件
	@OneToMany(mappedBy = "slave")
	@JsonIgnore
	private List<ArtifactDetail> supDetail;
	
	public Long getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(Long artifactId) {
		this.artifactId = artifactId;
	}
	public String getArtifactName() {
		return artifactName;
	}
	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}
	public String getArtifactCode() {
		return artifactCode;
	}
	public void setArtifactCode(String artifactCode) {
		this.artifactCode = artifactCode;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Short getProductFlag() {
		return productFlag;
	}
	public void setProductFlag(Short productFlag) {
		this.productFlag = productFlag;
	}
	public Boolean getCanBeSplit() {
		return canBeSplit;
	}
	public void setCanBeSplit(Boolean canBeSplit) {
		this.canBeSplit = canBeSplit;
	}
	public String getArtifactMemo() {
		return artifactMemo;
	}
	public void setArtifactMemo(String artifactMemo) {
		this.artifactMemo = artifactMemo;
	}
	public List<ArtifactDetail> getSubDetail() {
		return subDetail;
	}
	public void setSubDetail(List<ArtifactDetail> subDetail) {
		this.subDetail = subDetail;
	}
	public List<ArtifactDetail> getSupDetail() {
		return supDetail;
	}
	public void setSupDetail(List<ArtifactDetail> supDetail) {
		this.supDetail = supDetail;
	}	

}

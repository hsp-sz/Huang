package cn.keyi.bye.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import cn.keyi.bye.model.Artifact;

public interface ArtifactDao extends JpaRepository<Artifact, Long> {
	
	// 根据工件名称进行模糊查询，允许分页
	public Page<Artifact> findByArtifactNameContaining(String artifactName, Pageable pageable);
	// 根据工件类型进行查询，主要用于过滤顶层产品和下级零件
	public Page<Artifact> findByProductFlag(Short productFlag, Pageable pageable);
	// 根据工件名称进行模糊查询，附带工件类型
	public Page<Artifact> findByArtifactNameContainingAndProductFlag(String artifactName, Short productFlag, Pageable pageable);

}

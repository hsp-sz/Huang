package cn.keyi.bye.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.keyi.bye.dao.ArtifactDao;
import cn.keyi.bye.model.Artifact;

@Service
public class ArtifactService {
	
	@Autowired
	ArtifactDao artifactDao;
	
	/**
	 * comment: 根据给定id获取对应的工件
	 * author : 兴有林栖
	 * date   : 2020-8-1
	 * @param artifactId: 工件Id
	 * @return
	 */
	public Artifact getArtifactById(Long artifactId) {
		Optional<Artifact> findResult = artifactDao.findById(artifactId);
		if(findResult.isPresent()) {
			return findResult.get();
		} else {
			return null;
		}
	}
	
	/**
	 * comment: 以分页的方式查询产品（工件）列表
	 * author : 兴有林栖
	 * date   : 2020-8-1
	 * @param artifactName: 工件名称，允许空
	 * @param productFlag : 工件类型标识，0-顶层产品，1-普通工件，-1-查询时忽略此参数
	 * @param pageable
	 * @return
	 */
	public Page<Artifact> getArtifactsByPage(String artifactName, Short productFlag, Pageable pageable) {
		if(productFlag == -1) {
			return artifactDao.findByArtifactNameContaining(artifactName, pageable);
		} else {
			return artifactDao.findByArtifactNameContainingAndProductFlag(artifactName, productFlag, pageable);
		}		
	}
}

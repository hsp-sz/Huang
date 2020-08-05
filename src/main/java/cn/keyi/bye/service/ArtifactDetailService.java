package cn.keyi.bye.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.keyi.bye.dao.ArtifactDetailDao;
import cn.keyi.bye.model.Artifact;
import cn.keyi.bye.model.ArtifactDetail;

@Service
public class ArtifactDetailService {

	@Autowired
	ArtifactDetailDao artifactDetailDao;
	
	/**
	 * comment: 根据一个明细ID获取该明细记录
	 * author : 兴有林栖
	 * date   : 2020-8-1
	 * @param detailId
	 * @return
	 */
	public ArtifactDetail getArtifactDetailById(Long detailId) {
		Optional<ArtifactDetail> findResult = artifactDetailDao.findById(detailId);
		if(findResult.isPresent()) {
			return findResult.get();
		} else {
			return null;
		}
	}
	
	// 根据指定的工件ID查询其包含的下层（单层）工件组成明细
	public List<ArtifactDetail> getDetailByMasterId(Long masterId) {
		return artifactDetailDao.findByMasterArtifactId(masterId);
	}
	
	// 根据指定的工件ID以分页的形式查询其包含的下层（单层）工件组成明细
	public Page<ArtifactDetail> getDetailByMasterId(Long masterId, Pageable pageable) {
		return artifactDetailDao.findByMasterArtifactId(masterId, pageable);
	}
	
	/**
	 * comment: 指定一个工件，使用递归方式获取从属于它的所有下级工件，并组装成一个列表
	 * author : 兴有林栖
	 * date   : 2020-8-1
	 * @param master: 遍历的顶层零件（出发点）
	 * @param times : 倍数，即层级之间的零件数量倍数
	 * @param recursiveDetail: 存储着各级包含下级零件的列表对象
	 */
	public void findRecursiveDetailByMaster(Artifact master, int times, List<Map<String, Object>> recursiveDetail) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("artifact", master);
		List<ArtifactDetail> detail = this.getDetailByMasterId(master.getArtifactId());
		map.put("detail", detail);
		map.put("times", times);
		recursiveDetail.add(map);
		for(ArtifactDetail detailItem : detail) {
			if(detailItem.getNeedSplit()) {
				int newtimes = times * detailItem.getNumber();
				findRecursiveDetailByMaster(detailItem.getSlave(), newtimes, recursiveDetail);
			}			
		}
		return;
	}
	
}

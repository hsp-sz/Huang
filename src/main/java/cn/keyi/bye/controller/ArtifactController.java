package cn.keyi.bye.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.keyi.bye.model.Artifact;
import cn.keyi.bye.model.ArtifactDetail;
import cn.keyi.bye.service.ArtifactDetailService;
import cn.keyi.bye.service.ArtifactService;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {

	@Autowired
	ArtifactService artifactService;
	@Autowired
	ArtifactDetailService artifactDetailService;
	
	/**
	 * comment: 以分页的方式查询产品（工件）列表
	 * author : 兴有林栖
	 * date   : 2020-8-1
	 * @param artifactName: 工件名称，允许空
	 * @param productFlag : 工件类型标识，0-顶层产品，1-普通工件，-1-查询时忽略此参数
	 * @param pageNumber  : 页码
	 * @param pageSize    : 页大小
	 * @return
	 */
	@RequestMapping("/findArtifacts")
	public Object findArtifacts(HttpServletRequest request) {
		int draw = Integer.parseInt(request.getParameter("draw"));			// DataTable 要求要返回的参数
		int pageNumber = Integer.parseInt(request.getParameter("start"));	// 记录起始编号
		int pageSize = Integer.parseInt(request.getParameter("length"));	// 页大小
		pageNumber = pageSize <= 0 ? 1 : pageNumber / pageSize;				// 计算页码
		String artifactName = request.getParameter("artifactName");			// 工件名称
		short productFlag = (short) Integer.parseInt(request.getParameter("productFlag"));	// 工件类别	
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		Page<Artifact> page = artifactService.getArtifactsByPage(artifactName, productFlag, pageable);
		// 下面代码为满足DataTable插件要求而进行的组装
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("draw", draw);
		map.put("recordsTotal", page.getTotalElements());
		map.put("recordsFiltered", page.getTotalElements());
		map.put("data", page.getContent());
		return map;
	}
	
	@RequestMapping("/findDetailById")
	public Object findDetailById(Long detailId) {
		ArtifactDetail detail = artifactDetailService.getArtifactDetailById(detailId);
		if(detail == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("status", 0);
			map.put("message", "未找到指定的明细记录！");
			return map;
		} else {
			return detail.getSlave();
		}
	}
	
	@RequestMapping("/findDetailByMasterId")
	public List<ArtifactDetail> findDetailByMasterId(Long masterId) {
		return artifactDetailService.getDetailByMasterId(masterId);
	}
	
	/**
	 * comment: 根据给出的工件Id, 以递归的方式获取其下层所有零件
	 * author : 兴有林栖
	 * date   : 2020-8-1
	 * @param masterId : 指定的工件Id
	 * @return
	 */
	@RequestMapping("/findRecursiveDetail")
	public List<Map<String, Object>> findRecursiveDetail(Long masterId) {
		List<Map<String, Object>> listDetail = new ArrayList<Map<String, Object>>();
		Artifact master = artifactService.getArtifactById(masterId);
		int times = 1;	// 初始倍数置为1, 即最开始要遍历下级零件的产品看成是1件
		if(master != null) {			
			artifactDetailService.findRecursiveDetailByMaster(master, times, listDetail);
		}
		return listDetail;
	}
}

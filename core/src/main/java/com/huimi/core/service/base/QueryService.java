package com.huimi.core.service.base;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.baseMapper.QueryMapper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.entity.dtgrid.QueryUtils;
import com.huimi.common.page.BasePageParams;
import com.huimi.common.page.PageBean;
import com.huimi.common.page.Pages;
import com.huimi.common.utils.JsonUtils;
import com.huimi.core.exception.DtGridException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface QueryService<PK, PO extends GenericPo<PK>> {

    QueryMapper<PO, PK> _getQueryMapper();

    /**
     * 分页查询
     */
    default DtGrid getDtGridList(String dtGridPager) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> hashMap = new HashMap<>();
            DtGrid dtGrid = mapper.readValue(dtGridPager, DtGrid.class);
            if (dtGrid.getNcColumnsType() != null && dtGrid.getNcColumnsType().size() > 0) {
                for (String key : dtGrid.getNcColumnsType().keySet()) {
                    for (int i = 0; i < dtGrid.getNcColumnsType().get(key).size(); i++) {
                        hashMap.put((String) dtGrid.getNcColumnsType().get(key).get(i), key);
                    }
                    dtGrid.setNcColumnsTypeList(hashMap);
                }
            }
            // 表格查询参数处理
            QueryUtils.parseDtGridSql(dtGrid);
            // 获取查询条件Sql
            String whereSql = dtGrid.getWhereSql();
            //通过like判断是否是模糊搜索sql，不是直接跳过效验
            //暂时注释，影响激活码列表激活码查询
           /* if (whereSql.contains("like")) {
                //截取sql获取参数
                String[] strs = whereSql.split("%");
                //效验查询参数
                boolean b = SqlInjectVerifyUtils.injectVerify(strs[1]);
                if (b){
                    throw new BussinessException();
                }
            }*/
            // 获取排序Sql
            String sortSql = dtGrid.getSortSql();

            Map<String, Object> params = new HashMap<>();
            params.put("whereSql", whereSql);
            params.put("sortSql", sortSql);
            long recordCount = _getQueryMapper().countBySql(params);
            int pageSize = dtGrid.getPageSize();
            int pageNum = (int) recordCount / dtGrid.getPageSize() + (recordCount % dtGrid.getPageSize() > 0 ? 1 : 0);

            dtGrid.setPageCount(pageNum);
            dtGrid.setRecordCount(recordCount);

            params.put("nowPage", (dtGrid.getNowPage() - 1) * pageSize);
            params.put("pageSize", pageSize);

            List<PO> list = _getQueryMapper().findListBySql(params);
            dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
            }));
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }

    }

    /**
     * 分页查询 (用于Export查询)
     */
    default DtGrid getDtGridList(DtGrid dtGrid) throws DtGridException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> hashMap = new HashMap<>();
            if (dtGrid.getNcColumnsType() != null && dtGrid.getNcColumnsType().size() > 0) {
                for (String key : dtGrid.getNcColumnsType().keySet()) {
                    for (int i = 0; i < dtGrid.getNcColumnsType().get(key).size(); i++) {
                        hashMap.put((String) dtGrid.getNcColumnsType().get(key).get(i), key);
                    }
                    dtGrid.setNcColumnsTypeList(hashMap);
                }
            }
            // 表格查询参数处理
//            QueryUtils.parseDtGridSql(dtGrid);
            // 获取查询条件Sql
            String whereSql = dtGrid.getWhereSql();
            // 获取排序Sql
            String sortSql = dtGrid.getSortSql();

            Map<String, Object> params = new HashMap<>();
            params.put("whereSql", whereSql);
            params.put("sortSql", sortSql);
            long recordCount = _getQueryMapper().countBySql(params);
            int pageSize = dtGrid.getPageSize();
            int pageNum = (int) recordCount / dtGrid.getPageSize() + (recordCount % dtGrid.getPageSize() > 0 ? 1 : 0);

            dtGrid.setPageCount(pageNum);
            dtGrid.setRecordCount(recordCount);

            params.put("nowPage", (dtGrid.getNowPage() - 1) * pageSize);
            params.put("pageSize", pageSize);

            List<PO> list = _getQueryMapper().findListBySql(params);
            dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
            }));
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * 分页查询普通对象, 使用该方法必须再mapper内重写findListBySqlJoin方法 否则报异常(用于Export查询)
     */
    default <G> DtGrid getDtGridList(DtGrid dtGrid, Class<G> g) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> hashMap = new HashMap<>();
            if (dtGrid.getNcColumnsType() != null && dtGrid.getNcColumnsType().size() > 0) {
                for (String key : dtGrid.getNcColumnsType().keySet()) {
                    for (int i = 0; i < dtGrid.getNcColumnsType().get(key).size(); i++) {
                        hashMap.put((String) dtGrid.getNcColumnsType().get(key).get(i), key);
                    }
                    dtGrid.setNcColumnsTypeList(hashMap);
                }
            }
            // 表格查询参数处理
            //QueryUtils.parseDtGridSql(dtGrid);
            // 获取查询条件Sql
            String whereSql = dtGrid.getWhereSql();
            // 获取排序Sql
            String sortSql = dtGrid.getSortSql();

            Map<String, Object> params = new HashMap<>();
            params.put("whereSql", whereSql);
            params.put("sortSql", sortSql);
            long recordCount = _getQueryMapper().countBySqlJoin(params);
            int pageSize = dtGrid.getPageSize();
            int pageNum = (int) recordCount / dtGrid.getPageSize() + (recordCount % dtGrid.getPageSize() > 0 ? 1 : 0);

            dtGrid.setPageCount(pageNum);
            dtGrid.setRecordCount(recordCount);

            params.put("nowPage", (dtGrid.getNowPage() - 1) * pageSize);
            params.put("pageSize", pageSize);

            List<G> list = (List<G>) _getQueryMapper().findListBySqlJoin(params);
            dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
            }));
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * 分页查询普通对象, 使用该方法必须再mapper内重写findListBySqlJoin方法 否则报异常
     *
     * @param dtGridPager 分页对象
     * @return DtGrid
     * @throws DtGridException DtGridEx
     */
    default <G> DtGrid getDtGridList(String dtGridPager, Class<G> g) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> hashMap = new HashMap<>();
            DtGrid dtGrid = mapper.readValue(dtGridPager, DtGrid.class);
            if (dtGrid.getNcColumnsType() != null && dtGrid.getNcColumnsType().size() > 0) {
                for (String key : dtGrid.getNcColumnsType().keySet()) {
                    for (int i = 0; i < dtGrid.getNcColumnsType().get(key).size(); i++) {
                        hashMap.put((String) dtGrid.getNcColumnsType().get(key).get(i), key);
                    }
                    dtGrid.setNcColumnsTypeList(hashMap);
                }
            }
            // 表格查询参数处理
            QueryUtils.parseDtGridSql(dtGrid);
            // 获取查询条件Sql
            String whereSql = dtGrid.getWhereSql();
            // 获取排序Sql
            String sortSql = dtGrid.getSortSql();

            Map<String, Object> params = new HashMap<>();
            params.put("whereSql", whereSql);
            params.put("sortSql", sortSql);
            long recordCount = _getQueryMapper().countBySqlJoin(params);
            int pageSize = dtGrid.getPageSize();
            int pageNum = (int) recordCount / dtGrid.getPageSize() + (recordCount % dtGrid.getPageSize() > 0 ? 1 : 0);

            dtGrid.setPageCount(pageNum);
            dtGrid.setRecordCount(recordCount);

            params.put("nowPage", (dtGrid.getNowPage() - 1) * pageSize);
            params.put("pageSize", pageSize);

            List<G> list = (List<G>) _getQueryMapper().findListBySqlJoin(params);
            dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
            }));
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * 分页查询普通对象, 使用该方法必须再mapper内重写findListBySqlJoin方法 否则报异常
     */
    default <G> DtGrid getDtGridListExport(String dtGridPager, Class<G> g) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            DtGrid dtGrid = mapper.readValue(dtGridPager, DtGrid.class);
            // 默认导出当前页数据（不需要数据库查询），ExportAllData为TRUE导出所有数据（取数据库进行查询）
            if (dtGrid.getExportAllData()) {
                dtGrid.setNowPage(1);
                dtGrid.setPageSize(1000000);
                dtGrid = getDtGridList(dtGrid, g);
                String dataJsonStr = JSON.toJSONString(dtGrid.getExhibitDatas());
                List<Map<String, Object>> exportDatas = mapper.readValue(dataJsonStr, new TypeReference<List<HashMap<String, Object>>>() {
                });
                dtGrid.setExportDatas(exportDatas);
            }
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * excel导出
     */
    default <G> DtGrid getDtGridListExport(DtGrid dtGrid, Class<G> g) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            dtGrid.setNowPage(1);
            dtGrid.setPageSize(1000000);
            dtGrid = getDtGridList(dtGrid, g);
            String dataJsonStr = JSON.toJSONString(dtGrid.getExhibitDatas());
            List<Map<String, Object>> exportDatas = mapper.readValue(dataJsonStr, new TypeReference<List<HashMap<String, Object>>>() {
            });
            dtGrid.setExportDatas(exportDatas);
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * 分页查询普通对象, 使用该方法必须再mapper内重写findListBySqlJoin方法 否则报异常
     */
    default <G> DtGrid getDtGridListExport(String dtGridPager) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            DtGrid dtGrid = mapper.readValue(dtGridPager, DtGrid.class);
            // 默认导出当前页数据（不需要数据库查询），ExportAllData为TRUE导出所有数据（取数据库进行查询）
            if (dtGrid.getExportAllData()) {
                dtGrid.setNowPage(1);
                dtGrid.setPageSize(1000000);
                dtGrid = getDtGridList(dtGrid);
                String dataJsonStr = JSON.toJSONString(dtGrid.getExhibitDatas());
                List<Map<String, Object>> exportDatas = mapper.readValue(dataJsonStr, new TypeReference<List<HashMap<String, Object>>>() {
                });
                dtGrid.setExportDatas(exportDatas);
            }
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * 分页查询普通对象, 使用该方法必须再mapper内重写findListBySqlJoin方法 否则报异常
     */
    default <G> DtGrid getDtGridListExport(DtGrid dtGrid) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            // 默认导出当前页数据（不需要数据库查询），ExportAllData为TRUE导出所有数据（取数据库进行查询）
            if (dtGrid.getExportAllData()) {
                dtGrid.setNowPage(1);
                dtGrid.setPageSize(1000000);
                dtGrid = getDtGridList(dtGrid);
                String dataJsonStr = JSON.toJSONString(dtGrid.getExhibitDatas());
                List<Map<String, Object>> exportDatas = mapper.readValue(dataJsonStr, new TypeReference<List<HashMap<String, Object>>>() {
                });
                dtGrid.setExportDatas(exportDatas);
            }
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * 通用前台分页
     *
     * @param po     查询对象
     * @param params 分页参数
     */
    default Pages<PO> selectByPage(PO po, BasePageParams params) {
        if (po.getDelFlag() == null) po.setDelFlag(GenericPo.DELFLAG.NO.code);
        return selectByPage(params, q -> q.selectCount(po), q -> q.select(po));
    }


    /**
     * 通用前台分页(参考同名方法selectByPage)<br/>
     * 在建立mapper方法时 传入参数和返回对象相同才能使用该分页方法
     *
     * @param params 分页参数
     * @param count  查询总数 mapper方法
     * @param select 查询记录 mapper方法
     * @param <T>    建议model对象
     */
    default <T> Pages<T> selectByPage(BasePageParams params, Function<QueryMapper<PO, PK>, Integer> count, Function<QueryMapper<PO, PK>, List<T>> select) {
        int c = count.apply(_getQueryMapper());
        PageHelper.startPage(params.getPage(), params.getSize(), false);
        List<T> list = select.apply(_getQueryMapper());
        return new Pages<>(list, c, params.getPage(), params.getSize());
    }

    default <T> Pages<T> selectByListPage(BasePageParams params, Function<QueryMapper<PO, PK>, List<T>> select) {
        List<T> lists = select.apply(_getQueryMapper());
        PageHelper.startPage(params.getPage(), params.getSize(), false);
        List<T> list = select.apply(_getQueryMapper());
        return new Pages<>(list, lists.size(), params.getPage(), params.getSize());
    }

    /**
     * 接口端分页 + 排序实现
     */
    default DtGrid selectByPage(DtGrid dtGrid) throws DtGridException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> hashMap = new HashMap<>();
            if (dtGrid.getNcColumnsType() != null && dtGrid.getNcColumnsType().size() > 0) {
                for (String key : dtGrid.getNcColumnsType().keySet()) {
                    for (int i = 0; i < dtGrid.getNcColumnsType().get(key).size(); i++) {
                        hashMap.put((String) dtGrid.getNcColumnsType().get(key).get(i), key);
                    }
                    dtGrid.setNcColumnsTypeList(hashMap);
                }
            }
            // 表格查询参数处理
            QueryUtils.parseDtGridSql(dtGrid);
            // 获取查询条件Sql
            String whereSql = dtGrid.getWhereSql();
            // 获取排序Sql
            String sortSql = dtGrid.getSortSql();

            Map<String, Object> params = new HashMap<>();
            params.put("whereSql", whereSql);
            params.put("sortSql", sortSql);
            long recordCount = _getQueryMapper().countBySql(params);
            int pageSize = dtGrid.getPageSize();
            int pageNum = (int) recordCount / dtGrid.getPageSize() + (recordCount % dtGrid.getPageSize() > 0 ? 1 : 0);

            dtGrid.setPageCount(pageNum);
            dtGrid.setRecordCount(recordCount);

            params.put("nowPage", (dtGrid.getNowPage() - 1) * pageSize);
            params.put("pageSize", pageSize);

            List<PO> list = _getQueryMapper().findListBySql(params);
            dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
            }));
            return dtGrid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DtGridException();
        }
    }

    /**
     * 查询对应参数记录
     */
    default List<PO> select(PO po) {
        if (po.getDelFlag() == null) po.setDelFlag(GenericPo.DELFLAG.NO.code);
        return _getQueryMapper().select(po);
    }

    /**
     * 查询所有
     */
    default List<PO> selectAll() {
        return _getQueryMapper().selectAll();
    }

    /**
     * 根据对应参数查询单条记录
     */
    default PO selectOne(PO po) {
        if (po.getDelFlag() == null) {
            po.setDelFlag(GenericPo.DELFLAG.NO.code);
        } else if (po.getDelFlag() == 2) {
            po.setDelFlag(null);
        }
        return _getQueryMapper().selectOne(po);
    }

    /**
     * 根据对应参数查询数量
     */
    default int selectCount(PO po) {
        return _getQueryMapper().selectCount(po);
    }

    /**
     * 根据主键查询
     */
    default PO selectByPrimaryKey(PK pk) {
        return _getQueryMapper().selectByPrimaryKey(pk);
    }

    /**
     * 正常状态记录
     */
    default PO getNormalModelById(PO po) {
        po.setDelFlag(GenericPo.DELFLAG.NO.code);
        return _getQueryMapper().selectOne(po);
    }

    /**
     * 分页查询普通对象, 使用该方法必须再mapper内重写findListBySqlJoin方法 否则报异常
     */
    default <G> PageBean getPageBeanList(PageBean pageBean, Class<G> g) throws Exception {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("whereSql", pageBean.getWhereSql());
            params.put("sortSql", pageBean.getSortSql());
            long recordCount = _getQueryMapper().countBySqlJoin(params);
            int pageSize = pageBean.getPageSize();
            int pageNum = (int) recordCount / pageBean.getPageSize() + (recordCount % pageBean.getPageSize() > 0 ? 1 : 0);
            pageBean.setTotalPage(pageNum);
            params.put("nowPage", (pageBean.getPageNumber() - 1) * pageSize);
            params.put("pageSize", pageSize);
            pageBean.setTotalNum((int) recordCount);

            List<G> list = (List<G>) _getQueryMapper().findListBySqlJoin(params);
            pageBean.setItems(list);
            return pageBean;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}

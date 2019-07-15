package com.threey.guard.manage.service;

import com.threey.guard.base.dao.CrudDAO;
import com.threey.guard.base.service.CrudService;
import com.threey.guard.base.util.ExcelUtil;
import com.threey.guard.manage.dao.ManagerResidentailDao;
import com.threey.guard.manage.domain.Community;
import com.threey.guard.manage.domain.Residentail;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Service
public class ManagerResidentailService extends CrudService<Residentail> {

    private Logger logger = Logger.getLogger(ManagerResidentailService.class);

    @Autowired
    protected ManagerResidentailDao managerResidentailDao;
    @Override
    protected CrudDAO getDao() {
        return this.managerResidentailDao;
    }

    public List<Community> getCommunity(Community community){
        return this.managerResidentailDao.getAreaList(community);
    }

    /**
     * 查询小区是否有楼栋信息 有则禁止删除
     * @param id
     * @return true
     */
    public boolean checkHasChild(String id){
        return this.managerResidentailDao.checkHasChild(id);
    }

    public List<Residentail> getResidentailList(){
        return this.managerResidentailDao.getResidentailList();
    }
    public List<Residentail> getResidentailListByCommunity(String community){
        return this.managerResidentailDao.getResidentailListByCommunity(community);
    }
    public List<Residentail> getExportList(Residentail obj) {
        return this.managerResidentailDao.getExportList(obj);
    }

    public void export(HttpServletRequest request, List<Residentail> residentails, HttpServletResponse response) {

        //excel标题
        String[] title = {"小区名称","所属社区","物业公司","物业电话","物业负责人","负责人电话"};

        //excel文件名
        String fileName = "小区信息.xls";


        //sheet名
        String sheetName = "小区信息";

        String[][] content=new String[residentails.size()][title.length];
        for (int i = 0; i < residentails.size(); i++) {
            content[i][0] = residentails.get(i).getName();
            content[i][1] = residentails.get(i).getCommunity();
            content[i][2] = residentails.get(i).getWyCompany();
            content[i][3] = residentails.get(i).getWyTelphone();
            content[i][4] = residentails.get(i).getWyPerson();
            content[i][5] = residentails.get(i).getWyPhone();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            ExcelUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

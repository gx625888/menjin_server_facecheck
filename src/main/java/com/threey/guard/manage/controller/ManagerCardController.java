package com.threey.guard.manage.controller;

import com.threey.guard.base.domain.DataTable;
import com.threey.guard.base.domain.ManagerUser;
import com.threey.guard.base.service.LogAuditService;
import com.threey.guard.base.util.Constants;
import com.threey.guard.base.util.StringUtil;
import com.threey.guard.manage.domain.AddCard;
import com.threey.guard.manage.domain.BandCard;
import com.threey.guard.manage.domain.Card;
import com.threey.guard.manage.domain.Residentail;
import com.threey.guard.manage.domain.TreeNode;
import com.threey.guard.manage.service.ManagerCardService;
import com.threey.guard.manage.service.ManagerResidentailService;
import com.threey.guard.manage.service.ResidentailDetailService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

@Controller
@RequestMapping("/card")
public class ManagerCardController {

    private Logger logger = Logger.getLogger(ManagerCardController.class);

    @Autowired
    ManagerCardService managerCardService;
    @Autowired
    ManagerResidentailService managerResidentailService;
    @Autowired
    private ResidentailDetailService residentailDetailService;

    @RequestMapping("/toAddCardList.shtml")
    public String toAddCardList(HttpServletRequest request){
        return "manage/card/addCardIndex";
    }

    @RequestMapping(value = "/queryAddCard.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<AddCard> queryAddCard(DataTable.Req p,HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        String residentail = request.getParameter("p_name");
        AddCard addCard = new AddCard();
        if(loginUser.getManagerType() != 0){
            addCard.setCreateUserCompany(loginUser.getManagerCompany());
            if(loginUser.getManagerType() == 2){
                addCard.setCreateUserProvince(loginUser.getManagerProvince());
            }
            if(loginUser.getManagerType() == 3){
                addCard.setCreateUserCity(loginUser.getManagerCity());
            }
            if(loginUser.getManagerType() == 4){
                addCard.setCreateUserResidentail(loginUser.getManagerResidentail());
            }
        }
        addCard.setResidentail(residentail);
        List<AddCard> addCards = managerCardService.getAddCardList(addCard,p.getPage()-1,p.getLimit());
        int count = this.managerCardService.countAddCard(addCard);
        return new DataTable.Resp<AddCard>(addCards,count,0);
    }

    @RequestMapping("/toInsertAddCard.shtml")
    public String toAdd(HttpServletRequest request){
        request.setAttribute("id",request.getParameter("id"));
        return "manage/card/addCardAdd";
    }

    @RequestMapping(value = "/insertAddCard.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> insertAddCard(HttpServletRequest request){
        Map<String,Object> p_map = new HashMap<>();
        p_map.put("residentail",request.getParameter("id"));
        p_map.put("addNum",request.getParameter("num"));
        this.managerCardService.insetAddCard(p_map);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/toCardList.shtml")
    public String toCardList(HttpServletRequest request){
        return "manage/card/cardIndex";
    }

    @RequestMapping(value = "/page.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Card> page(DataTable.Req p, HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map = new HashMap<String,Object>();
        if(loginUser.getManagerType() != 0){
            map.put("createUserCompany",loginUser.getManagerCompany());
            if(loginUser.getManagerType() == 2){
                map.put("createUserProvince",loginUser.getManagerProvince());
            }
            if(loginUser.getManagerType() == 3){
                map.put("createUserCity",loginUser.getManagerCity());
            }
            if(loginUser.getManagerType() == 4){
                map.put("createUserResidentail",loginUser.getManagerResidentail());
            }
        }
        map.put("cardId",request.getParameter("p_cardId"));
        map.put("residentailName",request.getParameter("p_residentailName"));
        map.put("cardStatus",request.getParameter("p_cardStatus"));
        map.put("band",request.getParameter("p_band"));
        List<Card> cards = managerCardService.list(map,p.getPage()-1,p.getLimit());
        int count = this.managerCardService.count(map);
        return new DataTable.Resp<Card>(cards,count,0);
    }

    @RequestMapping("/toAdd.shtml")
    public String toInsertCard(HttpServletRequest request){
        String id = request.getParameter("id");
        if (id!=null){
            Card card = this.managerCardService.getOne(id);
            request.setAttribute("card",card);
            request.setAttribute("opt","edit");
        }
        return "manage/card/cardAdd";
    }

    @RequestMapping(value = "/saveCard.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveCard(HttpServletRequest request,Card card){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(card.getId())){
            this.managerCardService.update(card);

        }else{
            if(this.managerCardService.cardCount(card.getCardId())){
                map.put("ret",-1);
                map.put("msg","该卡号已存在！");
                return map;
            }
            card.setCreateUser(loginUser.getMid());
            this.managerCardService.add(card);
        }

        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/del.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request){
        String id = request.getParameter("id");
        String cardId = request.getParameter("cardId");
        Map<String,Object> map = new HashMap<>();
        if(this.managerCardService.bandCount(cardId)){
            map.put("ret",-1);
            map.put("msg","该卡已经下发，不能删除！");
            return map;
        }
        this.managerCardService.del(id);

        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/toBandCardList.shtml")
    public String toBandCardList(HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        List<Residentail> residentails = this.managerResidentailService.getResidentailList();
        //request.setAttribute("residentails",residentails);
        List<TreeNode> list = this.managerCardService.treeNodes(loginUser);
		ListIterator li = list.listIterator();
    	while(li.hasNext()){
    		TreeNode li_obj = (TreeNode) li.next();
    		if(li_obj.getType() != 6) {
        		li_obj.setSpread(true);
    		}
    		List<TreeNode> child_list = li_obj.getChildren();
    		ListIterator child_li = child_list.listIterator();
    		while(child_li.hasNext()){
        		TreeNode child_li_obj = (TreeNode) child_li.next();
        		if(child_li_obj.getType() != 6) {
        			child_li_obj.setSpread(true);
        		}
    		}
    	}
        JSONArray jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        request.setAttribute("treeNode",JSONArray.fromObject(list));
        //return "manage/card/bandCardIndex";
        //return "manage/card/bandCardIndexNew";
        return "manage/card/bandCardIndexNew02";
    }

    @RequestMapping("/bandCardMainIndex.shtml")
    public String bandCardMainIndex(HttpServletRequest request){
        request.setAttribute("areaId",request.getParameter("id"));
        request.setAttribute("areaType",request.getParameter("type"));
        return "manage/card/bandCardMainIndex";
    }

    @RequestMapping(value = "/queryBandCard.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<Card> queryBandCard(DataTable.Req p,HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("cardId",request.getParameter("p_cardId"));
        map.put("cardStatus",request.getParameter("p_cardStatus"));
        map.put("personName",request.getParameter("p_personName"));
        List<Card> addCards = managerCardService.getBandCardList(map,p.getPage()-1,p.getLimit());
        int count = this.managerCardService.countBandCard(map);
        return new DataTable.Resp<Card>(addCards,count,0);
    }

    @RequestMapping(value = "/queryBandCardNew.shtml",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DataTable.Resp<BandCard> queryBandCardNew(DataTable.Req p,HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("cardId",request.getParameter("p_cardId"));
        map.put("personName",request.getParameter("p_personName"));
        map.put("residentailId",request.getParameter("p_residentailId"));
        map.put("band",request.getParameter("p_band"));
        map.put("area",request.getParameter("p_areaId"));
        int areaType = Integer.parseInt(request.getParameter("p_areaType"));
        if(areaType==0){
            map.put("province",request.getParameter("p_areaId"));
        }else if(areaType==1){
            map.put("city",request.getParameter("p_areaId"));
        }else if(areaType==2){
            map.put("county",request.getParameter("p_areaId"));
        }else if(areaType==3){
            map.put("community",request.getParameter("p_areaId"));
        }else if(areaType==4){
            map.put("residentail",request.getParameter("p_areaId"));
        }else if(areaType==5){
            map.put("build",request.getParameter("p_areaId"));
        }else {
            map.put("unit",request.getParameter("p_areaId"));
        }
        List<BandCard> addCards = managerCardService.getBandCardList2(map,p.getPage()-1,p.getLimit());
        int count = this.managerCardService.countBandCardNew(map);
        return new DataTable.Resp<BandCard>(addCards,count,0);
    }

    @RequestMapping("/toAddBandCard.shtml")
    public String toAddBandCard(HttpServletRequest request){
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        request.setAttribute("cardId",id);
        request.setAttribute("type",type);
        return "manage/card/addBandCard";
    }

    @RequestMapping("/toAddBandCardNew.shtml")
    public String toAddBandCardNew(HttpServletRequest request){
        String id = request.getParameter("id");
        request.setAttribute("personId",id);
        return "manage/card/addBandCardNew";
    }

    @RequestMapping(value = "/toLoseCard.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> toLoseCard(HttpServletRequest request){
        String id = request.getParameter("id");
        this.managerCardService.toLoseCard(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/TreeNode.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<TreeNode> TreeNode(HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        return this.managerCardService.treeNodes(loginUser);
    }


    @RequestMapping(value = "/addBindInfo.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> addBindInfo(HttpServletRequest request){
        String person = request.getParameter("person");
        String card = request.getParameter("card");
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("personId",person);
        rmap.put("cardId",card);
        this.managerCardService.addBindInfo(rmap);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/toNoBand.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> toNoBand(HttpServletRequest request){
        String id = request.getParameter("id");
        this.managerCardService.toNoBand(id);
        Map<String,Object> map = new HashMap<>();
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping(value = "/baseTxAddBindInfo.shtml",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> baseTxAddBindInfo(HttpServletRequest request){
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        String person = request.getParameter("person");
        String card = request.getParameter("cardId");
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("personId",person);
        rmap.put("cardId",card);
        rmap.put("createUser", loginUser.getMid());
        if(managerCardService.bandCount(card)){
            map.put("ret",-1);
            map.put("msg","该卡号已被绑定！");
            return map;
        }
        if(managerCardService.cardCount(card)){
            map.put("ret",-1);
            map.put("msg","该卡号已存在！");
            return map;
        }
        this.managerCardService.baseTxAddBindInfo(rmap);
        map.put("ret",0);
        map.put("msg","成功");
        return map;
    }

    @RequestMapping("/toQuickBandCard.shtml")
    public String toQuickBandCard(HttpServletRequest request) {
        String id = request.getParameter("cardId");
        String person = request.getParameter("person");
        request.setAttribute("cardId", id);
        request.setAttribute("person", person);
        return "manage/card/toQuickBandCard";
    }

    @RequestMapping("/syncInfo.shtml")
    @ResponseBody
    public Map<String,Object> syncInfo(HttpServletRequest request){
        String deviceId = request.getParameter("deviceId");
        String flag = request.getParameter("flag");
        String device = this.managerCardService.getDeviceId(deviceId);
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isEmpty(device)){
            map.put("ret",-1);
            map.put("msg","同步失败：该单元未绑定设备！");
            return map;
        }
        ManagerUser loginUser = (ManagerUser) request.getSession().getAttribute(Constants.SessionKey.LOGIN_USER);
        int status = this.residentailDetailService.openDoor(device,loginUser.getMid(),flag);
        if (status==1){
            map.put("ret",0);
            map.put("msg","成功");
        }else {
            map.put("ret",-1);
            map.put("msg","操作失败");
        }
        return map;
    }
}

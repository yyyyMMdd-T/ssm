package com.qf.controller;

import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.util.PageInfo;
import com.qf.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qf.constant.Ssmconstant.REDIRECT;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    //商品的首页
    @GetMapping("/list")
    public String list(String name,
                       @RequestParam(value ="page",defaultValue="1")Integer page,
                       @RequestParam(value = "size",defaultValue = "5")Integer size,
                       Model model){
        //调用service查询数据
        PageInfo<Item> pageInfo=itemService.findItemAndLimit(name,page,size);
        //controller讲pageinfo放到request渝中   将接受到的商品名称放到request中
        model.addAttribute("pageInfo", pageInfo);

        model.addAttribute("name", name);



        return "item/item_list";
    }


    //跳转到添加商品页面
    @GetMapping("/add-ui")
    public String addUI(){


        return "item/item_add";
    }


    @Value("${pic_types}")
    public String picType;
    //添加商品信息
    @PostMapping("/add")
    public String add(Item item, BindingResult bindingResult,MultipartFile picFile, HttpServletRequest request) throws IOException {

       //校验参数
    if (bindingResult.hasErrors()){
        //获得具体信息
        String msg=bindingResult.getFieldError().getDefaultMessage();
        return null;
    }


        //对图片大小做校验  要求图片小于5m
        long size=picFile.getSize();
        if (size>5242880L){

            return null;
        }
        boolean flag=false;
//        对图片类型做校验  jpg  png  gif
        String[] types=picType.split(",");
        for (String type : types) {
            if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(), type)){
                //图片类型正确
                flag=true;
               break;
            }
        }
        if (!flag){
    //图片类型
            return null;
        }
    //图片是否损坏
        BufferedImage image= ImageIO.read(picFile.getInputStream());
    if (image==null){
        //图片损坏
        return null;
    }
    //将图片保存到本地
        //给图片器一个新名字
        String prefix=UUID.randomUUID().toString();
        String suffix=StringUtils.substringAfterLast(picFile.getOriginalFilename(), ".");
        String newName=prefix+"."+suffix;
             System.out.println(newName);

        //爱你跟图片保存到本地
        String path=request.getServletContext().getRealPath("/static/img/"+newName)  ;
        System.out.println(path);
        File file=new File(path);

        // 健壮性判断.
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        picFile.transferTo(file);

        //3. 封装图片的访问路径.
        String pic = "http://localhost/static/img/" + newName;
        System.out.println(pic);
        item.setPic(pic);

        //调用service保存商品信息
        Integer count=itemService.save(item);

        //判断count
        if (count == 1){
            return REDIRECT + "/item/list";
        }else {
            //添加商品信息失败
            return null;
        }
    }
        //根据id  删除商品信息
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVo del(@PathVariable long id){
        //调用service 删除
        Integer count=itemService.del(id);
        //根据结果给页面相应
        if (count==1){
            //删除成功
            return new ResultVo(0, "成功", null);
        }else {
            return new ResultVo(2, "删除失败", null);
        }
    }
}

package com.yf.controller;/*
 *
 *
 */

import com.yf.pojo.Item;
import com.yf.service.ItemService;
import com.yf.util.PageInfo;
import com.yf.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.yf.constant.SsmConstant.*;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //商品首页
    @GetMapping("/list")
    public String list(String name,
                       @RequestParam(value = "page",defaultValue = "1") Integer page,
                       @RequestParam(value = "size",defaultValue = "5") Integer size,
                       Model model){
        PageInfo<Item> pageInfo = itemService.findItemAndLimit(name,page,size);

        int i= 1/0;

        //把pageInfo放入request域中
        model.addAttribute("pageInfo",pageInfo);
        //name作用：回显
        model.addAttribute("name",name);
        //转发页面
        return "item/item_list";
    }



    @GetMapping("/add-ui")
    public String addUI(){
        return "/item/item_add";
    }


    @Value("${pic_types}")
    public String picType;

    //用MultipartFile接收文件上传项
    @PostMapping("/add")
    public String add(@Valid Item item, BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      MultipartFile picFile, HttpServletRequest request) throws IOException {

        //------------------------校验参数------------------------
        if (bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().getDefaultMessage();
            redirectAttributes.addAttribute("addInfo", msg);
            return REDIRECT + ITEM_ADD_UI;
        }

        //----------------------图片上传--------------------
        //对图片的大小校验， 要求图片小于5M   5242880字节
        Long size = picFile.getSize();
        if (size > 5242880L){
            //图片过大、
            redirectAttributes.addAttribute("addInfo","图片过大，要求小于5M" );
            return REDIRECT+ITEM_ADD_UI;
        }
        String aa = picFile.getOriginalFilename();
        System.out.println(aa);
        //立标志
        boolean flag = false;
        //对图片的类型做校验
        String[] types = picType.split(",");
        for (String type : types){
            if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(),type )){
                //图片类型正确
                flag = true;
                break;
            }
        }
        if (!flag){
            //图片类型不正确
            redirectAttributes.addAttribute("addInfo", "图片类型不正确,要求"+picType);
            return REDIRECT+ITEM_ADD_UI;
        }
        //3、校验图片是否损坏
        BufferedImage image = ImageIO.read(picFile.getInputStream());
        if (image == null){
            //图片已经损坏
            redirectAttributes.addAttribute("addInfo","图片已经损坏" );
            return REDIRECT+ITEM_ADD_UI;
        }
        //--------------将图片保存到本地-------------------------------------
        //给图片起一个新名字
        String prefix = UUID.randomUUID().toString();
        String suffix = StringUtils.substringAfterLast(picFile.getOriginalFilename(),"." );
        String newName = prefix + "." +suffix;
        //将图片保存到本地
        String path = request.getServletContext().getRealPath("") + "\\static\\img\\" +newName;
        File file = new File(path);
        //健壮性判断
        if (file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        picFile.transferTo(file);
        //封装图片的访问路径
        String pic  = "http://localhost/static/img/"+newName;

        //----------------保存信息-------------------
        item.setPic(pic);
        //调用service保存商品信息
        Integer count = itemService.save(item);
        //判断count
        if (count == 1){
            return REDIRECT+ "/item/list";
        }else{
            //添加产品信息失败
            redirectAttributes.addAttribute("addInfo","添加商品信息失败！" );
            return REDIRECT + ITEM_ADD_UI;
        }
    }


    //根据id删除商品信息
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVO del(@PathVariable(value = "id") Long id){
        //1、调用service删除商品
        Integer count = itemService.del(id);
        //2、根据结果给页面响应
        if (count == 1){
            //删除成功
            return new ResultVO(0,"成功",null);
        }else {
            return new ResultVO(1,"删除商品失败",null);
        }
    }



    //===========================修改商品信息=============================================

//    HashMap
    //跳转修改页面
    @GetMapping("/update-ui/{id}")
    public String updateUI(Model model,
                           @PathVariable Long id){
        //1、查找需改修改的商品信息
        Item item = itemService.up(id);
        //2、根据结果响应给页面数据
        if (item != null){
            model.addAttribute("item",item );
            return "/item/item_update";
        }else {
            model.addAttribute("addInfo","跳转修改页面失败！" );
            return REDIRECT + "/item/item_list";
        }
    }


    @PostMapping("/update")
    public String update(@Valid Item item, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         MultipartFile picFile, HttpServletRequest request) throws IOException {
        //上传数据
        Long id = item.getId();
        //------------------------校验参数------------------------
        if (bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().getDefaultMessage();
            redirectAttributes.addAttribute("addInfo", msg);
            return REDIRECT + "/item/update-ui/"+item.getId();
        }
        //----------------------图片上传--------------------
        //对图片的大小校验， 要求图片小于5M   5242880字节
        Long size = picFile.getSize();
        if (size > 5242880L){
            //图片过大、
            redirectAttributes.addAttribute("addInfo","图片过大，要求小于5M" );
            return REDIRECT + ITEM_UPDATE+"/"+item.getId();
        }
        String aa = picFile.getOriginalFilename();
        System.out.println(aa);
        //立标志
        boolean flag = false;
        //对图片的类型做校验
        String[] types = picType.split(",");
        for (String type : types){
            if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(),type )){
                //图片类型正确
                flag = true;
                break;
            }
        }
        if (!flag){
            //图片类型不正确
            redirectAttributes.addAttribute("addInfo", "图片类型不正确,要求"+picType);
            return REDIRECT + ITEM_UPDATE+"/"+item.getId();
        }
        //3、校验图片是否损坏
        BufferedImage image = ImageIO.read(picFile.getInputStream());
        if (image == null){
            //图片已经损坏
            redirectAttributes.addAttribute("addInfo","图片已经损坏" );
            return REDIRECT + ITEM_UPDATE+"/"+item.getId();
        }
        //--------------将图片保存到本地-------------------------------------
        //给图片起一个新名字
        String prefix = UUID.randomUUID().toString();
        String suffix = StringUtils.substringAfterLast(picFile.getOriginalFilename(),"." );
        String newName = prefix + "." +suffix;
        //将图片保存到本地
        String path = request.getServletContext().getRealPath("") + "\\static\\img\\" +newName;
        File file = new File(path);
        //健壮性判断
        if (file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        picFile.transferTo(file);
        //封装图片的访问路径
        String pic  = "http://localhost/static/img/"+newName;
        //----------------保存信息-------------------
        item.setPic(pic);
        //调用service保存商品信息
        Integer count = itemService.update(item);
        //判断count
        if (count == 1){
            return REDIRECT+ "/item/list";
        }else{
            //添加产品信息失败
            redirectAttributes.addAttribute("addInfo","添加商品信息失败！" );
            return REDIRECT + ITEM_UPDATE+"/"+item.getId();
        }
    }
}






























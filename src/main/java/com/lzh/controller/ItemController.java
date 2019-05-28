package com.lzh.controller;

import com.lzh.bean.Item;
import com.lzh.constant.SSMConstant;
import com.lzh.service.ItemService;
import com.lzh.util.PageInfo;
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

/**
 * @author lizhenhao
 */


@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //图片类型
    @Value("${picTypes}")
    private String picTypes;

    /*
     * 商品展示
     * */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "5") Integer size,
                       String name,
                       Model model
    ) {

        //通过条件查询获取分页信息PageInfo
        PageInfo<Item> pageInfo = itemService.findItemByCondition(name, page, size);
        //将分页信息方如request域中
        model.addAttribute(SSMConstant.PAGE_INFO, pageInfo);

        //为了回显查询的商品名
        model.addAttribute(SSMConstant.ITEM_LIKE_NAME, name);

        return SSMConstant.ITEM_LIST_PAGE;
    }


    /*
     * 添加商品
     * */
    @RequestMapping("/add")
    public String add(@Valid Item item,
                      BindingResult bindingResult,
                      HttpServletRequest request,
                      Model model

    ) throws IOException {

        if (bindingResult.hasErrors()) {
            return SSMConstant.ITEM_ADD_PAGE;
        }


        MultipartFile picFile = item.getPicFile();

        //======================调用图片上传================================
        UploadPic uploadPic = new UploadPic(request, model, picFile).invoke();
        if (uploadPic.is()) {//失败
            return SSMConstant.ITEM_ADD_PAGE;
        }
        String pic = uploadPic.getPic();

        //================================================================

        item.setPic(pic);

        boolean b = itemService.addItem(item);

        if (b) {//商品添加成功

            return SSMConstant.REDIRECT + SSMConstant.ITEM_LIST;

        }
        model.addAttribute("addInfo", "商品添加失败!!!");

        return SSMConstant.ITEM_ADD_PAGE;
    }

    /*
        RESTful风格进行删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody//返回给ajax
    public Integer delete(@PathVariable Long id) {

        return itemService.deleteItemById(id);
    }

    /*
        RESTful风格进行GET请求
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String toUpdatePage(@PathVariable Long id, Model model) {

        Item item = itemService.findItemById(id);

        model.addAttribute("item", item);

        return SSMConstant.ITEM_UPDATE_PAGE;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Item item,
                         HttpServletRequest request,
                         Model model,
                         RedirectAttributes redirectAttributes
    ) throws IOException {
        //1.判断修改时是否出上传了图片
        MultipartFile picFile = item.getPicFile();

        if (picFile != null && picFile.getSize()>0) {
            UploadPic uploadPic = new UploadPic(request, model, picFile).invoke();
            if (uploadPic.is()) {//失败
                return SSMConstant.ITEM_UPDATE_PAGE;
            }
            //获取图片的访问路径
            String pic = uploadPic.getPic();
            item.setPic(pic);
        }
        //调用service
        boolean b = itemService.updateItemById(item);

        if (b) {//修改成功(转发到展示页面)
            return SSMConstant.REDIRECT + SSMConstant.ITEM_LIST;
        }

        //修改失败
        /*
        重定向传参:(重定向请求本身就是GET请求)
        方式一:重定向直接在路径传参
        方式二:通过RedirectAttributes向重定向传参
         */
        //重定向直接在路径传参
        //return SSMConstant.REDIRECT + SSMConstant.ITEM_UPDATE + item.getId();
        //通过RedirectAttributes向重定向传参
        redirectAttributes.addAttribute("id",item.getId());
        return SSMConstant.REDIRECT + SSMConstant.ITEM_UPDATE;
    }


    //封装一个图片上传的类
    private class UploadPic {
        private boolean myResult;
        private HttpServletRequest request;
        private Model model;
        private MultipartFile picFile;
        private String pic;

        public UploadPic(HttpServletRequest request, Model model, MultipartFile picFile) {
            this.request = request;
            this.model = model;
            this.picFile = picFile;
        }

        boolean is() {
            return myResult;
        }

        public String getPic() {
            return pic;
        }

        public UploadPic invoke() throws IOException {
            //定义一个标识
            boolean flag = false;

            String[] types = picTypes.split(",");

            //1.获取图片的原名
            String fileName = picFile.getOriginalFilename();

            for (String type : types) {
                if (StringUtils.endsWithIgnoreCase(fileName, type)) {
                    //文件类型正确
                    flag = true;
                    break;
                }
            }
            //图片类型验证
            if (!flag) {
                //图片类型错误
                model.addAttribute("picInfo", "图片类型错误!!!");
                myResult = true;
                return this;
            }

            //判断图片是否损坏
            BufferedImage bufferedImage = ImageIO.read(picFile.getInputStream());
            if (bufferedImage == null) {

                System.out.println("图片损坏!!!");
                model.addAttribute("picInfo", "图片损坏!!!");
                myResult = true;
                return this;
            }

            //1.2使用lang3下的StringUtils工具类
            String typeName = StringUtils.substringAfterLast(fileName, ".").toLowerCase();
            //1.3通过UUID取一个新名字前缀
            String prefixName = UUID.randomUUID().toString();
            //1.4拼接文件新名字
            String newsName = prefixName + "." + typeName;

            //2.将图片保存到本地(项目的绝对路径)
            File file = new File(request.getServletContext().getRealPath("/static/images/" + newsName));

            picFile.transferTo(file);

            //3.封装一个访问路径
            pic = "http://localhost/static/images/" + newsName;
            myResult = false;
            return this;
        }
    }

}

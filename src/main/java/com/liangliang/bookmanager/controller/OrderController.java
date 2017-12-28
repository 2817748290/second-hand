package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.*;
import com.liangliang.bookmanager.service.OrderService;
import com.liangliang.bookmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取借阅记录列表
     * @return
     */
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    @ResponseBody
    public Message getOrderList(){

        List<Order> orderList = new ArrayList<>();

        try {
            orderList = orderService.getOrderList();
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取借阅记录列表失败！",null);
        }
        return new Message(Message.SUCCESS,"获取借阅记录列表成功！",orderList);
    }

    /**
     * 根据id查询借阅记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOrderById", method = RequestMethod.POST)
    @ResponseBody
    public Message getOrderById(@RequestParam("orderId") Integer id){
        Order order = new Order();
        try {
            order = orderService.getOrderById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取借阅记录失败！", null);
        }
        return new Message(Message.SUCCESS,"获取借阅记录成功！",order);
    }

    /**
     * 根据
     * @param tableMessage
     * @return
     */
    @RequestMapping(value = "/getSearchOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map getSearchOrder(@RequestBody TableMessageForOrder tableMessage) throws Exception{
        System.out.println(tableMessage);
        return orderService.searchOrder(tableMessage).result();
    }
    /**
     * 新增借阅记录
     * @param order
     * @return
     */
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public Message addOrder(HttpServletRequest request, @RequestBody Order order){
        try {
            if (request.getSession().getAttribute("userId")==null){
                return new Message(Message.ERROR,"用户未登录！", null);
            }
            int userId = (int)request.getSession().getAttribute("userId");

            //借书时间最多为60天
            order.setCreateDate(new Date());
            order.setBorrowerId(userId);
            Date upDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(upDate);
            calendar.add(Calendar.DATE, 60);//+1今天的时间加一天
            upDate = calendar.getTime();

            order.setCreateDate(new Date());
            order.setUpdateDate(upDate);

            //预约时间15分钟
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, 15);
            Date readyTime = nowTime.getTime();

            order.setReadyDate(readyTime);
            if(orderService.addOrder(order)){
                return new Message(Message.SUCCESS,"新增借阅记录成功！", order);
            }else{
                return new Message(Message.ERROR,"新增借阅记录失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"新增借阅记录失败！",null);
        }
    }

    /**
     * 修改借阅记录
     * @param order
     * @return
     */
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    @ResponseBody
    public Message updateOrder(@RequestBody Order order){
        try {
            if(orderService.updateOrder(order)){
                return new Message(Message.SUCCESS,"修改借阅记录成功！",null);
            }else{
                return new Message(Message.ERROR,"修改借阅记录失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改借阅记录失败！",null);
        }
    }
    /**
     * 删除借阅记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteOrder(@RequestParam(value = "orderId") Integer id){
        try {
            if(orderService.deleteOrder(id)){
                return new Message(Message.SUCCESS,"删除借阅记录成功！",null);
            }else{
                return new Message(Message.ERROR,"删除借阅记录失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"删除借阅记录失败！",null);
        }
    }

}

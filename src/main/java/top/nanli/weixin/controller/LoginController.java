package top.nanli.weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.nanli.weixin.service.WebChatService;
import top.nanli.weixin.util.CheckUtil;
import top.nanli.weixin.util.MessageType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Description: 与微信对接登陆验证
 * @Auther: LiNan
 * @Date: 2018/12/30 21:01
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/webchat",method= RequestMethod.GET)
    public void loginGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println("getMethodSuccess");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(CheckUtil.checkSignature(signature, timestamp, nonce)){
                out.write(echostr);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            out.close();
        }

    }

    @RequestMapping(value = "/webchat",method= RequestMethod.POST)
    public void loginPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println("PostMethodSuccess");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (!CheckUtil.checkSignature(signature, timestamp, nonce)) {
                //消息不可靠，直接返回
                response.getWriter().write("");
                return;
            }else {
//                if(echostr==null){
//                    System.out.println("接受消息为空");
//                    echostr = "";
//                }else{
//                    System.out.println("接受消息为"+echostr);
//                }
//                if(out==null){
//                    System.out.println("out为空");
//                }
//                System.out.println("echostr:"+echostr);
//                out.write(echostr);
                System.out.println("开始返回文本消息......");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/xml");
                //调用parseXml方法解析请求消息
                Map<String, String> map = MessageType.parseXml(request, response);
                System.out.println("解析微信端xml成功......");
                String MsgType = map.get("MsgType");
                String xml = null;//处理输入消息，返回结果的xml
                if (MessageType.REQ_MESSAGE_TYPE_EVENT.equals(MsgType)) {
                    System.out.println("事件......");
                    xml = WebChatService.parseEvent(map);
                } else {
                    System.out.println("消息......");
                    xml = WebChatService.parseMessage(map);
                    System.out.println("返回消息内容及格式:"+xml);
                }
                //返回封装的xml
                //System.out.println(xml);
                response.getWriter().write(xml);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            out.close();
        }

    }
}

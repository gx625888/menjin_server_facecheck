package com.threey.guard.clientmsg;

import com.threey.guard.clientmsg.domain.MsgType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;

/**
 * nettyClient
 */
public class NettyClient extends Thread{

    private String registerkey ="";
    private String ip;
    private int port;
    private String channelId = "";

    public NettyClient(String ip ,int port){
        registerkey = ip+":"+port;
        this.ip = ip ;
        this.port =port;
    }

    public void run(){
        //worker负责读写数据
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //辅助启动类
            Bootstrap bootstrap = new Bootstrap();

            //设置线程池
            bootstrap.group(worker);

            //设置socket工厂
            bootstrap.channel(NioSocketChannel.class);

            //设置管道
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //获取管道
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new LineBasedFrameDecoder(2*1024));
                    pipeline.addLast(new StringDecoder());
                    //字符串编码器
                    pipeline.addLast(new LineEncoder());
                    pipeline.addLast(new StringEncoder());
                    //处理类
                    pipeline.addLast(new ClientHandler4(registerkey));
                }
            });

            //发起异步连接操作
            ChannelFuture futrue = bootstrap.connect(new InetSocketAddress(ip,port)).sync();

            channelId = futrue.channel().id().asLongText();
            //等待客户端链路关闭
            futrue.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            MsgRouteFact.instance.getInstance().unRegisterSlave(registerkey,channelId);
            MsgRouteFact.instance.getInstance().saveDisconectedSlave(registerkey);
            //优雅的退出，释放NIO线程组
            worker.shutdownGracefully();
        }
    }
}

class ClientHandler4 extends SimpleChannelInboundHandler<String> {
    private String registerkey ="";

    public ClientHandler4(String key){
        this.registerkey = key;

    }    //接受服务端发来的消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("server response ： "+msg);

        String[] msgArr = msg.split(",");
        if (msgArr.length>=2){
            MsgUtil.dispatchMsg(msgArr,ctx);
        }


    }

    //与服务器建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //给服务器发消息
        ctx.channel().writeAndFlush(MsgType.SERVER_MSG_TAG+"i am menjinserver ,give me live clients\n");
        MsgRouteFact.instance.getInstance().registerSlave(ctx,registerkey);
        System.out.println("channelActive");
    }

    //与服务器断开连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive--->"+registerkey);
        MsgRouteFact.instance.getInstance().unRegisterSlave(registerkey,ctx.channel().id().asLongText());
        MsgRouteFact.instance.getInstance().saveDisconectedSlave(registerkey);

        System.out.println("channelInactive");
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught--->"+registerkey);
        MsgRouteFact.instance.getInstance().unRegisterSlave(registerkey,ctx.channel().id().asLongText());
        MsgRouteFact.instance.getInstance().saveDisconectedSlave(registerkey);
        //关闭管道
        ctx.channel().close();
        //打印异常信息
        cause.printStackTrace();
    }

}

package com.tools.android.a25011.iodemo.nio;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by 25011 on 2018/5/23.
 */
public class NioClient {
    private Selector selector;
    public NioClient(int port) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost",port));
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listion() throws IOException {
        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                if(next.isConnectable()){
                    SocketChannel socketChannel1 = (SocketChannel) next.channel();
                    if (socketChannel1.isConnectionPending()) {
                        socketChannel1.finishConnect();
                    }
                    socketChannel1.configureBlocking(false);
                    socketChannel1.write(ByteBuffer.wrap(new String("你好服务").getBytes()));
                    socketChannel1.register(selector,SelectionKey.OP_READ);
                }else if(next.isReadable()){
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                    channel.read(byteBuffer);
                    String msg = new String(byteBuffer.array());
                    Log.i("客户端：--->", msg);
                }
            }
        }
    }
}

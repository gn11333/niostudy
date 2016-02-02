package nio.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoNIO {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(9999));
        serverChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        Set<SelectionKey> prev = null;
        while (true) {
            selector.select();
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            if(prev == null){
                prev = readyKeys;
            }else{
                System.out.println(prev == readyKeys);
            }
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            System.out.println(readyKeys.hashCode());
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ,
                                ByteBuffer.allocate(100));
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);
                        output.compact();
                    }
                }
                catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    }
                    catch (IOException cex) {
                    }
                }
            }
        }
    }
}

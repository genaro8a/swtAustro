package ec.fin.baustro.isoutils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.NACChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.NameRegistrar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
@ApplicationScoped
public class Server {
    private final Thread hilo;
    private final ISOServer server;
    private final GenericPackager packager;
    private String dirDefiniciones="cfg/";
    private final NACChannel canal;
    private  int port=8000;
    private  String definicion="pos.xml";

    public Server() {
        try {
            byte[] tpduCliente = ISOUtil.hex2byte("6080000001");
            packager = new GenericPackager(dirDefiniciones + definicion);
            canal = new NACChannel(packager, tpduCliente);
//            canal.setServerSocket(new ServerSocket(port));
            server = new ISOServer(port,canal,null);
            server.addISORequestListener(new Procesador());
            Timer timer1 = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });
            NameRegistrar.register("timer1", timer1);
            timer1.setRepeats(true);
            timer1.start();
            hilo = new Thread(this.server);
            this.hilo.setName("IsoServer");
            this.hilo.start();
            log.debug("inicio de servidor port: {} definicion {}",port,definicion);
        } catch (ISOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public Server(int port, String definicion){
        this.port=port;
        this.definicion=definicion;
        byte[] tpduCliente = ISOUtil.hex2byte("6080000001");
        try {
            packager = new GenericPackager(dirDefiniciones + definicion);
            canal = new NACChannel(packager, tpduCliente);
//            canal.setServerSocket(new ServerSocket(port));
            server = new ISOServer(port,canal,null);
            server.addISORequestListener(new Procesador());
            Timer timer1 = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });
            NameRegistrar.register("timer1", timer1);
            timer1.setRepeats(true);
            timer1.start();
            hilo = new Thread(this.server);
            this.hilo.setName("IsoServer");
            this.hilo.start();
            log.debug("inicio de servidor port: {} definicion {}",port,definicion);
        } catch (ISOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] arg) {
    }
}
